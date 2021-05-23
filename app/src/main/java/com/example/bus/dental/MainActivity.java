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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    final String TAG="MainActivity";
    private TextView signUp,forgotPassword;
    private EditText mainemailEdittext, mainpasswordEdittext;
    private Button btn_login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        signUp=(TextView)findViewById(R.id.main_sing_up);
        mainemailEdittext=(EditText)findViewById(R.id.main_email);
        mainpasswordEdittext=(EditText)findViewById(R.id.main_password);

        forgotPassword=(TextView)findViewById(R.id.forget_password);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ForgetPassword.class));
            }
        });

        btn_login=(Button)findViewById(R.id.btn_login);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,SignUpActivity.class);
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
                            Toast.makeText(MainActivity.this,"Authentication Success "+user.getEmail(),Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(MainActivity.this,Home.class);
                            startActivity(i);

                        }
                        else{
                            Log.d(TAG, "loginUserWithEmail:failed",task.getException());
                            Toast.makeText(MainActivity.this,"Authentication failed",Toast.LENGTH_SHORT).show();


                        }



                    }
                });
    }
}