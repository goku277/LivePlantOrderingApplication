package com.simi.plantorderingapp.Profile;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.simi.plantorderingapp.Components.Users;
import com.simi.plantorderingapp.Database.Profiledb;
import com.simi.plantorderingapp.R;

import java.util.ArrayList;

public class UpdateProfile extends AppCompatActivity {

    ArrayList<String> setToList= new ArrayList<>();

    FirebaseDatabase database1;
    DatabaseReference ref1;

    EditText password, address, pin, city, location;

    TextView email;

    TextView name, phone;

    Button update;

    Profiledb pf;

    String username="", usermobile="", key="";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UpdateProfile.this, Users.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        Intent getData = getIntent();

        pf= new Profiledb(UpdateProfile.this);

        email= (TextView) findViewById(R.id.email_id);

        setToList = getData.getStringArrayListExtra("settolist");

        database1= FirebaseDatabase.getInstance();

        System.out.println("From UpdateProfile setToList: " + setToList);

        SQLiteDatabase db= pf.getWritableDatabase();

        String query = "select * from profile";
        Cursor c1 = db.rawQuery(query, null);

        if (c1!=null && c1.getCount() > 0) {
            if (c1.moveToFirst()) {
                username = c1.getString(0);
                usermobile = c1.getString(1);
            }
        }

        key= username + " " + usermobile;

        name= (TextView) findViewById(R.id.name_id);
        password= (EditText) findViewById(R.id.password_id);
        phone= (TextView) findViewById(R.id.phone_id);
        address= (EditText) findViewById(R.id.address_id);
        pin= (EditText) findViewById(R.id.pin_id);
        city= (EditText) findViewById(R.id.city_id);
        location= (EditText) findViewById(R.id.location_id);

        name.setText(setToList.get(0));
        email.setText(setToList.get(1).replace("email:","").trim());
        password.setText(setToList.get(2).replace("password:","").trim());
        phone.setText(setToList.get(3).replace("phone:","").trim());
        address.setText(setToList.get(4).replace("address:", "").trim());
        pin.setText(setToList.get(5).replace("pin:", "").trim());
        city.setText(setToList.get(6).replace("city:","").trim());
        location.setText(setToList.get(7).replace("location:", "").trim());


        update= (Button) findViewById(R.id.update_btn_id);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref1= database1.getInstance().getReference("Customer");
                ref1.child(key).child("name").setValue(name.getText().toString().trim());
                ref1.child(key).child("email").setValue(email.getText().toString().trim().replace("email:","").trim());
                ref1.child(key).child("password").setValue(password.getText().toString().trim().replace("password:","").trim());
                ref1.child(key).child("phone").setValue(phone.getText().toString().trim().replace("phone:","").trim());
                ref1.child(key).child("address").setValue(address.getText().toString().trim().replace("address:","").trim());
                ref1.child(key).child("pin").setValue(pin.getText().toString().trim().replace("pin:", "").trim());
                ref1.child(key).child("city").setValue(city.getText().toString().trim().replace("city:","").trim());
                ref1.child(key).child("location").setValue(location.getText().toString().trim().replace("location:", "").trim());

                Toast.makeText(UpdateProfile.this, "Update successful", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(UpdateProfile.this, Users.class));
                finish();
            }
        });
    }
}