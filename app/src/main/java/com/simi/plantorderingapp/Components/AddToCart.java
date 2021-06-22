package com.simi.plantorderingapp.Components;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.simi.plantorderingapp.Adapter.AddToCartAdapter;
import com.simi.plantorderingapp.Database.Profiledb;
import com.simi.plantorderingapp.ProductModel.AddToCartModelData;
import com.simi.plantorderingapp.R;

import java.util.ArrayList;

public class AddToCart extends AppCompatActivity {

    String details="", Name="", id1="", pos="";

    RecyclerView rec;

    AddToCartAdapter addToCartData;

    boolean isFromSeedsAndFertilizer= false;

    boolean isCategoricalData= false, addToCart= true, isGoToAddCart= false;

    ArrayList<AddToCartModelData> dataList= new ArrayList<>();

    Profiledb pf;

    String username="", usermobile="";

    @Override
    protected void onStart() {
        super.onStart();

        Intent getData= getIntent();

        isGoToAddCart= getData.getExtras().getBoolean("gotoaddcart");

        System.out.println("From AddToCart isGoToAddCart: " + isGoToAddCart);

        addToCart= getData.getExtras().getBoolean("addtocartadapter");

        pos= getData.getStringExtra("pos");

        System.out.println("From AddToCart addToCart? " + addToCart);

        System.out.println("From AddToCart pos: " + pos);

        if (!addToCart) {
            if (pos!=null) {
                dataList.remove(Integer.parseInt(pos));
            }
        }

        isFromSeedsAndFertilizer= getData.getExtras().getBoolean("isFromSeedsAndFertilizer");

        isCategoricalData= getData.getExtras().getBoolean("categoricaldata");

        if (!isFromSeedsAndFertilizer && !isCategoricalData && !isGoToAddCart) {
            startActivity(new Intent(AddToCart.this, Admin.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        rec= (RecyclerView) findViewById(R.id.recyclerview_id);

        pf= new Profiledb(AddToCart.this);

        initializeProfile();

        Intent getData= getIntent();
        details= getData.getStringExtra("concat");

        addToCart= getData.getExtras().getBoolean("addtocartadapter");

        System.out.println("From AddToCart addToCart? " + addToCart);

        pos= getData.getStringExtra("pos");

        System.out.println("From AddToCart pos: " + pos);

        isCategoricalData= getData.getExtras().getBoolean("categoricaldata");

        isFromSeedsAndFertilizer= getData.getExtras().getBoolean("isFromSeedsAndFertilizer");

        id1= getData.getStringExtra("id1");

        System.out.println("From AddToCart id1: " + id1);

        if (isFromSeedsAndFertilizer && !isCategoricalData) {
            String split[]= details.split("\\*");

            Name= getData.getStringExtra("name");

            for (String s: split) {
                if (!s.trim().isEmpty()) {
                    System.out.println("split: " + s);
                    addToCart1(s);
                }
            }
        }

        else if (!isFromSeedsAndFertilizer && !isCategoricalData && !isGoToAddCart) {
            startActivity(new Intent(AddToCart.this, Admin.class));
            finish();
        }

        else if (!isFromSeedsAndFertilizer && isCategoricalData){

            System.out.println("From AddToCart details: " + details);

            if (details!=null) {

                String split[] = details.split("\\*");

                for (String s : split) {
                    if (!s.trim().isEmpty()) {
                        System.out.println("split: " + s);
                        addToCart(s);
                    }
                }
            }
        }
    }

    private void addToCart1(String s) {
        ArrayList<String> a11= new ArrayList<>();
        if (s.contains("Name:") && s.contains("Price:")) {
            a11.add(s.substring(s.indexOf("Name:"), s.indexOf("Price:")).trim());
        }
        if (s.contains("Price:") && s.contains("imageUri:")) {
            a11.add(s.substring(s.indexOf("Price:"), s.indexOf("imageUri:")).trim());
        }
        if (s.contains("imageUri:") && s.contains("Quantity:")) {
            a11.add(s.substring(s.indexOf("imageUri:"), s.indexOf("Quantity:")).replace("imageUri:", "").trim());
        }
        if (s.contains("Quantity:") && s.contains("seedsqty:")) {
            a11.add(s.substring(s.indexOf("Quantity"),s.indexOf("seedsqty:")).trim());
        }
        if (s.contains("seedsqty:")) {
            a11.add(s.substring(s.indexOf("seedsqty:")).trim());
        }
        System.out.println("a11: " + a11);

        AddToCartModelData addToCartModelData= new AddToCartModelData(username,usermobile, a11.get(2), a11.get(1), a11.get(0), a11.get(3), a11.get(4),id1);

        dataList.add(addToCartModelData);

        if (addToCart) {
            if (pos!=null) {
                dataList.remove(pos);
            }
        }

        LinearLayoutManager layoutManager= new LinearLayoutManager(AddToCart.this, LinearLayoutManager.VERTICAL, false);

        rec.setLayoutManager(layoutManager);

        rec.setItemAnimator(new DefaultItemAnimator());

        addToCartData = new AddToCartAdapter(dataList, AddToCart.this);

        rec.setAdapter(addToCartData);
    }

    private void initializeProfile() {
        SQLiteDatabase db= pf.getWritableDatabase();

        String query = "select * from profile";
        Cursor c1 = db.rawQuery(query, null);
        if (c1!=null && c1.getCount() > 0) {
            if (c1.moveToFirst()) {
                username = c1.getString(0);
                usermobile = c1.getString(1);
            }
        }
    }

    private void addToCart(String s) {
        ArrayList<String> a11= new ArrayList<>();
        if (s.contains("Name:") && s.contains("Price:")) {
            a11.add(s.substring(s.indexOf("Name:"), s.indexOf("Price:")).trim());
        }
        if (s.contains("Price:") && s.contains("imageUri:")) {
            a11.add(s.substring(s.indexOf("Price:"), s.indexOf("imageUri:")).trim());
        }
        if (s.contains("imageUri:") && s.contains("quantity:")) {
            a11.add(s.substring(s.indexOf("imageUri:")).trim());
        }
        if (s.contains("quantity:")) {
            a11.add(s.substring(s.indexOf("quantity")));
        }
        System.out.println("a11: " + a11);

        try {

            AddToCartModelData addToCartModelData = new AddToCartModelData("", "", a11.get(2), a11.get(1), a11.get(0), a11.get(3), "", "");

            dataList.add(addToCartModelData);

            LinearLayoutManager layoutManager = new LinearLayoutManager(AddToCart.this, LinearLayoutManager.VERTICAL, false);

            rec.setLayoutManager(layoutManager);

            rec.setItemAnimator(new DefaultItemAnimator());

            addToCartData = new AddToCartAdapter(dataList, AddToCart.this);

            rec.setAdapter(addToCartData);
        } catch (Exception e) {}
    }
}