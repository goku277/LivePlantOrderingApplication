package com.simi.plantorderingapp.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.simi.plantorderingapp.Components.Users;
import com.simi.plantorderingapp.R;

import java.util.ArrayList;

public class DisplayProfile extends AppCompatActivity {

    ArrayList<String> setToList= new ArrayList<>();

    TextView name, email, password, phone, address, pin, city, location;

    String pin1="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_profile);

        Intent getData = getIntent();

        setToList = getData.getStringArrayListExtra("settolist");

        System.out.println("From DisplayProfile setToList: " + setToList);

        if (setToList!=null) {

            for (String s : setToList) {
                if (s.contains("pin:")) {
                    pin1 = s.replace("pin:", "Pin:\t");
                }
            }
        }

        System.out.println("pin1: " + pin1);

        name= (TextView) findViewById(R.id.name_id);
        email= (TextView) findViewById(R.id.email_id);
        password= (TextView) findViewById(R.id.password_id);
        phone= (TextView) findViewById(R.id.phone_id);
        address= (TextView) findViewById(R.id.address_id);
        pin= (TextView) findViewById(R.id.pin_id);
        city= (TextView) findViewById(R.id.city_id);
        location= (TextView) findViewById(R.id.location_id);

        if (setToList!=null) {

            name.setText("Name:\t" + setToList.get(0));
            email.setText(setToList.get(1).replace("email:", "Email:\t"));
            password.setText(setToList.get(2).replace("password:", "Password:\t"));
            phone.setText(setToList.get(3).replace("phone:", "Phone:\t"));
            address.setText(setToList.get(4).replace("address:", "Address:\t"));
            System.out.println(pin1);
            pin.setText(pin1);
            city.setText(setToList.get(6).replace("city:", "City:\t"));
            location.setText(setToList.get(7).replace("location:", "Location:\t"));
        }
        else Toast.makeText(this, "Profile not created yet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DisplayProfile.this, Users.class));
        finishAffinity();
    }
}