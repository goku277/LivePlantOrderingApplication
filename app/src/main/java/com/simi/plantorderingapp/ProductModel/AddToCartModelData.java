package com.simi.plantorderingapp.ProductModel;

public class AddToCartModelData {
    private String username, usermobile, imageUrl, price, name, qty, seedsqty, id;

    public AddToCartModelData(String username, String usermobile, String imageUrl, String price, String name, String qty, String seedsqty, String id) {
        this.username= username;
        this.usermobile= usermobile;
        this.imageUrl = imageUrl;
        this.price = price;
        this.name = name;
        this.qty= qty;
        this.seedsqty= seedsqty;
        this.id= id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeedsqty() {
        return seedsqty;
    }

    public void setSeedsqty(String seedsqty) {
        this.seedsqty = seedsqty;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}