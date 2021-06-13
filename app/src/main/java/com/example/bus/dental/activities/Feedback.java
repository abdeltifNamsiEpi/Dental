package com.example.bus.dental.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.bus.dental.R;
import com.example.bus.dental.adapters.CommentAdapter;
import com.example.bus.dental.interfaces.ItemClickInterface;
import com.example.bus.dental.models.Comment;
import com.example.bus.dental.models.User;
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
import java.util.List;

public class Feedback extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ItemClickInterface {
    EditText editTextFeedback;
    ImageView btnAddFeedback;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userReference,commentReference;
    RecyclerView RvComment;
    CommentAdapter commentAdapter;
    List<Comment> listComment= new ArrayList<>();
    static  String COMMENT="Comment";
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    String uid,id_comment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        RvComment=findViewById(R.id.comment_recyclerview);
        RvComment.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter= new CommentAdapter(getApplicationContext(),listComment,Feedback.this::onItemClick);
        RvComment.setAdapter(commentAdapter);

        editTextFeedback=findViewById(R.id.feedback_edit);
        btnAddFeedback=findViewById(R.id.feedback_btn);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        userReference= FirebaseDatabase.getInstance().getReference("Users");

        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        uid=firebaseUser.getUid();
        commentReference=firebaseDatabase.getReference(COMMENT);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        btnAddFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userReference.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user=snapshot.getValue(User.class);
                        if(user!=null){


                            String feedback=editTextFeedback.getText().toString();
                            if (feedback.isEmpty()){
                                Toast.makeText(Feedback.this,"Please provide a feedback",Toast.LENGTH_LONG).show();

                            }else {

                                String uname=user.getName();
                                String ulastname=user.getLastName();
                                Comment comment = new Comment(feedback,uname,ulastname,uid);
                                commentReference.push().setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Feedback.this,"Feedback added successfully!!!",Toast.LENGTH_LONG).show();
                                        editTextFeedback.getText().clear();
                                    }
                                });

                            }


                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });


            }
        });

        initRvComment();
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
                startActivity(new Intent(this,Home.class));
                break;
            case R.id.nav_profile:
                startActivity(new Intent(this, Profile.class));
                break;
            case R.id.nav_contact_us:
                startActivity(new Intent(this, ContactUs.class));
                break;
            case R.id.nav_rate_us:
                startActivity(new Intent(this,RateUs.class));
                break;
            case R.id.nav_feedback:

                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, Login.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    private void initRvComment() {



        DatabaseReference commentRef=firebaseDatabase.getReference(COMMENT);
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listComment.clear();
                for(DataSnapshot snap:snapshot.getChildren()){
                    Comment comment= snap.getValue(Comment.class);
                    listComment.add(comment);
                }
                commentAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onItemClick(int position) {

                Toast.makeText(Feedback.this,"adssada",Toast.LENGTH_LONG).show();


    }

}