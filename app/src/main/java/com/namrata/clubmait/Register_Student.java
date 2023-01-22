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

public class Register_Student extends AppCompatActivity {
    private EditText Department;
    private EditText name;
    private EditText email;
    private EditText password;
    private Button register;

    private DatabaseReference mRootRef;
   private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);
        name = findViewById(R.id.Name);
        Department = findViewById(R.id.DepartmentName);
        email = findViewById(R.id.mail);
        password = findViewById(R.id.Password);
        register = findViewById(R.id.Register_btn);
        mRootRef = FirebaseDatabase.getInstance().getReference();
       mAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              String txtDepartment = Department.getText().toString();
               String txtName = name.getText().toString();
               String txtEmail = email.getText().toString();
                String txtPassword = password.getText().toString();
              if(TextUtils.isEmpty(txtDepartment) || TextUtils.isEmpty(txtName) || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)){
                   Toast.makeText(Register_Student.this,"Empty Credentials",Toast.LENGTH_SHORT).show();               }else if(txtPassword.length() < 6){
                  Toast.makeText(Register_Student.this,"Password to short",Toast.LENGTH_SHORT).show();
               }else{
                   registerUser(txtDepartment,txtName,txtEmail,txtPassword);
               }
            }
        });
    }
   private void registerUser(final String Department, final String Name, final String Email, final String Password) {

       mAuth.createUserWithEmailAndPassword(Email, Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
           @Override public void onSuccess(AuthResult authResult) {
               HashMap<String, Object> map = new HashMap<>();

               map.put("Name", Name);
               map.put("Email", Email);
               map.put("Department name", Department);
               map.put("id", mAuth.getCurrentUser().getUid());
               map.put("imageUrl","default");
                mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                           Toast.makeText(Register_Student.this, "Profile created", Toast.LENGTH_SHORT).show();
                          Intent intent = new Intent(Register_Student.this, FirstActivity.class);
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
                Toast.makeText(Register_Student.this, "Not generated", Toast.LENGTH_SHORT).show();
          }
       });
    }

}