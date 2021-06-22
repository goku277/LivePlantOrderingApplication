package com.simi.plantorderingapp.ProcessingUnit;

import java.lang.*;
import java.util.*;
public class GetIdsFromSeedsAndFertilizers {
  /*  public static void main(String args[]) {
        String data="username: Sneha usermobile: 9101754385 Name:  Vermi Compost Price:  150 Category:  Soil and Fertilizer id: https://firebasestorage.googleapis.com/v0/b/myplantapp-10c99.appspot.com/o/kg%20Vermi%20Compost%20ba9f56b9-4c54-4083-9960-5bf65dcd4fab?alt=media&token=b49217b7-5572-4ae6-8f3a-914b02ddb21e Quantity:  60 seedsqty: 1 kg c7611925-1979-4bb8-8908-e87499b766f5 Details:  Good for plant username: Sneha usermobile: 9101754385 Name:  Vermi Compost Price:  150 Category:  Soil and Fertilizer id: https://firebasestorage.googleapis.com/v0/b/myplantapp-10c99.appspot.com/o/kg%20Vermi%20Compost%20ba9f56b9-4c54-4083-9960-5bf65dcd4fab?alt=media&token=b49217b7-5572-4ae6-8f3a-914b02ddb21e Quantity:  60 seedsqty: 1 kg 9ab6d0c7-b9c2-415b-ac85-7cabc3c066a9 Details:  Good for plant username: Sneha usermobile: 9101754385 Name:  Fertilizer 2 Price:  10 Category:  Soil and Fertilizer id: https://firebasestorage.googleapis.com/v0/b/myplantapp-10c99.appspot.com/o/kg%20Fertilizer%202%20f0505a5a-0257-4e63-a7b9-d09fab52d634?alt=media&token=eae67ded-38d7-4a25-bb4f-c970b7a1034d Quantity:  10 seedsqty: 10 kg bddd77a6-3a70-4b7b-a22f-1e2a8469e095 Details:  Fertilizer 2 ";
        String name="name: Vermi Compost", price="price: 150", category="category: Soil and Fertilizer id:", quantity="quantity: 60";
        String id= init(data, name, price, category, quantity);
        System.out.println("From main() id: " + id);
    }   */

    public String init(String data, String name, String price, String quantity) {
        name= name.replace("name:", "");
        price= price.replace("price:", "");
        quantity= quantity.replace("quantity:", "");
        System.out.println(name + "\t" + price + "\t" + quantity);
        System.out.println("data: " + data);
        String split[]= data.split("username:");
        String id="", id11="", userName="", userMobile="";
        for (String s: split) {
            if (!s.trim().isEmpty()) {
                if (s.contains(name) && s.contains(price) && s.contains(quantity)) {
                    System.out.println("split: " + s);
                    if (s.contains("usermobile:")) {
                        userName= s.substring(s.indexOf(s.charAt(0)), s.indexOf("usermobile:")).replace("username:", "").trim();
                    }
                    if (s.contains("usermobile:") && s.contains("Name:")) {
                        userMobile= s.substring(s.indexOf("usermobile:"), s.indexOf("Name:")).replace("usermobile:", "").trim();
                    }
                    if (s.contains("seedsqty:") && s.contains("Details:")) {
                        id= s.substring(s.indexOf("seedsqty:"), s.indexOf("Details:")).trim();
                        String id1[]= id.split("\\s+");
                        for (String s5: id1) {
                            System.out.println("id1: " + s5);
                        }
                        id11= id1[3].trim();
                    }
                }
            }
        }
        System.out.println("id11 is: " + id11);
        String key= userName+ " " + userMobile + " " + id11;
        System.out.println("From GetIdsFromSeedsAndFertilizers key is: " + key);
        return key;
    }
}