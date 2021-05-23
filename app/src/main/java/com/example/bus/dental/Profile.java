
package com.example.bus.dental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private String userID;

    Button btnUpdateProfile;
    TextView greetingTextview,nameTextview,emailTextview,phoneTextview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        greetingTextview=findViewById(R.id.profile_greeting);
        nameTextview=findViewById(R.id.profile_name);
        phoneTextview=findViewById(R.id.profile_phone);
        emailTextview=findViewById(R.id.profile_email);
        btnUpdateProfile=findViewById(R.id.profile_update);


        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID=firebaseUser.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                if(user!=null){
                    greetingTextview.setText("Welcome to our app "+user.getName()+"!!");
                    nameTextview.setText(user.getName());
                    phoneTextview.setText(user.getPhone());
                    emailTextview.setText(user.getEmail());


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this,"Something wrong happened!!!",Toast.LENGTH_LONG).show();
            }
        });


        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                reference= FirebaseDatabase.getInstance().getReference("Users");
                userID=firebaseUser.getUid();

                reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user=snapshot.getValue(User.class);
                        if(user!=null){

                            Intent intent=new Intent(getApplicationContext(),UpdateProfile.class);
                            intent.putExtra("name",user.getName());
                            intent.putExtra("email",user.getEmail());
                            intent.putExtra("phone",user.getPhone());
                            intent.putExtra("password",user.getPassword());
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Profile.this,"Something wrong happened!!!",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });




    }



}