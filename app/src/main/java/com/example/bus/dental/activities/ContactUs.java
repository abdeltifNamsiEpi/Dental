package com.example.bus.dental.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bus.dental.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ContactUs extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    EditText contact_subject,contact_message;
    Button call,send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        contact_subject =findViewById(R.id.conact_email_subject);
        contact_message=findViewById(R.id.contact_email_message);
        call=findViewById(R.id.contact_call);
        send=findViewById(R.id.contact_send);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number="+21624278211";
                Intent i=new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + "+21624278211"));
                if(i.resolveActivity(getPackageManager()) !=null)
                {
                    startActivity(i);
                }

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            String email="Dental_lesson@gmail.com";
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:"+email));
                    intent.putExtra(Intent.EXTRA_SUBJECT,contact_subject.getText().toString());
                    intent.putExtra(Intent.EXTRA_TEXT,contact_message.getText().toString());

                    startActivity(intent);
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
                startActivity(new Intent(this, Profile.class));
                break;
            case R.id.nav_contact_us:
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