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

public class AddToCartSeedsAndFertilizer extends AppCompatActivity {

    RecyclerView rec;

    AddToCartAdapter addToCartData;

    ArrayList<AddToCartModelData> dataList= new ArrayList<>();


    String name="", price="", quantity="", seedqty="", imageurl="", pos="";

    boolean gotoSeedsandFertilizer= false, isAddToCartAdapter= false;

    Profiledb pf;

    String username="", usermobile="";

    @Override
    protected void onStart() {
        super.onStart();

        Intent getData= getIntent();

        gotoSeedsandFertilizer= getData.getExtras().getBoolean("gotoseedsandfertilizer");

        if (!gotoSeedsandFertilizer) {
            startActivity(new Intent(AddToCartSeedsAndFertilizer.this, Admin.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart_seeds_and_fertilizer);

        rec= (RecyclerView) findViewById(R.id.recyclerview_id);

        pf= new Profiledb(AddToCartSeedsAndFertilizer.this);

        initializeProfile();

        Intent getData= getIntent();

        isAddToCartAdapter= getData.getExtras().getBoolean("addtocartadapter");

        pos= getData.getStringExtra("pos");

        gotoSeedsandFertilizer= getData.getExtras().getBoolean("gotoseedsandfertilizer");

        if (!gotoSeedsandFertilizer) {
            startActivity(new Intent(AddToCartSeedsAndFertilizer.this, Admin.class));
            finish();
        }


        name= getData.getStringExtra("name");
        price= getData.getStringExtra("price");
        imageurl= getData.getStringExtra("imageurl");

        seedqty= getData.getStringExtra("seedsqqty");
        quantity= getData.getStringExtra("quantity");

        System.out.println("From AddToCartSeedsAndFertilizer name: " + name);
        System.out.println("From AddToCartSeedsAndFertilizer price: " + price);
        System.out.println("From AddToCartSeedsAndFertilizer imageurl: " + imageurl);
        System.out.println("From AddToCartSeedsAndFertilizer seedqty: " + seedqty);
        System.out.println("From AddToCartSeedsAndFertilizer quantity: " + quantity);

        AddToCartModelData addToCartModelData= new AddToCartModelData(username, usermobile, imageurl, price, name, quantity, seedqty,"");

        dataList.add(addToCartModelData);

        if (isAddToCartAdapter) {
            dataList.remove(Integer.parseInt(pos));
            startActivity(new Intent(AddToCartSeedsAndFertilizer.this, Users.class));
            finish();
        }

        LinearLayoutManager layoutManager= new LinearLayoutManager(AddToCartSeedsAndFertilizer.this, LinearLayoutManager.VERTICAL, false);

        rec.setLayoutManager(layoutManager);

        rec.setItemAnimator(new DefaultItemAnimator());

        addToCartData = new AddToCartAdapter(dataList, AddToCartSeedsAndFertilizer.this);

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
}