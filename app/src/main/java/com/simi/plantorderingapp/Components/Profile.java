package com.simi.plantorderingapp.Components;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.simi.plantorderingapp.Database.Profiledb;
import com.simi.plantorderingapp.ProcessingUnit.getCustomerData;
import com.simi.plantorderingapp.ProductModel.SaveCustomerData;
import com.simi.plantorderingapp.Profile.UpdateProfile;
import com.simi.plantorderingapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    Profiledb pf;

    FirebaseStorage storage;
    StorageReference storageReference;

    String uriPath="", username="", usermobile="";

    ImageView profile, check, update, delete;

    TextView profile_text, check_text, update_text, delete_text;

    ArrayList<String> getCustomerData= new ArrayList<>();

    com.simi.plantorderingapp.ProcessingUnit.getCustomerData csData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        csData= new getCustomerData();

        profile= (ImageView) findViewById(R.id.profile);
        profile_text= (TextView) findViewById(R.id.profile_text_id);

        check= (ImageView) findViewById(R.id.check_profile_id);
        check_text= (TextView) findViewById(R.id.check_profile_text_id);

        update= (ImageView) findViewById(R.id.update_id);
        update_text= (TextView) findViewById(R.id.update_text_id);

        delete= (ImageView) findViewById(R.id.delete_id);
        delete_text= (TextView) findViewById(R.id.delete_text_id);

        profile.setOnClickListener(this);
        profile_text.setOnClickListener(this);

        check.setOnClickListener(this);
        check_text.setOnClickListener(this);

        update.setOnClickListener(this);
        update_text.setOnClickListener(this);

        delete.setOnClickListener(this);
        delete_text.setOnClickListener(this);

        storage= FirebaseStorage.getInstance();
        storageReference= storage.getReference();

        pf= new Profiledb(Profile.this);

        profileAlert();
    }

    private void profileAlert() {
        AlertDialog.Builder a11= new AlertDialog.Builder(Profile.this);
        a11.setTitle("Profile Section");
        a11.setMessage("Choose appropriate option\n\n");
        a11.setPositiveButton("Crete Profile", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               // openDialog();
            }
        });

        a11.setNegativeButton("Check Profile", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                checkProfile();
            }
        });

        a11.setNeutralButton("Update profile", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                update();
            }
        });

        AlertDialog a1= a11.create();
        a1.show();
    }

    private void update() {
        System.out.println("From Profile Update() username: " + username + " usermobile: " + usermobile);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Customer");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                            SaveCustomerData customerData = new SaveCustomerData((String) userData.get("name"), (String) userData.get("email"), (String) userData.get("password"), (String) userData.get("phone"), (String) userData.get("address"), (String) userData.get("pin"), (String) userData.get("city"), (String) userData.get("location"));

                            getCustomerData.add("userName: " + customerData.getName());
                            getCustomerData.add("email: " + customerData.getEmail());
                            getCustomerData.add("password: " + customerData.getPassword());
                            getCustomerData.add("phone: " + customerData.getPhone());
                            getCustomerData.add("address: " + customerData.getAddress());
                            getCustomerData.add("pin: " + customerData.getPin());
                            getCustomerData.add("city: " + customerData.getCity());
                            getCustomerData.add("location: " + customerData.getLocation());
                            //   System.out.println("From Tpo getData is: " + getData);

                        } catch (ClassCastException cce) {
                            try {

                                String mString = String.valueOf(dataMap.get(key));

                                System.out.println("From Tpo mString is: " + mString);


                            } catch (ClassCastException cce2) {

                            }
                        }

                        System.out.println("getCustomerData " + getCustomerData);

                        String val11= "";

                        for (String s: getCustomerData) {
                            val11+= s + " ";
                        }

                        System.out.println("From Profile val11: " + val11);

                        Set<String> a1= csData.init(val11, username, usermobile);

                        System.out.println("From Profile a1: " + a1);

                        ArrayList<String> setToList= new ArrayList<>();

                        setToList.addAll(a1);

                        Intent sendData= new Intent(Profile.this, UpdateProfile.class);
                        sendData.putStringArrayListExtra("settolist", getCustomerData);
                        startActivity(sendData);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkProfile() {
        SQLiteDatabase db= pf.getWritableDatabase();

        String query = "select * from profile";
        Cursor c1 = db.rawQuery(query, null);

        if (c1.getCount() == 0) {
          //  Toast.makeText(this, "Create profile first!", Toast.LENGTH_SHORT).show();
        }
        else {
            if (c1!=null && c1.getCount() > 0) {
                if (c1.moveToFirst()) {
                    username = c1.getString(0);
                    usermobile = c1.getString(1);
                }
            }
            display(username, usermobile);
        }
    }

    private void display(final String username, final String usermobile) {
        System.out.println("From Profile display() username: " + username + " usermobile: " + usermobile);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Customer");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                            SaveCustomerData getData = new SaveCustomerData((String) userData.get("name"), (String) userData.get("email"), (String) userData.get("password"), (String) userData.get("phone"), (String) userData.get("address"), (String) userData.get("pin"), (String) userData.get("city"), (String) userData.get("location"));

                            getCustomerData.add(getData.getName());
                            getCustomerData.add(getData.getEmail());
                            getCustomerData.add(getData.getPassword());
                            getCustomerData.add(getData.getPhone());
                            getCustomerData.add(getData.getAddress());
                            getCustomerData.add(getData.getPin());
                            getCustomerData.add(getData.getCity());
                            getCustomerData.add(getData.getLocation());

                            //   System.out.println("From Tpo getData is: " + getData);

                        } catch (ClassCastException cce) {
                            try {

                                String mString = String.valueOf(dataMap.get(key));

                                //   System.out.println("From Tpo mString is: " + mString);


                            } catch (ClassCastException cce2) {

                            }
                        }
                    }

                    System.out.println("From Profile getCustomerData is: " + getCustomerData);

                    String value = "";
                    for (String s : getCustomerData) {
                        value += s + " ";
                    }

                    System.out.println("From profile value is: " + value);

                    Set<String> receiveData= csData.init(value, username, usermobile);

                    System.out.println("From Tpo receiveData is: " + receiveData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}