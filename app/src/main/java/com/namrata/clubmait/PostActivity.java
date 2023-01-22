package com.namrata.clubmait;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.namrata.clubmait.Fragments.HomeFragment;
import com.namrata.clubmait.Fragments.ProfileFragment;
import com.namrata.clubmait.Fragments.SearchFragment;

import java.util.HashMap;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class PostActivity extends AppCompatActivity {
    private ImageView close;
    private ImageView imageAdded;
    private TextView post;
    private final int GALLERY_REQ_CODE = 1000;
    TextView description;
    private String imageUrl;
     Uri imageUri;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        close = findViewById(R.id.close);
        imageAdded = findViewById(R.id.image_added);
        post = findViewById(R.id.post);
        description = findViewById(R.id.description);
        add = findViewById(R.id.add);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostActivity.this , FirstActivity.class));
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent iGallery = new Intent(Intent.ACTION_PICK);
              iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
              startActivityForResult(iGallery,GALLERY_REQ_CODE);

            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });

    }
    private void upload() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        if (imageUri != null){
            final StorageReference filePath = FirebaseStorage.getInstance().getReference("Posts").child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

            StorageTask uploadtask = filePath.putFile(imageUri);
            uploadtask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }

                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Uri downloadUri = task.getResult();
                    imageUrl = downloadUri.toString();

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
                    String postId = ref.push().getKey();

                    HashMap<String , Object> map = new HashMap<>();
                    map.put("postid" , postId);
                    map.put("imageurl" , imageUrl);
                    map.put("description" , description.getText().toString());
                    map.put("publisher" , FirebaseAuth.getInstance().getCurrentUser().getUid());

                    ref.child(postId).setValue(map);

                    DatabaseReference mHashTagRef = FirebaseDatabase.getInstance().getReference().child("HashTags");
//                    List<String> hashTags = description.getHashtags();
//                    if (!hashTags.isEmpty()){
//                        for (String tag : hashTags){
//                            map.clear();
//
//                            map.put("tag" , tag.toLowerCase());
//                            map.put("postid" , postId);
//
//                            mHashTagRef.child(tag.toLowerCase()).child(postId).setValue(map);
//                        }
//                    }

                    pd.dismiss();
                    startActivity(new Intent(PostActivity.this , MainActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "No image was selected!", Toast.LENGTH_SHORT).show();
        }

    }

    private String getFileExtension(Uri uri) {

        return MimeTypeMap.getSingleton().getExtensionFromMimeType(this.getContentResolver().getType(uri));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == GALLERY_REQ_CODE){
                imageUri = data.getData();
                imageAdded.setImageURI(imageUri);
            }
        }
    }
}