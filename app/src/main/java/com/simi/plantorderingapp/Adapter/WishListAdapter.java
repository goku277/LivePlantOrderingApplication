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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.simi.plantorderingapp.Components.AddToCart;
import com.simi.plantorderingapp.Components.PlaceOrder;
import com.simi.plantorderingapp.Database.Profiledb;
import com.simi.plantorderingapp.ProcessingUnit.AddToCartData;
import com.simi.plantorderingapp.ProductModel.OrderDetailsData;
import com.simi.plantorderingapp.ProductModel.SaveAddToCartItemsDetails;
import com.simi.plantorderingapp.ProductModel.WishListModel;
import com.simi.plantorderingapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder>{

    ArrayList<WishListModel> wishListModelList;
    Context context;
    OrderDetailsData orderData;

    String username="", usermobile="";

    Profiledb pf;

    ArrayList<String> getCartDetails= new ArrayList<>();

    FirebaseDatabase database1;
    DatabaseReference ref1;

    SaveAddToCartItemsDetails saveAddToCartItemsDetails;

    AddToCartData add;

    public WishListAdapter(ArrayList<WishListModel> wishListModelList, Context context) {
        this.wishListModelList = wishListModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.wish_list_layout, parent, false);
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
        System.out.println("From WishListAdapter wishListModelList.get(position).getImageUrl(): " + wishListModelList.get(position).getImageUrl());
        Glide.with(context).load(wishListModelList.get(position).getImageUrl()).into(holder.cig);
        holder.quantity.setText("\t" + wishListModelList.get(position).getQuantity());
        holder.category.setText("Category:\t" + wishListModelList.get(position).getCategory());
        holder.detail.setText("Details:\t" + wishListModelList.get(position).getDetail());
        holder.price.setText("Price:\t" + wishListModelList.get(position).getPrice());
        holder.name.setText("Name:\t" + wishListModelList.get(position).getName());

        holder.order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeProfile();
                AlertDialog.Builder a11= new AlertDialog.Builder(context);
                a11.setTitle("Place order");
                a11.setMessage("Please order as per your convinience");
                a11.setNegativeButton("Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initializeProfile();
                        orderData= new OrderDetailsData();
                        String itemName= wishListModelList.get(position).getName();
                        String userName= username;
                        String userMobile= usermobile;
                        String itemPrice= wishListModelList.get(position).getPrice().replace("Price:", "price:");
                        String itemImageUrl= wishListModelList.get(position).getImageUrl();
                        String itemId= "";
                        String itemQuantity= wishListModelList.get(position).getQuantity();
                        String itemSeedsQuantity= "";
                        orderData.setItemId(itemId);
                        orderData.setItemImageUrl(itemImageUrl);
                        orderData.setItemName(itemName);
                        orderData.setItemPrice(itemPrice.replace("Price:", "price"));
                        orderData.setItemQuantity(itemQuantity);
                        orderData.setItemSeedsQuantity(itemSeedsQuantity);
                        orderData.setUserMobile(userMobile);
                        orderData.setUserName(userName);

                        //    mDatabaseReference= database.getInstance().getReference().child(userName + " " + userMobile + " " + "Orders");

                        //    mDatabaseReference.child(key).setValue(orderData);

                        order(position);
                    }
                });
                AlertDialog a1= a11.create();
                a1.show();
            }
        });

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeProfile();
                addToCart(holder, wishListModelList.get(position).getImageUrl());
            }
        });
    }

    private void addToCart(ViewHolder holder, String imageUri) {
        ref1= FirebaseDatabase.getInstance().getReference().child("AddToCartPerUser");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        pf= new Profiledb(context);

        SQLiteDatabase db= pf.getWritableDatabase();
        String query = "select * from profile";
        Cursor c1 = db.rawQuery(query, null);

        if (c1!=null && c1.getCount() > 0) {
            if (c1.moveToFirst()) {
                username= c1.getString(0);
                usermobile= c1.getString(1);
            }
        }

        String rand= UUID.randomUUID().toString().trim();

        saveAddToCartItemsDetails= new SaveAddToCartItemsDetails(username, usermobile, holder.name.getText().toString().trim(), wishListModelList.get(holder.getAdapterPosition()).getDetail(), holder.price.getText().toString().trim(), holder.category.getText().toString().trim(), wishListModelList.get(holder.getAdapterPosition()).getImageUrl(), holder.quantity.getText().toString().trim(), rand);

        saveAddToCartItemsDetails.setCategory(holder.category.getText().toString().trim().replace("\t"," "));
        saveAddToCartItemsDetails.setImageuri("imageUri: " + imageUri);
        saveAddToCartItemsDetails.setItemdetail("Detail: " + wishListModelList.get(holder.getAdapterPosition()).getDetail());
        saveAddToCartItemsDetails.setMobile("usermobile: " + usermobile);
        saveAddToCartItemsDetails.setPrice(holder.price.getText().toString().trim().replace("Price:", "price:").replace("\t"," ").trim());
        saveAddToCartItemsDetails.setUsername("username: " + username);
        saveAddToCartItemsDetails.setId("id: " + rand);
        saveAddToCartItemsDetails.setName(holder.name.getText().toString().trim().replace("\t"," ").trim());
        saveAddToCartItemsDetails.setQuantity("quantity: " + wishListModelList.get(holder.getAdapterPosition()).getQuantity());
        ref1.child(username + " " + usermobile + " " + rand).setValue(saveAddToCartItemsDetails);

    //    Toast.makeText(context, "Added to cart successfully", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder a11= new AlertDialog.Builder(context);
        a11.setTitle("Check cart");
        a11.setMessage("Do you want to check your cart items?");
        a11.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // checkCart();
            }
        });

        a11.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

      //  AlertDialog a1= a11.create();
      //  a1.show();
    }

    private void checkCart() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("AddToCartPerUser");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                            SaveAddToCartItemsDetails saveCart = new SaveAddToCartItemsDetails((String) userData.get("username"), (String) userData.get("mobile"), (String) userData.get("name"), (String) userData.get("itemdetail"), (String) userData.get("price"), (String) userData.get("category"), (String) userData.get("imageuri"), (String) userData.get("quantity"), (String) userData.get("quantity"));

                            getCartDetails.add(saveCart.getUsername());
                            getCartDetails.add(saveCart.getMobile());
                            getCartDetails.add(saveCart.getName());
                            getCartDetails.add(saveCart.getItemdetail());
                            getCartDetails.add(saveCart.getPrice());
                            getCartDetails.add(saveCart.getCategory());
                            getCartDetails.add(saveCart.getImageuri());
                            getCartDetails.add(saveCart.getQuantity());

                        } catch (ClassCastException cce) {
                            try {
                                String mString = String.valueOf(dataMap.get(key));
                                System.out.println("From Tpo mString is: " + mString);
                            } catch (ClassCastException cce2) {

                            }
                        }
                    }

                    System.out.println("getCartDetails: " + getCartDetails);

                    String val1= "";

                    for (String s: getCartDetails) {
                        val1+= s + " ";
                    }
                    System.out.println("From CategoricalDataAdapter val1: " + val1);

                    add= new AddToCartData();

                    pf= new Profiledb(context);

                    SQLiteDatabase db= pf.getWritableDatabase();
                    String query = "select * from profile";
                    Cursor c1 = db.rawQuery(query, null);

                    String username= "";

                    String usermobile= "";

                    if (c1!=null && c1.getCount() > 0) {
                        if (c1.moveToFirst()) {
                            username= c1.getString(0);
                            usermobile= c1.getString(1);
                        }
                    }

                    Set<Set<String>> a1= add.init(val1, username, usermobile);

                    System.out.println("From CategoricalDataAdapter checkCart() a1 is: " + a1);

                    String concat="";

                    for (Set<String> set1: a1) {
                        for (String s12: set1) {
                            concat+= s12 + " ";
                        }
                        concat+= " * ";
                    }

                    System.out.println("concat is: " + concat);

                    Intent sendData= new Intent(context, AddToCart.class);

                    sendData.putExtra("concat", concat);

                    sendData.putExtra("categoricaldata", true);

                    context.startActivity(sendData);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void order(int position) {
        Intent sendData= new Intent(context, PlaceOrder.class);
        sendData.putExtra("name", wishListModelList.get(position).getName());
        sendData.putExtra("price", wishListModelList.get(position).getPrice());
        sendData.putExtra("qty", wishListModelList.get(position).getQuantity());
        sendData.putExtra("imageurl", wishListModelList.get(position).getImageUrl());
        context.startActivity(sendData);
    }

    @Override
    public int getItemCount() {
        return wishListModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cig;
        TextView name, price, detail, category, quantity;
        Button addToCart, order;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cig= (ImageView) itemView.findViewById(R.id.cig_id);
            name= (TextView) itemView.findViewById(R.id.name_id);
            price= (TextView) itemView.findViewById(R.id.price_id);
            detail= (TextView) itemView.findViewById(R.id.details_id);
            category= (TextView) itemView.findViewById(R.id.category_id);
            quantity= (TextView) itemView.findViewById(R.id.quantity_id);

            addToCart= (Button) itemView.findViewById(R.id.add_to_cart_id);
            order= (Button) itemView.findViewById(R.id.order_id);
        }
    }
}