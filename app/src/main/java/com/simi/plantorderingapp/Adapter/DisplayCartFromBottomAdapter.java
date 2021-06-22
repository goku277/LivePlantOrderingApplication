package com.simi.plantorderingapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.simi.plantorderingapp.Components.PlaceOrder;
import com.simi.plantorderingapp.Database.MapUrl;
import com.simi.plantorderingapp.ProductModel.OrderDetailsData;
import com.simi.plantorderingapp.ProductModel.SaveAddToCartSeedsAndFertilizers;
import com.simi.plantorderingapp.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

public class DisplayCartFromBottomAdapter extends RecyclerView.Adapter<DisplayCartFromBottomAdapter.ViewHolder>{
    ArrayList<SaveAddToCartSeedsAndFertilizers> aList11= new ArrayList<>();

    Context context;

    OrderDetailsData orderData;

    private DatabaseReference mDatabaseReference;

    FirebaseDatabase database1;

    MapUrl mapUrl;


    public DisplayCartFromBottomAdapter(ArrayList<SaveAddToCartSeedsAndFertilizers> aList11, Context context) {
        this.aList11 = aList11;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.display_cart_from_bottom_layout, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        System.out.println("From DisplayCartFromBottomAdapter onBindViewHolder() aList11: " + aList11.get(position).getName() + " " +
                aList11.get(position).getPrice() + " " + aList11.get(position).getCategory() + " "  +
                aList11.get(position).getId() + " "  + aList11.get(position).getImageuri() + " " + aList11.get(position).getUsername() + " " +
                aList11.get(position).getSeedsqty() + " " + aList11.get(position).getQuantity());

        database1= FirebaseDatabase.getInstance();

        mapUrl= new MapUrl(context);

        SQLiteDatabase db1 = mapUrl.getReadableDatabase();
        String query1 = "select * from map";
        Cursor c11 = db1.rawQuery(query1, null);

        String name= "", link="", cmpName="";

        if (c11!=null && c11.getCount() > 0) {
            if (c11.moveToFirst()) {
                do {
                    name= c11.getString(0);
                    if (aList11.get(position).getName().contains("Tulsi") || aList11.get(position).getName().contains("Neem") ||
                            aList11.get(position).getName().contains("Cactus") || aList11.get(position).getName().contains("Sunflower")) {
                        cmpName= aList11.get(position).getName().replace("Name:", "");
                    }
                    System.out.println("From DisplayCartFromBottomAdapter name is: " + name + "\tcmpName: " + cmpName);
                    if (name.equals(cmpName)) {
                        if (cmpName.contains("Sunflower")) {
                            holder.cig.setImageResource(R.drawable.sunflower_1);
                        }
                        else if (cmpName.contains("Cactus")) {
                            holder.cig.setImageResource(R.drawable.cactus_1);
                        }
                        else if (cmpName.contains("Neem")) {
                            holder.cig.setImageResource(R.drawable.neem_1);
                        }
                        else if (cmpName.contains("Tulsi")) {
                            holder.cig.setImageResource(R.drawable.tulsi_1);
                        }
                    }
                    System.out.println("user is: " + c11.getString(0));
                } while (c11.moveToNext());
            }
        }

        String img= aList11.get(position).getImageuri().replace("id:","").replace("imageuri:", "").replace("imageUri:", "").trim();
        Glide.with(context).load(img).into(holder.cig);
        holder.name.setText(aList11.get(position).getName());
        holder.price.setText(aList11.get(position).getPrice());
        holder.seedsqty.setText(aList11.get(position).getSeedsqty());

        System.out.println("From DisplayCartFromBottomAdapter onBindViewHolder() aList11.size() is: " + aList11.size());

        System.out.println("From DisplayCartFromBottomAdapter onBindViewHolder() img is: " + img);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context, "Clicked on: " + aList11.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a11= new AlertDialog.Builder(context);
                a11.setTitle("Place order");
                a11.setMessage("order is available.Please choose as per your convinience");
             /*   a11.setPositiveButton("Cod", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    //    Toast.makeText(context, "Order placed successfully", Toast.LENGTH_SHORT).show();
                    }
                });  */
                a11.setNegativeButton("Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        orderData= new OrderDetailsData();

                        String itemName= aList11.get(position).getName();
                        String userName= aList11.get(position).getUsername();
                        String userMobile= aList11.get(position).getMobile();
                        String itemPrice= aList11.get(position).getPrice();
                        String itemImageUrl= aList11.get(position).getImageuri();
                        String itemId= aList11.get(position).getId();
                        String itemSeedsQuantity= aList11.get(position).getSeedsqty();

                        orderData.setItemId(itemId);
                        orderData.setItemImageUrl("imageUri: " + itemImageUrl);
                        orderData.setItemName(itemName);
                        orderData.setItemPrice(itemPrice);
                        orderData.setItemSeedsQuantity(itemSeedsQuantity);
                        orderData.setUserMobile(userMobile);
                        orderData.setUserName(userName);

                        order(position);


                      /*  AlertDialog.Builder a11= new AlertDialog.Builder(context);
                        a11.setTitle("Place order");
                        a11.setMessage("Please order as per your convinience");
                        a11.setNegativeButton("Order", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                orderData= new OrderDetailsData();

                                String itemName= aList11.get(position).getName();
                                String userName= aList11.get(position).getUsername();
                                String userMobile= aList11.get(position).getMobile();
                                String itemPrice= aList11.get(position).getPrice();
                                String itemImageUrl= aList11.get(position).getImageuri();
                                String itemId= aList11.get(position).getId();
                                String itemSeedsQuantity= aList11.get(position).getSeedsqty();

                                orderData.setItemId(itemId);
                                orderData.setItemImageUrl("imageUri: " + itemImageUrl);
                                orderData.setItemName(itemName);
                                orderData.setItemPrice(itemPrice);
                                orderData.setItemSeedsQuantity(itemSeedsQuantity);
                                orderData.setUserMobile(userMobile);
                                orderData.setUserName(userName);

                           //     mDatabaseReference= database1.getInstance().getReference().child("Orders");

                           //     mDatabaseReference.child(userName + " " + userMobile + " " + "Order " + UUID.randomUUID().toString()).setValue(orderData);

                                order(position);
                            }
                        });
                        AlertDialog a1= a11.create();
                        a1.show();   */
                    }
                });
                AlertDialog a1= a11.create();
                a1.show();
            }
        });
    }

    private void order(int position) {
        System.out.println("From categoricalDataAdapter order() save.get(position).getName(): " + aList11.get(position).getName() + "\nsave.get(position).getPrice() " + aList11.get(position).getPrice() +
                "\nsave.get(position).getQty() " + aList11.get(position).getQuantity());
        Intent sendData= new Intent(context, PlaceOrder.class);
        sendData.putExtra("name", aList11.get(position).getName());
        sendData.putExtra("price", aList11.get(position).getPrice());
        sendData.putExtra("qty", aList11.get(position).getQuantity());
        sendData.putExtra("imageurl", aList11.get(position).getImageuri());
        sendData.putExtra("seedsqty", aList11.get(position).getSeedsqty());
        context.startActivity(sendData);
    }


    @Override
    public int getItemCount() {
        return aList11.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        PhotoView cig;
        Button order;
        TextView name, price, seedsqty;
        CardView cv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cv= (CardView) itemView.findViewById(R.id.cv_id);
            order= (Button) itemView.findViewById(R.id.order_id);
            cig= (PhotoView) itemView.findViewById(R.id.cig_id);
            name= (TextView) itemView.findViewById(R.id.name_id);
            price= (TextView) itemView.findViewById(R.id.price_id);
            seedsqty= (TextView) itemView.findViewById(R.id.seeds_qty_id);
        }
    }
}