package com.simi.plantorderingapp.Components;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.simi.plantorderingapp.Adapter.DisplayCartFromBottomAdapter;
import com.simi.plantorderingapp.ProductModel.SaveAddToCartSeedsAndFertilizers;
import com.simi.plantorderingapp.R;

import java.util.ArrayList;

public class DisplaySearchedItems extends AppCompatActivity {

    String getData= "";

    boolean isFromSeedsAndFertilizers= false;

    RecyclerView recyclerView11;

    ArrayList<SaveAddToCartSeedsAndFertilizers> list1= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_searched_items);

        recyclerView11= (RecyclerView) findViewById(R.id.recycler_id);

        Intent receiveData= getIntent();
        getData= receiveData.getStringExtra("value0001");
        isFromSeedsAndFertilizers= receiveData.getExtras().getBoolean("isseed");

        System.out.println("From DisplaySearchedItems getData: " + getData);

        String split[]= getData.split("\\*");

        for (String s: split) {
            if (!s.trim().isEmpty()) {
                s= s.trim();
                System.out.println("From DisplaySearchedItems split: " + s);
                if (!isFromSeedsAndFertilizers) {
                    display(s);
                }
                else {
                    display1(s);
                }
            }
        }
    }

    private void display1(String s) {
        System.out.println("From DisplaySearchedItems display()1 is: " + s);
        String Name="", Price="", SeedQty="", Detail= "", ImageUrl="", Quantity="";

        if (s.contains("price:")) {
            Name= s.substring(s.indexOf(s.charAt(0)), s.indexOf("price:")).trim();
        }
        if (s.contains("price:") && s.contains("seedsqty:")) {
            Price= s.substring(s.indexOf("price:"), s.indexOf("seedsqty:")).trim();
        }
        if (s.contains("seedsqty:") && s.contains("detail:")) {
            SeedQty= s.substring(s.indexOf("seedsqty:"), s.indexOf("detail:")).trim();
        }
        if (s.contains("detail:") && s.contains("category:")) {
            Detail= s.substring(s.indexOf("detail:") , s.indexOf("category:")).trim();
        }
        if (s.contains("imageUri:")) {
            ImageUrl= s.substring(s.indexOf("imageUri:"), s.indexOf("quantity:")).trim();
        }
        if (s.contains("quantity:")) {
            Quantity= s.substring(s.indexOf("quantity:")).trim();
        }

        SaveAddToCartSeedsAndFertilizers saveAddToCartSeedsAndFertilizers= new SaveAddToCartSeedsAndFertilizers("", "", Name, Detail, Price, "", ImageUrl, Quantity, "", "");
        list1.add(saveAddToCartSeedsAndFertilizers);

        LinearLayoutManager layoutManager = new LinearLayoutManager(DisplaySearchedItems.this, LinearLayoutManager.VERTICAL, false);

        recyclerView11.setLayoutManager(layoutManager);

        recyclerView11.setItemAnimator(new DefaultItemAnimator());

        DisplayCartFromBottomAdapter disp= new DisplayCartFromBottomAdapter(list1, DisplaySearchedItems.this);

        recyclerView11.setAdapter(disp);
    }

    private void display(String s) {
        String Name= "", Price="", Detail="", ImageUrl= "", Quantity="";
        if (s.contains("price:")) {
            Name= s.substring(s.indexOf(s.charAt(0)), s.indexOf("price:")).trim();
        }
        if (s.contains("price:") && s.contains("detail:")) {
            Price= s.substring(s.indexOf("price:"), s.indexOf("detail:")).trim();
        }
        if (s.contains("detail:") && s.contains("key:")) {
            Detail= s.substring(s.indexOf("detail:"), s.indexOf("key:")).trim();
        }
        if (s.contains("imageUri:") && s.contains("quantity:")) {
            ImageUrl= s.substring(s.indexOf("imageUri:"), s.indexOf("quantity:")).replace("imageUri:","").trim();
        }
        if (s.contains("quantity:")) {
            Quantity= s.substring(s.indexOf("quantity:"));
        }
        SaveAddToCartSeedsAndFertilizers saveAddToCartSeedsAndFertilizers= new SaveAddToCartSeedsAndFertilizers("", "", Name, Detail, Price, "", ImageUrl, Quantity, "", "");
        list1.add(saveAddToCartSeedsAndFertilizers);

        LinearLayoutManager layoutManager = new LinearLayoutManager(DisplaySearchedItems.this, LinearLayoutManager.VERTICAL, false);

        recyclerView11.setLayoutManager(layoutManager);

        recyclerView11.setItemAnimator(new DefaultItemAnimator());

        DisplayCartFromBottomAdapter disp= new DisplayCartFromBottomAdapter(list1, DisplaySearchedItems.this);

        recyclerView11.setAdapter(disp);
    }
}