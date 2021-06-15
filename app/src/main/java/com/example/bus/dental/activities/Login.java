package com.example.bus.dental.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bus.dental.R;
import com.example.bus.dental.utilities.MySession;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private TextView signUp,forgotPassword;
    private EditText mainemailEdittext, mainpasswordEdittext;
    private Button btn_login;
    private FirebaseAuth mAuth;
    MySession mySession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        signUp=(TextView)findViewById(R.id.main_sing_up);
        mainemailEdittext=(EditText)findViewById(R.id.main_email);
        mainpasswordEdittext=(EditText)findViewById(R.id.main_password);
        mySession = new MySession(getApplicationContext());
        final String x = mySession.getUid();




        forgotPassword=(TextView)findViewById(R.id.forget_password);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,ForgetPassword.class));
            }
        });

        btn_login=(Button)findViewById(R.id.btn_login);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login.this,SignUpActivity.class);
                startActivity(i);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateEmail() || !validatePassword()){
                    return;
                }

                String email=mainemailEdittext.getText().toString();
                String password=mainpasswordEdittext.getText().toString();

                login(email,password);
            }
        });

    }

    public Boolean validateEmail(){
        String val=mainemailEdittext.getText().toString();
        if (val.isEmpty()){
            mainemailEdittext.setError("Email is required");
            mainemailEdittext.requestFocus();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(val).matches()){
            mainemailEdittext.setError("Please provide valid Email");
            mainemailEdittext.requestFocus();
            return false;
        }
        else{
            mainemailEdittext.setError(null);
            return true;
        }

    }

    public Boolean validatePassword(){
        String val=mainpasswordEdittext.getText().toString();
        if(val.isEmpty()){
            mainpasswordEdittext.setError("Password is required");
            mainpasswordEdittext.requestFocus();
            return false;
        }
        else if(val.length()<6){
            mainpasswordEdittext.setError("Password should at least have 6 characters");
            mainpasswordEdittext.requestFocus();
            return false;
        }
        else{
            mainpasswordEdittext.setError(null);
            return true;
        }

    }

    private void login(String email, String password) {


        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.e("tt",user.getUid());
                            Toast.makeText(Login.this,"Authentication Success "+user.getEmail()+" !!",Toast.LENGTH_SHORT).show();

                            String uid= user.getUid();
                            mySession.setLoggedin(true,uid.toString());

                            Intent i=new Intent(Login.this,Home.class);
                            startActivity(i);
                            finish();

                        }
                        else{

                            Toast.makeText(Login.this,"Wrong email or password!!",Toast.LENGTH_SHORT).show();


                        }



                    }
                });
    }
}