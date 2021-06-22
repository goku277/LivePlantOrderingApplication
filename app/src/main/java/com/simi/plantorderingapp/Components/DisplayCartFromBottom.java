package com.simi.plantorderingapp.Components;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.simi.plantorderingapp.Adapter.DisplayCartFromBottomAdapter;
import com.simi.plantorderingapp.Database.Profiledb;
import com.simi.plantorderingapp.ProductModel.SaveAddToCartItemsDetails;
import com.simi.plantorderingapp.ProductModel.SaveAddToCartSeedsAndFertilizers;
import com.simi.plantorderingapp.R;

import java.util.ArrayList;

public class DisplayCartFromBottom extends AppCompatActivity {

    String a1="", a11="", username="", usermobile="";

    RecyclerView recyclerView11;

    ArrayList<SaveAddToCartItemsDetails> aList1= new ArrayList<>();

    ArrayList<SaveAddToCartSeedsAndFertilizers> aList11= new ArrayList<>();

    Profiledb pf;

    ArrayList<ArrayList<String>> listOfLists1= new ArrayList<>();
    ArrayList<ArrayList<String>> listOfLists11= new ArrayList<>();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cart_from_bottom);

        pf= new Profiledb(DisplayCartFromBottom.this);

        recyclerView11= (RecyclerView) findViewById(R.id.recycler_id);
        Intent getData= getIntent();

        a1= getData.getStringExtra("copyval1");
        a11= getData.getStringExtra("copyval11");

        System.out.println("From Display a1 is: " + a1);
        System.out.println("From Display a11 is: " + a11);

        String split1[]= a1.split("\\[|\\]|,");

        ArrayList<String> list1= new ArrayList<>();

        for (String s: split1) {
            if (!s.trim().isEmpty()) {
                String name = "", price = "", image = "", quantity = "", concat = "";
                s = s.trim();
                System.out.println("split1: " + s);
                if (s.contains("Name:")) {
                    name = s;
                }
                if (s.contains("price:")) {
                    price = s;
                }
                if (s.contains("https:")) {
                    image = s.trim();
                }
                if (s.contains("quantity:")) {
                    quantity = s;
                }
                concat = name + " " + price + " " + image + " " + quantity;

             //   if (!list1.contains(concat)) {
                    list1.add(concat);
            //    }

            }

        }

        System.out.println("From DisplayCartFromBottom split1 list1: " + list1);

        ArrayList<String> listcopy= new ArrayList<>();

        for (String s1: list1) {
            if (!s1.trim().isEmpty()) {
                s1= s1.trim();
                System.out.println("list1: " + s1);
                listcopy.add(s1);
            }
        }

        slidingWindow1(listcopy);

     //   System.out.println("After split1 aList11: " + aList11);

        String split11[]= a11.split("\\[|\\]|,");

        ArrayList<String> list11= new ArrayList<>();
        for (String s: split11) {
            if (!s.trim().isEmpty()) {
                String name="", price="", image="", quantity="", seedsqty1="", concat="";
                s=s.trim();
                System.out.println("split11: " + s);
                if (s.contains("Name:")) {
                    name= s;
                }
                if (s.contains("Price:")) {
                    price= s;
                }
                if (s.contains("id:")) {
                    image= s.replace("id:", "imageUri: ").trim();
                }
                if (s.contains("Quantity:")) {
                    quantity= s;
                }
                if (s.contains("seedsqty:")) {
                    seedsqty1= s;
                }
                concat= name + " " + price + " " + image + " " + quantity + " " + seedsqty1;

              //  if (!list11.contains(concat)) {
                    list11.add(concat);
             //   }
            }

         //   SaveAddToCartSeedsAndFertilizers save11= new SaveAddToCartSeedsAndFertilizers(username, usermobile, name, "", price, "", image, quantity, seedsqty1,"");
         //   aList11.add(save11);
          //  listOfLists11.add(list11);
        }

        System.out.println("From DisplayCartFromBottom split11 list11: " + list11);

        ArrayList<String> list1copy= new ArrayList<>();

        for (String s1: list11) {
            if (!s1.trim().isEmpty()) {
                s1= s1.trim();
                System.out.println("list11: " + s1);
                list1copy.add(s1);
            }
        }

        slidingWindow11(list1copy);

     //   System.out.println("listOfLists1: " + listOfLists1);

      //  System.out.println("listOfLists11: " + listOfLists11);

      //  System.out.println("After split11 aList11: " + aList11);

        LinearLayoutManager layoutManager = new LinearLayoutManager(DisplayCartFromBottom.this, LinearLayoutManager.VERTICAL, false);

        recyclerView11.setLayoutManager(layoutManager);

        recyclerView11.setItemAnimator(new DefaultItemAnimator());

        DisplayCartFromBottomAdapter disp= new DisplayCartFromBottomAdapter(aList11, DisplayCartFromBottom.this);

        recyclerView11.setAdapter(disp);
    }

    private void slidingWindow11(ArrayList<String> list1copy) {
        int start=0, end=0;
        while (end <= list1copy.size()) {
            end++;
            if (end-start+1==5) {
                SaveAddToCartSeedsAndFertilizers save1= new SaveAddToCartSeedsAndFertilizers(username, usermobile, list1copy.get(start), "", list1copy.get(start + 1), "", list1copy.get(start+2), "", list1copy.get(end), "");
                aList11.add(save1);
                end++;
                start= end;
            }
        }
        System.out.println("From slidingWindow11 aList11: " + aList11);
    }

    private void slidingWindow1(ArrayList<String> listcopy) {
        int start=0, end=0;
        while (end <= listcopy.size()) {
            end++;
            if (end-start+1==4) {
                try {
                    System.out.println("From DisplayCartFromBottom slidingWindow11 listcopy: " + listcopy);
                    SaveAddToCartSeedsAndFertilizers save1 = new SaveAddToCartSeedsAndFertilizers(username, usermobile, listcopy.get(start), "", listcopy.get(start + 1), "", listcopy.get(start + 2), " ", "", "");
                    aList11.add(save1);
                    end++;
                    start = end;
                } catch (Exception e) {}
            }
        }
        System.out.println("From slidingWindow1 aList11: " + aList11);
    }
}