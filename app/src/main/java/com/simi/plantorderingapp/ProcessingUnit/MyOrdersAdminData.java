package com.simi.plantorderingapp.ProcessingUnit;
import java.util.*;
public class MyOrdersAdminData {
  /*  public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        String data= "userName: userName: Master Roshi userMobile: userMobile: 9101054385 itemName: itemName: Dalia itemPrice: itemPrice: 50 itemQuantity: itemQty: 2 orderStatus: PaymentStatus: Online itemImageUrl: imageUri: https://firebasestorage.googleapis.com/v0/b/myplantapp-10c99.appspot.com/o/Plants%20Dalia%2080cd7e49-ece6-44e1-a985-037bb718abd6?alt=media&token=31be70c3-4ce9-41f9-9837-8cc409ee6c4a quantity: 10 quantity: 10 Id: Id: 582f5a36-9a27-4355-9268-875d35dfec7b userName: userName: Master Roshi userMobile: userMobile: 9101054385 itemName: itemName: Dalia itemPrice: itemPrice: 50 itemQuantity: itemQty: 2 orderStatus: PaymentStatus: Online itemImageUrl: imageUri: https://firebasestorage.googleapis.com/v0/b/myplantapp-10c99.appspot.com/o/Plants%20Dalia%2080cd7e49-ece6-44e1-a985-037bb718abd6?alt=media&token=31be70c3-4ce9-41f9-9837-8cc409ee6c4a quantity: 10 quantity: 10 Id: Id: 582f5a36-9a27-4355-9268-875d35dfec7b userName: userName: Master Roshi userMobile: userMobile: 9101054385 itemName: itemName: Dalia itemPrice: itemPrice: 25 itemQuantity: itemQty: 1 orderStatus: PaymentStatus: COD itemImageUrl: imageUri: https://firebasestorage.googleapis.com/v0/b/myplantapp-10c99.appspot.com/o/Plants%20Dalia%2080cd7e49-ece6-44e1-a985-037bb718abd6?alt=media&token=31be70c3-4ce9-41f9-9837-8cc409ee6c4a quantity: 10 quantity: 10 Id: Id: f06a1a52-ab5c-46ff-b347-f88437b6855b";
        Set<Set<String>> aList= init(data);
        System.out.println("From main() aList is: " + aList);
    }   */

    public Set<Set<String>> init(String data) {
        //   System.out.println("data: " + data);
        String split[]= data.split("userName: userName:");
        Set<Set<String>> detailsSet= new LinkedHashSet<>();
        Set<String> set1= null;
        for (String s: split) {
            if (!s.trim().isEmpty()) {
                set1= new LinkedHashSet<>();
                //  System.out.println("split: " + s);
                if (s.contains("userMobile: userMobile:")) {
                    set1.add("userName: " + s.substring(s.indexOf(s.charAt(0)), s.indexOf("userMobile: userMobile:")).trim());
                }
                if (s.contains("userMobile: userMobile:") && s.contains("itemName: itemName:")) {
                    set1.add("userMobile: " + s.substring(s.indexOf("userMobile: userMobile:"), s.indexOf("itemName: itemName:")).replace("userMobile: userMobile:","").trim());
                }
                if (s.contains("itemName: itemName:") && s.contains("itemPrice: itemPrice:")) {
                    set1.add("itemName: " + s.substring(s.indexOf("itemName: itemName:"), s.indexOf("itemPrice: itemPrice:")).replace("itemName: itemName:", "").trim());
                }
                if (s.contains("itemPrice: itemPrice:") && s.contains("itemQuantity: itemQty:")) {
                    set1.add("itemPrice: " + s.substring(s.indexOf("itemPrice: itemPrice:"), s.indexOf("itemQuantity: itemQty:")).replace("itemPrice: itemPrice:","").trim());
                }
                if (s.contains("itemQuantity: itemQty:") && s.contains("orderStatus:")) {
                    set1.add("itemQuantity: " + s.substring(s.indexOf("itemQuantity: itemQty:"), s.indexOf("orderStatus:")).replace("itemQuantity: itemQty:", "").trim());
                }
                if (s.contains("orderStatus:") && s.contains("itemImageUrl:")) {
                    set1.add(s.substring(s.indexOf("orderStatus:"), s.indexOf("itemImageUrl:")).replace("orderStatus:","").replace("itemImageUrl:","").trim());
                }
                if (s.contains("imageUri:") && s.contains("quantity:")) {
                    set1.add(s.substring(s.indexOf("imageUri:"), s.indexOf("quantity:")).trim());
                }
                if (s.contains("quantity:") && s.contains("Id: Id:")) {
                    String qty[]= s.substring(s.indexOf("quantity:"), s.indexOf("Id: Id:")).replace("quantity:","").trim().split("\\s+");
                    String quantity= qty[0];
                    set1.add("quantity: " + quantity);
                }
                if (s.contains("Id:")) {
                    String id[]= s.substring(s.indexOf("Id:")).replace("Id:","").trim().split("\\s+");
                    String id1= id[0];
                    set1.add("Id: " + id1);
                }
                detailsSet.add(set1);
            }
        }
        // System.out.println("detailsSet: " + detailsSet);
        return detailsSet;
    }
}