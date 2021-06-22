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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.simi.plantorderingapp.Components.AddToCart;
import com.simi.plantorderingapp.Components.PlaceOrder;
import com.simi.plantorderingapp.Components.Users;
import com.simi.plantorderingapp.Database.Profiledb;
import com.simi.plantorderingapp.ProductModel.AddToCartModelData;
import com.simi.plantorderingapp.ProductModel.OrderDetailsData;
import com.simi.plantorderingapp.R;

import java.util.ArrayList;
import java.util.UUID;

public class AddToCartAdapter extends RecyclerView.Adapter<AddToCartAdapter.ViewHolder>{

    ArrayList<AddToCartModelData> dataList= new ArrayList<>();
    Context context;

    FirebaseDatabase database1;
    DatabaseReference ref1;

    String key= "";

    ArrayList<String> getCartDetails= new ArrayList<>();

    Profiledb pf;

    OrderDetailsData orderData;

    private DatabaseReference mDatabaseReference;

    FirebaseDatabase database;

    String username="", usermobile="";

    public AddToCartAdapter(ArrayList<AddToCartModelData> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.add_to_cart_layout_list_item, parent, false);
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        database1= FirebaseDatabase.getInstance();

        initializeProfile();

        holder.price.setText(dataList.get(position).getPrice());
        holder.name.setText(dataList.get(position).getName());
        String qty= dataList.get(position).getSeedsqty().trim();
        String split[]= qty.split(" ");
        if (split.length > 1) {
            String seedQty = split[1] + " " + split[2];
            holder.seedsqty.setText(seedQty);
        }
        else {
            holder.seedsqty.setText(dataList.get(position).getSeedsqty().trim());
        }
        System.out.println("From onBindViewHolder() dataList.get(position).getImageUrl(): " + dataList.get(position).getImageUrl());
        Glide.with(context).load(dataList.get(position).getImageUrl().replace("imageUri:","").trim()).into(holder.cig);

