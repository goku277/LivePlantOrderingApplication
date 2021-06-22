package com.simi.plantorderingapp.Components;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.simi.plantorderingapp.Adapter.AddToCartAdapter;
import com.simi.plantorderingapp.ProductModel.AddToCartModelData;
import com.simi.plantorderingapp.R;

import java.util.ArrayList;

public class MyCart extends AppCompatActivity {

    ArrayList<AddToCartModelData> dataList= new ArrayList<>();

    AddToCartAdapter addToCartData;

    RecyclerView rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        rec= (RecyclerView) findViewById(R.id.recyclerview_id);

        AddToCartModelData addToCartModelData= new AddToCartModelData("", "", "", "", "", "","","");

        dataList.add(addToCartModelData);

        LinearLayoutManager layoutManager= new LinearLayoutManager(MyCart.this, LinearLayoutManager.VERTICAL, false);

        rec.setLayoutManager(layoutManager);

        rec.setItemAnimator(new DefaultItemAnimator());

        addToCartData = new AddToCartAdapter(dataList, MyCart.this);

        rec.setAdapter(addToCartData);
    }
}