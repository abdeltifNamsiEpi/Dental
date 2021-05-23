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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.provider.SyncStateContract.Helpers.update;

public class UpdateProfile extends AppCompatActivity {

    String Name,Phone,Email,Password;
    EditText updateName,updatePhone,updateEmail,updatePassword;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID=firebaseUser.getUid();


        updateName =findViewById(R.id.profile_update_name);
        updatePhone =findViewById(R.id.profile_update_phone);
        updateEmail =findViewById(R.id.profile_update_email);
        updatePassword =findViewById(R.id.profile_update_password);
        Button updateNow=findViewById(R.id.profile_update_now);

        Intent intent= getIntent();
        Name=intent.getStringExtra("name");
        Phone=intent.getStringExtra("phone");
        Email=intent.getStringExtra("email");
        Password=intent.getStringExtra("password");

        updateName.setText(Name);
        updatePhone.setText(Phone);
        updateEmail.setText(Email);


        updateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("aaa","gngchmv");
                update(v);
            }
        });




    }

    public Boolean validateName(){
        String val=updateName.getText().toString();
        if (val.isEmpty()){
            updateName.setError("Name is required");
            updateName.requestFocus();
            return false;
        }
        else{
            updateName.setError(null);
            return true;
        }

    }
    public Boolean validatePhone(){
        String val=updatePhone.getText().toString();

        if(val.length()!=8){
            updatePhone.setError("Phone should have 8 numbers");
            updatePhone.requestFocus();
            return false;
        }
        else{
            updatePhone.setError(null);
            return true;
        }

    }

    public Boolean validateEmail(){
        String val=updateEmail.getText().toString();
        if (val.isEmpty()){
            updateEmail.setError("Email is required");
            updateEmail.requestFocus();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(val).matches()){
            updateEmail.setError("Please provide valid Email");
            updateEmail.requestFocus();
            return false;
        }
        else{
            updateEmail.setError(null);
            return true;
        }

    }

    public Boolean validatePassword(){
        String val=updatePassword.getText().toString();
        if(val.isEmpty()){
            updatePassword.setError("Password is required");
            updatePassword.requestFocus();
            return false;
        }
        else if(val.length()<6){
            updatePassword.setError("Password should at least have 6 characters");
            updatePassword.requestFocus();
            return false;
        }
        else{
            updatePassword.setError(null);
            return true;
        }

    }


  public void update(View view){
        if (isNameChanged() || isPhoneChanged() || isEmailChanged() || isPasswordChanged()){
            Toast.makeText(this,"Data has been updated!!",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Data has NOT been updated!!",Toast.LENGTH_LONG).show();
        }

    }

    private boolean isPasswordChanged() {
        if (!Password.equals(updatePassword.getText().toString())){
            reference.child(userID).child("password").setValue(updatePassword.getText().toString());
            return true;
        }else {
            return false;
        }
    }

    private boolean isEmailChanged() {
        if (!Email.equals(updateEmail.getText().toString())){
            reference.child(userID).child("email").setValue(updateEmail.getText().toString());
            return true;
        }else {
            return false;
        }
    }

    private boolean isPhoneChanged() {
        if (!Phone.equals(updatePhone.getText().toString())){
            reference.child(userID).child("phone").setValue(updatePhone.getText().toString());
            return true;
        }else {
            return false;
        }
    }

    private boolean isNameChanged() {
        if (!Name.equals(updateName.getText().toString())){
            reference.child(userID).child("name").setValue(updateName.getText().toString());
            return true;
        }else {
            return false;
        }
    }
}