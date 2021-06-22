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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.simi.plantorderingapp.Components.MyOrders;
import com.simi.plantorderingapp.Components.Users;
import com.simi.plantorderingapp.Database.Profiledb;
import com.simi.plantorderingapp.ProcessingUnit.getCustomerData;
import com.simi.plantorderingapp.ProductModel.SaveCustomerData;
import com.simi.plantorderingapp.ProductModel.SaveMyOrdersData;
import com.simi.plantorderingapp.Profile.DisplayProfile;
import com.simi.plantorderingapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

public class ShowMyOrdersAdapter extends RecyclerView.Adapter<ShowMyOrdersAdapter.ViewHolder>{

    ArrayList<SaveMyOrdersData> saveMyOrdersDataList;

    Context context;

    Profiledb pf;

    String username="", usermobile="";

    ArrayList<String> getCustomerData= new ArrayList<>();

    ArrayList<String> setToList= new ArrayList<>();

    com.simi.plantorderingapp.ProcessingUnit.getCustomerData csData;

    public ShowMyOrdersAdapter(ArrayList<SaveMyOrdersData> saveMyOrdersDataList, Context context) {
        this.saveMyOrdersDataList= saveMyOrdersDataList;
        this.context= context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.myorder_layout_items, parent, false);
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
        initializeProfile();
        System.out.println("From ShowMyOrdersAdapter saveMyOrdersDataList.get(position).getItemImageUrl(): " + saveMyOrdersDataList.get(position).getItemImageUrl());
        System.out.println("From ShowMyOrdersAdapter username is: " + username + "\tusermobile: " + usermobile);
        System.out.println("From saveMyOrdersDataList: " + saveMyOrdersDataList.get(position).getItemName());
        System.out.println("From saveMyOrdersDataList saveMyOrdersDataList.get(position).getId(): " + saveMyOrdersDataList.get(position).getId());
        try {
           // holder.username.setText("User name: " + username);
         //   holder.usermobile.setText("User Mobile: " + usermobile);
            Glide.with(context).load(saveMyOrdersDataList.get(position).getItemImageUrl().replace("itemImageUrl: imageUri:", "").trim()).into(holder.cig);
            holder.itemName.setText("ItemName: " + saveMyOrdersDataList.get(position).getItemName().replace("itemName:", "ItemName:").replace("ItemName:",""));
            holder.itemPrice.setText("ItemPrice: " + saveMyOrdersDataList.get(position).getItemPrice().replace("itemPrice:", "ItemPrice:").replace("ItemPrice:","").replace("ItemPrice:","").replace("Item Price:", "").trim());
            holder.itemQuantity.setText("ItemQuantity: " + saveMyOrdersDataList.get(position).getItemQuantity().replace("itemQuantity:", "ItemQuantity:").replace("ItemQuantity:","").replace("itemQty:","").replace("Item Quantity:","").trim());
            String q[]= saveMyOrdersDataList.get(position).getQuantity().replace("quantity:", "").split("\\s+");
          //  holder.quantity.setText("Quantity: " + q[0] + " " + q[1]);
            holder.orderStatus.setText(saveMyOrdersDataList.get(position).getOrderStatus().replace("orderStatus:", "OrderStatus:").replace("OrderStatus:",""));
            holder.orderId.setText("Order: " + saveMyOrdersDataList.get(position).getId());
        } catch (Exception e) {}

        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                System.out.println("From ShowMyOrdersAdapter onBindViewHolder() currentDate is: " + currentDate);

                String date= getShipmentDate(currentDate);

                System.out.println("From ShowMyOrdersAdapter onBindViewHolder() date is: " + date);

