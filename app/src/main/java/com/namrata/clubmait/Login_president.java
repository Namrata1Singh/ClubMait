package com.namrata.clubmait;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Login_president extends AppCompatActivity {
    private EditText SocietyId;
    private EditText key;
    private Button login_btnP;
    private Button Register_btnP ;
   private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_president);
        SocietyId = findViewById(R.id.SocietyId);
        key = findViewById(R.id.Key);
        login_btnP = findViewById(R.id.Login_btnP);
        Register_btnP = findViewById(R.id.Register_btnP);
        mAuth = FirebaseAuth.getInstance();
        login_btnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = SocietyId.getText().toString();
                String txt_password = key.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(Login_president.this, "empty credentials", Toast.LENGTH_SHORT).show();
                } else {
                    loginP(txt_email, txt_password);
                }
            }
        });
    }
    private void loginP(String email,String password){
        mAuth.signInWithEmailAndPassword( email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login_president.this,"Profile created",Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(Login_student.this,MainActivity2.class);
//                    startActivity(intent);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login_president.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}