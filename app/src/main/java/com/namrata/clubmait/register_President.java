package com.namrata.clubmait;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class register_President extends AppCompatActivity {
    private EditText SocietyName;
    private EditText mail;
    private EditText password;
    private Button registerP;

    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_president);
        SocietyName = findViewById(R.id.societyName);
        mail = findViewById(R.id.mailId);
        password = findViewById(R.id.KeyP);
        registerP = findViewById(R.id.RegisterP);
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        registerP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtSocietyName = SocietyName.getText().toString();
                String txtmail = mail.getText().toString();
                String txtPassword = password.getText().toString();

                if(TextUtils.isEmpty(txtSocietyName) || TextUtils.isEmpty(txtmail) || TextUtils.isEmpty(txtPassword)){
                    Toast.makeText(register_President.this,"Empty Credentials",Toast.LENGTH_SHORT).show();
                }else if(txtPassword.length() < 6){
                    Toast.makeText(register_President.this,"Password to short",Toast.LENGTH_SHORT).show();
                }else{
                    registerUserP(txtSocietyName,txtmail,txtPassword);
                }
            }
        });
   }
    private void registerUserP(final String SocietyName,final String mail, final String Password) {

        mAuth.createUserWithEmailAndPassword(mail,Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                HashMap<String,Object> map = new HashMap<>();

                map.put("Name",SocietyName);
                map.put("Email",mail);
                map.put("id",mAuth.getCurrentUser().getUid());
                map.put("bio","");
                map.put("imageUrl","default");

                mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //  pd.dismiss();
                        if(task.isSuccessful()){
                            Toast.makeText(register_President.this,"Profile created",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(register_President.this,FirstActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // pd.dismiss();
                Toast.makeText(register_President.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}