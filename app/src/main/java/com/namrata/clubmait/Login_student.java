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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_student extends AppCompatActivity {
    private EditText UserId;
    private EditText password_1;
    private Button login_btn1;
    private Button Register_btn1 ;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);
        UserId = findViewById(R.id.UserId);
        password_1 = findViewById(R.id.Password_1);
        login_btn1 = findViewById(R.id.Login_btn1);
        Register_btn1 = findViewById(R.id.Register_btn1);
        mAuth = FirebaseAuth.getInstance();
        login_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = UserId.getText().toString();
                String txt_password = password_1.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(Login_student.this, "empty credentials", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(txt_email, txt_password);
                }
            }
        });
    }
    private void loginUser(String email,String password){
        mAuth.signInWithEmailAndPassword( email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login_student.this,"Profile created",Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(Login_student.this,FirstActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login_student.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}