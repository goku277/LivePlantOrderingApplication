package com.simi.plantorderingapp.Components;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.simi.plantorderingapp.Database.Profiledb;
import com.simi.plantorderingapp.ProductModel.SaveAddToCartItemsDetails;
import com.simi.plantorderingapp.R;

import java.util.ArrayList;
import java.util.Objects;

public class CollapsingToolbar extends AppCompatActivity {

    Toolbar toolbar;

    TextView name, fees, mobile, schedule, discount;

    ImageView placeholderImage;

    FloatingActionButton book;

    ArrayList<String> detailsList= new ArrayList<>();

    FirebaseDatabase database1;
    DatabaseReference ref1;

    SaveAddToCartItemsDetails saveAddToCartItemsDetails;

    Profiledb pdb;

    String usename="", usermobile="", imageUri="", quantity="", id1="", fee1="", discount1="";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing_toolbar);

        toolbar = (Toolbar) findViewById(R.id.collapsing_toolbar_toolbar_id);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        name = (TextView) findViewById(R.id.collapsing_toolbar_name_id);

        fees = (TextView) findViewById(R.id.collapsing_toolbar_fees_id);

        discount= (TextView) findViewById(R.id.sale_discount_id);

        mobile = (TextView) findViewById(R.id.collapsing_toolbar_mobile_id);

        schedule = (TextView) findViewById(R.id.collapsing_toolbar_schedule_id);

        placeholderImage = (ImageView) findViewById(R.id.collapsing_toolbar_imageview_id);

        book = (FloatingActionButton) findViewById(R.id.collapsing_toolbar_book_id);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a11= new AlertDialog.Builder(CollapsingToolbar.this);
                a11.setTitle("Add to cart");
                a11.setMessage("Are you sure you want add items to the cart");
                a11.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addToCart();
                    }
                });
                a11.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog a1= a11.create();
                a1.show();
            }
        });

        Intent receiveData = getIntent();

        detailsList= receiveData.getStringArrayListExtra("details");

        System.out.println("From CollapsingToolbar detailsList is: " + detailsList);

        Glide.with(CollapsingToolbar.this).load(detailsList.get(5).replace("imageUri:","").trim()).into(placeholderImage);

        imageUri= detailsList.get(5);

        name.setText("Name: "+ detailsList.get(0));

        if (!detailsList.get(1).contains("..")) {
            fees.setText(detailsList.get(1).replace("price:", "Price:"));
        }

        else if (detailsList.get(1).contains("..")) {
            String Fee[]= detailsList.get(1).trim().split("\\..");
            fee1= Fee[0];

            discount1= Fee[1];

            System.out.println("From CollapsingToolbar fee1 is: " + fee1 + " discount1 is: " + discount1);

            discount.setVisibility(View.VISIBLE);

            discount.setText("Discount: " + discount1 + "%");

            fees.setText(fee1);
        }

        mobile.setText(detailsList.get(2).replace("detail:", "Detail:"));

        schedule.setText(detailsList.get(3).replace("category:", "Category:"));

        String q[]= detailsList.get(5).split("\\s+");

        quantity= q[q.length-2] + " " + q[q.length-1];

        quantity= "quantity: " + quantity.replace("quantity:","").trim();

        System.out.println("quantity is: " + quantity);
        // quantity= qty;

        String Id1[]= detailsList.get(4).split("\\s+");

        id1= "id: " + Id1[Id1.length-1];

        System.out.println("From CollapsingToolbar quantity is: " + quantity);

        System.out.println("From CollapsingToolbar id1 is:" + id1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CollapsingToolbar.this, Users.class));
        finish();
    }

    private void addToCart() {
        ref1= FirebaseDatabase.getInstance().getReference().child("AddToCartPerUser");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        pdb= new Profiledb(CollapsingToolbar.this);

        SQLiteDatabase db= pdb.getWritableDatabase();
        String query = "select * from profile";
        Cursor c1 = db.rawQuery(query, null);

        if (c1!=null && c1.getCount() > 0) {
            if (c1.moveToFirst()) {
                usename= c1.getString(0);
                usermobile= c1.getString(1);
            }
        }
        quantity= quantity.replace("quantity:","");
        String split[]= quantity.split("\\s+");

        System.out.println("From CollapsingToolbar addToCart() quantity: " + quantity);


        saveAddToCartItemsDetails= new SaveAddToCartItemsDetails(usename, usermobile, name.getText().toString().trim(), schedule.getText().toString().trim(), fees.getText().toString().trim(), mobile.getText().toString().trim(), imageUri, quantity, id1);

        saveAddToCartItemsDetails.setCategory(schedule.getText().toString().trim());
        saveAddToCartItemsDetails.setImageuri(imageUri);
        saveAddToCartItemsDetails.setItemdetail(mobile.getText().toString().trim());
        saveAddToCartItemsDetails.setMobile("usermobile: " + usermobile);
        saveAddToCartItemsDetails.setPrice(fees.getText().toString().replace("Price:", "price:").trim());
        saveAddToCartItemsDetails.setUsername("username: " + usename);
        saveAddToCartItemsDetails.setName(name.getText().toString().trim());
        saveAddToCartItemsDetails.setId(id1);
        quantity= quantity.replace("quantity:","");
        saveAddToCartItemsDetails.setQuantity("quantity:" + quantity);
        id1= id1.replace("id:","");
        ref1.child(usename + " " + usermobile + "" + id1).setValue(saveAddToCartItemsDetails);
     //   Toast.makeText(CollapsingToolbar.this, "Added to cart successfully", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(CollapsingToolbar.this, Users.class));
    }
}