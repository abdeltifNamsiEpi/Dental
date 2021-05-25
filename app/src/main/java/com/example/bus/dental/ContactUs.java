package com.example.bus.dental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity {

    EditText contact_subject,contact_message;
    Button call,send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

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
}