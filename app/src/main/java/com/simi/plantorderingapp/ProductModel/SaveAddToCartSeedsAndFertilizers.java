package com.simi.plantorderingapp.ProductModel;

public class SaveAddToCartSeedsAndFertilizers {
    private String username, mobile, name, itemdetail, price, category, imageuri, quantity, seedsqty, id;

    public SaveAddToCartSeedsAndFertilizers(String username, String mobile, String name, String itemdetail, String price, String category, String imageuri, String quantity, String seedsqty, String id) {
        this.username = username;
        this.mobile = mobile;
        this.name = name;
        this.itemdetail = itemdetail;
        this.price = price;
        this.category = category;
        this.imageuri = imageuri;
        this.quantity = quantity;
        this.seedsqty = seedsqty;
        this.id= id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
}