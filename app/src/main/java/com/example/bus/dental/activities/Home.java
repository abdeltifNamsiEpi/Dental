package com.example.bus.dental.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.bus.dental.R;
import com.example.bus.dental.adapters.SubjectAdapter;
import com.example.bus.dental.models.Subject;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ItemClickInterface {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView subjectRecyclerview;
    SubjectAdapter subjectAdapter;
    List<Subject> listSubject = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        listSubject.add(new Subject(1, "Immunologie"));
        listSubject.add(new Subject(1, "Microbiologie"));
        listSubject.add(new Subject(1, "Histologie"));
        listSubject.add(new Subject(1, "Radiologie"));

        subjectRecyclerview = findViewById(R.id.subject_recycler_view);
        subjectRecyclerview.setLayoutManager(new GridLayoutManager(this, 2));

        subjectAdapter = new SubjectAdapter(getApplicationContext(), listSubject, this::onItemClick);
        subjectRecyclerview.setAdapter(subjectAdapter);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

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
                startActivity(new Intent(this, Login.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(Home.this, LessonsListActivity.class);
        intent.putExtra("subject_id", listSubject.get(position).getId());
        intent.putExtra("subject_title", listSubject.get(position).getSubjectName());
        startActivity(intent);
    }
}