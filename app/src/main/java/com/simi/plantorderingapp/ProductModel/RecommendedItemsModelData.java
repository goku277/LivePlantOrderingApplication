package com.simi.plantorderingapp.ProductModel;

public class RecommendedItemsModelData {
    private String imageUri;

    public RecommendedItemsModelData(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}