package com.simi.plantorderingapp.ProductModel;

public class SaveSummerPlantsData {
    private String imageUri;

    public SaveSummerPlantsData(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}