package com.simi.plantorderingapp.Components;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.simi.plantorderingapp.Database.Profiledb;
import com.simi.plantorderingapp.PaymentGateway.Paymentgateway;
import com.simi.plantorderingapp.ProcessingUnit.GetIdsFromAddToCart;
import com.simi.plantorderingapp.ProcessingUnit.GetIdsFromSeedsAndFertilizers;
import com.simi.plantorderingapp.ProductModel.CategoricalCartData;
import com.simi.plantorderingapp.ProductModel.MyOrderData;
import com.simi.plantorderingapp.ProductModel.OrderDetailsData;
import com.simi.plantorderingapp.ProductModel.SeedsAndFerrtilizersCart;
import com.simi.plantorderingapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;


public class PlaceOrder extends AppCompatActivity {

    String name="", price="", qty="", imageUrl="", seedqty="";

    TextView name1, price1, qty1, number1, seedqty1;

    ImageButton plus, minus;

    PhotoView cig;

    Button order;

    long initial=0, res=0;

    String itemPrice="", itemQty="";

    Profiledb pf;

    OrderDetailsData orderData;

    private DatabaseReference mDatabaseReference;

    FirebaseDatabase database;

    String username="", usermobile="";

    MyOrderData myOrderData;

    GetIdsFromAddToCart getIdsFromAddToCart;

    String getAddToCartId="";

    ArrayList<String> getAllIds= new ArrayList<>();

    GetIdsFromSeedsAndFertilizers getIdsFromSeedsAndFertilizers;

    String getSeedAndFertilizersId="";

    boolean seedAndFertilizer= false, addToCart= false;

    Map<String, String> mapCartTypeToId= new LinkedHashMap<>();

    boolean isCOD= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        database = FirebaseDatabase.getInstance();

        getIdsFromAddToCart = new GetIdsFromAddToCart();

        getIdsFromSeedsAndFertilizers = new GetIdsFromSeedsAndFertilizers();

        initializeProfile();

        myOrderData = new MyOrderData();

        name1 = (TextView) findViewById(R.id.name_id);
        price1 = (TextView) findViewById(R.id.price_id);
        qty1 = (TextView) findViewById(R.id.quantity_status_id);

        number1 = (TextView) findViewById(R.id.number_id);

        plus = (ImageButton) findViewById(R.id.plus_id);
        minus = (ImageButton) findViewById(R.id.minus_id);

        order = (Button) findViewById(R.id.place_order_id);

        cig = (PhotoView) findViewById(R.id.cig_id);

        seedqty1 = (TextView) findViewById(R.id.seed_qty_id);

        Intent getData = getIntent();
        name = getData.getStringExtra("name");
        price = getData.getStringExtra("price");
        qty = getData.getStringExtra("qty");
        imageUrl = getData.getStringExtra("imageurl");
        seedqty = getData.getStringExtra("seedsqty");

        //  if (getAddToCartId!=null) {
        //     System.out.println("getAddToCartId: " + getAddToCartId);
        //   DatabaseReference dbref= FirebaseDatabase.getInstance().getReference("AddToCartPerUser").child(getAddToCartId);
        //   dbref.removeValue();
        //  }

        //  if (getSeedAndFertilizersId!=null) {
        //      System.out.println("getSeedAndFertilizer: " + getSeedAndFertilizersId);
        //   DatabaseReference dbref= FirebaseDatabase.getInstance().getReference("AddToCartSeedsAndFertilizerPerUser").child(getSeedAndFertilizersId);
        //   dbref.removeValue();
        //   }

        if (seedqty != null) {
            seedqty1.setText(seedqty);
        }

        name1.setText(name);
        price1.setText(price);

        try {
            qty = qty.replace("quantity:", "").replace("Quantity:", "").trim();
        } catch (Exception e) {}

        String q[]= qty.split("");

        for (String s: q) {
            System.out.println("q is: " + s);
        }

