package com.simi.plantorderingapp.ProductModel;

public class WishListModel {
    private String Name, Price, Category, Detail, ImageUrl, Quantity;

    public WishListModel(String name, String price, String category, String detail, String imageUrl, String quantity) {
        Name = name;
        Price = price;
        Category = category;
        Detail = detail;
        ImageUrl = imageUrl;
        Quantity = quantity;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}