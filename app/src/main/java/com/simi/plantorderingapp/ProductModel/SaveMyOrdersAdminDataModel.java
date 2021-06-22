package com.simi.plantorderingapp.ProductModel;

public class SaveMyOrdersAdminDataModel {
    private String username, usermobile, itemname, itemprice, itemquantity, paymentstatus, quantity, id;

    public SaveMyOrdersAdminDataModel(String username, String usermobile, String itemname, String itemprice, String itemquantity, String paymentstatus, String quantity, String id) {
        this.username = username;
        this.usermobile = usermobile;
        this.itemname = itemname;
        this.itemprice = itemprice;
        this.itemquantity = itemquantity;
        this.paymentstatus = paymentstatus;
        this.quantity = quantity;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsermobile() {
        return usermobile;
    }

    public void setUsermobile(String usermobile) {
        this.usermobile = usermobile;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemprice() {
        return itemprice;
    }

    public void setItemprice(String itemprice) {
        this.itemprice = itemprice;
    }

    public String getItemquantity() {
        return itemquantity;
    }

    public void setItemquantity(String itemquantity) {
        this.itemquantity = itemquantity;
    }

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}