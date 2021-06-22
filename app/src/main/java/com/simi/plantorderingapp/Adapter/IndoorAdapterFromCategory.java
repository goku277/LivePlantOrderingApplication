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
import com.simi.plantorderingapp.Components.DisplayIndoorItemsFromCategory;
import com.simi.plantorderingapp.Components.PlaceOrder;
import com.simi.plantorderingapp.Components.Users;
import com.simi.plantorderingapp.Components.WishList;
import com.simi.plantorderingapp.Database.Profiledb;
import com.simi.plantorderingapp.ProcessingUnit.AddToCartData;
import com.simi.plantorderingapp.ProductModel.OrderDetailsData;
import com.simi.plantorderingapp.ProductModel.SaveAddToCartItemsDetails;
import com.simi.plantorderingapp.ProductModel.SaveINdoorItemsFromCategoryModelData;
import com.simi.plantorderingapp.R;

import java.util.ArrayList;
import java.util.UUID;

public class IndoorAdapterFromCategory extends RecyclerView.Adapter<IndoorAdapterFromCategory.ViewHolder>{

    ArrayList<SaveINdoorItemsFromCategoryModelData> saveINdoorItemsFromCategoryModelDataList;
    Context context;

    String Name="", Price="", Detail="", Category="", ImageUrl="";

    SaveAddToCartItemsDetails saveAddToCartItemsDetails;

    Profiledb pdb;

    String username="", usermobile="";

    ArrayList<String> getCartDetails= new ArrayList<>();

    AddToCartData add;

    Profiledb pf;

    private DatabaseReference mDatabaseReference;

    FirebaseDatabase database;

    String getId="";

    OrderDetailsData orderData;

    FirebaseDatabase database1;
    DatabaseReference ref1;

