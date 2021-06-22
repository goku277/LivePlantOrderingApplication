package com.simi.plantorderingapp.ProcessingUnit;

import java.util.LinkedHashSet;
import java.util.Set;

public class getCartData {
  /*  public static void main(String[] args) {
        String data="username: Goku usermobile: 8812055712 Name: Dalia Detail: Dalia is foumd in every season Price: 125 Category: All Season Plants imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/All%20Season%20Plants%20Dalia%202d4a2ced-28fc-4560-8ad0-000f100c38d7?alt=media&token=1e45d197-2185-4045-8bf0-9acdca28933e quantity: 100 id: 4ab575c6-b402-427b-9ca3-5035bbcb83cb username: Goku usermobile: 8812055712 Name: Cactus Detail: Cactus are plants that has enormous thorns on its body Price: 25 Category: Plants imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Plants%20Cactus%202a529854-7ec5-4742-90f5-3dc9b1f71109?alt=media&token=823132e7-369f-4054-97d6-08c3fa495719 quantity: 10 id: e88b9297-e2d6-4029-9c74-404d837e9cc8 username: Goku usermobile: 8812055712 Name: Tomato Detail: Tomato is a vegetable Price: 20 Category: Vegetable imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Vegetable%20Tomato%20cdefb202-b015-435b-8db1-28d7952c51b5?alt=media&token=cd469d4d-a818-440b-a212-001c3aeef5fc quantity: 25 id: 78e1f93a-4863-406b-8032-3de7c5601edc username: Goku usermobile: 8812055712 Name: Cactus Detail: Cactus are summer plants. Price: 100 Category: Summer Plants imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Summer%20Plants%20Cactus%20fce6c945-ddff-4e70-9b80-30c8e3c27db6?alt=media&token=c48ec6cc-4642-4d9f-9c79-d6b9e47c4739 quantity: 120 id: 24c86384-0034-48c9-a6aa-76f420c02d30 username: Goku usermobile: 8812055712 Name: Papaya Detail: Papaya has many health benefits.Papaya include a reduce risk of heart disease,dibetics,lowering blood pressure Price: 150 Category: Vegetable imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Vegetable%20Papaya%20ec971a55-ec5a-4fcf-8666-5ef2ee23b05f?alt=media&token=323a997d-f37c-453f-92c9-c23e676f8054 quantity: 25 id: 6cf24f12-588b-4c50-b73e-17265787b365";
        String username="Goku", usermobile= "8812055712";
        Set<Set<String>> a1= init(data, username, usermobile);
        System.out.println("From main() a1: " + a1);
    }   */
    public Set<Set<String>> init(String data, String username, String usermobile) {
        //  System.out.println("data: " + data);
        String split[]= data.split("username:");
        Set<Set<String>> set1= new LinkedHashSet<>();
        Set<String> setDetails= null;
        for (String s: split) {
            setDetails= new LinkedHashSet<>();
            if (!s.trim().isEmpty()) {
                System.out.println("From getCartData split: " + s);
                if (s.contains(username) && s.contains(usermobile)) {
                    try {
                        System.out.println("From init() split: " + s);
                        if (s.contains("Name:") && s.contains("Detail:")) {
                            setDetails.add(s.substring(s.indexOf("Name:"), s.indexOf("Detail:")).trim());
                        }
                        if (s.contains("price:") && s.contains("Category:")) {
                            setDetails.add(s.substring(s.indexOf("price:"), s.indexOf("Category:")).trim());
                        }
                        if (s.contains("https://firebasestorage") && s.contains("quantity:")) {
                            setDetails.add(s.substring(s.indexOf("https://firebasestorage"), s.indexOf("quantity:")).trim());
                        }
                        if (s.contains("quantity:") && s.contains("id:")) {
                            setDetails.add(s.substring(s.indexOf("quantity:"), s.indexOf("id:")).trim());
                        }
                        set1.add(setDetails);
                    } catch (Exception e) {}
                }
            }
        }
        System.out.println("From CartData set1: " + set1);
        return set1;
    }
}