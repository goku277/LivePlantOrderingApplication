package com.simi.plantorderingapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.simi.plantorderingapp.Components.CollapsingToolbar;
import com.simi.plantorderingapp.Components.Users;
import com.simi.plantorderingapp.ProductModel.SaleImageList_1;
import com.simi.plantorderingapp.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.ViewHolder>{

    ArrayList<SaleImageList_1> saleImageList_11;
    ArrayList<String> alternateSaleList1;
    Context context;
    Set<Set<String>> saleSetOfSets;
    Map<String, String> mapImageToPriceId;

    ArrayList<String> detailsList= new ArrayList<>();

    public SaleAdapter(ArrayList<SaleImageList_1> saleImageList_11, Set<Set<String>> saleSetOfSets, ArrayList<String> alternateSaleList1, Map<String, String> mapImageToPriceId, Context context) {
        this.saleImageList_11= saleImageList_11;
        this.saleSetOfSets= saleSetOfSets;
        this.alternateSaleList1= alternateSaleList1;
        this.mapImageToPriceId= mapImageToPriceId;
        this.context= context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sale_image_layout, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(saleImageList_11.get(position).getImage()).into(holder.image);
        System.out.println("From SaleAdapter mapImageToPriceId: "  + mapImageToPriceId);
        System.out.println("From SaleAdapter saleImageList_11.get(position).getImage(): " + saleImageList_11.get(position).getImage());

        String price_1[]= mapImageToPriceId.get(saleImageList_11.get(position).getImage()).split("\\..");
        ArrayList<String> list1= new ArrayList<>();
        for (String s: price_1) {
            System.out.println("price_1: " + s);
            if (!s.trim().isEmpty()) {
                list1.add(s);
            }
        }

        System.out.println("list1: " + list1);

        if (list1.size()>1) {
            float amt= (Float.parseFloat(list1.get(1).replace("%",""))/100) * Float.parseFloat(list1.get(0));
            String amt1[]= String.valueOf(amt).split("\\.");
            System.out.println("amt1[0]: " + amt1[0] + "\tamt1[1]: " + amt1[1]);
            int accurateAmt= Integer.parseInt(list1.get(0))- Integer.parseInt(amt1[0]);
            holder.price.setText("Discount " + " " + list1.get(1) + "%" + "\n Discounted amount: " + accurateAmt + "/-");
        }

        else {
            holder.price.setText(list1.get(0) + "/-");
        }
     //   int discount= (Integer.parseInt(price_1[1]) /100) * Integer.parseInt(price_1[0]);
     //   holder.price.setText("Discount " + " " + price_1[1] + "/-");


        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     Toast.makeText(context, "Clicked on: " + save.get(holder.getAdapterPosition()).getImageUri(), Toast.LENGTH_SHORT).show();
                System.out.println("Clicked on: " + saleImageList_11.get(holder.getAdapterPosition()).getImage());
                String url= saleImageList_11.get(position).getImage().trim();
                for (Set<String> set1: saleSetOfSets) {
                    for (String s: set1) {
                        if (s.contains("imageUri:")) {
                            s= s.replace("imageUri:","").trim();
                            if (s.contains(url)) {
                                doWork(set1, saleImageList_11.get(holder.getAdapterPosition()).getImage());
                            }
                        }
                    }
                }
            }
        });
    }

    private void doWork(Set<String> set1, String url) {
        detailsList.addAll(set1);
        System.out.println("From AllSeasonAdapter doWork() is: " + detailsList);

        String val1= "";

        for (String s: detailsList) {
            val1+= s+ " ";
        }

        System.out.println("From AllSeasonAdapter val1: " + val1);

        String split[]= val1.split("name:");

        String details="", s1="";

        ArrayList<String> detailsLList= new ArrayList<>();

        for (String s: split) {
            if (!s.trim().isEmpty()) {
                s= s.trim();
                System.out.println("split: " + s);
                if (s.contains("imageUri:")) {
                    s1= s;
                    String str= s.substring(s.indexOf("imageUri:"), s.indexOf("quantity:")).replace("imageUri:","").trim();
                    if (str.contains(url.trim())) {
                        details = s1;
                    }
                }
            }
        }

        System.out.println("From AllSeasonAdapter details: " + details);

        if (details.contains("price:")) {
            detailsLList.add(details.substring(details.indexOf(details.charAt(0)), details.indexOf("price:")).trim());
        }
        if (details.contains("price:") && details.contains("detail:")) {
            detailsLList.add(details.substring(details.indexOf("price:"), details.indexOf("detail:")).trim());
        }
        if (details.contains("detail:") && details.contains("category:")) {
            detailsLList.add(details.substring(details.indexOf("detail:"), details.indexOf("category:")).trim());
        }
        if (details.contains("category:") && details.contains("key:")) {
            detailsLList.add(details.substring(details.indexOf("category:"), details.indexOf("key:")).trim());
        }
        if (details.contains("key:") && details.contains("imageUri:")) {
            detailsLList.add(details.substring(details.indexOf("key:"), details.indexOf("imageUri:")).trim());
        }
        if (details.contains("imageUri:")) {
            detailsLList.add(details.substring(details.indexOf("imageUri:")).trim());
        }

        System.out.println("From SaleAdapter detailsList: " + detailsLList);

        Intent sendData= new Intent(context, CollapsingToolbar.class);
        sendData.putStringArrayListExtra("details", detailsLList);
        context.startActivity(sendData);
    }

    @Override
    public int getItemCount() {
        return saleImageList_11.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image= (ImageView) itemView.findViewById(R.id.img_id1);
            price= (TextView) itemView.findViewById(R.id.price_id);
        }
    }
}