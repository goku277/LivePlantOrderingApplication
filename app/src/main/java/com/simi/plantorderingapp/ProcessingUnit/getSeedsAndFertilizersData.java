package com.simi.plantorderingapp.ProcessingUnit;

import java.util.LinkedHashSet;
import java.util.Set;

public class getSeedsAndFertilizersData {
    public Set<Set<String>> init(String data, String username, String usermobile) {
        String split[]= data.split("username:");
        Set<Set<String>> set1= new LinkedHashSet<>();
        Set<String> setDetails= null;
        for (String s: split) {
            setDetails= new LinkedHashSet<>();
            if (!s.trim().isEmpty()) {
                if (s.contains(username) && s.contains(usermobile)) {
                    if (s.contains("Name:") && s.contains("Details:")) {
                        setDetails.add(s.substring(s.indexOf("Name:"), s.indexOf("Details:")).trim());
                    }
                    if (s.contains("Price:") && s.contains("Category:")) {
                        setDetails.add(s.substring(s.indexOf("Price:"), s.indexOf("Category:")).trim());
                    }
                    if (s.contains("id:") && s.contains("Quantity:")) {
                        setDetails.add(s.substring(s.indexOf("id:"), s.indexOf("Quantity:")).trim());
                    }
                    if (s.contains("Quantity:") && s.contains("seedsqty:")) {
                        setDetails.add(s.substring(s.indexOf("Quantity:"), s.indexOf("seedsqty:")).trim());
                    }
                    if (s.contains("seedsqty:")) {
                        String seedQty[]= s.substring(s.indexOf("seedsqty:")).split("\\s+");
                        String seedsqty= seedQty[0] + " " + seedQty[1] + " " + seedQty[2];
                        setDetails.add(seedsqty);
                    }
                    set1.add(setDetails);
                }
            }
        }
        return set1;
    }
}