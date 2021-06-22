package com.simi.plantorderingapp.ProcessingUnit;

import java.util.*;
public class getMyOrdersData {
  /*  public static void main(String[] args) {
        String data="userName: Simi userMobile: 9101754385 itemName: Name: Rose itemPrice: 100 itemQuantity: 10 orderStatus: Online itemImageUrl: imageUri: https://firebasestorage.googleapis.com/v0/b/myplantapp-10c99.appspot.com/o/Plants%20Rose%20511557b2-edbb-447d-96c5-9a14e7871a71?alt=media&token=7c6d97ea-f1b4-4ac5-a004-6f74a1c5ae43 quantity: 10 userName: Simi userMobile: 9101754385 itemName: Name: Rose itemPrice: 100 itemQuantity: 10 orderStatus: Online itemImageUrl: imageUri: https://firebasestorage.googleapis.com/v0/b/myplantapp-10c99.appspot.com/o/Plants%20Rose%20511557b2-edbb-447d-96c5-9a14e7871a71?alt=media&token=7c6d97ea-f1b4-4ac5-a004-6f74a1c5ae43 quantity: 10 userName: Simi userMobile: 9101754385 itemName: Name: Rose itemPrice: 10 itemQuantity: 1 orderStatus: Online itemImageUrl: imageUri: https://firebasestorage.googleapis.com/v0/b/myplantapp-10c99.appspot.com/o/Plants%20Rose%20511557b2-edbb-447d-96c5-9a14e7871a71?alt=media&token=7c6d97ea-f1b4-4ac5-a004-6f74a1c5ae43 quantity: 10 userName: Simi userMobile: 9101754385 itemName: Name: Rose itemPrice: 100 itemQuantity: 10 orderStatus: Online itemImageUrl: imageUri: https://firebasestorage.googleapis.com/v0/b/myplantapp-10c99.appspot.com/o/Plants%20Rose%20511557b2-edbb-447d-96c5-9a14e7871a71?alt=media&token=7c6d97ea-f1b4-4ac5-a004-6f74a1c5ae43 quantity: 10 userName: Simi userMobile: 9101754385 itemName: Name: Rose itemPrice: 10 itemQuantity: 1 orderStatus: Online itemImageUrl: imageUri: https://firebasestorage.googleapis.com/v0/b/myplantapp-10c99.appspot.com/o/Plants%20Rose%20511557b2-edbb-447d-96c5-9a14e7871a71?alt=media&token=7c6d97ea-f1b4-4ac5-a004-6f74a1c5ae43 quantity: 10 userName: Simi userMobile: 9101754385 itemName: Name: Rose itemPrice: 50 itemQuantity: 5 orderStatus: COD itemImageUrl: imageUri: https://firebasestorage.googleapis.com/v0/b/myplantapp-10c99.appspot.com/o/Plants%20Rose%20511557b2-edbb-447d-96c5-9a14e7871a71?alt=media&token=7c6d97ea-f1b4-4ac5-a004-6f74a1c5ae43 quantity: 10";
        String username= "Simi";
        String usermobile= "9101754385";
        Set<Set<String>> aList= init(data, username, usermobile);
        System.out.println("From main() aList is: " + aList);
    }  */

    public Set<Set<String>> init(String data, String username, String usermobile) {
        System.out.println("data: " + data);
        String split[]= data.split("userName:");
        Set<Set<String>> MyOrders= new LinkedHashSet<>();
        Set<String> set1= null;
        for (String s: split) {
            set1= new LinkedHashSet<>();
            if (!s.trim().isEmpty()) {
                System.out.println("split: " + s);
                if (s.contains(username) && s.contains(usermobile)) {
                    String itemname="", itemprice="", itemquantity="", orderstatus="", itemurl="", quantity="", id="";
                    if (s.contains("itemName: itemName:") && s.contains("itemPrice: itemPrice:")) {
                        itemname= "itemName: " + s.substring(s.indexOf("itemName: itemName:"), s.indexOf("itemPrice: itemPrice:")).replace("itemName:","").replace("Name:", "").trim();
                    }
                    if (s.contains("itemPrice: itemPrice:") && s.contains("itemQuantity:")) {
                        itemprice= s.substring(s.indexOf("itemPrice: itemPrice:"), s.indexOf("itemQuantity:")).trim();
                    }
                    if (s.contains("itemQty:") && s.contains("orderStatus:")) {
                        itemquantity= s.substring(s.indexOf("itemQty:"), s.indexOf("orderStatus:")).trim();
                    }
                    if (s.contains("orderStatus:") && s.contains("itemImageUrl:")) {
                        orderstatus= s.substring(s.indexOf("orderStatus:") , s.indexOf("itemImageUrl:")).trim();
                    }
                    if (s.contains("itemImageUrl: imageUri:") && s.contains("Id: Id:")) {
                        itemurl= s.substring(s.indexOf("itemImageUrl: imageUri:"), s.indexOf("Id: Id:")).trim();
                        System.out.println("itemUrl is: " + itemurl);
                    }
                  //  if (s.contains("quantity:") && s.contains("Id: Id:")) {
                  //      quantity= s.substring(s.indexOf("quantity:"), s.indexOf("Id: Id:")).trim();
                  //  }
                    if (s.contains("Id: Id:")) {
                        id= s.substring(s.indexOf("Id: Id:"));
                    }
                    set1.add(itemname);
                    set1.add(itemprice);
                    set1.add(itemquantity);
                    set1.add(orderstatus);
                    set1.add(itemurl);
                 //   set1.add(quantity);
                    set1.add(id);

                    MyOrders.add(set1);
                    System.out.println("itemUrl is: " + itemurl);
                }
            }
        }
        System.out.println("MyOrders: " + MyOrders);
        return MyOrders;
    }
}