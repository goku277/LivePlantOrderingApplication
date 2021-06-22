package com.simi.plantorderingapp.PaymentGateway;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.simi.plantorderingapp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Paymentgateway extends AppCompatActivity implements PaymentResultWithDataListener {

    TextView name, amount, email, mobile;

    String itemName="", itemPrice="", itemQty="";

    int amount1=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentgateway);

        Intent getData= getIntent();
        itemName= getData.getStringExtra("itemname");
        itemPrice= getData.getStringExtra("itemprice");
        itemQty= getData.getStringExtra("itemqty");

        System.out.println("From Paymentgateway onCreate() itemName: " + itemName + "\titemPrice: " + itemPrice + "\titemQty: " + itemQty);

        name= (TextView) findViewById(R.id.name_id);

        amount= (TextView) findViewById(R.id.amount);

        email= (TextView) findViewById(R.id.email_id);

        mobile= (TextView) findViewById(R.id.mobile_number_id);

        name.setText("Product "+itemName);

        amount.setText("Product price: " + itemPrice);

        try {
            amount1 = Math.round(Float.parseFloat(itemPrice) * 100);
        } catch (Exception e) {}

        email.setText("Receipent's email: simiheartfilia@gmail.com");

        mobile.setText("Receipent's mobile: 9101754385");

    }

    public void pay(View view) {
        Checkout checkout= new Checkout();
        checkout.setKeyID("rzp_test_KecHJhxcpspDxe");
        checkout.setImage(R.drawable.razorpay);
        JSONObject jsonObject= new JSONObject();

        try {
            jsonObject.put("name","Simi Roy");
            jsonObject.put("description","Payment");
            jsonObject.put("theme.color","#0093DD");
            jsonObject.put("currency","INR");
            jsonObject.put("amount", amount1);
            jsonObject.put("prefill.contact", mobile.getText().toString().replace("Receipent's mobile:", "").trim());
            jsonObject.put("prefill.email", email.getText().toString().replace("Receipent's email:","").trim());
            checkout.open(Paymentgateway.this, jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        AlertDialog.Builder a11= new AlertDialog.Builder(Paymentgateway.this);
        a11.setTitle("Payment ID");
        a11.setMessage("Successfully paid\n\n" + s);
        a11.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog a1= a11.create();
        a1.show();
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
     //   Toast.makeText(this, "Payment error " + s, Toast.LENGTH_LONG).show();
    }
}