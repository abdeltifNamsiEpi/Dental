
package com.example.bus.dental.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bus.dental.R;
import com.example.bus.dental.models.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private String userID;

    Button btnUpdateProfile,updatePasswordBtn;
    TextView greetingTextview,nameTextview,lastnameTextview,emailTextview,phoneTextview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        updatePasswordBtn=findViewById(R.id.update_password_btn);

        updatePasswordBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(Profile.this);
            View mView=getLayoutInflater().inflate(R.layout.fragment_reset_password,null);
            builder.setView(mView);
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        });

        greetingTextview=findViewById(R.id.profile_greeting);
        nameTextview=findViewById(R.id.profile_name);
        lastnameTextview=findViewById(R.id.profile_lastname);
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
                    lastnameTextview.setText(user.getLastName());
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
                            intent.putExtra("lastname",user.getLastName());
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


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                startActivity(new Intent(this, Home.class));
                break;
            case R.id.nav_profile:
                break;
            case R.id.nav_contact_us:
                startActivity(new Intent(this, ContactUs.class));
                break;
            case R.id.nav_rate_us:
                startActivity(new Intent(this,RateUs.class));
                break;
            case R.id.nav_feedback:
                startActivity(new Intent(this,Feedback.class));
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, Login.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}