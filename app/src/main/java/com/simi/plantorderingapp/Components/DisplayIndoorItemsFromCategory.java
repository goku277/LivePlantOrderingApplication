package com.simi.plantorderingapp.Components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.simi.plantorderingapp.Adapter.IndoorAdapterFromCategory;
import com.simi.plantorderingapp.Adapter.ShowMyOrdersAdapter;
import com.simi.plantorderingapp.ProductModel.SaveINdoorItemsFromCategoryModelData;
import com.simi.plantorderingapp.R;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class DisplayIndoorItemsFromCategory extends AppCompatActivity {

    Set<Set<String>> indoorSetOfSets= new LinkedHashSet<>();

    SaveINdoorItemsFromCategoryModelData saveINdoorItemsFromCategoryModelData;
    ArrayList<SaveINdoorItemsFromCategoryModelData> saveINdoorItemsFromCategoryModelDataList= new ArrayList<>();

    RecyclerView rec1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_indoor_items_from_category);

        rec1= (RecyclerView) findViewById(R.id.recycler_id);

        Intent getData= getIntent();
        indoorSetOfSets= (Set<Set<String>>) getData.getSerializableExtra("indoorsetofsets");

        System.out.println("From DisplayIndoorItemsFromCategory is: " + indoorSetOfSets);

        for (Set<String> set1: indoorSetOfSets) {
            ArrayList<String> list1= new ArrayList<>();
            list1.addAll(set1);
            formatData(list1);
        }

        LinearLayoutManager layoutManager= new LinearLayoutManager(DisplayIndoorItemsFromCategory.this, LinearLayoutManager.VERTICAL, false);

        rec1.setLayoutManager(layoutManager);

        rec1.setItemAnimator(new DefaultItemAnimator());

        IndoorAdapterFromCategory indoorAdapterFromCategory = new IndoorAdapterFromCategory(saveINdoorItemsFromCategoryModelDataList, DisplayIndoorItemsFromCategory.this);

        rec1.setAdapter(indoorAdapterFromCategory);
    }

    private void formatData(ArrayList<String> list1) {
        String img= list1.get(5).substring(list1.get(5).indexOf("imageUri:"), list1.get(5).indexOf("quantity:")).replace("imageUri:", "").trim();
        saveINdoorItemsFromCategoryModelData= new SaveINdoorItemsFromCategoryModelData(list1.get(0), list1.get(2), list1.get(1), img);
        saveINdoorItemsFromCategoryModelDataList.add(saveINdoorItemsFromCategoryModelData);
    }
}