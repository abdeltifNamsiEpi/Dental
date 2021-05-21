package com.example.bus.dental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    Button sign_up;
    EditText emailEdittext,passwordEdittext,nameEdittext,phoneEditText;

    private FirebaseAuth mAuth;
    final String TAG="SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();


        passwordEdittext=(EditText)findViewById(R.id.signUp_password);
        emailEdittext=(EditText)findViewById(R.id.signUp_email);
        phoneEditText=(EditText)findViewById(R.id.signUp_phone);
        nameEdittext=(EditText)findViewById(R.id.signUp_name);
        sign_up=(Button)findViewById(R.id.sign_up);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                signUp();
            }
        });
    }


    private void signUp() {

        String email=emailEdittext.getText().toString();
        String password=passwordEdittext.getText().toString();
        String name=nameEdittext.getText().toString();
        String phone=phoneEditText.getText().toString();

        if (name.isEmpty()){
            emailEdittext.setError("Name is required");
            emailEdittext.requestFocus();
            return;
        }
        if (phone.isEmpty()){
            emailEdittext.setError("Phone is required");
            emailEdittext.requestFocus();
            return;
        }
        if(phone.length()<8){
            passwordEdittext.setError("Phone should at least have 6 numbers");
            passwordEdittext.requestFocus();
            return;
        }
        if (email.isEmpty()){
            emailEdittext.setError("Email is required");
            emailEdittext.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEdittext.setError("Please provide valid Email");
            emailEdittext.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passwordEdittext.setError("Password is required");
            passwordEdittext.requestFocus();
            return;
        }
        if(password.length()<6){
            passwordEdittext.setError("Password should at least have 6 characters");
            passwordEdittext.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user=new User(name,phone,email,password);

                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignUpActivity.this,"User has been signed up Successfully!!! "+user.getEmail(),Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(SignUpActivity.this,Home.class);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(SignUpActivity.this,"Failed to Sign Up!!! Try again!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(SignUpActivity.this,"Failed to Sign Up!!! ",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}