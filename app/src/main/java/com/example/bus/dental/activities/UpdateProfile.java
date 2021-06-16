package com.example.bus.dental.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bus.dental.R;
import com.example.bus.dental.utilities.ConfirmationDialog;
import com.example.bus.dental.utilities.MySession;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ConfirmationDialog.ConfirmUpdate {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    String Name, LastName, Phone, Password;
    Button updateBtn, resetPassword;
    EditText updateLastname, updateName, updatePhone, confirm_password;
    FirebaseAuth mAuth;
    MySession mySession;
    private FirebaseUser firebaseUser;
    private DatabaseReference usersreference, ratingsreference, commentreference;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mySession = new MySession(getApplicationContext());
        final String x = mySession.getUid();
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        usersreference = FirebaseDatabase.getInstance().getReference("Users");
        ratingsreference = FirebaseDatabase.getInstance().getReference("Ratings");
        commentreference = FirebaseDatabase.getInstance().getReference("Comment");
        userID = firebaseUser.getUid();
        updateName = findViewById(R.id.profile_update_name);
        updateLastname = findViewById(R.id.profile_update_lastname);
        updatePhone = findViewById(R.id.profile_update_phone);
        resetPassword = findViewById(R.id.update_password_btn);
        confirm_password = findViewById(R.id.profile_confirmation_password);
        updateBtn = findViewById(R.id.profile_update_btn);

        Intent intent = getIntent();
        Name = intent.getStringExtra("name");
        LastName = intent.getStringExtra("lastname");
        Phone = intent.getStringExtra("phone");
        Password = intent.getStringExtra("password");

        updateName.setText(Name);
        updateLastname.setText(LastName);
        updatePhone.setText(Phone);


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = updateName.getText().toString();
                String newLastName = updateLastname.getText().toString();
                String newPhone = updatePhone.getText().toString();

                if (newName.isEmpty()) {
                    updateName.setError("Name is required");
                    updateName.requestFocus();

                } else if (newLastName.isEmpty()) {
                    updateLastname.setError("Last Name is required");
                    updateLastname.requestFocus();
                } else if (newPhone.length() != 8) {
                    updatePhone.setError("Phone should have 8 numbers");
                    updatePhone.requestFocus();

                } else if (newName.equals(Name) && newLastName.equals(LastName) && newPhone.equals(Phone)) {
                    Toast.makeText(UpdateProfile.this, "It's the same data", Toast.LENGTH_LONG).show();
                } else {
                    updateBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ConfirmationDialog x = new ConfirmationDialog();
                            x.show(getSupportFragmentManager(), "ConfirmationDialog");
                        }
                    });
                }
            }
        });


    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(this, Home.class));

                break;
            case R.id.nav_profile:
                startActivity(new Intent(this, Profile.class));

                break;
            case R.id.nav_contact_us:
                startActivity(new Intent(this, ContactUs.class));

                break;
            case R.id.nav_rate_us:
                startActivity(new Intent(this, RateUs.class));

                break;
            case R.id.nav_feedback:
                startActivity(new Intent(this, Feedback.class));

                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                mySession.logout();
                Intent i = new Intent(this, Login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(i);

                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void Update(String Name, String LastName, String Phone) {
        String newName = updateName.getText().toString();
        String newLastName = updateLastname.getText().toString();
        String newPhone = updatePhone.getText().toString();

        if (!Name.equals(newName)) {
            usersreference.child(userID).child("name").setValue(updateName.getText().toString());
            ratingsreference.child(userID).child("uName").setValue(updateName.getText().toString());
            commentreference.child(userID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Log.e("aa", snap.toString());
                        commentreference.child(userID).child(snap.getKey()).child("uname").setValue(updateName.getText().toString());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        if (!LastName.equals(newLastName)) {
            usersreference.child(userID).child("lastName").setValue(updateLastname.getText().toString());
            ratingsreference.child(userID).child("uLastName").setValue(updateLastname.getText().toString());
            commentreference.child(userID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Log.e("aa", snap.toString());
                        commentreference.child(userID).child(snap.getKey()).child("ulastname").setValue(updateName.getText().toString());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }
        if (!Phone.equals(newPhone)) {
            usersreference.child(userID).child("phone").setValue(updatePhone.getText().toString());
        }

        Toast.makeText(UpdateProfile.this, "Data changed successfully", Toast.LENGTH_LONG).show();
        Intent i = new Intent(UpdateProfile.this, Profile.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(i);

    }
}