package com.example.baocaothuchanh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText mFullName,mEmail, mPassword,mPassword2, mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFullName = findViewById(R.id.nameRegister);
        mEmail=findViewById(R.id.mailRegister);
        mPassword=findViewById(R.id.passwordRegister);
        mPassword2=findViewById(R.id.passwordRegister2);
        mRegisterBtn=(Button)findViewById(R.id.registerBtn);
        mLoginBtn=findViewById(R.id.signInText);
        fAuth = FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);
//        if(fAuth.getCurrentUser()!=null){
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            finish();
//
//        }
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xu ly nghiep vu
                String name = mFullName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String password2 = mPassword2.getText().toString().trim();
                if(password.contains(password2)==false){
                    mPassword2.setError("Password and ConfirmPassword must same");
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    mFullName.setError("Full Name is Required");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required");
                    return;
                }
                if(password.length()<6){
                    mPassword.setError("Password must be >= 6 characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                // FireBase Auth
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"User Created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(Register.this,"Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }
}