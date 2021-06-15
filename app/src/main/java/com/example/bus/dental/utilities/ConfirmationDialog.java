package com.example.bus.dental.utilities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ConfirmationDialog extends DialogFragment {

    public interface ConfirmUpdate{
        void Update(String Name,String LastName,String Phone);
    }
    public ConfirmUpdate confirmUpdate;

    Button confirm,cancel;
    EditText confirmPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_confirm_password,container,false);

        confirm=view.findViewById(R.id.profile_confirmation_password_btn);
        cancel=view.findViewById(R.id.profile_confirmation_password_cancel_btn);
        confirmPassword=view.findViewById(R.id.profile_confirmation_password);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password=confirmPassword.getText().toString();
                String x="",y="",z = "";
                if(password.isEmpty()){
                    confirmPassword.setError("Password is required");
                    confirmPassword.requestFocus();
                }else {
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                    AuthCredential authCredential= EmailAuthProvider.getCredential(user.getEmail(),password);
                    user.reauthenticate(authCredential)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    confirmUpdate.Update(x,y,z);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(),"Wrong Password",Toast.LENGTH_LONG).show();
                                }
                            });
                }

            }
        });





        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            confirmUpdate=(ConfirmUpdate) getActivity();
        }catch (ClassCastException e){
            Log.e("aa","onAttach: ClassException: "+e.getMessage());
        }
    }
}
