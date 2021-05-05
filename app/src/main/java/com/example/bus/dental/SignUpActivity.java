package com.example.bus.dental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class SignUpActivity extends AppCompatActivity {
    Button sign_up;
    EditText emailEdittext;
    EditText passwordEdittext;
    private FirebaseAuth mAuth;
    final String TAG="SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();


        passwordEdittext=(EditText)findViewById(R.id.signUp_password);
        emailEdittext=(EditText)findViewById(R.id.signUp_email);
        sign_up=(Button)findViewById(R.id.sign_up);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailEdittext.getText().toString();
                String password=passwordEdittext.getText().toString();


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

                signUp(email,password);
            }
        });
    }


    private void signUp(String email, String password) {


        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignUpActivity.this,"Authentication Success"+user.getEmail(),Toast.LENGTH_SHORT).show();
                            finish();

                        }else{
                            Log.d(TAG, "createUserWithEmail:failed",task.getException());
                            Toast.makeText(SignUpActivity.this,"Authentication failed",Toast.LENGTH_SHORT).show();


                        }



                    }
                });
    }
}