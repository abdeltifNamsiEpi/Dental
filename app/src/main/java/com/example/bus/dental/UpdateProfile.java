package com.example.bus.dental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private String userID;
    EditText updateName,updatePhone,updateEmail,updatePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID=firebaseUser.getUid();


        updateName =(EditText)findViewById(R.id.profile_update_name);
        updatePhone =(EditText)findViewById(R.id.profile_update_phone);
        updateEmail =(EditText)findViewById(R.id.profile_update_email);
        updatePassword =(EditText)findViewById(R.id.profile_update_password);
        Button updateNow=(Button)findViewById(R.id.profile_update_now);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                if(user!=null){
                    updateName.setText(user.getName());
                    updatePhone.setText(user.getPhone());
                    updateEmail.setText(user.getEmail());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateProfile.this,"Something wrong happened!!!",Toast.LENGTH_LONG).show();
            }
        });


        updateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(updateName.getText().toString().isEmpty() ){
                    Toast.makeText(UpdateProfile.this,"Name is empty",Toast.LENGTH_SHORT).show();
                }else if(updateEmail.getText().toString().isEmpty() ){
                    Toast.makeText(UpdateProfile.this,"Email is empty",Toast.LENGTH_SHORT).show();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(updateEmail.getText().toString()).matches()){
                    Toast.makeText(UpdateProfile.this,"Please provide valid Email",Toast.LENGTH_SHORT).show();
                }
                else if(updatePhone.length()<8){
                    Toast.makeText(UpdateProfile.this,"phone should at leat have 8 numbers",Toast.LENGTH_SHORT).show();
                }
                /*else if(isNameChanged()||isPhoneChanged()||isEmailChanged()||isPasswordChanged()){
                    Toast.makeText(UpdateProfile.this,"Data has been updated!!!",Toast.LENGTH_LONG).show();

                }*/
                else {
                    Toast.makeText(UpdateProfile.this,"Error Occured!!!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    /*public void update(View view){
        String _NAME= updateName.getText().toString();


        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        updateName.setText(user.getName());
        UserProfileChangeRequest request=new UserProfileChangeRequest.Builder().setDisplayName();


    }

    private boolean isPasswordChanged() {

    }

    private boolean isEmailChanged() {
    }

    private boolean isPhoneChanged() {
    }

    private boolean isNameChanged() {
    }*/
}