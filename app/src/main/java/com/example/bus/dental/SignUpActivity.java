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

    public Boolean validateName(){
        String val=nameEdittext.getText().toString();
        if (val.isEmpty()){
            nameEdittext.setError("Name is required");
            nameEdittext.requestFocus();
            return false;
        }
        else{
            nameEdittext.setError(null);
            return true;
        }

    }
    public Boolean validatePhone(){
        String val=phoneEditText.getText().toString();

        if(val.length()!=8){
            phoneEditText.setError("Phone should have 8 numbers");
            phoneEditText.requestFocus();
            return false;
        }
        else{
            phoneEditText.setError(null);
            return true;
        }

    }

    public Boolean validateEmail(){
        String val=emailEdittext.getText().toString();
        if (val.isEmpty()){
            emailEdittext.setError("Email is required");
            emailEdittext.requestFocus();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(val).matches()){
            emailEdittext.setError("Please provide valid Email");
            emailEdittext.requestFocus();
            return false;
        }
        else{
            emailEdittext.setError(null);
            return true;
        }

    }

    public Boolean validatePassword(){
        String val=passwordEdittext.getText().toString();
        if(val.isEmpty()){
            passwordEdittext.setError("Password is required");
            passwordEdittext.requestFocus();
            return false;
        }
        else if(val.length()<6){
            passwordEdittext.setError("Password should at least have 6 characters");
            passwordEdittext.requestFocus();
            return false;
        }
        else{
            passwordEdittext.setError(null);
            return true;
        }

    }





    private void signUp() {

        if (!validateName() || !validatePhone() || !validateEmail() || !validatePassword() ){
            return;
        }

        String name=nameEdittext.getText().toString();
        String phone=phoneEditText.getText().toString();
        String email=emailEdittext.getText().toString();
        String password=passwordEdittext.getText().toString();



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

        /**/
    }
}