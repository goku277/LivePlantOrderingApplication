package com.simi.plantorderingapp.Components;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.simi.plantorderingapp.Adapter.AddToCartAdapter;
import com.simi.plantorderingapp.Adapter.ShowMyOrdersAdapter;
import com.simi.plantorderingapp.Database.Profiledb;
import com.simi.plantorderingapp.ProcessingUnit.getMyOrdersData;
import com.simi.plantorderingapp.ProductModel.MyOrderData;
import com.simi.plantorderingapp.ProductModel.SaveCustomerData;
import com.simi.plantorderingapp.ProductModel.SaveMyOrdersData;
import com.simi.plantorderingapp.Profile.UpdateProfile;
import com.simi.plantorderingapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MyOrders extends AppCompatActivity {

    ArrayList<String> getMyOrderList= new ArrayList<>();

    String val11= "";

    Profiledb pf;

    String username="", usermobile="";

    getMyOrdersData getMyOrdersData1;

    ShowMyOrdersAdapter showMyOrdersAdapter;

    RecyclerView rec1;

    SaveMyOrdersData saveMyOrdersData;

    ArrayList<SaveMyOrdersData> saveMyOrdersDataList= new ArrayList<>();

    private void initializeProfile() {
        pf= new Profiledb(MyOrders.this);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        initializeProfile();

        getMyOrdersData1= new getMyOrdersData();

        rec1= (RecyclerView) findViewById(R.id.recyclerview_id);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Orders");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                            MyOrderData myOrderData= new MyOrderData((String) userData.get("orderedTotalItems"), (String) userData.get("itemName"), (String) userData.get("itemPrice"), (String) userData.get("itemQuantity")
                            , (String) userData.get("itemImageUrl"), (String) userData.get("userName"), (String) userData.get("userMobile"), (String) userData.get("orderStatus"), (String) userData.get("orderId"));
                            getMyOrderList.add("userName: " + myOrderData.getUserName());
                            getMyOrderList.add("userMobile: " + myOrderData.getUserMobile());
                            getMyOrderList.add("itemName: " + myOrderData.getItemName());
                            getMyOrderList.add("itemPrice: " + myOrderData.getItemPrice());
                            getMyOrderList.add("itemQuantity: " + myOrderData.getItemQuantity());
                            getMyOrderList.add("orderStatus: " + myOrderData.getOrderStatus());
                            getMyOrderList.add("itemImageUrl: " + myOrderData.getItemImageUrl());
                            getMyOrderList.add("Id: " + myOrderData.getOrderId());
                            //   System.out.println("From Tpo getData is: " + getData);

                        } catch (ClassCastException cce) {
                            try {

                                String mString = String.valueOf(dataMap.get(key));

                                System.out.println("From Tpo mString is: " + mString);


                            } catch (ClassCastException cce2) {

                            }
                        }

                        System.out.println("From MyOrders getMyOrderList: " + getMyOrderList);

                        for (String s: getMyOrderList) {
                            val11+= s + " ";
                        }
                    }

                    System.out.println("From MyOrders username: " + username + " and usermobile: " + usermobile);

                    System.out.println("From MyOrders val11: " + val11);

                    Set<Set<String>> aList= getMyOrdersData1.init(val11, username, usermobile);

                    System.out.println("From MyOrders aList is: " + aList);

                    ArrayList<String> setToList= null;

                    for (Set<String> set1: aList) {
                        setToList= new ArrayList<>();
                        for (String s: set1) {
                            if (s.contains("itemName:")) {
                                setToList.add(s);
                            }
                            if (s.contains("itemPrice:")) {
                                setToList.add(s);
                            }
                            if (s.contains("itemQty:")) {
                                setToList.add(s);
                            }
                            if (s.contains("PaymentStatus:")) {
                                setToList.add(s.substring(s.indexOf("PaymentStatus:")).trim());
                            }
                            if (s.contains("itemImageUrl: imageUri:")) {
                                setToList.add(s);
                            }
                            if (s.contains("Id: Id:")) {
                                setToList.add(s.substring(s.indexOf("Id: Id:")));
                                setToList.add(s.substring(s.indexOf("Id: Id:")).replace("Id: Id:", "").trim());
                            }
                        }
                        DisplayData(setToList);
                    }

                    LinearLayoutManager layoutManager= new LinearLayoutManager(MyOrders.this, LinearLayoutManager.VERTICAL, false);

                    rec1.setLayoutManager(layoutManager);

                    rec1.setItemAnimator(new DefaultItemAnimator());

                    showMyOrdersAdapter= new ShowMyOrdersAdapter(saveMyOrdersDataList, MyOrders.this);

                    rec1.setAdapter(showMyOrdersAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void DisplayData(ArrayList<String> setToList) {
        System.out.println("setToList is: " + setToList);
        System.out.println("setToList.get(0): " + setToList.get(0));
        System.out.println("setToList.get(3): " + setToList.get(3));
      //  try {
            saveMyOrdersData = new SaveMyOrdersData(setToList.get(0), "Item Price: " + setToList.get(1).replace("itemPrice: itemPrice:",""), setToList.get(4).replace("itemImageUrl: imageUri:", ""), "Item Quantity: " + setToList.get(2).replace("itemQuantity: itemQty:", ""), setToList.get(3).replace("orderStatus:","").trim(), "", "Id: " + setToList.get(5).replace("Id: Id:","").trim());
            saveMyOrdersDataList.add(saveMyOrdersData);
       // } catch (Exception e) {}
    }
}