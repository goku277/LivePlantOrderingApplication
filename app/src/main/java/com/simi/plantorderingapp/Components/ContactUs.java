package com.simi.plantorderingapp.Components;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.simi.plantorderingapp.R;


public class ContactUs extends AppCompatActivity {

    TextView contact, email, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        contact= (TextView) findViewById(R.id.contact_no_id);
        email= (TextView) findViewById(R.id.email_id);
        address= (TextView) findViewById(R.id.address_id);

        contact.setText("9101754385");
        email.setText("simiheartfilia@gmail.com");
        address.setText("Guwahati, Assam, India");
    }
}