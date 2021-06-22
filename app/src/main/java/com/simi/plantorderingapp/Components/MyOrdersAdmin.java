package com.simi.plantorderingapp.Components;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.simi.plantorderingapp.Adapter.MyOrdersAdminAdapter;
import com.simi.plantorderingapp.Adapter.ShowMyOrdersAdapter;
import com.simi.plantorderingapp.Database.Profiledb;
import com.simi.plantorderingapp.ProcessingUnit.MyOrdersAdminData;
import com.simi.plantorderingapp.ProductModel.MyOrderData;
import com.simi.plantorderingapp.ProductModel.SaveMyOrdersAdminDataModel;
import com.simi.plantorderingapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class MyOrdersAdmin extends AppCompatActivity {

    ArrayList<String> getMyOrderList= new ArrayList<>();

    String val11="";

    Profiledb pf;

    String username="", usermobile="";

    MyOrdersAdminData myOrdersAdminData;

    Set<Set<String>> aList= new LinkedHashSet<>();

    SaveMyOrdersAdminDataModel saveMyOrdersAdminDataModel;

    ArrayList<SaveMyOrdersAdminDataModel> saveMyOrdersAdminDataModelList= new ArrayList<>();

    RecyclerView rec1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders_admin);

        myOrdersAdminData= new MyOrdersAdminData();

        rec1= (RecyclerView) findViewById(R.id.recycler_id);

        pf= new Profiledb(MyOrdersAdmin.this);

        initializeProfile();

        fetchOrder();
    }

    private void initializeProfile() {
        pf= new Profiledb(MyOrdersAdmin.this);
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

    private void fetchOrder() {
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
                                System.out.println("From MyOrdersAdmin mString is: " + mString);
                            } catch (ClassCastException cce2) {

                            }
                        }
                        System.out.println("From MyOrdersAdmin getMyOrderList: " + getMyOrderList);
                        for (String s: getMyOrderList) {
                            val11+= s + " ";
                        }
                    }

                    System.out.println("From MyOrdersAdmin username: " + username + " and usermobile: " + usermobile);
                    System.out.println("From MyOrdersAdmin val11: " + val11);
                    aList= myOrdersAdminData.init(val11);
                    System.out.println("aList is: " + aList);
                    for (Set<String> set1: aList) {
                        ArrayList<String> setToList= new ArrayList<>();
                        setToList.addAll(set1);
                        storeData(setToList);
                    }

                    LinearLayoutManager layoutManager= new LinearLayoutManager(MyOrdersAdmin.this, LinearLayoutManager.VERTICAL, false);

                    rec1.setLayoutManager(layoutManager);

                    rec1.setItemAnimator(new DefaultItemAnimator());

                    MyOrdersAdminAdapter myOrdersAdminAdapter = new MyOrdersAdminAdapter(saveMyOrdersAdminDataModelList, MyOrdersAdmin.this);

                    rec1.setAdapter(myOrdersAdminAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void storeData(ArrayList<String> setToList) {
        System.out.println("setToList: " + setToList);
        saveMyOrdersAdminDataModel= new SaveMyOrdersAdminDataModel(setToList.get(0), setToList.get(1), setToList.get(2), setToList.get(3), setToList.get(4), setToList.get(5), "", setToList.get(6));
        saveMyOrdersAdminDataModelList.add(saveMyOrdersAdminDataModel);
    }
}