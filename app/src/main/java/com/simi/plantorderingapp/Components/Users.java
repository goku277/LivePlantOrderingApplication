package com.simi.plantorderingapp.Components;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.simi.plantorderingapp.Adapter.AddToCartAdapter;
import com.simi.plantorderingapp.Adapter.AllSeasonAdapter;
import com.simi.plantorderingapp.Adapter.IndoorAdapter;
import com.simi.plantorderingapp.Adapter.RecommendedItemsAdapter;
import com.simi.plantorderingapp.Adapter.SaleAdapter;
import com.simi.plantorderingapp.Adapter.SummerAdapter;
import com.simi.plantorderingapp.Credentials.Signin;
import com.simi.plantorderingapp.CustomeAlertDialog.ChooseCartDialog;
import com.simi.plantorderingapp.Database.MapUrl;
import com.simi.plantorderingapp.Database.Profiledb;
import com.simi.plantorderingapp.ProcessingUnit.GetProductData;
import com.simi.plantorderingapp.ProcessingUnit.SearchSeedsAndFertilizers;
import com.simi.plantorderingapp.ProcessingUnit.getCartData;
import com.simi.plantorderingapp.ProcessingUnit.getCustomerData;
import com.simi.plantorderingapp.ProcessingUnit.getSeedsAndFertilizersData;
import com.simi.plantorderingapp.ProcessingUnit.searchRecommendedSeedsAndFertilizers;
import com.simi.plantorderingapp.ProductModel.AddToCartModelData;
import com.simi.plantorderingapp.ProductModel.IndoorData1;
import com.simi.plantorderingapp.ProductModel.IndoorData2;
import com.simi.plantorderingapp.ProductModel.RecommendedItemsAddToCart;
import com.simi.plantorderingapp.ProductModel.RecommendedItemsModelData;
import com.simi.plantorderingapp.ProductModel.RecommendedItems_Image_1;
import com.simi.plantorderingapp.ProductModel.RecommendedItems_Image_2;
import com.simi.plantorderingapp.ProductModel.SaleImageList_1;
import com.simi.plantorderingapp.ProductModel.SaleImageList_2;
import com.simi.plantorderingapp.ProductModel.SaveAddToCartItemsDetails;
import com.simi.plantorderingapp.ProductModel.SaveAddToCartSeedsAndFertilizers;
import com.simi.plantorderingapp.ProductModel.SaveAllSeasonPlantsData;
import com.simi.plantorderingapp.ProductModel.SaveCustomerData;
import com.simi.plantorderingapp.ProductModel.SaveSummerPlantsData;
import com.simi.plantorderingapp.ProductModel.SetProductDetails;
import com.simi.plantorderingapp.ProductModel.SetSoilAndFertilizerData;
import com.simi.plantorderingapp.Profile.DisplayProfile;
import com.simi.plantorderingapp.Profile.UpdateProfile;
import com.simi.plantorderingapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Users extends AppCompatActivity implements View.OnClickListener, ChooseCartDialog.ChooseCategory {

    private static final int PROXIMITY_RADIUS = 25;
    private static final String TAG = "MainActivity";

    ImageView plants, seeds, winterplants, gardentools, medicinalplants, vegetable ;
    TextView plants_text, seeds_text, winter_plants_text, gardening_tools_text, medicinal_plants_text, vegetable_text;

    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseDatabase database1;
    DatabaseReference ref1;

    String uriPath= "";

    Profiledb pf;

    private static final int CONTACT_PERMISSION_CODE= 1;
    private static final int CONTACT_PICK_CODE= 2;

    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    ArrayList<String> getProductDetails= new ArrayList<>();

    ArrayList<String> getSeedsProductDetails= new ArrayList<>();

    GetProductData getProductData;

    RecyclerView recyclerView;

    RecyclerView recyclerView1;

    RecyclerView recomendedRecyclerView;

    RecyclerView saleRecyclerView;

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

    Set<Set<String>> indoorSetOfSets= new LinkedHashSet<>();

    Set<Set<String>> saleSetOfSets= new LinkedHashSet<>();

    Map<String, Set<Set<String>>> a1;

    BottomNavigationView bottomNavigationView;

    com.simi.plantorderingapp.ProcessingUnit.seedsandfertilizerdata seedsandfertilizerdata;

    ArrayList<AddToCartModelData> dataList= new ArrayList<>();

    AddToCartAdapter addToCartData;

    RecyclerView rec;

    String username="", usermobile="";

    ImageView sale_img;

    TextView sale_text;

    ImageView indoor_img;

    TextView indoor_text;

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

    String searchItem= "";

    ArrayList<String> checkItemsRecommend= new ArrayList<>();
    ArrayList<String> checkItemsSeedsAndFertilizers= new ArrayList<>();
    ArrayList<String> checkItemsProducts= new ArrayList<>();

    searchRecommendedSeedsAndFertilizers search1;

    SearchSeedsAndFertilizers search11;

    ArrayList<String> AlternateRecommendedImage1= new ArrayList<>();
    ArrayList<String> AlternateRecommendedImage2= new ArrayList<>();

    ArrayList<RecommendedItems_Image_1> imgRecommendedList1= new ArrayList<>();
    ArrayList<RecommendedItems_Image_2> imgRecommendedList2= new ArrayList<>();

    RecommendedItems_Image_1 rec1;

    RecommendedItems_Image_2 rec2;

    ArrayList<SaleImageList_1> saleImageList_11= new ArrayList<>();
    ArrayList<SaleImageList_2> saleImageList_22= new ArrayList<>();

  //  SaleRecyclerViewAdapter saleRecyclerViewAdapter;

    ArrayList<String> AlternateSaleList1= new ArrayList<>();
    ArrayList<String> AlternateSaleList2= new ArrayList<>();

    SaleImageList_1 saleImageList_1;

    SaleImageList_2 saleImageList_2;

    ArrayList<String> IndoorList1= new ArrayList<>();
    ArrayList<String> IndoorList2= new ArrayList<>();

    ArrayList<IndoorData1> indoorDataList1= new ArrayList<>();
    ArrayList<IndoorData2> indoorDataList2= new ArrayList<>();

    IndoorData1 indoorData1;
    IndoorData2 indoorData2;

    RecyclerView indoorRecyclerView;

    ProgressDialog pd;

    SaleAdapter saleAdapter;

    IndoorAdapter indoorAdapter;

    ImageView cactus, neem, sunflower, tulsi;

    TextView cactus_text_id, neem_text_id, sunflower_id, tulsi_text_id;

    TextView cactus_price_id, neem_price_id, sunflower_price_id, tulsi_price_id;

    String details="", name="", price="";

    Uri imageUri= null;

    String uri1= "";

    String quantity="";

    RecommendedItemsAddToCart recommendedItemsAddToCart;

    MapUrl mapUrl;

    TextView viewAll;

    SetProductDetails setProductDetails;

    Map<String, String> mapImageToPriceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        pd= new ProgressDialog(Users.this);

        mapUrl= new MapUrl(Users.this);

        viewAll= (TextView) findViewById(R.id.view_all_id);

        viewAll.setOnClickListener(this);

        cactus= (ImageView) findViewById(R.id.img_id1);
        cactus_text_id= (TextView) findViewById(R.id.cactus_textt_id);
        cactus_price_id= (TextView) findViewById(R.id.cactus_text_id);

        cactus.setOnClickListener(this);
        cactus_text_id.setOnClickListener(this);
        cactus_price_id.setOnClickListener(this);

        neem= (ImageView) findViewById(R.id.img_id2);
        neem_text_id= (TextView) findViewById(R.id.neem_text_id);
        neem_price_id= (TextView) findViewById(R.id.neem_price_id);

        neem.setOnClickListener(this);
        neem_text_id.setOnClickListener(this);
        neem_price_id.setOnClickListener(this);

        sunflower= (ImageView) findViewById(R.id.img_id3);
        sunflower_id= (TextView) findViewById(R.id.sunflower_text_id);
        sunflower_price_id= (TextView) findViewById(R.id.sunflower_price_id);

        sunflower.setOnClickListener(this);
        sunflower_id.setOnClickListener(this);
        sunflower_price_id.setOnClickListener(this);

        tulsi= (ImageView) findViewById(R.id.img_id4);
        tulsi_text_id= (TextView) findViewById(R.id.tulsi_text_id);
        tulsi_price_id= (TextView) findViewById(R.id.tulsi_price_id);

        tulsi.setOnClickListener(this);
        tulsi_text_id.setOnClickListener(this);
        tulsi_price_id.setOnClickListener(this);


        search1= new searchRecommendedSeedsAndFertilizers();
        search11= new SearchSeedsAndFertilizers();
        csData= new getCustomerData();
     //   rec= (RecyclerView) findViewById(R.id.recyclerview_id);
     //   recomendedRecyclerView= (RecyclerView) findViewById(R.id.rv_1);
        indoorRecyclerView= (RecyclerView) findViewById(R.id.rec_id2);
        saleRecyclerView= (RecyclerView) findViewById(R.id.rec_id);
    //    seedsandfertilizerdata= new seedsandfertilizerdata();
        FirebaseApp.initializeApp(this);
        getProductData= new GetProductData();
        storage= FirebaseStorage.getInstance();
        storageReference= storage.getReference();
        pf= new Profiledb(Users.this);
     //   recyclerView= (RecyclerView) findViewById(R.id.summer_recycler_id);
    //    recyclerView1= (RecyclerView) findViewById(R.id.all_season_recycler_id);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nav=(NavigationView)findViewById(R.id.navmenu);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        toggle=new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getPlantDetails();
        getRecomendedPlantDetails();
        sale();
        indoor();
        getSeeds= new getSeedsAndFertilizersData();

        getCart= new getCartData();

        database1= FirebaseDatabase.getInstance();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.home:
                    //    Toast.makeText(Users.this, "Home", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.cart:
                    //    Toast.makeText(Users.this, "Cart", Toast.LENGTH_SHORT).show();
                        openCart();
                       // openFragment(new TrendFragment());
                        return true;

                    case R.id.category:
                     //   Toast.makeText(Users.this, "Category", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Users.this, Category.class));
                      //  openFragment(new AccountFragment());
                        return true;

                    case R.id.profile:
                     //   Toast.makeText(Users.this, "Profile", Toast.LENGTH_SHORT).show();
                        gotoProfile();
                      //  openFragment(new SettingsFragment());
                      //  startActivity(new Intent(Users.this, com.goku277.plantorder.Components.Profile.class));
                        return true;
                }
                return false;
            }
        });

        initializeProfile();

      //  populateSoilandFertilizer();

     //   sale_img= (ImageView) findViewById(R.id.sale_id);
    //    sale_text= (TextView) findViewById(R.id.sale1_text_id);

    //    indoor_img= (ImageView) findViewById(R.id.indoor_id);
   //     indoor_text= (TextView) findViewById(R.id.indoor_text_id);

    //    sale_img.setOnClickListener(this);
   //     sale_text.setOnClickListener(this);

   //     indoor_img.setOnClickListener(this);
    //    indoor_text.setOnClickListener(this);

    /*    plants= (ImageView) findViewById(R.id.plants_id);
        plants_text= (TextView) findViewById(R.id.plants_text_id);

        seeds= (ImageView) findViewById(R.id.seeds_id);
        seeds_text= (TextView) findViewById(R.id.seeds_text_id);

        winterplants= (ImageView) findViewById(R.id.winter_id);
        winter_plants_text= (TextView) findViewById(R.id.winter_text_id);

        gardentools= (ImageView) findViewById(R.id.gardening_id);
        gardening_tools_text= (TextView) findViewById(R.id.gardening_text_id);

        medicinalplants= (ImageView) findViewById(R.id.medicinal_id);
        medicinal_plants_text= (TextView) findViewById(R.id.medicinal_text_id);

        vegetable= (ImageView) findViewById(R.id.vegetable_id);
        vegetable_text= (TextView) findViewById(R.id.vegetable_text_id);

        plants.setOnClickListener(this);
        plants_text.setOnClickListener(this);

        seeds.setOnClickListener(this);
        seeds_text.setOnClickListener(this);

        winterplants.setOnClickListener(this);
        winter_plants_text.setOnClickListener(this);

        gardentools.setOnClickListener(this);
        gardening_tools_text.setOnClickListener(this);

        medicinalplants.setOnClickListener(this);
        medicinal_plants_text.setOnClickListener(this);

        vegetable.setOnClickListener(this);
        vegetable_text.setOnClickListener(this);   */

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.menu_home:
                   //     Toast.makeText(getApplicationContext(),"Clicked on Home",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                  /*      case R.id.menu_plants:
                        populatePlants();
                        Toast.makeText(getApplicationContext(),"Clicked on plants",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.soil_and_fertilizers_id:
                        System.out.println("Clicked on Soil And Fertilizers");
                        populateSoilandFertilizer();
                        Toast.makeText(getApplicationContext(),"Clicked on Soil And Fertilizers",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_seeds :
                        populateSeeds();
                        Toast.makeText(getApplicationContext(),"Clicked on seeds",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_medicinalplants:
                        populateMedicinal();
                    //    Toast.makeText(getApplicationContext(),"Clicked on medicinal plants",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_winterplants :
                        populateWinter();
                     //   Toast.makeText(getApplicationContext(),"Clicked on Winter plants",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_vegetable :
                        populateVegatable();
                     //   Toast.makeText(getApplicationContext(),"Clicked on All Vegetable",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;    */

                    case R.id.menu_myorders :
                    //    Toast.makeText(getApplicationContext(),"Clicked on My orders",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Users.this, MyOrders.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_contactus :
                     //   Toast.makeText(getApplicationContext(),"Clicked on Contact Us",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Users.this, ContactUs.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_aboutus :
                      //  Toast.makeText(getApplicationContext(),"Clicked on About Us",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Users.this, AboutUs.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_logout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), Signin.class));
                        finish();
                        break;
                  /*  case R.id.menu_pricing:
                        Toast.makeText(Users.this, "Clicked on Pricing", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Users.this, Pricing.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;  */

                }
                return true;
            }
        });
    }

    private void gotoProfile() {
        initializeProfile();
        AlertDialog.Builder a11= new AlertDialog.Builder(Users.this);
        a11.setTitle("Profile");
        a11.setMessage("Choose either check profile or update profile");
        a11.setPositiveButton("Check profile", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkProfile();
            }
        });
        a11.setNegativeButton("Update profile", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateProfile();
            }
        });
        AlertDialog a1= a11.create();
        a1.show();
    }

    private void initializeProfile() {
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

    private void updateProfile() {
        update();
    }

    private void update() {
        System.out.println("From Profile Update() username: " + username + " usermobile: " + usermobile);

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

                            //    System.out.println("From Tpo mString is: " + mString);


                            } catch (ClassCastException cce2) {

                            }
                        }

                     //   System.out.println("getCustomerData " + getCustomerData);

                        String val11= "";

                        for (String s: getCustomerData) {
                            val11+= s + " ";
                        }

                     //   System.out.println("From Profile val11: " + val11);

                        Set<String> a1= csData.init(val11, username, usermobile);

                     //   System.out.println("From Profile a1: " + a1);

                        ArrayList<String> setToList= new ArrayList<>();

                        setToList.addAll(a1);

                        Intent sendData= new Intent(Users.this, UpdateProfile.class);
                        sendData.putStringArrayListExtra("settolist", setToList);
                        startActivity(sendData);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkProfile() {
        display();
    }

    private void display() {
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

                        ArrayList<String> setToList= new ArrayList<>();

                        setToList.addAll(a1);

                        System.out.println("From Users display() setToList: " + setToList);

                        Intent sendData= new Intent(Users.this, DisplayProfile.class);
                        sendData.putStringArrayListExtra("settolist", setToList);
                        startActivity(sendData);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openCart() {
        AlertDialog.Builder a11= new AlertDialog.Builder(Users.this);
        a11.setTitle("Open cart");
        a11.setMessage("Go to MyCart");
        a11.setNegativeButton("Mycart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder a01= new AlertDialog.Builder(Users.this);
                a01.setTitle("Info");
                a01.setMessage("Please wait while server retrieves your data");
                a01.setCancelable(true);
                a01.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog a1= a01.create();
                a1.show();
                MyAnotherCart();
            }
        });
        AlertDialog a1= a11.create();
        a1.show();
      //  System.out.println("From openCart() set1: " + copySet1 + "\tset11: " + copySet11);
    }

    private void MyAnotherCart() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("AddToCartSeedsAndFertilizerPerUser");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                            SaveAddToCartSeedsAndFertilizers addcartData = new SaveAddToCartSeedsAndFertilizers((String) userData.get("username"),(String) userData.get("mobile"),(String) userData.get("name"), (String) userData.get("itemdetail"), (String) userData.get("price"), (String) userData.get("category"), (String) userData.get("imageuri"), (String) userData.get("quantity"), (String) userData.get("seedsqty"), (String) userData.get("id"));

                            addCartModel.add(addcartData.getUsername());
                            addCartModel.add(addcartData.getMobile());
                            addCartModel.add(addcartData.getName());
                            addCartModel.add(addcartData.getItemdetail());
                            addCartModel.add(addcartData.getPrice());
                            addCartModel.add(addcartData.getCategory());
                            addCartModel.add(addcartData.getImageuri());
                            addCartModel.add(addcartData.getQuantity());
                            addCartModel.add(addcartData.getSeedsqty());
                            addCartModel.add(addcartData.getId());

                            //   System.out.println("From Tpo getData is: " + getData);

                        } catch (ClassCastException cce) {
                            try {

                                String mString = String.valueOf(dataMap.get(key));

                            //    System.out.println("From MyAnotherCart() mString is: " + mString);


                            } catch (ClassCastException cce2) {

                            }
                        }

                     //   System.out.println("From MyAnotherCart() addCartModel1: " + addCartModel);

                        for (String s: addCartModel) {
                            val11+= s + " ";
                        }

                        System.out.println("From MyAnotherCart() val1: " + val11);

                        System.out.println("From MyAnotherCart() username: " + username + "\tusermobile: " + usermobile);
                    }

                    set1= getSeeds.init(val11, username, usermobile);

                    copySet1.addAll(set1);

                    System.out.println("From MyAnotherCart() set1 is: " + set1);

                    MyCart(set1);

                    System.out.println("From MyAnotherCart() set1 is: " + set1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void MyCart(final Set<Set<String>> a11) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("AddToCartPerUser");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            ArrayList<String> tempList= new ArrayList<>();

                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                            SaveAddToCartItemsDetails addcartData = new SaveAddToCartItemsDetails((String) userData.get("username"),(String) userData.get("mobile"),(String) userData.get("name"), (String) userData.get("itemdetail"), (String) userData.get("price"), (String) userData.get("category"), (String) userData.get("imageuri"), (String) userData.get("quantity"), (String) userData.get("id"));

                            addCartModel1.add(addcartData.getUsername());
                            addCartModel1.add(addcartData.getMobile());
                            addCartModel1.add(addcartData.getName());
                            addCartModel1.add(addcartData.getItemdetail());
                            addCartModel1.add(addcartData.getPrice());
                            addCartModel1.add(addcartData.getCategory());
                            String imageUrl[]= addcartData.getImageuri().split("\\s+");
                            for (String s: imageUrl) {
                                System.out.println("imageUrl is: " + s);
                            }
                            if (imageUrl.length > 1) {
                                String quant = imageUrl[imageUrl.length - 2] + " " + imageUrl[imageUrl.length - 1];
                                String image = addcartData.getImageuri().replace(quant, "").trim();
                             //   addCartModel1.add(image);
                             //   addCartModel1.add(addcartData.getQuantity());
                             //   addCartModel1.add(addcartData.getId());
                                tempList.add(image);
                                tempList.add(addcartData.getQuantity());
                                tempList.add(addcartData.getId());
                            }
                            if (tempList.contains("https://firebasestorage")) {
                                if (imageUrl.length > 1) {
                                    String quant = imageUrl[imageUrl.length - 2] + " " + imageUrl[imageUrl.length - 1];
                                    String image = addcartData.getImageuri().replace(quant, "").trim();
                                       addCartModel1.add(image);
                                       addCartModel1.add(addcartData.getQuantity());
                                       addCartModel1.add(addcartData.getId());
                                 //   tempList.add(image);
                                  //  tempList.add(addcartData.getQuantity());
                                  //  tempList.add(addcartData.getId());
                                }
                            }
                            else if (!tempList.contains("https://firebasestorage")) {
                                addCartModel1.add(imageUrl[1]);
                                addCartModel1.add(addcartData.getQuantity());
                                addCartModel1.add(addcartData.getId());
                            }
                            //   System.out.println("From Tpo getData is: " + getData);
                        } catch (ClassCastException cce) {
                            try {

                                String mString = String.valueOf(dataMap.get(key));

                            //   System.out.println("From MyCart() mString is: " + mString);


                            } catch (ClassCastException cce2) {

                            }
                        }

                        System.out.println("From MyCart() addCartModel1: " + addCartModel1);

                        for (String s: addCartModel1) {
                            val111+= s + " ";
                        }

                        System.out.println("From MyCart() val1: " + val11);

                        System.out.println("From MyCart() username: " + username + "\tusermobile: " + usermobile);
                    }

                    System.out.println("From MyCart() val111 is: " + val111);

                    set11= getCart.init(val111, username, usermobile);

                    copySet11.addAll(set11);

                    System.out.println("From MyCart() set11 is: " + set11);

                 //   System.out.println("From MyCart() a11 is: " + a11);

                    String copyVal1= set11+ "";
                    String copyVal11= a11 + "";

                 //   System.out.println("From MyCart() copyVal1: " + copyVal1);
                  //  System.out.println("From MyCart() copyVal11: " + copyVal11);

                    pd.dismiss();

                    Intent sendData= new Intent(Users.this, DisplayCartFromBottom.class);
                    sendData.putExtra("copyval1", copyVal1);
                    sendData.putExtra("copyval11", copyVal11);

                    startActivity(sendData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openCategoryDialog() {
        ChooseCartDialog ctd= new ChooseCartDialog();
        ctd.show(getSupportFragmentManager(), "Choose Cart Dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
     //   menu.add(0,Menu.FIRST,Menu.NONE,"Logout");
     //   menu.add(1, Menu.FIRST+1, Menu.NONE, "Profile");

         MenuItem.OnActionExpandListener onActionExpandListener= new MenuItem.OnActionExpandListener() {
             @Override
             public boolean onMenuItemActionExpand(MenuItem item) {
               //  Toast.makeText(Users.this, "Search is expanded", Toast.LENGTH_SHORT).show();
                 return true;
             }

             @Override
             public boolean onMenuItemActionCollapse(MenuItem item) {
               //  Toast.makeText(Users.this, "Search is closed", Toast.LENGTH_SHORT).show();
                 return true;
             }
         };
         menu.findItem(R.id.search_id).setOnActionExpandListener(onActionExpandListener);
         SearchView searchView= (SearchView) menu.findItem(R.id.search_id).getActionView();
         String input= searchView.getQuery()+"";
      //  Toast.makeText(this, "input is: " + input, Toast.LENGTH_SHORT).show();
      //   SearchView searchView= (SearchView) menu.findItem(R.id.search_id).getActionView();
         searchView.setQueryHint("Search product items here...");


         searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
             @Override
             public boolean onQueryTextSubmit(String query) {
                 searchItem= query;
              //   Toast.makeText(Users.this, "Typed item is: " + searchItem, Toast.LENGTH_SHORT).show();
                 searchAndShowItems(searchItem);
                 return true;
             }

             @Override
             public boolean onQueryTextChange(String newText) {
                 return true;
             }
         });

         return super.onCreateOptionsMenu(menu);
    }

    private void searchAndShowItems(String searchItem) {
        System.out.println("From Users searchAndShowItems() searchItem is: " + searchItem);
        checkItemsInProducts(searchItem);
        checkItemsInRecomendedItems(searchItem);
        checkItemsInSeedsAndFertilizers(searchItem);
    }

    private void checkItemsInProducts(final String searchItem) {
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
                            checkItemsProducts.add(getProduct.getName());
                            checkItemsProducts.add(getProduct.getPrice());
                            checkItemsProducts.add(getProduct.getDetails());
                            checkItemsProducts.add(getProduct.getCategory());
                            checkItemsProducts.add(getProduct.getId());
                            checkItemsProducts.add(getProduct.getImageUrl());
                            checkItemsProducts.add(getProduct.getQuantity());

                        } catch (ClassCastException cce) {
                            try {
                                String mString = String.valueOf(dataMap.get(key));
                            } catch (ClassCastException cce2) {

                            }
                        }
                    }

                 //   System.out.println("From users checkItemsInProducts checkItemsProducts: " + checkItemsProducts);

                    String val1= "";

                    for (String s: checkItemsProducts) {
                        val1+= s + " ";
                    }

                    System.out.println("From Users checkItemsInRecomendedItems val1: " + val1);

                    if (search1.init(val1, searchItem)) {
                      //  System.out.println("search1.detailsSet: " + search1.detailsSet);
                        Set<Set<String>> set1= search1.detailsSet;

                        accumulateAllDatas(set1, false);
                    }
                    else {
                     //   System.out.println("No items has been found!");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void accumulateAllDatas(Set<Set<String>> set12345, boolean isFromSeedsAndFertilizers) {
     //   System.out.println("From accumulateAllDatas() set1 is: " + set12345);
        if (set12345.size()==0) {
          //  Toast.makeText(this, "No specific item has been found!", Toast.LENGTH_SHORT).show();
        }
        else {
            displaySearchedItems(set12345, isFromSeedsAndFertilizers);
            set12345.clear();
        }
    }

    private void displaySearchedItems(Set<Set<String>> set12345, boolean isFromSeedsAndFertilizers) {
        String value001="";
        for (Set<String> set001: set12345) {
            for (String s: set001) {
                value001+= s + " ";
            }
            value001+=" * ";
        }
      //  System.out.println("From displaySearchedItems() value001: " + value001);
        Intent sendData= new Intent(Users.this, DisplaySearchedItems.class);
        sendData.putExtra("value0001", value001);
        sendData.putExtra("isseed", isFromSeedsAndFertilizers);
        startActivity(sendData);
    }

    private void checkItemsInSeedsAndFertilizers(final String searchItem) {

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
                            checkItemsSeedsAndFertilizers.add(getProduct.getName());
                            checkItemsSeedsAndFertilizers.add(getProduct.getPrice());
                            checkItemsSeedsAndFertilizers.add(getProduct.getSeedsQty());
                            checkItemsSeedsAndFertilizers.add(getProduct.getDetails());
                            checkItemsSeedsAndFertilizers.add(getProduct.getCategory());
                            checkItemsSeedsAndFertilizers.add("id: " + getProduct.getId());
                            checkItemsSeedsAndFertilizers.add(getProduct.getImageUrl());
                            checkItemsSeedsAndFertilizers.add(getProduct.getQuantity());

                        } catch (ClassCastException cce) {
                            try {
                                String mString = String.valueOf(dataMap.get(key));
                             //   System.out.println("mString " + mString);
                            } catch (ClassCastException cce2) {
                            }
                        }

                    //    System.out.println("From users checkItemsInSeedsAndFertilizers() checkItemsSeedsAndFertilizers: " + checkItemsSeedsAndFertilizers);

                        String val1= "";

                        for (String s: checkItemsSeedsAndFertilizers) {
                            val11+= s + " ";
                        }
                    }

                    System.out.println("From checkItemsInSeedsAndFertilizers() val1: " + val11);

                    if (search11.init(val11, searchItem)) {
                     //   System.out.println("From checkItemsInSeedsAndFertilizers() search11.extractData(): " + search11.detailsSet);
                        Set<Set<String>> set1= search11.detailsSet;

                        accumulateAllDatas(set1, true);
                    }
                    else {
                     //   System.out.println("From checkItemsInSeedsAndFertilizers() No items has been found!");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkItemsInRecomendedItems(final String searchItem) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Recommend Items");
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
                            checkItemsRecommend.add(getProduct.getName());
                            checkItemsRecommend.add(getProduct.getPrice());
                            checkItemsRecommend.add(getProduct.getDetails());
                            checkItemsRecommend.add(getProduct.getCategory());
                            checkItemsRecommend.add(getProduct.getId());
                            checkItemsRecommend.add(getProduct.getImageUrl());
                            checkItemsRecommend.add(getProduct.getQuantity());

                        } catch (ClassCastException cce) {
                            try {
                                String mString = String.valueOf(dataMap.get(key));
                            } catch (ClassCastException cce2) {

                            }
                        }
                    }

                 //   System.out.println("From users checkItemsInRecomendedItems checkItemsRecommend: " + checkItemsRecommend);

                    String val1= "";

                    for (String s: checkItemsRecommend) {
                        val1+= s + " ";
                    }

                    System.out.println("From Users checkItemsInRecomendedItems val1: " + val1);

                    if (search1.init(val1, searchItem)) {
                      //  System.out.println("search1.detailsSet: " + search1.detailsSet);

                        Set<Set<String>> set1= search1.detailsSet;

                     //   System.out.println("From Users set1 is: " + set1);

                        accumulateAllDatas(set1, false);
                    }
                    else {
                    //    System.out.println("No items has been found!");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      /*  switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Signin.class));
                finish();
                break;
        //    case Menu.FIRST+1:
        //        startActivity(new Intent(Users.this, com.goku277.plantorder.Components.Profile.class));
        //        break;
        }   */
        return super.onOptionsItemSelected(item);
    }

    private void cart() {

    }

    private void profile() {

    }

    private void getRecomendedPlantDetails() {
    //    Toast.makeText(this, "getRecommendedPlantDetails() initiated...", Toast.LENGTH_SHORT).show();
        System.out.println("getRecommendedPlantDetails() initiated...");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Recommend Items");
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

                      //  System.out.println("From users getPlanDetails() getRecommendedItems: " + getRecommendedItems);

                        String val1= "";

                        for (String s: getRecommendedItems) {
                            val1+= s + " ";
                        }

                      //  System.out.println("From Users getPlanDetails() val1: " + val1);

                        a1= getProductData.init(val1);

                        System.out.println("From Users getPlanDetails() a1: " + a1);

                        for (Map.Entry<String, Set<Set<String>>> e1: a1.entrySet()) {
                          //  System.out.println("e1.getKey(): " + e1.getKey() + "\te1.getValue(): " + e1.getValue());
                            setRecommendedItemsRecyclerView(e1.getValue());
                        }
                    }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setRecommendedItemsRecyclerView(Set<Set<String>> value) {
        String name= "", urlImage="";
        ArrayList<String> urlImageList= new ArrayList<>();
        for (Set<String> set1: value) {
            for (String s1: set1) {
                if (s1.contains("imageUri:")) {
                    urlImage= s1.substring(s1.indexOf("https://"), s1.indexOf("quantity:")).replace("imageUri:","").trim();
                    if (!imageUrlList.contains(urlImage)) {
                        imageUrlList.add(urlImage);
                    }
                }
            }
            setOfSets1.add(set1);
            System.out.println("From Users setRecommendedItemsRecyclerView() setOfSets1: " + setOfSets1);
        }
        cluster(imageUrlList);
    }

    private void cluster(ArrayList<String> imageUrlList) {
        System.out.println("From cluster() imageUrlList: " + imageUrlList);
        for (int i=0;i<imageUrlList.size();i=i+2) {
            AlternateRecommendedImage1.add(imageUrlList.get(i));
            rec1= new RecommendedItems_Image_1(imageUrlList.get(i));
            imgRecommendedList1.add(rec1);
        }
        for (int i=1;i<imageUrlList.size();i=i+2) {
            AlternateRecommendedImage2.add(imageUrlList.get(i));
            rec2= new RecommendedItems_Image_2(imageUrlList.get(i));
            imgRecommendedList2.add(rec2);
        }

     //   System.out.println("From Users cluster() imgRecommendedList1: " + imgRecommendedList1 + "\timgRecommendedList2: " + imgRecommendedList2);

      //  LinearLayoutManager layoutManager= new LinearLayoutManager(Users.this, LinearLayoutManager.HORIZONTAL, false);

      //  saleRecyclerView.setLayoutManager(layoutManager);

     //   saleRecyclerView.setItemAnimator(new DefaultItemAnimator());

        System.out.println("From SaveSummerPlantsData setHorizontalRecyclerView() saveList is: " + saveList1);



        System.out.println("From cluster() AlternateRecommendedImage1: " + AlternateRecommendedImage1);
        System.out.println("From cluster() AlternateRecommendedImage2: " + AlternateRecommendedImage2);
    }

    private String getPlantDetails1(String key) {
    //    Toast.makeText(this, "getPlantDetails() initiated...", Toast.LENGTH_SHORT).show();
        System.out.println("getPlantDetails() initiated...");
        final String[] value = {""};
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

                        System.out.println("From Users getPlanDetails() a1: " + a1);

                        //   sale();

                        for (Map.Entry<String, Set<Set<String>>> e1: a1.entrySet()) {
                            if (e1.getKey().equals(key)) {
                                System.out.println("e1.getValue() is: " + e1.getValue());
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
        return value[0];
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

                        System.out.println("From Users getPlanDetails() a1: " + a1);

                     //   sale();

                        for (Map.Entry<String, Set<Set<String>>> e1: a1.entrySet()) {
                            if (e1.getKey().equals("Sale")) {
                              //  sale();
                               // break;
                               // setHorizontalRecyclerView1(e1.getValue());
                            }
                            if (e1.getKey().equals("Recommend Items")) {
                              //  getRecomendedPlantDetails();
                             //   break;
                             //   setHorizontalRecyclerView11(e1.getValue());
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

    private void setHorizontalRecyclerView11(Set<Set<String>> value) {
    //    System.out.println("From Users setHorizontalRecyclerView11 value is: " + value);
        String name= "", urlImage="";
        ArrayList<String> urlImageList= new ArrayList<>();
        for (Set<String> set1: value) {
            for (String s1: set1) {
                if (s1.contains("imageUri:")) {
                    urlImage= s1.replace("imageUri:","").trim();
                    if (!imageUrlList.contains(urlImage)) {
                        imageUrlList.add(urlImage);
                        saveAll = new SaveAllSeasonPlantsData(urlImage);
                        saveList1.add(saveAll);
                    }
                }
            }
            setOfSets1.add(set1);
            LinearLayoutManager layoutManager= new LinearLayoutManager(Users.this, LinearLayoutManager.HORIZONTAL, false);

         //   recyclerView1.setLayoutManager(layoutManager);
//
         //   recyclerView1.setItemAnimator(new DefaultItemAnimator());

        //    System.out.println("From SaveSummerPlantsData setHorizontalRecyclerView() saveList is: " + saveList1);

       //     allSeasonAdapter = new AllSeasonAdapter(Users.this, saveList1, setOfSets1);

       //     recyclerView1.setAdapter(allSeasonAdapter);
        }

    //    System.out.println("From Users SetHorizontalRecyclerView1() setOfSets: " + setOfSets1);

    //   System.out.println("From Users SetHorizontalRecyclerView1() imageUrlList is: " + imageUrlList);
    }

    private void setHorizontalRecyclerView1(Set<Set<String>> value) {
        //     System.out.println("From Users setHorizontalRecyclerView value is: " + value);
        String name = "", urlImage = "";
        ArrayList<String> urlImageList = new ArrayList<>();
        for (Set<String> set1 : value) {
            for (String s1 : set1) {
                if (s1.contains("imageUri:")) {
                    urlImage = s1.replace("imageUri:", "").trim();
                    if (!imageUrlList.contains(urlImage)) {
                        imageUrlList.add(urlImage);
                        save = new SaveSummerPlantsData(urlImage);
                        saveList.add(save);
                    }
                }
            }
         /*   setOfSets.add(set1);
            LinearLayoutManager layoutManager= new LinearLayoutManager(Users.this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setItemAnimator(new DefaultItemAnimator());

        //    System.out.println("From SaveSummerPlantsData setHorizontalRecyclerView() saveList is: " + saveList);

            summerAdapter= new SummerAdapter(Users.this, saveList, setOfSets);

            recyclerView.setAdapter(summerAdapter);
        }

    //    System.out.println("From Users SetHorizontalRecyclerView1() setOfSets: " + setOfSets);

    //    System.out.println("From Users SetHorizontalRecyclerView1() imageUrlList is: " + imageUrlList);  */
        }
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Signin.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plants_id:
                populatePlants();
                break;
                case R.id.plants_text_id:
                    populatePlants();
                    break;
                case R.id.seeds_id:
                    populateSeeds();
                    break;
                case R.id.soil_and_fertilizer_id:
                    populateSoilandFertilizer();
                    break;
                case R.id.soil_and_fertilizer_text_id:
                    populateSoilandFertilizer();
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

                case R.id.sale_id:
                    sale();
                    break;
            case R.id.img_id1:
                details= "Cactus are very essential plants and it is enrich with vitamins";
                name= "Cactus";
                price= "200";
                quantity= "50";
                addToCart(name, details, price, quantity);
                break;
            case R.id.cactus_textt_id:
                details= "Cactus are very essential plants and it is enrich with vitamins";
                name= "Cactus";
                price= "200";
                quantity= "50";
                addToCart(name, details, price, quantity);
                break;
            case R.id.cactus_text_id:
                details= "Cactus are very essential plants and it is enrich with vitamins";
                name= "Cactus";
                quantity= "50";
                addToCart(name, details, price, quantity);
                break;
            case R.id.img_id2:
                details= "Neem plants are very important and are used for medicinal purposes";
                name= "Neem";
                price= "500";
                quantity= "200";
                addToCart(name, details, price, quantity);
                break;
            case R.id.neem_text_id:
                details= "Neem plants are very important and are used for medicinal purposes";
                name= "Neem";
                price= "500";
                quantity= "200";
                addToCart(name, details, price, quantity);
                break;
            case R.id.neem_price_id:
                details= "Neem plants are very important and are used for medicinal purposes";
                name= "Neem";
                price= "500";
                quantity= "200";
                addToCart(name, details, price, quantity);
                break;
            case R.id.img_id3:
                details= "Sunflower are very rare and beautiful flowers";
                name= "Sunflower";
                price= "150";
                quantity= "350";
                addToCart(name, details, price, quantity);
                break;
            case R.id.sunflower_text_id:
                details= "Sunflower are very rare and beautiful flowers";
                name= "Sunflower";
                price= "150";
                quantity= "350";
                addToCart(name, details, price, quantity);
                break;
            case R.id.sunflower_price_id:
                details= "Sunflower are very rare and beautiful flowers";
                name= "Sunflower";
                price= "150";
                quantity= "350";
                addToCart(name, details, price, quantity);
                break;
            case R.id.img_id4:
                details= "Tulsi plants are the most essential plants among all other plants as its very important in day to day life and are also used for medicinal purposes";
                name= "Tulsi";
                price= "250";
                quantity= "1200";
                addToCart(name, details, price, quantity);
                break;
            case R.id.tulsi_text_id:
                details= "Tulsi plants are the most essential plants among all other plants as its very important in day to day life and are also used for medicinal purposes";
                name= "Tulsi";
                price= "250";
                quantity= "1200";
                addToCart(name, details, price, quantity);
                break;
            case R.id.tulsi_price_id:
                details= "Tulsi plants are the most essential plants among all other plants as its very important in day to day life and are also used for medicinal purposes";
                name= "Tulsi";
                price= "250";
                quantity= "1200";
                addToCart(name, details, price, quantity);
                break;
            case R.id.view_all_id:
                viewAllRecommendItems();
        }
    }

    private void viewAllRecommendItems() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Recommend Items");
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
                        System.out.println("From Users viewAllRecommendItems() val1 is: " + val1);
                        a1= getProductData.init(val1);
                        System.out.println("From Users viewAllRecommendations() a1: " + a1);
                        Set<Set<String>> set111= a1.get("Recommend Items");
                        System.out.println("From Users viewAllRecommendations() a1.get(Recommend Items): " + set111);

                        Intent sendData= new Intent(Users.this, ViewAllRecommendations.class);
                        sendData.putExtra("set111", (Serializable) set111);
                        startActivity(sendData);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addToCart(String name, String details, String price, String quantity) {
        AlertDialog.Builder a11= new AlertDialog.Builder(Users.this);
        a11.setTitle("Add To Cart");
        a11.setMessage("Do you want to add this in your cart?");
        a11.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (username==null ||usermobile==null) {
                    initializeProfile();
                }
                storageReference= FirebaseStorage.getInstance().getReference("Store Images");
                ref1= FirebaseDatabase.getInstance().getReference("AddToCartPerUser");

                if (name.equals("Sunflower")) {
                    String link= "android.resource://com.simi.plantorderingapp/" + R.drawable.sunflower_1;
                    imageUri = Uri.parse("android.resource://com.simi.plantorderingapp/" + R.drawable.sunflower_1);
                    mapUrl.insertData("Sunflower", link);
                }
                else if (name.equals("Cactus")) {
                    String link= "android.resource://com.simi.plantorderingapp/" + R.drawable.cactus_1;
                    imageUri = Uri.parse("android.resource://com.simi.plantorderingapp/" + R.drawable.cactus_1);
                    mapUrl.insertData("Cactus", link);
                }
                else if (name.equals("Neem")) {
                    String link= "android.resource://com.simi.plantorderingapp/" + R.drawable.neem_1;
                    imageUri = Uri.parse("android.resource://com.simi.plantorderingapp/" + R.drawable.neem_1);
                    mapUrl.insertData("Neem", link);
                }
                else if (name.equals("Tulsi")) {
                    String link= "android.resource://com.simi.plantorderingapp/" + R.drawable.tulsi_1;
                    imageUri = Uri.parse("android.resource://com.simi.plantorderingapp/" + R.drawable.tulsi_1);
                    mapUrl.insertData("Tulsi", link);
                }
                if (imageUri!=null) {
                    StorageReference storageReference1= storageReference.child(System.currentTimeMillis() + "");
                    storageReference1.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //    Toast.makeText(Users.this, "Image successfully uploaded", Toast.LENGTH_SHORT).show();
                            storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    uri1= uri + "";
                                 //   Toast.makeText(Users.this, "uri1 is: " + uri1, Toast.LENGTH_SHORT).show();
                                    uploadData(uri1);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        }
                    });
                }
            }
        });

        a11.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog a1= a11.create();
        a1.show();
    }

    private void uploadData(String uri1) {
        if (uri1!=null) {
            System.out.println("From Users uri1 addToCart() is: " + uri1);
            recommendedItemsAddToCart= new RecommendedItemsAddToCart();

            String rand= UUID.randomUUID().toString();

            recommendedItemsAddToCart.setCategory("Category: Recommended Items");
            recommendedItemsAddToCart.setImageuri("imageUri: " + uri1);
            recommendedItemsAddToCart.setItemdetail("Detail: " + details);
            recommendedItemsAddToCart.setMobile("usermobile: " + usermobile);
            recommendedItemsAddToCart.setUsername("username: " + username);
            recommendedItemsAddToCart.setPrice("price: " + price.replace("Price:", "price:"));
            recommendedItemsAddToCart.setQuantity("quantity: " + quantity);
            recommendedItemsAddToCart.setName("Name: " + name);
            recommendedItemsAddToCart.setId("id: " + rand);

            String key= username + " " + usermobile + " " + rand;

            ref1.child(key).setValue(recommendedItemsAddToCart);

         //   Toast.makeText(this, "Added to the cart", Toast.LENGTH_SHORT).show();
        }
        else {
        //    Toast.makeText(Users.this, "uri is empty", Toast.LENGTH_SHORT).show();
        }
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
                        System.out.println("From Users getPlanDetails() a1: " + a1);
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

        System.out.println("From Users setIndoorRecyclerView() indoorSetOfSets: " + indoorSetOfSets);
        System.out.println("From Users setIndoorRecyclerView() IndoorList1: " + IndoorList1);
        System.out.println("From Users setIndoorRecyclerView() IndoorList2: " + IndoorList2);
        System.out.println("From Users setIndoorRecyclerView() IndoorDataList1: " + indoorDataList1);
        LinearLayoutManager layoutManager= new LinearLayoutManager(Users.this, LinearLayoutManager.HORIZONTAL, false);

        indoorRecyclerView.setLayoutManager(layoutManager);

        indoorRecyclerView.setItemAnimator(new DefaultItemAnimator());

        System.out.println("From SaveSummerPlantsData setHorizontalRecyclerView() saveList is: " + saveList1);

        indoorAdapter = new IndoorAdapter(indoorDataList1, IndoorList1, IndoorList2, indoorSetOfSets, Users.this);

        indoorRecyclerView.setAdapter(indoorAdapter);
    }

    private void sale() {
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

                        System.out.println("From Users Sale() val1: " + val1);

                        a1= getProductData.init(val1);

                        Set<Set<String>> fetchSaleData= a1.get("Sale");

                        Map<String, String> mapImageToPriceId= new LinkedHashMap<>();

                        for (Set<String> set1: fetchSaleData) {
                            ArrayList<String> setToLists= new ArrayList<>();
                            setToLists.addAll(set1);
                            String imageId= setToLists.get(5).substring(setToLists.get(5).indexOf("imageUri:") ,setToLists.get(5).indexOf("quantity:")).replace("imageUri:","").trim();
                            String priceId= setToLists.get(1).replace("price:","").trim();
                            mapImageToPriceId.put(imageId, priceId);
                        }

                        System.out.println("From Users Sale() mapImageToPriceId is: " + mapImageToPriceId);


                        System.out.println("From Users getPlanDetails() a1: " + a1);
                        for (Map.Entry<String, Set<Set<String>>> e1: a1.entrySet()) {
                            if (e1.getKey().equals("Sale")) {
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
                                    saleSetOfSets.add(set1);
                                }
                                System.out.println("urlImageList: " + urlImageList);
                                SetSaleRecyclerView(urlImageList, mapImageToPriceId);
                              //  setSaleRecyclerView(val);
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

    private void SetSaleRecyclerView(ArrayList<String> urlImageList, Map<String, String> mapImageToPriceId) {
        System.out.println("From Users setSaleRecyclerView() urlImageList is: " + urlImageList);

        System.out.println("From Users SetSaleRecyclerView() urlImageList: " + urlImageList);

        for (int i=0;i<urlImageList.size();i++) {
            if (!AlternateSaleList1.contains(urlImageList.get(i))) {
                saleImageList_1 = new SaleImageList_1(urlImageList.get(i));
                saleImageList_11.add(saleImageList_1);
                AlternateSaleList1.add(urlImageList.get(i));
            }
        }

       /* for (int i=1;i<urlImageList.size();i=i+2) {
            if (!AlternateSaleList2.contains(urlImageList.get(i))) {
                saleImageList_2 = new SaleImageList_2(urlImageList.get(i));
                saleImageList_22.add(saleImageList_2);
                AlternateSaleList2.add(urlImageList.get(i));
            }
        }  */

        System.out.println("From Users SetSaleRecyclerView AlternateSaleList1: " + AlternateSaleList1);

        LinearLayoutManager layoutManager= new LinearLayoutManager(Users.this, LinearLayoutManager.HORIZONTAL, false);

        saleRecyclerView.setLayoutManager(layoutManager);

        saleRecyclerView.setItemAnimator(new DefaultItemAnimator());

        System.out.println("From SaveSummerPlantsData setHorizontalRecyclerView() saveList is: " + saveList1);

        System.out.println("From Users SetSaleRecyclerView SetSaleRecyclerView: " + mapImageToPriceId);

        saleAdapter = new SaleAdapter(saleImageList_11, saleSetOfSets, AlternateSaleList1, mapImageToPriceId, Users.this);

        saleRecyclerView.setAdapter(saleAdapter);


    /*  inearLayoutManager layoutManager= new LinearLayoutManager(Users.this, LinearLayoutManager.HORIZONTAL, false);

        saleRecyclerView.setLayoutManager(layoutManager);

        saleRecyclerView.setItemAnimator(new DefaultItemAnimator());

        System.out.println("From SaveSummerPlantsData setHorizontalRecyclerView() saveList is: " + saveList1);

        saleRecyclerViewAdapter = new SaleRecyclerViewAdapter(saleImageList_11, saleImageList_22, saleSetOfSets, AlternateSaleList1, Users.this);

        saleRecyclerView.setAdapter(saleRecyclerViewAdapter);     */

    }

    private void setSaleRecyclerView(Set<Set<String>> value) {
        String name= "", urlImage="";
        ArrayList<String> urlImageList= new ArrayList<>();
        for (Set<String> set1: value) {
            for (String s1: set1) {
                if (s1.contains("imageUri:")) {
                    urlImage= s1.substring(s1.indexOf("https://"), s1.indexOf("quantity:")).replace("imageUri:","").trim();
                    if (!imageUrlList.contains(urlImage)) {
                        imageUrlList.add(urlImage);
                        //   recommendedItemsModelData=  new RecommendedItemsModelData(urlImage);
                        //   saveList11.add(recommendedItemsModelData);
                    }
                }
            }
            setOfSets1.add(set1);
            LinearLayoutManager layoutManager= new LinearLayoutManager(Users.this, LinearLayoutManager.VERTICAL, false);

        /*    recomendedRecyclerView.setLayoutManager(layoutManager);

            recomendedRecyclerView.setItemAnimator(new DefaultItemAnimator());

            //    System.out.println("From SaveSummerPlantsData setHorizontalRecyclerView() saveList is: " + saveList1);

            recommendedItemsAdapter = new RecommendedItemsAdapter(saveList11, setOfSets1,Users.this);

            recomendedRecyclerView.setAdapter(recommendedItemsAdapter);   */
        }
        clusterSale(imageUrlList);
    }

    private void clusterSale(ArrayList<String> imageUrlList) {
        System.out.println("From Users clusterSale() imageUrlList: " + imageUrlList);
    }

    private void populateVegatable() {
        getPlantDetails();
        try {
            for (Map.Entry<String, Set<Set<String>>> e1 : a1.entrySet()) {
                if (e1.getKey().equals("Vegetable")) {
                    setVerticalRecyclerView(e1.getValue());
                    break;
                }
            }
        } catch (Exception e){}
    }

    private void populateSoilandFertilizer() {
        try {
            // Need to adjust this code... by retrieving SoilAndFertilizerProducts from the firebase database... To be continued on tomorrow...

         //   Toast.makeText(this, "Visited populateSoilAndFertilizer() ", Toast.LENGTH_SHORT).show();

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

                            Intent sendData= new Intent(Users.this, SeedsAndFertilizers.class);

                            sendData.putExtra("val11", val11);

                            sendData.putExtra("id", id);

                            sendData.putExtra("gotoseedsandfertilizer", true);

                         //   startActivity(sendData);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            for (Map.Entry<String, Set<Set<String>>> e1 : a1.entrySet()) {
              //  System.out.println("e1.getKey(): " + e1.getKey());
                if (e1.getKey().contains("Soil And Fertilizer")) {
                    setVerticalRecyclerView(e1.getValue());
                    break;
                }
            }
        } catch (Exception e){}
    }

    private void populateMedicinal() {
        getPlantDetails();
        try {
            for (Map.Entry<String, Set<Set<String>>> e1 : a1.entrySet()) {
                if (e1.getKey().equals("Medicinal Plants")) {
                    setVerticalRecyclerView(e1.getValue());
                    break;
                }
            }
        } catch (Exception e){}
    }

    private void populateGarden() {
        getPlantDetails();
        try {
            for (Map.Entry<String, Set<Set<String>>> e1 : a1.entrySet()) {
                if (e1.getKey().equals("Gardening Tools")) {
                    setVerticalRecyclerView(e1.getValue());
                    break;
                }
            }
        } catch (Exception e){}
    }

    private void populateWinter() {
        getPlantDetails();
        try {
            for (Map.Entry<String, Set<Set<String>>> e1 : a1.entrySet()) {
                if (e1.getKey().equals("Winter Plants")) {
                    setVerticalRecyclerView(e1.getValue());
                    break;
                }
            }
        } catch (Exception e){}
    }

    private void setVerticalRecyclerView(Set<Set<String>> value) {
     //   System.out.println("From User setVerticalRecyclerView value is: " + value);
        String val1="";
        for (Set<String> set1: value) {
            for (String s: set1) {
                val1+= s+ " ";
            }
        }
    //    System.out.println("From Users setVerticalRecyclerView() val1 is: " + val1);
        Intent sendData= new Intent(Users.this, CategoricalData.class);
        sendData.putExtra("val", val1);
        startActivity(sendData);
    }

    private void populateSeeds() {
        getPlantDetails();
        try {
            for (Map.Entry<String, Set<Set<String>>> e1 : a1.entrySet()) {
                if (e1.getKey().equals("Seeds")) {
                    setVerticalRecyclerView(e1.getValue());
                    break;
                }
            }
        } catch (Exception e){}
    }

    private void populatePlants() {
        getPlantDetails();
        try {
            // Plants
            for (Map.Entry<String, Set<Set<String>>> e1 : a1.entrySet()) {
                if (e1.getKey().equals("Plants")) {
                //    System.out.println("e1.getValue(): " + e1.getValue());
                    setVerticalRecyclerView(e1.getValue());
                    break;
                }
            }
        } catch (Exception e){}
    }

    @Override
    public void applyChooseCategorysFields(String choose) {
        if (choose.equalsIgnoreCase("Plants")) {
            populatePlants();
        }
        else if (choose.equalsIgnoreCase("Seeds")) {
            populateSeeds();
        }
        else if (choose.equalsIgnoreCase("Winter Plants")) {
            populateWinter();
        }
        else if (choose.equalsIgnoreCase("All Season Plants")) {

        }
        else if (choose.equalsIgnoreCase("Gardening Tools")) {
            populateGarden();
        }
        else if (choose.equalsIgnoreCase("Medicinal Plants")) {
            populateMedicinal();
        }
        else if (choose.equalsIgnoreCase("Vegetable")) {
            populateVegatable();
        }
        else if (choose.equalsIgnoreCase("Soil And Fertilizer")) {
            populateSoilandFertilizer();
        }
    }
}