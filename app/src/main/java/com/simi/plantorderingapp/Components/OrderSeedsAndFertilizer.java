package com.simi.plantorderingapp.Components;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.simi.plantorderingapp.R;

public class OrderSeedsAndFertilizer extends AppCompatActivity {

    String name="", price="", quantity="", seedqty="", imageurl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_seeds_and_fertilizer);

        Intent getData= getIntent();
        name= getData.getStringExtra("name");
        price= getData.getStringExtra("price");
        imageurl= getData.getStringExtra("imageurl");
        seedqty= getData.getStringExtra("seedsqqty");
        quantity= getData.getStringExtra("quantity");

        System.out.println("From OrderSeedsAndFertilizers name: " + name);
        System.out.println("From OrderSeedsAndFertilizers price: " + price);
        System.out.println("From OrderSeedsAndFertilizers imageurl: " + imageurl);
        System.out.println("From OrderSeedsAndFertilizers seedqty: " + seedqty);
        System.out.println("From OrderSeedsAndFertilizers quantity: " + quantity);
    }
}