        if (qty.length() > 1) {
            String qqty = q[0];
            qty1.setVisibility(View.VISIBLE);
           // qty1.setText("Hurry as only " + qqty + " items left");
        }

        else if (qty.length() == 1){
            qty1.setVisibility(View.VISIBLE);
            qty1.setText("Hurry as only " + qty + " items left");
        }

        Glide.with(PlaceOrder.this).load(imageUrl.replace("imageUri:","").trim()).into(cig);

        number1.setText(String.valueOf(initial));

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (initial < 0) {
                    initial=0;
                }
                res= initial++;
                System.out.println("From plus res: " + initial);
                itemPrice= String.valueOf(Long.valueOf(price.replace("Price:", "").replace("price:","").replace("..","").trim()) * Long.valueOf(initial));
                itemQty= String.valueOf(initial);
                number1.setText(String.valueOf(initial));
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (initial < 0) {
                    initial= 0;
                }
                res= initial--;
                System.out.println("From minus res: " + initial);
                itemPrice= String.valueOf(Long.valueOf(price.replace("Price:", "").trim()) * Long.valueOf(initial));
                itemQty= String.valueOf(initial);
                number1.setText(String.valueOf(initial));
            }
        });
        System.out.println("From PlaceOrder onCreate() " + name + "\t" + price + "\t" + qty);

        System.out.println("res is: " + itemPrice);

        final String itemName= name;
      //  final String itemPrice= String.valueOf(Long.valueOf(price.replace("Price:", "").trim()) * Long.valueOf(res));
      //  final String itemQty= String.valueOf(res);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder a010= new AlertDialog.Builder(PlaceOrder.this);
                a010.setMessage("Choose between COD and Online Order");
                a010.setPositiveButton("COD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        isCOD= true;

                        if (!imageUrl.contains("imageUri:")) {
                            myOrderData.setItemImageUrl("imageUri: " + imageUrl);
                        }
                        else myOrderData.setItemImageUrl(imageUrl);
                        myOrderData.setItemName("itemName: " + itemName.replace("\t","").replace("Name:","").trim());
                        myOrderData.setItemPrice("itemPrice: " + itemPrice);
                        myOrderData.setItemQuantity("itemQty: " + itemQty);
                        myOrderData.setUserMobile("userMobile: " + usermobile);
                        myOrderData.setUserName("userName: " + username);
                        myOrderData.setOrderStatus("PaymentStatus: " + "COD");

                        String key= UUID.randomUUID().toString();

                        myOrderData.setOrderId("Id: " + key);

                        mDatabaseReference= database.getInstance().getReference().child("Orders");

                        mDatabaseReference.child(username + " " + usermobile + " " + "Order " + key).setValue(myOrderData);

                        getIdFromAddToCart();
                        getIdFromSeedsAndFertilizers();

                    }
                });
                a010.setNegativeButton("Online Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!imageUrl.contains("imageUri:")) {
                            myOrderData.setItemImageUrl("imageUri: " + imageUrl);
                        }
                        else myOrderData.setItemImageUrl(imageUrl);
                        myOrderData.setItemName("itemName: " + itemName.replace("\t","").replace("Name:","").trim());
                        myOrderData.setItemPrice("itemPrice: " + itemPrice);
                        myOrderData.setItemQuantity("itemQty: " + itemQty);
                        myOrderData.setUserMobile("userMobile: " + usermobile);
                        myOrderData.setUserName("userName: " + username);
                        myOrderData.setOrderStatus("PaymentStatus: " + "Online");
                        String key= UUID.randomUUID().toString();
                        myOrderData.setOrderId("Id: " + key);

                        mDatabaseReference= database.getInstance().getReference().child("Orders");

                        mDatabaseReference.child(username + " " + usermobile + " " + "Order " + key).setValue(myOrderData);

                        getIdFromAddToCart();
                        getIdFromSeedsAndFertilizers();

                        openPaymentGateway(itemName, itemPrice, itemQty);
                    }
                });
                AlertDialog a1= a010.create();
                a1.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PlaceOrder.this, Users.class));
        finish();
    }

    private void display() {
        System.out.println("mapCartTypeToId: " + mapCartTypeToId);
        String key="", value= "";
        for (Map.Entry<String, String> e1: mapCartTypeToId.entrySet()) {
            key= e1.getKey();
            value= e1.getValue();
        }
        System.out.println("From display() key is: " + key + "\tvalue is: " + value);
        DatabaseReference dbref= FirebaseDatabase.getInstance().getReference(key.trim()).child(value.trim());
        dbref.removeValue();
        mapCartTypeToId.clear();
    }

    private void getIdFromSeedsAndFertilizers() {
     //   Toast.makeText(this, "getIdFromSeedsAndFertilizers() initiated...", Toast.LENGTH_SHORT).show();
        System.out.println("getIdFromSeedsAndFertilizers() initiated...");
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

                //    System.out.println("getAllIds: " + getAllIds);

                    String val1= "";

                    for (String s: getAllIds) {
                        val1+= s + " ";
                    }
               //    System.out.println("From SeedsAndFertilizersAdapter getIds() val1: " + val1);

                    getSeedAndFertilizersId = getIdsFromSeedsAndFertilizers.init(val1, name, price, qty);

                    System.out.println("From SeedsAndFertilizersAdapter getId(): " + getSeedAndFertilizersId);

                    if (getSeedAndFertilizersId.trim().length()!=0) {
                        mapCartTypeToId.put("AddToCartSeedsAndFertilizerPerUser", getSeedAndFertilizersId);

                        display();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getIdFromAddToCart() {
     //   Toast.makeText(this, "getIdFromAddToCart() initiated...", Toast.LENGTH_SHORT).show();
      //  System.out.println("getIdFromAddToCart() initiated...");
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

                            CategoricalCartData saveCart = new CategoricalCartData((String) userData.get("category"), (String) userData.get("id"), (String) userData.get("imageuri"), (String) userData.get("itemdetail"), (String) userData.get("mobile"), (String) userData.get("name"), (String) userData.get("price"), (String) userData.get("quantity"), (String) userData.get("username"));
                            getAllIds.add(saveCart.getUsername());
                            getAllIds.add(saveCart.getMobile());
                            getAllIds.add(saveCart.getName());
                            getAllIds.add(saveCart.getPrice());
                            getAllIds.add(saveCart.getCategory());
                            getAllIds.add(saveCart.getId());
                            getAllIds.add(saveCart.getImageuri());
                            getAllIds.add(saveCart.getItemdetail());
                            getAllIds.add(saveCart.getQuantity());


                        } catch (ClassCastException cce) {
                            try {
                                String mString = String.valueOf(dataMap.get(key));
                                System.out.println("From Tpo mString is: " + mString);
                            } catch (ClassCastException cce2) {

                            }
                        }
                    }

                //    System.out.println("getAllIds: " + getAllIds);

                    String val1= "";

                    for (String s: getAllIds) {
                        val1+= s + " ";
                    }
              //      System.out.println("From CategoricalDataAdapter getIds() val1: " + val1);

                    getAddToCartId= getIdsFromAddToCart.init(val1, name, price, qty);

                    System.out.println("From CategoricalDataAdapter getIds() getId: " + getAddToCartId);

                    if (getAddToCartId.trim().length()!=0) {
                        mapCartTypeToId.put("AddToCartPerUser", getAddToCartId);

                        display();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initializeProfile() {
        pf= new Profiledb(PlaceOrder.this);
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

    private void openPaymentGateway(String itemName, String itemPrice, String itemQty) {
     //   Toast.makeText(this, "Paymentgateway initiated...", Toast.LENGTH_SHORT).show();
        System.out.println("Paymentgateway initiated...");
        Intent sendData= new Intent(PlaceOrder.this, Paymentgateway.class);
        sendData.putExtra("itemname", itemName);
        sendData.putExtra("itemprice", itemPrice);
        sendData.putExtra("itemqty", itemQty);
        startActivity(sendData);
    }
}