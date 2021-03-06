package com.simi.plantorderingapp.Components;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simi.plantorderingapp.Adapter.SeedsAndFertilizersAdapter;
import com.simi.plantorderingapp.ProductModel.SeedsAndFertilizersModelData;
import com.simi.plantorderingapp.R;

import java.util.ArrayList;

public class SeedsAndFertilizers extends AppCompatActivity {

    String getData="", id="";

    RecyclerView recyclerView;

    ArrayList<SeedsAndFertilizersModelData> seedsModelData= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeds_and_fertilizers);

        Intent receiveData= getIntent();

        recyclerView= (RecyclerView) findViewById(R.id.recyclerview_id);

        getData= receiveData.getStringExtra("val11");

        id= receiveData.getStringExtra("id");

        System.out.println("SeedsAndFertilizers getData is: " + getData);

        String split[]= getData.split("\\*");
        for (String s: split) {
            if (!s.trim().isEmpty()) {
                addData(s);
            }
        }
    }

    private void addData(String s) {
        String name="", price="", detail= "", seedsqty="", category="", imageUrl="", quantity="";
        if (s.contains("name:") && s.contains("price:")) {
            name= s.substring(s.indexOf("name:"), s.indexOf("price:")).trim();
        }
        if (s.contains("price:") && s.contains("seedsqty:")) {
            price= s.substring(s.indexOf("price:"), s.indexOf("seedsqty:")).trim();
        }
        if (s.contains("seedsqty:") && s.contains("detail:")) {
            seedsqty= s.substring(s.indexOf("seedsqty:"), s.indexOf("detail:")).trim();
        }
        if (s.contains("detail:") && s.contains("category:")) {
            detail= s.substring(s.indexOf("detail:"), s.indexOf("category:")).trim();
        }
        if (s.contains("category:") && s.contains("imageUri:")) {
            category = s.substring(s.indexOf("category:"), s.indexOf("imageUri:")).trim();
        }
        if (s.contains("imageUri:") && s.contains("quantity:")) {
            imageUrl= s.substring(s.indexOf("imageUri:"), s.indexOf("quantity:")).replace("imageUri:","").trim();
        }
        if (s.contains("quantity:")) {
            quantity= s.substring(s.indexOf("quantity:")).replace("*","").trim();
        }
        SeedsAndFertilizersModelData seeds= new SeedsAndFertilizersModelData();

        seeds.setCategory(category);
        seeds.setDetail(detail);
        seeds.setImageUrl(imageUrl);
        seeds.setPrice(price);
        seeds.setName(name);
        seeds.setQuantity(quantity);
        seeds.setSeedqty(seedsqty);
        seeds.setId(id);

        seedsModelData.add(seeds);

        LinearLayoutManager layoutManager= new LinearLayoutManager(SeedsAndFertilizers.this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        SeedsAndFertilizersAdapter seeds1= new SeedsAndFertilizersAdapter(seedsModelData, SeedsAndFertilizers.this, this);

        recyclerView.setAdapter(seeds1);
    }
}