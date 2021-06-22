package com.simi.plantorderingapp.ProcessingUnit;

import java.util.*;
public class GetIdsFromAddToCart {
  /*  public static void main(String[] args) {
        String data="username: Sneha usermobile: 9101754385 Name: Neem Price: 150 Category: Indoor plants id: 9e8d5099-a44c-46ac-b67f-c9f89978c2af imageUri: https://firebasestorage.googleapis.com/v0/b/myplantapp-10c99.appspot.com/o/Indoor%20plants%20Neem%200851223b-e18c-44d6-9318-558a747f3cd1?alt=media&token=59e5be82-e194-4b54-9ccb-600b7888f077 quantity: 125 Detail: Neem plants are the medicinal plants. quantity: 125 username: Sneha usermobile: 9101754385 Name: Name: China Tose Price: Price:  100 Category: Recommended Items id: d2efe96f-9654-47c0-9c8b-c813cb1449c4 imageUri: https://firebasestorage.googleapis.com/v0/b/myplantapp-10c99.appspot.com/o/Recommend%20Items%20China%20Tose%209833e47b-d188-4c71-b671-83a9f85d9b11?alt=media&token=dd944f5f-54b2-4be3-9c4f-68cb3f0b3948 quantity: 111 Detail: Detail:  China Rose is a beautiful flower quantity:  username: bidyut usermobile: 9508922469 Name: Cactus Price: 200 Category: Recommended Items id: 91f0bafc-609d-42fb-9f22-ddfeb21fe2be imageUri: https://firebasestorage.googleapis.com/v0/b/myplantapp-10c99.appspot.com/o/Store%20Images%2F1624016044675?alt=media&token=0db2dd85-7305-4291-af2e-1d28adec5c00 Detail: Cactus are very essential plants and it is enrich with vitamins quantity: 50 username: Simi Das usermobile: 9401636095 Name: Cactus Price: 200 Category: Recommended Items id: e162d107-1f01-49ad-abe0-8fbf7dccd105 imageUri: https://firebasestorage.googleapis.com/v0/b/myplantapp-10c99.appspot.com/o/Store%20Images%2F1624025075041?alt=media&token=8713def4-a91f-4c90-98b5-d757cb0e69d3 Detail: Cactus are very essential plants and it is enrich with vitamins quantity: 50 username: Anu usermobile: 8876819791 Name: Name: Rose Price: Price:  100 Category: Recommended Items id: 9c857168-0130-4ebe-9048-442981814bdc imageUri: https://firebasestorage.googleapis.com/v0/b/myplantapp-10c99.appspot.com/o/Recommend%20Items%20Rose%20b46baab4-a8d3-494e-8f57-427d517fcb2f?alt=media&token=4ce4f578-d981-4750-b6ef-e42e3980a732 quantity: 100 Detail: Detail:  Roses are very beautiful flowers quantity:  username: Sneha usermobile: 9101754385 Name: Neem Price: 500 Category: Recommended Items id: 9d235c03-5518-4172-a267-000e7489d3b1 imageUri: https://firebasestorage.googleapis.com/v0/b/myplantapp-10c99.appspot.com/o/Store%20Images%2F1623977944267?alt=media&token=be9f5516-b9fb-4b98-8707-55531daa8020 Detail: Neem plants are very important and are used for medicinal purposes quantity: 200 username: Sneha usermobile: 9101754385 Name: Name: Sunflower Price: Price:  125 Category: Recommended Items id: 34f3a1c0-2b6c-40f8-b888-c6fdcb81be10 imageUri: https://firebasestorage.googleapis.com/v0/b/myplantapp-10c99.appspot.com/o/Recommend%20Items%20Sunflower%20fd22da69-43cf-48b1-bcd2-c05380966d21?alt=media&token=ed74c089-8945-4398-9966-ebedd3303dec quantity: 12 Detail: Detail:  Sunflowers are very good quantity:  username: Sneha usermobile: 9101754385 Name: Tulsi Price: 250 Category: Recommended Items id: 6caa530c-cc03-44b2-b377-b49609bc550d imageUri: https://firebasestorage.googleapis.com/v0/b/myplantapp-10c99.appspot.com/o/Store%20Images%2F1623967471855?alt=media&token=c7dc0c5d-4b82-42e4-a7a1-7787c1c6e18a Detail: Tulsi plants are the most essential plants among all other plants as its very important in day to day life and are also used for medicinal purposes quantity: 1200 username: Sneha usermobile: 9101754385 Name: Sunflower Price: 150 Category: Recommended Items id: 0f571e6e-c8c8-49fe-884b-ba3df85220dc imageUri: https://firebasestorage.googleapis.com/v0/b/myplantapp-10c99.appspot.com/o/Store%20Images%2F1623977653040?alt=media&token=b9b3eebe-dd75-48d6-ad38-33bfc76ea71d Detail: Sunflower are very rare and beautiful flowers quantity: 350";
        String name="Name: Neem", price="Price: 150", category="Category: Indoor plants", quantity="quantity: 125";
        String a1=init(data, name, price, category, quantity);
        System.out.println("From main() a1 is: " + a1);
    }   */

    public String init(String data, String name, String price, String quantity) {
        System.out.println("data: " + data);
        String split[]= data.split("username:");
        String id="", userName="", userMobile="";
        for (String s: split) {
            if (!s.trim().isEmpty()) {
                if (s.contains(name) && s.contains(price) && s.contains(quantity)) {
                    System.out.println("split: " + s);
                    if (s.contains("usermobile")) {
                        userName= s.substring(s.indexOf(s.charAt(0)), s.indexOf("usermobile:")).trim();
                    }
                    if (s.contains("usermobile:") && s.contains("Name:")) {
                        userMobile= s.substring(s.indexOf("usermobile:"), s.indexOf("Name:")).replace("usermobile:","").trim();
                    }
                    if (s.contains("id:") && s.contains("imageUri:")) {
                        id= s.substring(s.indexOf("id:"), s.indexOf("imageUri:")).replace("id:","").trim();
                    }
                }
            }
        }
        System.out.println("Id: " + id);
        String key= userName + " " + userMobile + " " + id;
        System.out.println("From GetIdsFromAddToCart key is: " + key);
        return key;
    }
}