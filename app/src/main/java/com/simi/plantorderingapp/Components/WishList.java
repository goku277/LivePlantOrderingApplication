package com.simi.plantorderingapp.Components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.simi.plantorderingapp.Adapter.AddToCartAdapter;
import com.simi.plantorderingapp.Adapter.WishListAdapter;
import com.simi.plantorderingapp.ProductModel.WishListModel;
import com.simi.plantorderingapp.R;

import java.util.ArrayList;

public class WishList extends AppCompatActivity {

    String concat= "";

    String Name="", Price="", Category="", Detail="", ImageUri="", Quantity="", Price1="";

    RecyclerView rec;

    WishListModel wishModel;

    ArrayList<WishListModel> wishList= new ArrayList<>();

    WishListAdapter wishListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

        rec= (RecyclerView) findViewById(R.id.recycler_id);

        Intent receiveData= getIntent();

        concat= receiveData.getStringExtra("concat");

        System.out.println("concat is: " + concat);

        if (concat.contains("Name:") && concat.contains("Price:")) {
            Name= concat.substring(concat.indexOf("Name:"), concat.indexOf("Price:")).replace("Name:", "").trim();
        }
        if (concat.contains("Price:") && concat.contains("Category:")) {
            Price= concat.substring(concat.indexOf("Price:"), concat.indexOf("Category:")).replace("Price:", "").trim();
            String price[]= Price.split("\\s+");
            for (String s: price) {
                System.out.println("price: " + s);
            }
            Price1= price[0];
            System.out.println("Price1 is: " + Price1);
        }
        if (concat.contains("Category:") && concat.contains("Detail:")) {
            Category= concat.substring(concat.indexOf("Category:"), concat.indexOf("Detail:")).replace("Category:", "").trim();
        }
        if (concat.contains("Detail:") && concat.contains("imageUri:")) {
            Detail= concat.substring(concat.indexOf("Detail:") , concat.indexOf("imageUri:")).replace("Detail:","").trim();
        }
        if (concat.contains("imageUri:") && concat.contains("quantity:")) {
            ImageUri= concat.substring(concat.indexOf("imageUri:"), concat.indexOf("quantity:")).replace("imageUri:","").trim();
        }
        if (concat.contains("quantity:")) {
            Quantity= concat.substring(concat.indexOf("quantity:")).replace("quantity:", "").trim();
        }

        System.out.println("From WishList Name is: " + Name + "\tPrice: " + Price + "\tCategory: " + Category
                + "\tDetail: " + Detail + "\tImageUri: " + ImageUri + "\tQuantity: " + Quantity);

        wishModel= new WishListModel(Name, Price, Category, Detail, ImageUri, Quantity);


        wishList.add(wishModel);

        LinearLayoutManager layoutManager= new LinearLayoutManager(WishList.this, LinearLayoutManager.VERTICAL, false);

        rec.setLayoutManager(layoutManager);

        rec.setItemAnimator(new DefaultItemAnimator());

        wishListAdapter = new WishListAdapter(wishList, WishList.this);

        rec.setAdapter(wishListAdapter);
    }
}