                makeShippingData(date, position);
            }
        });
    }

    private void makeShippingData(String date, int position) {
        display(date, position);
    }

    private void display(String date, int position) {

        csData= new getCustomerData();
        //    System.out.println("From Profile display() username: " + username + " usermobile: " + usermobile);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Customer");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                            SaveCustomerData customerData = new SaveCustomerData((String) userData.get("name"), (String) userData.get("email"), (String) userData.get("password"), (String) userData.get("phone"), (String) userData.get("address"), (String) userData.get("pin"), (String) userData.get("city"), (String) userData.get("location"));

                            getCustomerData.add("userName: " + customerData.getName());
                            getCustomerData.add("email: " + customerData.getEmail());
                            getCustomerData.add("password: " + customerData.getPassword());
                            getCustomerData.add("phone: " + customerData.getPhone());
                            getCustomerData.add("address: " + customerData.getAddress());
                            getCustomerData.add("pin: " + customerData.getPin());
                            getCustomerData.add("city: " + customerData.getCity());
                            getCustomerData.add("location: " + customerData.getLocation());
                            //   System.out.println("From Tpo getData is: " + getData);

                        } catch (ClassCastException cce) {
                            try {

                                String mString = String.valueOf(dataMap.get(key));

                                //     System.out.println("From Tpo mString is: " + mString);


                            } catch (ClassCastException cce2) {

                            }
                        }

                        //   System.out.println("getCustomerData " + getCustomerData);

                        String val11= "";

                        for (String s: getCustomerData) {
                            val11+= s + " ";
                        }

                        //    System.out.println("From Profile val11: " + val11);

                        Set<String> a1= csData.init(val11, username, usermobile);

                        //   System.out.println("From Profile a1: " + a1);

                        setToList.addAll(a1);

                    //    System.out.println("From howMyOrdersAdapter display() setToList: " + setToList);

                      //  Intent sendData= new Intent(Users.this, DisplayProfile.class);
                      //  sendData.putStringArrayListExtra("settolist", setToList);
                      //  startActivity(sendData);
                    }

                    System.out.println("From howMyOrdersAdapter display() setToList: " + setToList);

                    Set<String> listToSet= new LinkedHashSet<>();
                    listToSet.addAll(setToList);
                    System.out.println("From howMyOrdersAdapter display() listToSet: " + listToSet);
                    setToList.clear();
                    setToList.addAll(listToSet);
                    System.out.println("From howMyOrdersAdapter display() setToList: " + setToList);

                    StringBuilder sb1= new StringBuilder();

                    String itemName= saveMyOrdersDataList.get(position).getItemName();
                    String price= saveMyOrdersDataList.get(position).getItemPrice();
                    String qty= saveMyOrdersDataList.get(position).getItemQuantity();

                    sb1.append("Your item " + itemName.replace("itemName:", "") + " \n" + price + "\n" + qty.replace("itemQty:", ""));
                    sb1.append(" will be possibly delivered on " + date + "\n");
                    sb1.append("To: " + setToList.get(0) + "\n\n");
                    sb1.append("Emailid: " + setToList.get(1).replace("email:","").trim() + "\n\n");
                    sb1.append("Phone: " + setToList.get(3).replace("phone:", "") + "\n\n");
                    sb1.append("Address: " + setToList.get(4).replace("address:", "") + "\n\n");
                    sb1.append("Pin " + setToList.get(5).replace("pin:", "") + "\n\n");
                    sb1.append("City " + setToList.get(6).replace("city:", "") + "\n\n");
                    sb1.append("Location: " + setToList.get(7).replace("location:", "") + "\n\n");

                    AlertDialog.Builder a11= new AlertDialog.Builder(context);
                    a11.setTitle("Shipping Details");
                    a11.setMessage("Your shipping details:-" + "\n\n" + sb1);

                    a11.setIcon(R.drawable.order_icon);

                    a11.setCancelable(false);
                    a11.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog a1= a11.create();
                    a1.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String getShipmentDate(String currentDate) {
        String dateFormat[]= currentDate.split("\\-");
        String day="", month="", year="";
        for (String s: dateFormat) {
            System.out.println("dateFormat: " + s);
            day= dateFormat[0];
            month= dateFormat[1];
            year= dateFormat[2];
        }
        if (Integer.parseInt(day) >= 21)  {
            day= "01";
            month= String.valueOf(Integer.parseInt(month) +1);
        }
        else if (Integer.parseInt(month) == 12 && Integer.parseInt(day) >= 21) {
            day= "01";
            month="01";
            year= String.valueOf(Integer.parseInt(year) +1);
        }
        else {
            day= String.valueOf(Integer.parseInt(day) + 10);
        }
        return day + "-" + month + "-" + year;
    }

    @Override
    public int getItemCount() {
        return saveMyOrdersDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cig, info;
        TextView itemName, itemPrice, itemQuantity, orderStatus, orderId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cig= (ImageView) itemView.findViewById(R.id.cig_id);
            itemName= itemView.findViewById(R.id.itemname_id);
            itemPrice= itemView.findViewById(R.id.itemprice_id);
            itemQuantity= itemView.findViewById(R.id.itemquantity_id);
            orderStatus= itemView.findViewById(R.id.orderstatus_id);
            orderId= itemView.findViewById(R.id.order_id);
            info= itemView.findViewById(R.id.info_id);
        }
    }
}