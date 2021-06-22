package com.simi.plantorderingapp.ProcessingUnit;

import java.util.LinkedHashSet;
import java.util.Set;

public class SearchSeedsAndFertilizers {
    public Set<Set<String>> detailsSet= new LinkedHashSet<>();
  /*  public static void main(String[] args) {
        String data="name: Fertilizer 4 price: 25 seedsqty: 50 kg detail: Fertilozer 4 is very good for soil and plants. category: Soil and Fertilizer id: key: 50 kg Fertilizer 4 8e018017-d19c-4f3e-a1a1-bd2ebb10cada imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/kg%20Fertilizer%204%208fa57696-e298-489b-a1e4-54a399faa26a?alt=media&token=caa9a3de-8ace-4faa-9fea-4a30a4ab93f0 quantity: 10 name: Fertilizer 4 price: 25 seedsqty: 50 kg detail: Fertilozer 4 is very good for soil and plants. category: Soil and Fertilizer id: key: 50 kg Fertilizer 4 8e018017-d19c-4f3e-a1a1-bd2ebb10cada imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/kg%20Fertilizer%204%208fa57696-e298-489b-a1e4-54a399faa26a?alt=media&token=caa9a3de-8ace-4faa-9fea-4a30a4ab93f0 quantity: 10 name: Fertilizer 2 price: 15 seedsqty: 22 kg detail: Fertilizer 2 os essential for plant growth. category: Soil and Fertilizer id: key: 22 kg Fertilizer 2 62913c03-3823-4045-b686-4f9726f0427c imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/kg%20Fertilizer%202%204a0c19d2-4e44-4826-a330-0a7c73600484?alt=media&token=f84f84ef-6d2e-4cf5-987b-d354ebf92711 quantity: 10 name: Fertilizer 4 price: 25 seedsqty: 50 kg detail: Fertilozer 4 is very good for soil and plants. category: Soil and Fertilizer id: key: 50 kg Fertilizer 4 8e018017-d19c-4f3e-a1a1-bd2ebb10cada imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/kg%20Fertilizer%204%208fa57696-e298-489b-a1e4-54a399faa26a?alt=media&token=caa9a3de-8ace-4faa-9fea-4a30a4ab93f0 quantity: 10 name: Fertilizer 2 price: 15 seedsqty: 22 kg detail: Fertilizer 2 os essential for plant growth. category: Soil and Fertilizer id: key: 22 kg Fertilizer 2 62913c03-3823-4045-b686-4f9726f0427c imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/kg%20Fertilizer%202%204a0c19d2-4e44-4826-a330-0a7c73600484?alt=media&token=f84f84ef-6d2e-4cf5-987b-d354ebf92711 quantity: 10 name: Fertilizer price: 10 seedsqty: 10 kg detail: Fertilizer for supporting plants growth category: Soil and Fertilizer id: key: 10 kg Fertilizer 8aeed01b-babf-4294-bcf0-15a2f7b2e00f imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/kg%20Fertilizer%20dc7f0179-921e-42e5-ae16-4330c54137e0?alt=media&token=9abb2fa1-24da-4c4a-b73d-442f9451db96 quantity: 15 name: Fertilizer 4 price: 25 seedsqty: 50 kg detail: Fertilozer 4 is very good for soil and plants. category: Soil and Fertilizer id: key: 50 kg Fertilizer 4 8e018017-d19c-4f3e-a1a1-bd2ebb10cada imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/kg%20Fertilizer%204%208fa57696-e298-489b-a1e4-54a399faa26a?alt=media&token=caa9a3de-8ace-4faa-9fea-4a30a4ab93f0 quantity: 10 name: Fertilizer 2 price: 15 seedsqty: 22 kg detail: Fertilizer 2 os essential for plant growth. category: Soil and Fertilizer id: key: 22 kg Fertilizer 2 62913c03-3823-4045-b686-4f9726f0427c imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/kg%20Fertilizer%202%204a0c19d2-4e44-4826-a330-0a7c73600484?alt=media&token=f84f84ef-6d2e-4cf5-987b-d354ebf92711 quantity: 10 name: Fertilizer price: 10 seedsqty: 10 kg detail: Fertilizer for supporting plants growth category: Soil and Fertilizer id: key: 10 kg Fertilizer 8aeed01b-babf-4294-bcf0-15a2f7b2e00f imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/kg%20Fertilizer%20dc7f0179-921e-42e5-ae16-4330c54137e0?alt=media&token=9abb2fa1-24da-4c4a-b73d-442f9451db96 quantity: 15 name: Fertilizer 1 price: 25 seedsqty: 25 kg detail: Fertilizer 1 helps to nourish soils and also supports plants growth. category: Soil and Fertilizer id: key: 25 kg Fertilizer 1 41cfab75-3003-4fb2-b71f-0f4b2aafaeae imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/kg%20Fertilizer%201%20c3618d0e-8923-4279-bcde-d75b336c071c?alt=media&token=7382ea82-b7c6-41f7-9893-3f89ef1e3390 quantity: 22";
        String product="Fertilizer 2";
        if (init(data, product)) {
            System.out.println("From main() detailsSet is: " + detailsSet);
        }
        else {
            System.out.println("Item not found!");
        }
    }     */

    public boolean init(String data, String product) {
        boolean isFound= false;
        System.out.println("data: " + data);
        String split[]= data.split("name:");
        for (String s: split) {
            if (!s.trim().isEmpty()) {    // || s.contains(product.toUpperCase())
              //  s= s.toLowerCase();
                s= s.trim();
                product= product.trim();
                String prod1= product.charAt(0)+"", prod11="";
                if ((int) prod1.charAt(0) >= 65 && (int) prod1.charAt(0) <=90) {
                    if (s.contains(product)) {
                        extractData(s);
                        isFound = true;
                    }
                }
                else {
                    //  prod1= prod1.toUpperCase();
                    product= product.replace(prod1, prod1.toUpperCase());
                    if (s.contains(product)) {
                        extractData(s);
                        isFound = true;
                    }
                }
            }
        }
        return isFound;
    }

    public void extractData(String s) {
        Set<String> set1= new LinkedHashSet<>();
        if (s.contains("price:")) {
            set1.add(s.substring(s.indexOf(s.charAt(0)), s.indexOf("price:")).trim());
        }
        if (s.contains("price:") && s.contains("seedsqty:")) {
            set1.add(s.substring(s.indexOf("price:"), s.indexOf("seedsqty:")).trim());
        }
        if (s.contains("seedsqty:") && s.contains("detail:")) {
            set1.add(s.substring(s.indexOf("seedsqty:"), s.indexOf("detail:")).trim());
        }
        if (s.contains("detail:") && s.contains("category:")) {
            set1.add(s.substring(s.indexOf("detail:"), s.indexOf("category:")).trim());
        }
        if (s.contains("category:") && s.contains("id:")) {
            set1.add(s.substring(s.indexOf("category:"), s.indexOf("id:")).trim());
        }
        if (s.contains("key:") && s.contains("imageUri:")) {
            set1.add(s.substring(s.indexOf("key:"), s.indexOf("imageUri:")).trim());
        }
        if (s.contains("imageUri:") && s.contains("quantity:")) {
            set1.add(s.substring(s.indexOf("imageUri:"), s.indexOf("quantity:")).trim());
        }
        if (s.contains("quantity:")) {
            set1.add(s.substring(s.indexOf("quantity:")).trim());
        }
        detailsSet.add(set1);
    }
}