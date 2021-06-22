package com.simi.plantorderingapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.simi.plantorderingapp.Components.Users;
import com.simi.plantorderingapp.Components.ViewAllRecommendations;
import com.simi.plantorderingapp.Database.Profiledb;
import com.simi.plantorderingapp.ProductModel.RecommendedItemsAddToCart;
import com.simi.plantorderingapp.ProductModel.ViewAllRecomendationsModelData;
import com.simi.plantorderingapp.R;

import java.util.ArrayList;
import java.util.UUID;

public class ViewAllRecomendationsAdapter extends RecyclerView.Adapter<ViewAllRecomendationsAdapter.ViewHolder> {

    ArrayList<ViewAllRecomendationsModelData> viewAllRecomendationsModelDataList;
    Context context;
    String uri1= "";
    String username="", usermobile="";

    Profiledb pf;

    FirebaseDatabase database1;
    DatabaseReference ref1;

    RecommendedItemsAddToCart recommendedItemsAddToCart;

    public ViewAllRecomendationsAdapter(ArrayList<ViewAllRecomendationsModelData> viewAllRecomendationsModelDataList, Context context) {
        this.viewAllRecomendationsModelDataList= viewAllRecomendationsModelDataList;
        this.context= context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewallrecommendationslayout, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    private void initializeProfile() {
        pf= new Profiledb(context);
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(viewAllRecomendationsModelDataList.get(position).getImageUrl()).into(holder.cig);
        initializeProfile();
        database1= FirebaseDatabase.getInstance();
        ref1= FirebaseDatabase.getInstance().getReference("AddToCartPerUser");
        holder.name.setText(viewAllRecomendationsModelDataList.get(position).getName());
        holder.price.setText(viewAllRecomendationsModelDataList.get(position).getPrice());
        holder.detail.setText(viewAllRecomendationsModelDataList.get(position).getDetail());

        holder.goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Users.class));
            }
        });

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a11= new AlertDialog.Builder(context);
                a11.setTitle("Add to the cart");
                a11.setMessage("Do you want to add this item to the cart?");
                a11.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addToCart(position);
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
    }

    private void addToCart(int position) {
       // if (uri1!=null) {
            System.out.println("From Users uri1 addToCart() is: " + uri1);
            recommendedItemsAddToCart= new RecommendedItemsAddToCart();

            String rand= UUID.randomUUID().toString();

            recommendedItemsAddToCart.setCategory("Category: Recommended Items");
            recommendedItemsAddToCart.setImageuri("imageUri: " + viewAllRecomendationsModelDataList.get(position).getImageUrl());
            recommendedItemsAddToCart.setItemdetail("Detail: " + viewAllRecomendationsModelDataList.get(position).getDetail());
            recommendedItemsAddToCart.setMobile("usermobile: " + usermobile);
            recommendedItemsAddToCart.setUsername("username: " + username);
            recommendedItemsAddToCart.setPrice("price: " + viewAllRecomendationsModelDataList.get(position).getPrice());
            recommendedItemsAddToCart.setQuantity("quantity: " + "");
            recommendedItemsAddToCart.setName("Name: " + viewAllRecomendationsModelDataList.get(position).getName());
            recommendedItemsAddToCart.setId("id: " + rand);

            String key= username + " " + usermobile + " " + rand;

            ref1.child(key).setValue(recommendedItemsAddToCart);

         //   Toast.makeText(context, "Added to the cart", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, Users.class));
      //  }
    }

    @Override
    public int getItemCount() {
        return viewAllRecomendationsModelDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cig;
        TextView name, price, detail;
        Button goBack, addToCart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cig= (ImageView) itemView.findViewById(R.id.cig_id);
            name= (TextView) itemView.findViewById(R.id.name_id);
            price= (TextView) itemView.findViewById(R.id.price_id);
            detail= (TextView) itemView.findViewById(R.id.detail_id);

            goBack= (Button) itemView.findViewById(R.id.back_btn_id);
            addToCart= (Button) itemView.findViewById(R.id.add_to_cart_id);
        }
    }
}