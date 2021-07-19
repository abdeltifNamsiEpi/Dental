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
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.bus.dental.R;
import com.example.bus.dental.models.Rating;
import com.example.bus.dental.models.User;
import com.example.bus.dental.utilities.MySession;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class RateUs extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Button btnSubmit;
    RatingBar ratingBar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    MySession mySession;

    DatabaseReference userReference;
    float defaultValue;
    FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mySession = new MySession(getApplicationContext());
        final String x = mySession.getUid();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        ratingBar = findViewById(R.id.rating_bar);
        btnSubmit = findViewById(R.id.rating_btn);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Ratings");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap:snapshot.getChildren()){
                    if (snap.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Ratings").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        databaseReference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Rating rating = snapshot.getValue(Rating.class);
                                defaultValue = Float.parseFloat(rating.getRate());
                                ratingBar.setRating(defaultValue);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }else {
                        ratingBar.setRating(0);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = String.valueOf(ratingBar.getRating());


                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                userReference = FirebaseDatabase.getInstance().getReference("Users");
                userID = firebaseUser.getUid();
                userReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if (user != null) {
                            String uid = userID;
                            String uname = user.getName();
                            String ulastname = user.getLastName();
                            String uemail = user.getEmail();
                            Rating rating = new Rating(s, uname, ulastname, uid, uemail);
                            FirebaseDatabase.getInstance().getReference("Ratings").child(userID).setValue(rating).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), s + " Star", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(RateUs.this, Home.class));
                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
                break;
            case R.id.nav_feedback:
                startActivity(new Intent(this, Feedback.class));

                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                mySession.logout();
                Intent i=new Intent(this, Login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(i);


                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}