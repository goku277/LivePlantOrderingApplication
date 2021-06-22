package com.simi.plantorderingapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.simi.plantorderingapp.Components.CollapsingToolbar;
import com.simi.plantorderingapp.Components.Users;
import com.simi.plantorderingapp.ProductModel.IndoorData1;
import com.simi.plantorderingapp.ProductModel.IndoorData2;
import com.simi.plantorderingapp.R;

import java.util.ArrayList;
import java.util.Set;

public class IndoorAdapter extends RecyclerView.Adapter<IndoorAdapter.ViewHolder>{

    ArrayList<IndoorData1> indoorDataList1;
    ArrayList<String> indoorList1;
    ArrayList<String> indoorList2;
    Set<Set<String>> indoorSetOfSets;
    Context context;
    ArrayList<String> detailsList= new ArrayList<>();

    public IndoorAdapter(ArrayList<IndoorData1> indoorDataList1, ArrayList<String> indoorList1, ArrayList<String> indoorList2, Set<Set<String>> indoorSetOfSets, Context context) {
        this.indoorDataList1= indoorDataList1;
        this.indoorList1= indoorList1;
        this.indoorList2= indoorList2;
        this.indoorSetOfSets= indoorSetOfSets;
        this.context= context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.indoor_image_layout, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(indoorDataList1.get(position).getImage()).into(holder.image);

     //   Glide.with(context).load(indoorDataList1.get(position).getImage()).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     Toast.makeText(context, "Clicked on: " + save.get(holder.getAdapterPosition()).getImageUri(), Toast.LENGTH_SHORT).show();
                System.out.println("Clicked on: " + indoorDataList1.get(holder.getAdapterPosition()).getImage());
                String url= indoorDataList1.get(position).getImage().trim();
                for (Set<String> set1: indoorSetOfSets) {
                    for (String s: set1) {
                        if (s.contains("imageUri:")) {
                            s= s.replace("imageUri:","").trim();
                            System.out.println("From IndoorAdapter s: " + s);
                            System.out.println("From IndoorAdapter url: " + url);
                            if (s.contains(url)) {
                                doWork(set1, indoorDataList1.get(holder.getAdapterPosition()).getImage());
                            }
                        }
                    }
                }
            }
        });
    }

    private void doWork(Set<String> set1, String url) {
        detailsList.addAll(set1);
        

        String val1= "";

        for (String s: detailsList) {
            val1+= s+ " ";
        }

        

        String split[]= val1.split("name:");

        String details="", s1="";

        ArrayList<String> detailsLList= new ArrayList<>();

        for (String s: split) {
            if (!s.trim().isEmpty()) {
                s= s.trim();
               
                if (s.contains("imageUri:")) {
                    s1= s;
                    String str= s.substring(s.indexOf("imageUri:"), s.indexOf("quantity:")).replace("imageUri:","").trim();
                    
                    if (str.contains(url.trim())) {
                        details = s1;
                    }
                }
            }
        }

       

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

        

        Intent sendData= new Intent(context, CollapsingToolbar.class);
        sendData.putStringArrayListExtra("details", detailsLList);
        context.startActivity(sendData);
    }

    @Override
    public int getItemCount() {
        return indoorDataList1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image= (ImageView) itemView.findViewById(R.id.img_id1);
        }
    }
}
