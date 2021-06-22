package com.simi.plantorderingapp.ProcessingUnit;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class GetSalesProductData {
    public Map<String, Set<Set<String>>> init(String data) {
        Set<String> categories= new LinkedHashSet<>();
        String split[]= data.split("name:");
        for (String s: split) {
            if (!s.trim().isEmpty()) {
                if (s.contains("category:") && s.contains("key:")) {
                    categories.add(s.substring(s.indexOf("category:"), s.indexOf("key:")).replace("category:","").trim());
                }
            }
        }
        Map<String, Set<Set<String>>> mapCategoriesTodetails= new LinkedHashMap<>();
        Set<Set<String>> a1= null;
        Set<String> setExtractDetails= null;
        for (String s: categories) {
            a1= new LinkedHashSet<>();
            for (String s1: split) {
                setExtractDetails= new LinkedHashSet<>();
                String category1= "";
                if (s1.contains("category:") && s1.contains("key:")) {
                    category1= s1.substring(s1.indexOf("category:"), s1.indexOf("key:")).replace("category:","").trim();
                }
                if (category1.equals("Sale")) {
                    if (s1.contains("Price:")) {
                        setExtractDetails.add("name:" + s1.substring(s1.indexOf(s1.charAt(0)), s1.indexOf("Price:")).trim());
                    }
                    if (s1.contains("Price:") && s1.contains("detail:")) {
                        setExtractDetails.add(s1.substring(s1.indexOf("price:"), s1.indexOf("detail:")).trim());
                    }
                    if (s1.contains("detail:") && s1.contains("category:")) {
                        setExtractDetails.add(s1.substring(s1.indexOf("detail:"), s1.indexOf("category:")).trim());
                    }
                    if (s1.contains("category:") && s1.contains("key:")) {
                        setExtractDetails.add(s1.substring(s1.indexOf("category:"), s1.indexOf("key:")).trim());
                    }
                    if (s1.contains("key:") && s1.contains("imageUri:")) {
                        setExtractDetails.add(s1.substring(s1.indexOf("key:"), s1.indexOf("imageUri:")).trim());
                    }
                    if (s1.contains("imageUri:")) {
                        setExtractDetails.add(s1.substring(s1.indexOf("imageUri:")).trim());
                    }
                    a1.add(setExtractDetails);
                    //  System.out.println("From GetProductData a1: " + a1);
                }
                mapCategoriesTodetails.put(s, a1);
            }
        }
        return mapCategoriesTodetails;
    }
}
