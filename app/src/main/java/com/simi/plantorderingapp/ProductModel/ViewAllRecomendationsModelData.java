package com.simi.plantorderingapp.ProductModel;

public class ViewAllRecomendationsModelData {
    private String Name, Price, Detail, ImageUrl;

    public ViewAllRecomendationsModelData(String name, String price, String detail, String imageUrl) {
        Name = name;
        Price = price;
        Detail = detail;
        ImageUrl = imageUrl;
    }

    public ViewAllRecomendationsModelData() {
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
}