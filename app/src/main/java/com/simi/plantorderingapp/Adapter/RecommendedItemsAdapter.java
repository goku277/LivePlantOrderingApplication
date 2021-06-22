package com.simi.plantorderingapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.simi.plantorderingapp.Components.CollapsingToolbar;
import com.simi.plantorderingapp.Components.Users;
import com.simi.plantorderingapp.ProductModel.RecommendedItemsModelData;
import com.simi.plantorderingapp.ProductModel.RecommendedItems_Image_1;
import com.simi.plantorderingapp.ProductModel.RecommendedItems_Image_2;
import com.simi.plantorderingapp.ProductModel.SaleImageList_1;
import com.simi.plantorderingapp.ProductModel.SaleImageList_2;
import com.simi.plantorderingapp.R;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class RecommendedItemsAdapter extends RecyclerView.Adapter<RecommendedItemsAdapter.ViewHolder>{

    ArrayList<SaleImageList_1> imgRecommendedList1;
    ArrayList<SaleImageList_2> imgRecommendedList2;
    Set<Set<String>> setOfSets1;
    ArrayList<String> detailsList= new ArrayList<>();

    Context context;

    public RecommendedItemsAdapter(ArrayList<SaleImageList_1> imgRecommendedList1, ArrayList<SaleImageList_2> imgRecommendedList2, Set<Set<String>> setOfSets1, Context context) {
        this.imgRecommendedList1= imgRecommendedList1;
        this.imgRecommendedList2= imgRecommendedList2;
        this.setOfSets1= setOfSets1;
        this.context= context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_image_layout, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        try {
            Glide.with(context).load(imgRecommendedList1.get(position).getImage()).into(holder.image);
          //  Glide.with(context).load(imgRecommendedList2.get(position).getImage()).into(holder.image1);
        } catch (Exception e) {}

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    System.out.println("From RecommendedItemsAdapter onBindViewHolder setOfSets1: " + setOfSets1);
                    String url = imgRecommendedList1.get(position).getImage().trim();
                    for (Set<String> set1 : setOfSets1) {
                        for (String s : set1) {
                            if (s.contains("imageUri:")) {
                                s = s.substring(s.indexOf("imageUri:"), s.indexOf("quantity:")).replace("imageUri:", "").trim();
                                System.out.println("From RecommendedItemsAdapter onBindViewHolder imageUri: " + s);
                                System.out.println("From RecommendedItemsAdapter onBindViewHolder url is: " + url);
                                if (s.equals(url)) {
                                    doWork(set1, imgRecommendedList1.get(holder.getAdapterPosition()).getImage());
                                }
                            }
                        }
                    }
                } catch (Exception e) {}
            }
        });

     /*   holder.image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    System.out.println("From RecommendedItemsAdapter onBindViewHolder setOfSets1: " + setOfSets1);
                    String url = imgRecommendedList2.get(position).getImage().trim();
                    for (Set<String> set1 : setOfSets1) {
                        for (String s : set1) {
                            if (s.contains("imageUri:")) {
                                s = s.substring(s.indexOf("imageUri:"), s.indexOf("quantity:")).replace("imageUri:", "").trim();
                                System.out.println("From RecommendedItemsAdapter onBindViewHolder imageUri: " + s);
                                System.out.println("From RecommendedItemsAdapter onBindViewHolder url is: " + url);
                                if (s.equals(url)) {
                                    doWork(set1, imgRecommendedList2.get(holder.getAdapterPosition()).getImage());
                                }
                            }
                        }
                    }
                } catch (Exception e) {}
            }
        });  */
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
                    System.out.println("From RecommendedItemsAdapter doWork() is str: " + str);
                    System.out.println("From RecommendedItemsAdapter doWork() is url: " + url);
                    if (str.equals(url.trim())) {
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
            detailsLList.add(details.substring(details.indexOf("imageUri:"), details.indexOf("quantity:")).trim());
            detailsLList.add(details.substring(details.indexOf("quantity:")).trim());
        }

        System.out.println("From RecommendedItemsAdapter doWork() detailsList: " + detailsLList);

        Intent sendData= new Intent(context, CollapsingToolbar.class);
        sendData.putStringArrayListExtra("details", detailsLList);
        context.startActivity(sendData);
    }

    @Override
    public int getItemCount() {
        return imgRecommendedList1.size() + imgRecommendedList2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, image1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image= (ImageView) itemView.findViewById(R.id.img_1);
         //   image1= (ImageView) itemView.findViewById(R.id.img_2);
        }
    }
}