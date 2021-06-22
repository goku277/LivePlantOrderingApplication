package com.simi.plantorderingapp.Components;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.simi.plantorderingapp.Adapter.AddToCartAdapter;
import com.simi.plantorderingapp.Adapter.AllSeasonAdapter;
import com.simi.plantorderingapp.Adapter.IndoorAdapter;
import com.simi.plantorderingapp.Adapter.RecommendedItemsAdapter;
import com.simi.plantorderingapp.Adapter.SummerAdapter;
import com.simi.plantorderingapp.Database.Profiledb;
import com.simi.plantorderingapp.ProcessingUnit.GetProductData;
import com.simi.plantorderingapp.ProcessingUnit.GetSalesProductData;
import com.simi.plantorderingapp.ProcessingUnit.getCartData;
import com.simi.plantorderingapp.ProcessingUnit.getCustomerData;
import com.simi.plantorderingapp.ProcessingUnit.getSeedsAndFertilizersData;
import com.simi.plantorderingapp.ProcessingUnit.seedsandfertilizerdata;
import com.simi.plantorderingapp.ProductModel.AddToCartModelData;
import com.simi.plantorderingapp.ProductModel.IndoorData1;
import com.simi.plantorderingapp.ProductModel.IndoorData2;
import com.simi.plantorderingapp.ProductModel.RecommendedItemsModelData;
import com.simi.plantorderingapp.ProductModel.SaveAllSeasonPlantsData;
import com.simi.plantorderingapp.ProductModel.SaveSummerPlantsData;
import com.simi.plantorderingapp.ProductModel.SetProductDetails;
import com.simi.plantorderingapp.ProductModel.SetSoilAndFertilizerData;
import com.simi.plantorderingapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Category extends AppCompatActivity implements View.OnClickListener {

    ImageView summer_img, allseason_img, recommend_img, plant_img, seeds_img, winter_img, gardening_tools, medicinal_img, vegetable_img, seedsandfertilizers_img;
    TextView summer_text, allseason_text, recommend_text, plant_text, seeds_text, winter_text, gardening_text, medicinal_text, vegetable_text, seedsandfertilizers_text;

    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseDatabase database1;
    DatabaseReference ref1;

    String uriPath= "";

    Profiledb pf;

    ArrayList<String> getProductDetails= new ArrayList<>();

    ArrayList<String> getSeedsProductDetails= new ArrayList<>();

    GetProductData getProductData;

    GetSalesProductData getSalesProductData;

    RecyclerView recyclerView;

    RecyclerView recyclerView1;

    RecyclerView recomendedRecyclerView;

    RecommendedItemsModelData recommendedItemsModelData;

    SaveSummerPlantsData save;

    SaveAllSeasonPlantsData saveAll;

    ArrayList<SaveAllSeasonPlantsData> saveList1= new ArrayList<>();

    ArrayList<SaveSummerPlantsData> saveList= new ArrayList<>();

    ArrayList<RecommendedItemsModelData> saveList11= new ArrayList<>();

    SummerAdapter summerAdapter;

    AllSeasonAdapter allSeasonAdapter;

    RecommendedItemsAdapter recommendedItemsAdapter;

    ArrayList<String> imageUrlList= new ArrayList<>();

    Set<Set<String>> setOfSets= new LinkedHashSet<>();

    Set<Set<String>> setOfSets1= new LinkedHashSet<>();

    Map<String, Set<Set<String>>> a1;

    BottomNavigationView bottomNavigationView;

    com.simi.plantorderingapp.ProcessingUnit.seedsandfertilizerdata seedsandfertilizerdata;

    ArrayList<AddToCartModelData> dataList= new ArrayList<>();

    AddToCartAdapter addToCartData;

    RecyclerView rec;

    String username="", usermobile="";

    ArrayList<String> getCustomerData= new ArrayList<>();

    com.simi.plantorderingapp.ProcessingUnit.getCustomerData csData;

    ArrayList<String> addCartModel= new ArrayList<>();

    ArrayList<String> addCartModel1= new ArrayList<>();

    getSeedsAndFertilizersData getSeeds;

    getCartData getCart;

    Set<Set<String>> set1= new LinkedHashSet<>();

    Set<Set<String>> set11= new LinkedHashSet<>();

    Set<Set<String>> copySet1= new LinkedHashSet<>();

    Set<Set<String>> copySet11= new LinkedHashSet<>();

    String val11= "";

    String val111= "";

    ArrayList<String> getRecommendedItems= new ArrayList<>();


    ArrayList<String> IndoorList1= new ArrayList<>();
    ArrayList<String> IndoorList2= new ArrayList<>();

    ArrayList<IndoorData1> indoorDataList1= new ArrayList<>();
    ArrayList<IndoorData2> indoorDataList2= new ArrayList<>();

    IndoorData1 indoorData1;
    IndoorData2 indoorData2;

    Set<Set<String>> indoorSetOfSets= new LinkedHashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        getSalesProductData= new GetSalesProductData();

        csData= new getCustomerData();
        rec= (RecyclerView) findViewById(R.id.recyclerview_id);
     //   recomendedRecyclerView= (RecyclerView) findViewById(R.id.recomended_items_recycler_id);
        seedsandfertilizerdata= new seedsandfertilizerdata();
        FirebaseApp.initializeApp(this);
        getProductData= new GetProductData();
        storage= FirebaseStorage.getInstance();
        storageReference= storage.getReference();
        pf= new Profiledb(Category.this);
        getPlantDetails();
    //    recyclerView= (RecyclerView) findViewById(R.id.summer_recycler_id);
     //   recyclerView1= (RecyclerView) findViewById(R.id.all_season_recycler_id);

        summer_img= (ImageView) findViewById(R.id.summer_plants_id);
        summer_text= (TextView) findViewById(R.id.summer_plants_text_id);

        allseason_img= (ImageView) findViewById(R.id.all_seasonal_plants_id);
        allseason_text= (TextView) findViewById(R.id.all_seasonal_plants_text_id);

        recommend_img= (ImageView) findViewById(R.id.recomend_items_img_id);
        recommend_text= (TextView) findViewById(R.id.recomend_items_text_id);

        plant_img= (ImageView) findViewById(R.id.plants_id);
        plant_text= (TextView) findViewById(R.id.plants_text_id);

        seeds_img= (ImageView) findViewById(R.id.seeds_id);
        seeds_text= (TextView) findViewById(R.id.seeds_text_id);

        winter_img= (ImageView) findViewById(R.id.winter_id);
        winter_text= (TextView) findViewById(R.id.winter_text_id);

        gardening_tools= (ImageView) findViewById(R.id.gardening_id);
        gardening_text= (TextView) findViewById(R.id.gardening_text_id);

        medicinal_img= (ImageView) findViewById(R.id.medicinal_id);
        medicinal_text= (TextView) findViewById(R.id.medicinal_text_id);

        vegetable_img= (ImageView) findViewById(R.id.vegetable_id);
        vegetable_text= (TextView) findViewById(R.id.vegetable_text_id);

        seedsandfertilizers_img= (ImageView) findViewById(R.id.fertilizer_id);
        seedsandfertilizers_text= (TextView) findViewById(R.id.fertilizer_text_id);

        summer_img.setOnClickListener(this);
        summer_text.setOnClickListener(this);

        allseason_img.setOnClickListener(this);
        allseason_text.setOnClickListener(this);

        recommend_img.setOnClickListener(this);
        recommend_text.setOnClickListener(this);

        plant_img.setOnClickListener(this);
        plant_text.setOnClickListener(this);

        seeds_img.setOnClickListener(this);
        seeds_text.setOnClickListener(this);

        winter_img.setOnClickListener(this);
        winter_text.setOnClickListener(this);

        gardening_tools.setOnClickListener(this);
        gardening_text.setOnClickListener(this);

        medicinal_img.setOnClickListener(this);
        medicinal_text.setOnClickListener(this);

        vegetable_img.setOnClickListener(this);
        vegetable_text.setOnClickListener(this);

        seedsandfertilizers_img.setOnClickListener(this);
        seedsandfertilizers_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.summer_plants_id:
                summerPlants();
                break;
            case R.id.summer_plants_text_id:
                summerPlants();
                break;
            case R.id.all_seasonal_plants_id:
                allSeasonalPlants();
                break;
            case R.id.all_seasonal_plants_text_id:
                allSeasonalPlants();
                break;
            case R.id.recomend_items_img_id:
                recommendPlants();
                break;
            case R.id.recomend_items_text_id:
                recommendPlants();
                break;
            case R.id.plants_id:
                populatePlants();
                break;
            case R.id.plants_text_id:
                populatePlants();
                break;
            case R.id.seeds_id:
                populateSeeds();
                break;
            case R.id.seeds_text_id:
                populateSeeds();
                break;
            case R.id.winter_id:
                populateWinter();
                break;
            case R.id.winter_text_id:
                populateWinter();
                break;
            case R.id.gardening_id:
                populateGarden();
                break;
            case R.id.gardening_text_id:
                populateGarden();
                break;
            case R.id.medicinal_id:
                populateMedicinal();
                break;
            case R.id.medicinal_text_id:
                populateMedicinal();
                break;
            case R.id.vegetable_id:
                populateVegatable();
                break;
            case R.id.vegetable_text_id:
                populateVegatable();
                break;
            case R.id.fertilizer_id:
                populateSoilandFertilizer();
                break;
            case R.id.fertilizer_text_id:
                populateSoilandFertilizer();
                break;
        }
    }

    private void recommendPlants() {
        getRecomendedPlantDetails();
    }

    private void allSeasonalPlants() {
        getPlantDetails();

        try {
            for (Map.Entry<String, Set<Set<String>>> e1: a1.entrySet()) {
                if (e1.getKey().equals("All Season Plants")) {
                    setVerticalRecyclerView(e1.getValue());
                }
            }
        } catch (Exception e) {}
    }

    private void summerPlants() {
        getPlantDetails();

        try {
            for (Map.Entry<String, Set<Set<String>>> e1: a1.entrySet()) {
                if (e1.getKey().equals("Summer Plants")) {
                    setVerticalRecyclerView(e1.getValue());
                }
            }
        } catch (Exception e) {}
    }

    private void populateVegatable() {
        getPlantDetails();
        try {
            for (Map.Entry<String, Set<Set<String>>> e1 : a1.entrySet()) {
                if (e1.getKey().equals("Vegetable")) {
                    setVerticalRecyclerView(e1.getValue());
                }
            }
        } catch (Exception e){}
    }

    private void populateMedicinal() {
        getPlantDetails();

        try {
            for (Map.Entry<String, Set<Set<String>>> e1: a1.entrySet()) {
                if (e1.getKey().equals("Medicinal Plants")) {
                    setVerticalRecyclerView(e1.getValue());
                }
            }
        } catch (Exception e) {}
    }

    private void populateGarden() {
        getPlantDetails();

        try {
            for (Map.Entry<String, Set<Set<String>>> e1: a1.entrySet()) {
                if (e1.getKey().equals("Gardening Tools")) {
                    setVerticalRecyclerView(e1.getValue());
                }
            }
        } catch (Exception e){}
    }

    private void populateWinter() {
        getPlantDetails();

        try {
            for (Map.Entry<String, Set<Set<String>>> e1: a1.entrySet()) {
                if (e1.getKey().equals("Winter Plants")) {
                    setVerticalRecyclerView(e1.getValue());
                }
            }
        } catch (Exception e) {}
    }

    private void populateSoilandFertilizer1() {
        populateSoilandFertilizer();
    }

    private void populateSeeds() {
        getPlantDetails();

        try {
            for (Map.Entry<String, Set<Set<String>>> e1: a1.entrySet()) {
                if (e1.getKey().equals("Seeds")) {
                    setVerticalRecyclerView(e1.getValue());
                }
            }
        } catch (Exception e) {}
    }

    private void populatePlants() {
        getPlantDetails();
        try {
            for (Map.Entry<String, Set<Set<String>>> e1: a1.entrySet()) {
                if (e1.getKey().equals("Plants")) {
                    setVerticalRecyclerView(e1.getValue());
                }
            }
        } catch (Exception e) {}
    }

    private void getRecomendedPlantDetails() {

        indoor();
      /*  DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                            SetProductDetails getProduct = new SetProductDetails((String) userData.get("name"), (String) userData.get("price"), (String) userData.get("details"), (String) userData.get("category"), (String) userData.get("id"), (String) userData.get("imageUrl"), (String) userData.get("quantity"));
                            getRecommendedItems.add(getProduct.getName());
                            getRecommendedItems.add(getProduct.getPrice());
                            getRecommendedItems.add(getProduct.getDetails());
                            getRecommendedItems.add(getProduct.getCategory());
                            getRecommendedItems.add(getProduct.getId());
                            getRecommendedItems.add(getProduct.getImageUrl());
                            getRecommendedItems.add(getProduct.getQuantity());

                        } catch (ClassCastException cce) {
                            try {
                                String mString = String.valueOf(dataMap.get(key));
                            } catch (ClassCastException cce2) {

                            }
                        }
                    }

                    System.out.println("From users getPlanDetails() getRecommendedItems: " + getRecommendedItems);

                    String val1= "";

                    for (String s: getRecommendedItems) {
                        val1+= s + " ";
                    }

                    System.out.println("From Users getPlanDetails() val1: " + val1);

                //    a1= getSalesProductData.init(val1);

                //    a1= getSalesProductData.init(val1);

                    System.out.println("From Users getPlanDetails() a1: " + a1);

                    for (Map.Entry<String, Set<Set<String>>> e1: a1.entrySet()) {
                        System.out.println("e1.getKey(): " + e1.getKey() + "\te1.getValue(): " + e1.getValue());
                        setVerticalRecyclerView(e1.getValue());
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });   */
    }

    private void indoor() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();
                    for (String key : dataMap.keySet()) {
                        Object data = dataMap.get(key);
                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification
                            SetProductDetails getProduct = new SetProductDetails((String) userData.get("name"), (String) userData.get("price"), (String) userData.get("details"), (String) userData.get("category"), (String) userData.get("id"), (String) userData.get("imageUrl"), (String) userData.get("quantity"));
                            getProductDetails.add(getProduct.getName());
                            getProductDetails.add(getProduct.getPrice());
                            getProductDetails.add(getProduct.getDetails());
                            getProductDetails.add(getProduct.getCategory());
                            getProductDetails.add(getProduct.getId());
                            getProductDetails.add(getProduct.getImageUrl());
                            getProductDetails.add(getProduct.getQuantity());

                        } catch (ClassCastException cce) {
                            try {
                                String mString = String.valueOf(dataMap.get(key));
                            } catch (ClassCastException cce2) {

                            }
                        }
                        String val1= "";

                        for (String s: getProductDetails) {
                            val1+= s + " ";
                        }
                        a1= getProductData.init(val1);
                        System.out.println("From Category getPlanDetails() a1: " + a1);
                        for (Map.Entry<String, Set<Set<String>>> e1: a1.entrySet()) {
                            if (e1.getKey().equals("Indoor plants")) {
                                Set<Set<String>> val= e1.getValue();
                                System.out.println("e1.getValue() is: " + val);

                                String name= "", urlImage="";
                                ArrayList<String> urlImageList= new ArrayList<>();
                                for (Set<String> set1: val) {
                                    for (String s1 : set1) {
                                        if (s1.contains("imageUri:")) {
                                            urlImage = s1.substring(s1.indexOf("https://"), s1.indexOf("quantity:")).replace("imageUri:", "").trim();
                                            if (!urlImageList.contains(urlImage)) {
                                                urlImageList.add(urlImage);
                                            }
                                        }
                                    }
                                    setOfSets1.add(set1);
                                    indoorSetOfSets.add(set1);
                                }
                                System.out.println("urlImageList: " + urlImageList);
                                setIndoorRecyclerView(urlImageList);
                                //  SetSaleRecyclerView(urlImageList);
                                //   setSaleRecyclerView(val);
                                break;
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setIndoorRecyclerView(ArrayList<String> urlImageList) {
        System.out.println("From Users setIndoorRecyclerView() urlImageList: " + urlImageList);
        for (int i=0;i<urlImageList.size();i++) {
            if (!IndoorList1.contains(urlImageList.get(i))) {
                indoorData1 = new IndoorData1(urlImageList.get(i));
                indoorDataList1.add(indoorData1);
                IndoorList1.add(urlImageList.get(i));
            }
        }

        System.out.println("From Category setIndoorRecyclerView() indoorSetOfSets: " + indoorSetOfSets);
        System.out.println("From Category setIndoorRecyclerView() IndoorList1: " + IndoorList1);
        System.out.println("From Category setIndoorRecyclerView() IndoorList2: " + IndoorList2);
        System.out.println("From Category setIndoorRecyclerView() IndoorDataList1: " + indoorDataList1);
        LinearLayoutManager layoutManager= new LinearLayoutManager(Category.this, LinearLayoutManager.HORIZONTAL, false);

        Intent sendData= new Intent(Category.this, DisplayIndoorItemsFromCategory.class);
        sendData.putExtra("indoorsetofsets", (Serializable) indoorSetOfSets);
        startActivity(sendData);
    }

    private void setVerticalRecyclerView(Set<Set<String>> value) {
        System.out.println("From User setVerticalRecyclerView value is: " + value);
        String val1="";
        for (Set<String> set1: value) {
            for (String s: set1) {
                val1+= s+ " ";
            }
        }
        //    System.out.println("From Users setVerticalRecyclerView() val1 is: " + val1);
        Intent sendData= new Intent(Category.this, CategoricalData.class);
        sendData.putExtra("val", val1);
        startActivity(sendData);
    }

    private void getPlantDetails() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                            SetProductDetails getProduct = new SetProductDetails((String) userData.get("name"), (String) userData.get("price"), (String) userData.get("details"), (String) userData.get("category"), (String) userData.get("id"), (String) userData.get("imageUrl"), (String) userData.get("quantity"));
                            getProductDetails.add(getProduct.getName());
                            getProductDetails.add(getProduct.getPrice());
                            getProductDetails.add(getProduct.getDetails());
                            getProductDetails.add(getProduct.getCategory());
                            getProductDetails.add(getProduct.getId());
                            getProductDetails.add(getProduct.getImageUrl());
                            getProductDetails.add(getProduct.getQuantity());

                        } catch (ClassCastException cce) {
                            try {
                                String mString = String.valueOf(dataMap.get(key));
                            } catch (ClassCastException cce2) {

                            }
                        }

                        //    System.out.println("From users getPlanDetails() getProductDetails: " + getProductDetails);

                        String val1= "";

                        for (String s: getProductDetails) {
                            val1+= s + " ";
                        }

                        //     System.out.println("From Users getPlanDetails() val1: " + val1);

                        a1= getProductData.init(val1);

                        //      System.out.println("From Users getPlanDetails() a1: " + a1);

                    /*    for (Map.Entry<String, Set<Set<String>>> e1: a1.entrySet()) {
                            if (e1.getKey().equals("Summer Plants")) {
                               // setVerticalRecyclerView(e1.getValue());
                            }
                            if (e1.getKey().equals("All Season Plants")) {
                               // setVerticalRecyclerView(e1.getValue());
                            }
                        }  */
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void populateSoilandFertilizer() {
        try {
            // Need to adjust this code... by retrieving SoilAndFertilizerProducts from the firebase database... To be continued on tomorrow...

          //  Toast.makeText(this, "Visited populateSoilAndFertilizer() ", Toast.LENGTH_SHORT).show();

            //     System.out.println("Visited populateSoilAndFertilizer()");

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("SoilAndFertilizerProducts");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                        for (String key : dataMap.keySet()) {

                            Object data = dataMap.get(key);

                            try {
                                HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                                SetSoilAndFertilizerData getProduct = new SetSoilAndFertilizerData((String) userData.get("name"), (String) userData.get("price"), (String) userData.get("imageUrl"), (String) userData.get("seedsQty"), (String) userData.get("id"), (String) userData.get("category"), (String) userData.get("details"), (String) userData.get("quantity"));
                                getSeedsProductDetails.add(getProduct.getName());
                                getSeedsProductDetails.add(getProduct.getPrice());
                                getSeedsProductDetails.add(getProduct.getSeedsQty());
                                getSeedsProductDetails.add(getProduct.getDetails());
                                getSeedsProductDetails.add(getProduct.getCategory());
                                getSeedsProductDetails.add("id: " + getProduct.getId());
                                getSeedsProductDetails.add(getProduct.getImageUrl());
                                getSeedsProductDetails.add(getProduct.getQuantity());

                            } catch (ClassCastException cce) {
                                try {
                                    String mString = String.valueOf(dataMap.get(key));
                                    System.out.println("mString " + mString);
                                } catch (ClassCastException cce2) {
                                }
                            }

                            //         System.out.println("From users getPlanDetails() getProductDetails: " + getSeedsProductDetails);

                            String val1= "";

                            for (String s: getSeedsProductDetails) {
                                val1+= s + " ";
                            }

                            //         System.out.println("From Users getPlanDetails() val1: " + val1);

                            Set<Set<String>> a1= seedsandfertilizerdata.init(val1);

                            //         System.out.println("From populateSoilandFertilizer() a1 is: " + a1);

                            String val11= "";

                            for (Set<String> set1: a1) {
                                for (String s1: set1) {
                                    val11+= s1 + " ";
                                }
                                val11 += " * ";
                            }

                            String getId= getSeedsProductDetails.get(5);

                            String split[]= getId.split("\\s+");

                            String id= split[split.length-1].trim();

                            //          System.out.println("From Users populateSoilandFertilizer() id is: " + id);

                            Intent sendData= new Intent(Category.this, SeedsAndFertilizers.class);

                            sendData.putExtra("val11", val11);

                            sendData.putExtra("id", id);

                            sendData.putExtra("gotoseedsandfertilizer", true);

                            startActivity(sendData);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            for (Map.Entry<String, Set<Set<String>>> e1 : a1.entrySet()) {
                System.out.println("e1.getKey(): " + e1.getKey());
                if (e1.getKey().contains("Soil And Fertilizer")) {
                    setVerticalRecyclerView(e1.getValue());
                    break;
                }
            }
        } catch (Exception e){}
    }
}