    public IndoorAdapterFromCategory(ArrayList<SaveINdoorItemsFromCategoryModelData> saveINdoorItemsFromCategoryModelDataList, Context context) {
        this.saveINdoorItemsFromCategoryModelDataList= saveINdoorItemsFromCategoryModelDataList;
        this.context= context;
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.display_indoor_items_layout, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        System.out.println("saveINdoorItemsFromCategoryModelDataList.get(position).getImgUrl(): " + saveINdoorItemsFromCategoryModelDataList.get(position).getImgUrl() + "\n" +
                saveINdoorItemsFromCategoryModelDataList.get(position).getName()  + "\n" + saveINdoorItemsFromCategoryModelDataList.get(position).getDetail() + "\n" +
                saveINdoorItemsFromCategoryModelDataList.get(position).getPrice());
        Glide.with(context).load(saveINdoorItemsFromCategoryModelDataList.get(position).getImgUrl()).into(holder.img);
        holder.name.setText(saveINdoorItemsFromCategoryModelDataList.get(position).getName().replace("name:", "Name: "));
        holder.details.setText(saveINdoorItemsFromCategoryModelDataList.get(position).getDetail().replace("detail:", "Detail: "));
        holder.price.setText(saveINdoorItemsFromCategoryModelDataList.get(position).getPrice().replace("price:", "Price "));

        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(context, "Clicked on Add To Cart", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder a11= new AlertDialog.Builder(context);
                a11.setTitle("Add to cart");
                a11.setMessage("Are you sure that you want to add this items to the cart");
                a11.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      //  addToCart(holder, saveINdoorItemsFromCategoryModelDataList.get(position).getImgUrl());
                        addToCart("Indoor plants", holder.price, holder.details, holder.details, holder.name, saveINdoorItemsFromCategoryModelDataList.get(position).getImgUrl(), position);
                    }
                });

                a11.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog a1= a11.create();
                a1.show();
            }
        });

        holder.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Users.class));
            }
        });

        holder.wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name=  saveINdoorItemsFromCategoryModelDataList.get(position).getName().replace("name:", "Name:");
                Price= saveINdoorItemsFromCategoryModelDataList.get(position).getPrice().replace("price:", "Price:");
                Category= "Category: Indoor plants";
                Detail= saveINdoorItemsFromCategoryModelDataList.get(position).getDetail().replace("detail:", "Detail:");
                ImageUrl= "imageUri: " + saveINdoorItemsFromCategoryModelDataList.get(position).getImgUrl();

                String concat= Name + " " + Price + " " + Category + " " + Detail + " " + ImageUrl + "quantity:";

                System.out.println("From CategoricalDataAdapter concat is: " + concat);

                Intent sendData= new Intent(context, WishList.class);

                sendData.putExtra("concat", concat);

                context.startActivity(sendData);
            }
        });

        holder.order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  getIds(position);
                Toast.makeText(context, "Clicked on Order Items", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder a11= new AlertDialog.Builder(context);
                a11.setTitle("Alert");
                a11.setMessage("Place order");
                a11.setPositiveButton("Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        orderData= new OrderDetailsData();

                        initializeProfile();

                        String itemName= saveINdoorItemsFromCategoryModelDataList.get(position).getName();
                        String userName= username;
                        String userMobile= usermobile;
                        String itemPrice= saveINdoorItemsFromCategoryModelDataList.get(position).getPrice();
                        String itemImageUrl= saveINdoorItemsFromCategoryModelDataList.get(position).getImgUrl();
                        String itemId= "";
                        String itemQuantity= "";
                        String itemSeedsQuantity= "";

                        orderData.setItemId(itemId);
                        orderData.setItemImageUrl(itemImageUrl);
                        orderData.setItemName(itemName);
                        orderData.setItemPrice(itemPrice);
                        orderData.setItemQuantity(itemQuantity);
                        orderData.setItemSeedsQuantity(itemSeedsQuantity);
                        orderData.setUserMobile(userMobile);
                        orderData.setUserName(userName);

                        order(position);


                    /*    AlertDialog.Builder a11= new AlertDialog.Builder(context);
                        a11.setTitle("Place order");
                        a11.setMessage("Please order as per your convinience");
                        a11.setNegativeButton("Order", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                orderData= new OrderDetailsData();

                                initializeProfile();

                                String itemName= save.get(position).getName();
                                String userName= username;
                                String userMobile= usermobile;
                                String itemPrice= save.get(position).getPrice();
                                String itemImageUrl= save.get(position).getImageUri();
                                String itemId= "";
                                String itemQuantity= save.get(position).getQuantity();
                                String itemSeedsQuantity= "";

                                orderData.setItemId(itemId);
                                orderData.setItemImageUrl(itemImageUrl);
                                orderData.setItemName(itemName);
                                orderData.setItemPrice(itemPrice);
                                orderData.setItemQuantity(itemQuantity);
                                orderData.setItemSeedsQuantity(itemSeedsQuantity);
                                orderData.setUserMobile(userMobile);
                                orderData.setUserName(userName);

                           //     mDatabaseReference= database1.getInstance().getReference().child("Orders");

                          //      mDatabaseReference.child(userName + " " + userMobile + " " + "Order " + UUID.randomUUID().toString()).setValue(orderData);

                                order(position);
                            }
                        });
                        AlertDialog a1= a11.create();
                        a1.show();    */
                    }
                });
                a11.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // delete();
                    }
                });

                AlertDialog a1= a11.create();
                a1.show();
            }
        });

    }

    private void addToCart(String indoor_plants, TextView price, TextView details, TextView details1, TextView name, String imgUrl, int position) {
        ref1= FirebaseDatabase.getInstance().getReference().child("AddToCartPerUser");
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
                username= c1.getString(0);
                usermobile= c1.getString(1);
            }
        }

        String rand= UUID.randomUUID().toString().trim();

        saveAddToCartItemsDetails= new SaveAddToCartItemsDetails(username, usermobile, name.getText().toString().trim(), details.getText().toString().trim(), price.getText().toString().trim(), indoor_plants, saveINdoorItemsFromCategoryModelDataList.get(position).getImgUrl(), "", rand);

        saveAddToCartItemsDetails.setCategory("Category: " + indoor_plants);
        saveAddToCartItemsDetails.setImageuri("imageUri: " + imgUrl);
        saveAddToCartItemsDetails.setItemdetail("Detail: " + details.getText().toString().trim());
        saveAddToCartItemsDetails.setMobile("usermobile: " + usermobile);
        saveAddToCartItemsDetails.setPrice(price.getText().toString().replace("Price", "price:").trim());
        saveAddToCartItemsDetails.setUsername("username: " + username);
        saveAddToCartItemsDetails.setId("id: " + rand);
        saveAddToCartItemsDetails.setName(name.getText().toString().trim());
        saveAddToCartItemsDetails.setQuantity("quantity: ");
        ref1.child(username + " " + usermobile + " " + rand).setValue(saveAddToCartItemsDetails);

     //   Toast.makeText(context, "Added to cart successfully", Toast.LENGTH_SHORT).show();

      /*  AlertDialog.Builder a11= new AlertDialog.Builder(context);
        a11.setTitle("Check cart");
        a11.setMessage("Do you want to check your cart items?");
        a11.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkCart();
            }
        });

        a11.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog a1= a11.create();
        a1.show();  */

        context.startActivity(new Intent(context, Users.class));
    }

    private void order(int position) {

        System.out.println("From IndoorAdapterFromCategory order() save.get(position).getName(): " + saveINdoorItemsFromCategoryModelDataList.get(position).getName() + "\nsave.get(position).getPrice() " + saveINdoorItemsFromCategoryModelDataList.get(position).getPrice() + "\n imageUrl:" +
                saveINdoorItemsFromCategoryModelDataList.get(position).getImgUrl());

        Intent sendData= new Intent(context, PlaceOrder.class);
        sendData.putExtra("name", saveINdoorItemsFromCategoryModelDataList.get(position).getName());
        sendData.putExtra("price", saveINdoorItemsFromCategoryModelDataList.get(position).getPrice());
        sendData.putExtra("qty", "");
        sendData.putExtra("imageurl", saveINdoorItemsFromCategoryModelDataList.get(position).getImgUrl());

        context.startActivity(sendData);
    }


    private void addToCart(CategoricalDataAdapter.ViewHolder holder, String imageUri) {
        ref1= FirebaseDatabase.getInstance().getReference().child("AddToCartPerUser");
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
                username= c1.getString(0);
                usermobile= c1.getString(1);
            }
        }

        String rand= UUID.randomUUID().toString().trim();

        saveAddToCartItemsDetails= new SaveAddToCartItemsDetails(username, usermobile, holder.name.getText().toString().trim(), holder.details.getText().toString().trim(), holder.price.getText().toString().trim(), holder.category.getText().toString().trim(), saveINdoorItemsFromCategoryModelDataList.get(holder.getAdapterPosition()).getImgUrl(), holder.quantity.getText().toString().trim(), rand);

        saveAddToCartItemsDetails.setCategory(holder.category.getText().toString().trim());
        saveAddToCartItemsDetails.setImageuri(imageUri);
        saveAddToCartItemsDetails.setItemdetail(holder.details.getText().toString().trim());
        saveAddToCartItemsDetails.setMobile("usermobile: " + usermobile);
        saveAddToCartItemsDetails.setPrice(holder.price.getText().toString().replace("Price", "price").trim());
        saveAddToCartItemsDetails.setUsername("username: " + username);
        saveAddToCartItemsDetails.setId("id: " + rand);
        saveAddToCartItemsDetails.setName(holder.name.getText().toString().trim());
        saveAddToCartItemsDetails.setQuantity("");
        ref1.child(username + " " + usermobile + " " + rand).setValue(saveAddToCartItemsDetails);

    //    Toast.makeText(context, "Added to cart successfully", Toast.LENGTH_SHORT).show();

      /*  AlertDialog.Builder a11= new AlertDialog.Builder(context);
        a11.setTitle("Check cart");
        a11.setMessage("Do you want to check your cart items?");
        a11.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkCart();
            }
        });

        a11.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog a1= a11.create();
        a1.show();  */

        context.startActivity(new Intent(context, Users.class));
    }

    @Override
    public int getItemCount() {
        return saveINdoorItemsFromCategoryModelDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, price, details;
        Button cart, order, wishlist;
        ImageView back;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img=(ImageView) itemView.findViewById(R.id.img_id);
            name= (TextView) itemView.findViewById(R.id.name_id);
            price= (TextView) itemView.findViewById(R.id.price_id);
            details= (TextView) itemView.findViewById(R.id.detail_id);

            cart= (Button) itemView.findViewById(R.id.add_to_cart_id);
            order= (Button) itemView.findViewById(R.id.order_id);
            wishlist= (Button) itemView.findViewById(R.id.add_to_wishlist_id);

            back= (ImageView) itemView.findViewById(R.id.back_id);
        }
    }
}
