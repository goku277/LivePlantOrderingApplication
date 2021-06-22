package com.simi.plantorderingapp.ProcessingUnit;

import java.util.LinkedHashSet;
import java.util.Set;

public class getCustomerData {
  /*  public static void main(String[] args) {
        String data="userName: Goku email: goku@gmail.com password: 123456 phone: 9101058634 address: Hyderabad pin: 10000 city: Sucendrabad location: Hyderabad, India userName: Simi Roy email: simi@gmail.com password: Me123456 phone: 9401636095 address: Amingaon pin: 781031 city: Gauhati location: near saraighat high school userName: Manash Jyoti Roy email: manash4321@gmail.com password: manash12 phone: 9957583945 address: SH 2 pin: 781031 city: Guwahati location: Near Saraighat High school userName: Simi email: simi12345@gmail.com password: 123456 phone: 8812055712 address: Guwahati pin: 781017 city: Silpukri location: Guwahati userName: Laxmi email: laxmi@gmail.com password: 161912 phone: 8876819791 address: Guwahati pin: 781014 city: Guwahati location: Assam userName: Mona email: mona@gmail.com password: 123456 phone: 8876819791 address: Guwahati pin: 788009 city: Lankeshwar location: Assam";
        String username= "Goku", usermobile="9101058634";

        Set<String> a1= init(data, username, usermobile);
        System.out.println("From main() a1: " + a1);
    }   */

    public Set<String> init(String data, String username, String usermobile) {
        // System.out.println("data: " + data);
        Set<String> profileDetails= new LinkedHashSet<>();
        String split[]= data.split("userName:");
        for (String s: split) {
            if (!s.trim().isEmpty()) {
                if (s.contains(username) && s.contains(usermobile)) {
                    System.out.println("split: " + s);
                    if (s.contains("email:")) {
                        profileDetails.add(s.substring(s.indexOf(s.charAt(0)), s.indexOf("email:")).trim());
                    }
                    if (s.contains("email:") && s.contains("password:")) {
                        profileDetails.add(s.substring(s.indexOf("email:"), s.indexOf("password:")).trim());
                    }
                    if (s.contains("password:") && s.contains("phone:")) {
                        profileDetails.add(s.substring(s.indexOf("password:"), s.indexOf("phone:")).trim());
                    }
                    if (s.contains("phone:") && s.contains("address")) {
                        profileDetails.add(s.substring(s.indexOf("phone:"), s.indexOf("address")).trim());
                    }
                    if (s.contains("address:") && s.contains("pin:")) {
                        profileDetails.add(s.substring(s.indexOf("address:"), s.indexOf("pin:")).trim());
                    }
                    if (s.contains("pin:") && s.contains("city:")) {
                        profileDetails.add(s.substring(s.indexOf("pin:"), s.indexOf("city:")).trim());
                    }
                    if (s.contains("city:") && s.contains("location:")) {
                        profileDetails.add(s.substring(s.indexOf("city:"), s.indexOf("location:")).trim());
                    }
                    if (s.contains("location:")) {
                        profileDetails.add(s.substring(s.indexOf("location:")).trim());
                    }
                }
            }
        }
        System.out.println("profileDetails: " + profileDetails);
        return profileDetails;
    }
}