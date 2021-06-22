package com.simi.plantorderingapp.ProductModel;

public class RecommendedItemsAddToCart {
    private String username, mobile, quantity, price, name, itemdetail, imageuri, id, category;

    public RecommendedItemsAddToCart(String username, String mobile, String quantity, String price, String name, String itemdetail, String imageuri, String id, String category) {
        this.username = username;
        this.mobile = mobile;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.itemdetail = itemdetail;
        this.imageuri = imageuri;
        this.id = id;
        this.category = category;
    }

    public RecommendedItemsAddToCart() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public String getItemdetail() {
        return itemdetail;
    }

    public void setItemdetail(String itemdetail) {
        this.itemdetail = itemdetail;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
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
}
