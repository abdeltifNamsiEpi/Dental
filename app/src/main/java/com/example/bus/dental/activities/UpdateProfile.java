package com.example.bus.dental.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bus.dental.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    String Name,LastName,Phone,Password;
    Button updateBtn;
    EditText updateLastname,updateName,updatePhone,updatePassword,currentPassword;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private String userID;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        mAuth=FirebaseAuth.getInstance();
        firebaseUser= mAuth.getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID=firebaseUser.getUid();


        updateName =findViewById(R.id.profile_update_name);
        updateLastname=findViewById(R.id.profile_update_lastname);
        updatePhone =findViewById(R.id.profile_update_phone);
        updatePassword =findViewById(R.id.profile_update_password);
        currentPassword=findViewById(R.id.profile_current_update_password);
        updateBtn=findViewById(R.id.profile_update_btn);

        Intent intent= getIntent();
        Name=intent.getStringExtra("name");
        LastName=intent.getStringExtra("lastname");
        Phone=intent.getStringExtra("phone");
        Password=intent.getStringExtra("password");

        updateName.setText(Name);
        updateLastname.setText(LastName);
        updatePhone.setText(Phone);







    }



  public void update(View view){
        /*if (isNameChanged() || isPhoneChanged() || isEmailChanged() || isPasswordChanged()){
            Toast.makeText(this,"Data has been updated!!",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Data has NOT been updated!!",Toast.LENGTH_LONG).show();
        }*/
      String newPassword = updatePassword.getText().toString();
      String oldPassword=currentPassword.getText().toString();
      String newName=updateName.getText().toString();
      String newPhone=updatePhone.getText().toString();


      if (newName.isEmpty() && newPhone.isEmpty() && oldPassword.isEmpty()){
          Toast.makeText(UpdateProfile.this, "there's no data to update", Toast.LENGTH_SHORT).show();
      }else if (newName.equals(Name)&&newPhone.equals(Phone)){

              isPasswordChanged();


      }else {
          isNameChanged();
          isPhoneChanged();
          isPasswordChanged();
          Intent intent1 = new Intent(UpdateProfile.this, Home.class);
          startActivity(intent1);
      }

    }

    private void isPasswordChanged() {
        String newPassword = updatePassword.getText().toString();
        String oldPassword=currentPassword.getText().toString();
        if(oldPassword.isEmpty()){
            Toast.makeText(UpdateProfile.this,"Please type the old password!!",Toast.LENGTH_SHORT).show();
        }
        else if(!oldPassword.isEmpty()){

             if(oldPassword.equals(Password)){
                if(newPassword.length()<6){
                    updatePassword.setError("Password should at least have 6 characters");
                    updatePassword.requestFocus();

                }
                else if (!Password.equals(newPassword)) {
                    firebaseUser.updatePassword(newPassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        reference.child(userID).child("password").setValue(newPassword);
                                        Toast.makeText(UpdateProfile.this, "password is changed", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(UpdateProfile.this,Profile.class));

                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateProfile.this,"something wrong happened",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if(Password.equals(newPassword)){
                    Toast.makeText(UpdateProfile.this,"the password is the same",Toast.LENGTH_SHORT).show();
                };
            }
             else if (!oldPassword.equals(Password)){
                 if(newPassword.length()<6){
                     updatePassword.setError("Password should at least have 6 characters");
                     updatePassword.requestFocus();


                 }else {
                     Toast.makeText(UpdateProfile.this,"type the right old password",Toast.LENGTH_SHORT).show();
                 }
             }
        }



    }


    private void isPhoneChanged() {
        String newPhone=updatePhone.getText().toString();

        if(newPhone.length()!=8){
            updatePhone.setError("Phone should have 8 numbers");
            updatePhone.requestFocus();

        }
        else if (!Phone.equals(newPhone)){
            reference.child(userID).child("phone").setValue(updatePhone.getText().toString());
            startActivity(new Intent(UpdateProfile.this,Profile.class));

        }else if (Phone.equals(newPhone)){
            Toast.makeText(UpdateProfile.this,"It's the same Phone number",Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(UpdateProfile.this,"ERROR",Toast.LENGTH_SHORT).show();        }
    }

    private void isNameChanged() {
        String newName=updateName.getText().toString();
        if (newName.isEmpty()){
            updateName.setError("Name is required");
            updateName.requestFocus();

        }
        else if (!Name.equals(newName)){
            reference.child(userID).child("name").setValue(updateName.getText().toString());
            Toast.makeText(UpdateProfile.this,"Email is changed!!!",Toast.LENGTH_SHORT).show();

            startActivity(new Intent(UpdateProfile.this,Profile.class));

        }else if (Name.equals(newName)){
            Toast.makeText(UpdateProfile.this,"It's the same Name",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(UpdateProfile.this,"ERROR",Toast.LENGTH_SHORT).show();
        }
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