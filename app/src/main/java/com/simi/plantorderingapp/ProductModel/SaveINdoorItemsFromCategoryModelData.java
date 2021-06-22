package com.simi.plantorderingapp.ProductModel;

public class SaveINdoorItemsFromCategoryModelData {
    private String name, detail, price, imgUrl;

    public SaveINdoorItemsFromCategoryModelData(String name, String detail, String price, String imgUrl) {
        this.name = name;
        this.detail = detail;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}