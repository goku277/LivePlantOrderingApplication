package com.simi.plantorderingapp.ProcessingUnit;

import java.util.LinkedHashSet;
import java.util.Set;

public class searchRecommendedSeedsAndFertilizers {
    public Set<Set<String>> detailsSet= new LinkedHashSet<>();
  /*  public static void main(String[] args) {
        String data="name: Rose price: 125 detail: Roses are red and the skuly is blue. category: Recommend Items key: Recommend Items Rose b770ae0d-07eb-4668-a3a8-2eaf6df2648d imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Recommend%20Items%20Rose%2027c10879-39d1-46bf-8654-219f50154a39?alt=media&token=9bb52f61-028e-44be-9b6b-ca764743348a quantity: 1000 name: Lily price: 100 detail: Lilies are the beautiful flowers. category: Recommend Items key: Recommend Items Lily 867e3f20-44db-42f1-98be-b161f8e75850 imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Recommend%20Items%20Lily%20d8ea1485-da4c-43f3-844d-12fba756e74e?alt=media&token=b6e56a6e-370b-4259-9459-8b1d3f9a18e6 quantity: 150 name: China Rose price: 150 detail: China Rose is a beutiful flower category: Recommend Items key: Recommend Items China Rose 72e6a744-9df7-4c11-ab66-cb6d412dcfaf imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Recommend%20Items%20China%20Rose%2085fdf8a8-7a88-4c36-b5c8-ded27be77f3d?alt=media&token=c8ecf2a6-1810-4c72-b23a-21602d3551e5 quantity: 100";
        String product= "Roses";
        if (init(data, product)) {
            System.out.println("From main() detailsSet is: " + detailsSet);
        }
    }    */

    public boolean init(String data, String product) {
        String split[]= data.split("name:");
        boolean isFound= false;
        for (String s: split) {
            if (!s.trim().isEmpty()) {
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
              /*  if (s.contains(finalProd) || s.contains(finalProd1)) {
                    extractData(s);
                    isFound= true;
                }  */
            }
        }
        if (!isFound) return false;
        else  return true;
    }

    private void extractData(String s) {
        s= s.trim();
        System.out.println("data: " + s);
        Set<String> set1= new LinkedHashSet<>();
        if (s.contains("price:")) {
            set1.add(s.substring(s.indexOf(s.charAt(0)), s.indexOf("price:")).trim());
        }
        if (s.contains("price:") && s.contains("detail:")) {
            set1.add(s.substring(s.indexOf("price:"), s.indexOf("detail:")).trim());
        }
        if (s.contains("detail:") && s.contains("category:")) {
            set1.add(s.substring(s.indexOf("detail:"), s.indexOf("category:")).trim());
        }
        if (s.contains("category:") && s.contains("key:")) {
            set1.add(s.substring(s.indexOf("category:"), s.indexOf("key:")).trim());
        }
        if (s.contains("key:") && s.contains("imageUri:")) {
            set1.add(s.substring(s.indexOf("key:"), s.indexOf("imageUri")).trim());
        }
        if (s.contains("imageUri:") && s.contains("quantity:")) {
            set1.add(s.substring(s.indexOf("imageUri:"), s.indexOf("quantity:")).trim());
        }
        if (s.contains("quantity:")) {
            set1.add(s.substring(s.indexOf("quantity:")).trim());
        }
        detailsSet.add(set1);
        System.out.println("detailsSet: " + detailsSet);
    }
}