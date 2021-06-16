package com.example.bus.dental.utilities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bus.dental.R;
import com.example.bus.dental.activities.Profile;
import com.example.bus.dental.activities.UpdateProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdatePasswordDialog extends DialogFragment {
    Button update_password, cancel;
    EditText oldPassword, newPassword;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        update_password = view.findViewById(R.id.password_update_confirm_btn);
        cancel = view.findViewById(R.id.password_update_cancel_btn);
        oldPassword = view.findViewById(R.id.profile_current_password);
        newPassword = view.findViewById(R.id.profile_new_password);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newpassword = newPassword.getText().toString();
                String oldpassword = oldPassword.getText().toString();
                if (oldpassword.isEmpty()) {
                    oldPassword.setError("Password is required");
                    oldPassword.requestFocus();
                } else if (newpassword.length()<6) {
                    newPassword.setError("Password should at least have 6 characters");
                    newPassword.requestFocus();
                } else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), oldpassword);
                    user.reauthenticate(authCredential)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    FirebaseAuth.getInstance().getCurrentUser().updatePassword(newpassword)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("password").setValue(newpassword);

                                                        Toast.makeText(getActivity(), "Password changed successfully", Toast.LENGTH_LONG).show();
                                                        getDialog().dismiss();
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getActivity(), "something wrong happened", Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Wrong Password", Toast.LENGTH_LONG).show();
                                }
                            });

                }
            }


        });


        return view;
    }
}