        holder.cig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(context, "Clicked on: " + dataList.get(position).getName(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder a11= new AlertDialog.Builder(context);
                a11.setTitle("Alert");
                a11.setMessage("Place order");
                a11.setPositiveButton("Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder a11= new AlertDialog.Builder(context);
                        a11.setTitle("Place order");
                        a11.setMessage("Please order as per your convinience");
                        a11.setNegativeButton("Online Order", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                orderData= new OrderDetailsData();

                                initializeProfile();

                                String itemName= dataList.get(position).getName();
                                String userName= dataList.get(position).getUsername();
                                String userMobile= dataList.get(position).getUsermobile();
                                String itemPrice= dataList.get(position).getPrice();
                                String itemImageUrl= dataList.get(position).getImageUrl();
                                String itemId= dataList.get(position).getId();
                                String itemQuantity= dataList.get(position).getQty();
                                String itemSeedsQuantity= dataList.get(position).getSeedsqty();

                                orderData.setItemId(itemId);
                                orderData.setItemImageUrl(itemImageUrl);
                                orderData.setItemName(itemName);
                                orderData.setItemPrice(itemPrice);
                                orderData.setItemQuantity(itemQuantity);
                                orderData.setItemSeedsQuantity(itemSeedsQuantity);
                                orderData.setUserMobile(userMobile);
                                orderData.setUserName(userName);

                            //    mDatabaseReference= database1.getInstance().getReference().child("Orders");

                            //    mDatabaseReference.child(userName + " " + userMobile + " " + "Order " + UUID.randomUUID().toString()).setValue(orderData);

                                order(position);
                            }
                        });
                        AlertDialog a1= a11.create();
                        a1.show();
                    }
                });
                a11.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(position, dataList.get(position).getId(), holder);
                    }
                });

                AlertDialog a1= a11.create();
                a1.show();
            }
        });

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context, "Clicked on: " + dataList.get(position).getName(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder a11= new AlertDialog.Builder(context);
                a11.setTitle("Alert");
                a11.setMessage("Place order");
                a11.setPositiveButton("Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        AlertDialog.Builder a11= new AlertDialog.Builder(context);
                        a11.setTitle("Place order");
                        a11.setMessage("Please order as per your convinience");
                        a11.setNegativeButton("Online Order", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                orderData= new OrderDetailsData();

                                initializeProfile();

                                String itemName= dataList.get(position).getName();
                                String userName= dataList.get(position).getUsername();
                                String userMobile= dataList.get(position).getUsermobile();
                                String itemPrice= dataList.get(position).getPrice();
                                String itemImageUrl= dataList.get(position).getImageUrl();
                                String itemId= dataList.get(position).getId();
                                String itemQuantity= dataList.get(position).getQty();
                                String itemSeedsQuantity= dataList.get(position).getSeedsqty();

                                orderData.setItemId(itemId);
                                orderData.setItemImageUrl(itemImageUrl);
                                orderData.setItemName(itemName);
                                orderData.setItemPrice(itemPrice);
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
                a11.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(position, dataList.get(position).getId(), holder);
                        int pos= holder.getAdapterPosition();

                        Intent sendData= new Intent(context, AddToCart.class);
                        sendData.putExtra("addtocartadapter", true);
                        sendData.putExtra("pos", pos);

                        context.startActivity(new Intent(context, Users.class));
                    }
                });

                AlertDialog a1= a11.create();
                a1.show();
            }
        });

        holder.price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(context, "Clicked on: " + dataList.get(position).getName(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder a11= new AlertDialog.Builder(context);
                a11.setTitle("Alert");
                a11.setMessage("Place order");
                a11.setPositiveButton("Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder a11= new AlertDialog.Builder(context);
                        a11.setTitle("Place order");
                        a11.setMessage("Please order as per your convinience");
                        a11.setNegativeButton("Order", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                orderData= new OrderDetailsData();

                                String itemName= dataList.get(position).getName();
                                String userName= dataList.get(position).getUsername();
                                String userMobile= dataList.get(position).getUsermobile();
                                String itemPrice= dataList.get(position).getPrice();
                                String itemImageUrl= dataList.get(position).getImageUrl();
                                String itemId= dataList.get(position).getId();
                                String itemQuantity= dataList.get(position).getQty();
                                String itemSeedsQuantity= dataList.get(position).getSeedsqty();

                                orderData.setItemId(itemId);
                                orderData.setItemImageUrl(itemImageUrl);
                                orderData.setItemName(itemName);
                                orderData.setItemPrice(itemPrice);
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
                a11.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      //  delete(position, dataList.get(position).getId(), holder);
                    }
                });

                AlertDialog a1= a11.create();
                a1.show();
            }
        });
    }

    private void order(int position) {

        dataList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,dataList.size());
        notifyDataSetChanged();


        Intent sendData= new Intent(context, PlaceOrder.class);
        sendData.putExtra("name", dataList.get(position).getName());
        sendData.putExtra("price", dataList.get(position).getPrice());
        sendData.putExtra("qty", dataList.get(position).getQty());
        sendData.putExtra("imageurl", dataList.get(position).getImageUrl());
        context.startActivity(sendData);
    }

    private void delete(int position, String id, ViewHolder holder) {

        System.out.println("From AddToCartAdapter id: " + id);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("AddToCartSeedsAndFertilizerPerUser").child(id);

        reference.removeValue();

     //   Toast.makeText(context, "Data has been deleted successfully", Toast.LENGTH_SHORT).show();

        dataList.remove(holder.getAdapterPosition());

        notifyItemRemoved(holder.getAdapterPosition());
        notifyItemRangeChanged(holder.getAdapterPosition(), dataList.size());

        int pos= holder.getAdapterPosition();

        System.out.println("From AddToCArtAdapter pos is: " + pos);

        Intent sendData= new Intent(context, AddToCart.class);
        sendData.putExtra("addtocartadapter", true);
        sendData.putExtra("pos", pos+"");

        context.startActivity(sendData);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout parent;
        PhotoView cig;
        TextView name, price, seedsqty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent= (ConstraintLayout) itemView.findViewById(R.id.parent_id);
            cig= (PhotoView) itemView.findViewById(R.id.img_id);
            name= (TextView) itemView.findViewById(R.id.name_id);
            price= (TextView) itemView.findViewById(R.id.price_id);
            seedsqty= (TextView) itemView.findViewById(R.id.seeds_qty_id);
        }
    }
}