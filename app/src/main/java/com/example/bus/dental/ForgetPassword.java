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

public class ForgetPassword extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText forget_pass_email;
    private Button reset_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        mAuth = FirebaseAuth.getInstance();
        forget_pass_email=(EditText) findViewById(R.id.forget_email);
        reset_password=(Button)findViewById(R.id.btn_reset_password);

        reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email=forget_pass_email.getText().toString();

        if (email.isEmpty()){
            forget_pass_email.setError("Email is required");
            forget_pass_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            forget_pass_email.setError("Please provide valid Email");
            forget_pass_email.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgetPassword.this,"Check your Email!!! ",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(ForgetPassword.this,"Try again! Something wrong happened!!! ",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}