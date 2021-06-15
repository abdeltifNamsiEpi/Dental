package com.example.bus.dental.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.bus.dental.R;
import com.example.bus.dental.adapters.SubjectAdapter;
import com.example.bus.dental.interfaces.APIInterface;
import com.example.bus.dental.interfaces.ItemClickInterface;
import com.example.bus.dental.models.Subject;
import com.example.bus.dental.utilities.APIClient;
import com.example.bus.dental.utilities.MySession;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ItemClickInterface {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView subjectRecyclerview;
    SubjectAdapter subjectAdapter;
    List<Subject> listSubject = new ArrayList<>();
    APIInterface apiInterface;
    MySession mySession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mySession = new MySession(getApplicationContext());
        final String x = mySession.getUid();





        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<Subject>> call = apiInterface.getSubjects();
        call.enqueue(new Callback<List<Subject>>() {

            @Override
            public void onResponse(Call<List<Subject>> call, Response<List<Subject>> response) {
                Log.e("TAG", response.body() + "");
                List<Subject> list = response.body();
                listSubject.clear();
                listSubject.addAll(list);
                subjectAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<List<Subject>> call, Throwable t) {
                Log.e("fqqq", t.toString());

            }
        });


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
                mySession.logout();
                Intent i=new Intent(this, Login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(i);
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