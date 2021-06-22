package com.simi.plantorderingapp.ProductModel;

public class SeedsAndFerrtilizersCart {
    private String id, category, imageuri, itemdetail, mobile, name, price, quantity, seedsqty, username;

    public SeedsAndFerrtilizersCart(String id, String category, String imageuri, String itemdetail, String mobile, String name, String price, String quantity, String seedsqty, String username) {
        this.id = id;
        this.category = category;
        this.imageuri = imageuri;
        this.itemdetail = itemdetail;
        this.mobile = mobile;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.seedsqty = seedsqty;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }

    public String getItemdetail() {
        return itemdetail;
    }

    public void setItemdetail(String itemdetail) {
        this.itemdetail = itemdetail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSeedsqty() {
        return seedsqty;
    }

    public void setSeedsqty(String seedsqty) {
        this.seedsqty = seedsqty;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}