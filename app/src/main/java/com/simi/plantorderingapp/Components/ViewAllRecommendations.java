package com.simi.plantorderingapp.Components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.simi.plantorderingapp.Adapter.ShowMyOrdersAdapter;
import com.simi.plantorderingapp.Adapter.ViewAllRecomendationsAdapter;
import com.simi.plantorderingapp.ProductModel.ViewAllRecomendationsModelData;
import com.simi.plantorderingapp.R;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class ViewAllRecommendations extends AppCompatActivity {

    Set<Set<String>> set111= new LinkedHashSet<>();

    ArrayList<ViewAllRecomendationsModelData> viewAllRecomendationsModelDataList= new ArrayList<>();
    ViewAllRecomendationsModelData viewAllRecomendationsModelData;

    ViewAllRecomendationsAdapter viewAllRecomendationsAdapter;

    RecyclerView rec1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_recommendations);

        rec1= (RecyclerView) findViewById(R.id.recycler_id);

        Intent getData= getIntent();
        set111= (Set<Set<String>>) getData.getSerializableExtra("set111");

        System.out.println("From ViewAllRecommendations set111: " + set111);

        for (Set<String> set1: set111) {
            ArrayList<String> setToLists= new ArrayList<>();
            setToLists.addAll(set1);
            storeData(setToLists);
        }

        LinearLayoutManager layoutManager= new LinearLayoutManager(ViewAllRecommendations.this, LinearLayoutManager.VERTICAL, false);

        rec1.setLayoutManager(layoutManager);

        rec1.setItemAnimator(new DefaultItemAnimator());

        viewAllRecomendationsAdapter= new ViewAllRecomendationsAdapter(viewAllRecomendationsModelDataList, ViewAllRecommendations.this);

        rec1.setAdapter(viewAllRecomendationsAdapter);
    }

    private void storeData(ArrayList<String> setToLists) {
        String pr= setToLists.get(1).replace("price:", "");
        viewAllRecomendationsModelData= new ViewAllRecomendationsModelData(setToLists.get(0).replace("name:","Name: "), pr,
                setToLists.get(2).replace("detail:", "Detail: "), setToLists.get(5).replace("imageUri:", "").trim());

        viewAllRecomendationsModelDataList.add(viewAllRecomendationsModelData);
    }
}