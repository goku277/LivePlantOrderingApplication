package com.simi.plantorderingapp.Adapter;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.simi.plantorderingapp.Components.AddToCart;
import com.simi.plantorderingapp.Components.PlaceOrder;
import com.simi.plantorderingapp.Components.Users;
import com.simi.plantorderingapp.Database.Profiledb;
import com.simi.plantorderingapp.ProcessingUnit.AddToCartSeedsAndFertilizersData;
import com.simi.plantorderingapp.ProcessingUnit.GetIdsFromSeedsAndFertilizers;
import com.simi.plantorderingapp.ProductModel.OrderDetailsData;
import com.simi.plantorderingapp.ProductModel.SaveAddToCartSeedsAndFertilizers;
import com.simi.plantorderingapp.ProductModel.SeedsAndFerrtilizersCart;
import com.simi.plantorderingapp.ProductModel.SeedsAndFertilizersModelData;
import com.simi.plantorderingapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class SeedsAndFertilizersAdapter extends RecyclerView.Adapter<SeedsAndFertilizersAdapter.ViewHolder>{

    ArrayList<SeedsAndFertilizersModelData> seedsData= new ArrayList<>();

    Context context;

    Profiledb pdb;

    Profiledb pf;

    String usename="", usermobile="";

    ArrayList<String> getCartDetails= new ArrayList<>();

    FirebaseDatabase database1;
    DatabaseReference ref1;

    AddToCartSeedsAndFertilizersData add;

    String Name= "";

    Activity activity;

    boolean goto1= true;

    OrderDetailsData orderData;

    private DatabaseReference mDatabaseReference;

    FirebaseDatabase database;

    ArrayList<String> getAllIds= new ArrayList<>();

    SeedsAndFerrtilizersCart seedsAndFerrtilizersCart;

    GetIdsFromSeedsAndFertilizers getIdsFromSeedsAndFertilizers;

    String getId="";

    private void initializeProfile() {
        pf= new Profiledb(context);
        SQLiteDatabase db= pf.getWritableDatabase();

        String query = "select * from profile";
        Cursor c1 = db.rawQuery(query, null);
        if (c1!=null && c1.getCount() > 0) {
            if (c1.moveToFirst()) {
                usename = c1.getString(0);
                usermobile = c1.getString(1);
            }
        }
    }

  /*  private void getIds() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("AddToCartSeedsAndFertilizerPerUser");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                        //    SeedsAndFerrtilizersCart saveCart = new SeedsAndFerrtilizersCart((String) userData.get("id"));
                       //     getAllIds.add(saveCart.getIds());


                        } catch (ClassCastException cce) {
                            try {
                                String mString = String.valueOf(dataMap.get(key));
                                System.out.println("From Tpo mString is: " + mString);
                            } catch (ClassCastException cce2) {

                            }
                        }
                    }

                    System.out.println("getAllIds: " + getAllIds);

                    String val1= "";

                    for (String s: getAllIds) {
                        val1+= s + " ";
                    }
                    System.out.println("From SeedsAndFertilizersAdapter getIds() val1: " + val1);

                    String id[]= val1.split("id:");

                    for (String s: id) {
                        if (!s.trim().isEmpty()) {
                            System.out.println("id: " + s);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }    */

    public SeedsAndFertilizersAdapter(ArrayList<SeedsAndFertilizersModelData> seedsData, Context context, Activity activity) {
        this.seedsData = seedsData;
        this.context = context;
        this.activity= activity;
    }

    public Activity getActivity() {
        return activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.seedsandfertilizerslayout, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        database= FirebaseDatabase.getInstance();

        initializeProfile();

        pdb= new Profiledb(context);

        holder.name.setText("Name: "+ seedsData.get(position).getName().replace("name:",""));
        holder.quantity.setText("Quantity: " + seedsData.get(position).getQuantity().replace("quantity:",""));
        holder.category.setText("Category: " + seedsData.get(position).getCategory().replace("category:",""));
        holder.details.setText("Details: " + seedsData.get(position).getDetail().replace("detail:",""));
        holder.seedsqty.setText("Seeds Quantity: " + seedsData.get(position).getSeedqty().replace("seedsqty:",""));
        holder.price.setText("Price: " + seedsData.get(position).getPrice().replace("price:",""));
        Glide.with(context).load(seedsData.get(position).getImageUrl()).into(holder.cig);

        holder.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Users.class));
                activity.finishAffinity();
            }
        });

        holder.addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(holder, seedsData.get(position).getImageUrl(), seedsData.get(position).getSeedqty());
            }
        });

        holder.order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIds(position);
                AlertDialog.Builder a11= new AlertDialog.Builder(context);
                a11.setTitle("Place order");
                a11.setMessage("Please order as per your convinience");
                a11.setNegativeButton("Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        orderData= new OrderDetailsData();

                        initializeProfile();

                        String itemName= seedsData.get(position).getName();
                        String userName= usename;
                        String userMobile= usermobile;
                        String itemPrice= seedsData.get(position).getPrice();
                        String itemImageUrl= seedsData.get(position).getImageUrl();
                        String itemId= seedsData.get(position).getId();
                        String itemQuantity= seedsData.get(position).getQuantity();
                        String itemSeedsQuantity= "";

                        orderData.setItemId(itemId);
                        orderData.setItemImageUrl(itemImageUrl);
                        orderData.setItemName(itemName);
                        orderData.setItemPrice(itemPrice);
                        orderData.setItemQuantity(itemQuantity);
                        orderData.setItemSeedsQuantity(itemSeedsQuantity);
                        orderData.setUserMobile(userMobile);
                        orderData.setUserName(userName);

                  //      mDatabaseReference= database.getInstance().getReference().child("Orders");

                  //      mDatabaseReference.child(userName + " " + userMobile + " " + "Order " + UUID.randomUUID().toString()).setValue(orderData);

                        order(position);
                    }
                });
                AlertDialog a1= a11.create();
                a1.show();
            }
        });
    }

    private void getIds(int position) {

        String name= seedsData.get(position).getName();
        String price= seedsData.get(position).getPrice();
        String category= seedsData.get(position).getCategory();
        String quantity= seedsData.get(position).getQuantity();

        System.out.println("From SeedsAndFertilizersAdapter getIds() " + name + "\t" + price + "\t" + category + "\t" + quantity);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("AddToCartSeedsAndFertilizerPerUser");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification
                            SeedsAndFerrtilizersCart seedsAndFerrtilizersCart= new SeedsAndFerrtilizersCart((String) userData.get("id"), (String) userData.get("category"), (String) userData.get("imageuri"), (String) userData.get("itemdetail"), (String) userData.get("mobile"), (String) userData.get("name"), (String) userData.get("price"), (String) userData.get("quantity"), (String) userData.get("seedsqty"), (String) userData.get("username"));
                           getAllIds.add(seedsAndFerrtilizersCart.getUsername());
                           getAllIds.add(seedsAndFerrtilizersCart.getMobile());
                           getAllIds.add(seedsAndFerrtilizersCart.getName());
                           getAllIds.add(seedsAndFerrtilizersCart.getPrice());
                           getAllIds.add(seedsAndFerrtilizersCart.getCategory());
                           getAllIds.add(seedsAndFerrtilizersCart.getImageuri());
                           getAllIds.add(seedsAndFerrtilizersCart.getQuantity());
                           getAllIds.add(seedsAndFerrtilizersCart.getSeedsqty());
                           getAllIds.add(seedsAndFerrtilizersCart.getId());
                           getAllIds.add(seedsAndFerrtilizersCart.getItemdetail());
                        } catch (ClassCastException cce) {
                            try {
                                String mString = String.valueOf(dataMap.get(key));
                                System.out.println("From Tpo mString is: " + mString);
                            } catch (ClassCastException cce2) {
                            }
                        }
                    }

                    System.out.println("getAllIds: " + getAllIds);

                    String val1= "";

                    for (String s: getAllIds) {
                        val1+= s + " ";
                    }
                    System.out.println("From SeedsAndFertilizersAdapter getIds() val1: " + val1);

                    try {
                        getId = getIdsFromSeedsAndFertilizers.init(val1, name, price, quantity);
                    } catch (Exception e){}

                    System.out.println("From SeedsAndFertilizersAdapter getId(): " + getId);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void order(int position) {
        Intent sendData= new Intent(context, PlaceOrder.class);
        sendData.putExtra("name", seedsData.get(position).getName().replace("name:",""));
        sendData.putExtra("price", seedsData.get(position).getPrice().replace("price:",""));
        sendData.putExtra("qty", seedsData.get(position).getQuantity().replace("quantity:",""));
        sendData.putExtra("imageurl", seedsData.get(position).getImageUrl());
        context.startActivity(sendData);
    }

    private void addToCart(ViewHolder holder, String imageUri, String seedsqty) {
        ref1= FirebaseDatabase.getInstance().getReference().child("AddToCartSeedsAndFertilizerPerUser");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        pdb= new Profiledb(context);

        SQLiteDatabase db= pdb.getWritableDatabase();
        String query = "select * from profile";
        Cursor c1 = db.rawQuery(query, null);

        if (c1!=null && c1.getCount() > 0) {
            if (c1.moveToFirst()) {
                usename= c1.getString(0);
                usermobile= c1.getString(1);
            }
        }

        System.out.println("From SeedsAndFertilizersAdapter usename is: " + usename + " usermobile is: " + usermobile);

        String rand= UUID.randomUUID().toString().trim();

        SaveAddToCartSeedsAndFertilizers saveAddToCartItemsDetails= new SaveAddToCartSeedsAndFertilizers(usename, usermobile, holder.name.getText().toString().trim(), holder.details.getText().toString().trim(), holder.price.getText().toString().trim(), holder.category.getText().toString().trim(), seedsData.get(holder.getAdapterPosition()).getImageUrl(), holder.quantity.getText().toString().trim(), seedsData.get(holder.getAdapterPosition()).getSeedqty(), seedsData.get(holder.getAdapterPosition()).getId());

        saveAddToCartItemsDetails.setCategory(holder.category.getText().toString().trim());
        saveAddToCartItemsDetails.setImageuri(imageUri);
        saveAddToCartItemsDetails.setItemdetail(holder.details.getText().toString().trim());
        saveAddToCartItemsDetails.setMobile("usermobile: " + usermobile);
        saveAddToCartItemsDetails.setPrice(holder.price.getText().toString().trim());
        saveAddToCartItemsDetails.setUsername("username: " + usename);
        saveAddToCartItemsDetails.setName(holder.name.getText().toString().trim());
        saveAddToCartItemsDetails.setSeedsqty(seedsqty);
        saveAddToCartItemsDetails.setId(rand);
        ref1.child(usename + " " + usermobile + " " + rand).setValue(saveAddToCartItemsDetails);

    //    Toast.makeText(context, "Added to cart successfully", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder a11= new AlertDialog.Builder(context);
        a11.setTitle("Check cart");
        a11.setMessage("Do you want to check your cart items?");
        a11.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkCart();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                context.startActivity(new Intent(context, Users.class));
            }
        });

        a11.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(context, Users.class));
                activity.finishAffinity();
            }
        });

        AlertDialog a1= a11.create();
        a1.show();
    }

    private void checkCart() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("AddToCartSeedsAndFertilizerPerUser");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                            SaveAddToCartSeedsAndFertilizers saveCart = new SaveAddToCartSeedsAndFertilizers((String) userData.get("username"), (String) userData.get("mobile"), (String) userData.get("name"), (String) userData.get("itemdetail"), (String) userData.get("price"), (String) userData.get("category"), (String) userData.get("imageuri"), (String) userData.get("quantity"), (String) userData.get("seedsqty"), (String) userData.get("id"));

                            getCartDetails.add(saveCart.getUsername());
                            getCartDetails.add(saveCart.getMobile());
                            getCartDetails.add(saveCart.getName());
                            Name= saveCart.getName();
                            getCartDetails.add(saveCart.getItemdetail());
                            getCartDetails.add(saveCart.getPrice());
                            getCartDetails.add(saveCart.getCategory());
                            getCartDetails.add("imageUri: "+ saveCart.getImageuri());
                            getCartDetails.add(saveCart.getQuantity());
                            getCartDetails.add(saveCart.getSeedsqty());
                            getCartDetails.add(saveCart.getId());
                            //   System.out.println("From Tpo getData is: " + getData);

                        } catch (ClassCastException cce) {
                            try {

                                String mString = String.valueOf(dataMap.get(key));

                                System.out.println("From Tpo mString is: " + mString);

                            } catch (ClassCastException cce2) {

                            }
                        }
                    }

                    System.out.println("From SeedsAndFertilizersAdapter getCartDetails: " + getCartDetails);

                    String Id= getCartDetails.get(getCartDetails.size()-1);

                    String val1= "";

                    for (int i=0;i<getCartDetails.size()-1;i++) {
                        val1+= getCartDetails.get(i) + " ";
                    }
                    System.out.println("From SeedsAndFertilizersAdapter val1: " + val1);

                    System.out.println("From SeedsAndFertilizersAdapter Id: " + Id);

                    add= new AddToCartSeedsAndFertilizersData();

                    pf= new Profiledb(context);

                    SQLiteDatabase db= pf.getWritableDatabase();
                    String query = "select * from profile";
                    Cursor c1 = db.rawQuery(query, null);

                    System.out.println("From SeedsAndFertilizersAdapter checkCart() useName " + usename + "\tuserMobile " + usermobile);

                    Set<Set<String>> a1= add.init(val1, usename, usermobile);

                    String id1= usename + " " + usermobile + " " + Id;

                    System.out.println("From SeedsAndFertilizersAdapter checkCart() id1: " + id1);

                    System.out.println("From SeedsAndFertilizersAdapter checkCart() a1 is: " + a1);

                    String concat="";

                    for (Set<String> set1: a1) {
                        for (String s12: set1) {
                            concat+= s12 + " ";
                        }
                        concat+= " * ";
                    }

                    System.out.println("From SeedsAndFertilizersAdapter concat is: " + concat);

                    Intent sendData= new Intent(context, AddToCart.class);

                    sendData.putExtra("concat", concat);

                    sendData.putExtra("id1", id1);

                    sendData.putExtra("isFromSeedsAndFertilizer", true);

                    sendData.putExtra("name", Name);

                    context.startActivity(sendData);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return seedsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        PhotoView cig;
        TextView name, price, seedsqty, details, category, quantity;
        Button addtocart, order, back;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cig= (PhotoView) itemView.findViewById(R.id.cig_id);
            name= (TextView) itemView.findViewById(R.id.name_id);
            price= (TextView) itemView.findViewById(R.id.price_id);
            seedsqty= (TextView) itemView.findViewById(R.id.seeds_qty_id);
            details= (TextView) itemView.findViewById(R.id.details_id);
            category= (TextView) itemView.findViewById(R.id.category_id);
            quantity= (TextView) itemView.findViewById(R.id.quantity_id);
            addtocart= (Button) itemView.findViewById(R.id.add_to_cart_id);
            order= (Button) itemView.findViewById(R.id.order_id);
            back= (Button) itemView.findViewById(R.id.goback_id);
        }
    }
}