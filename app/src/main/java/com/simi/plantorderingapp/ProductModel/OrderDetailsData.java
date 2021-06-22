package com.simi.plantorderingapp.ProductModel;

public class OrderDetailsData {
    private String userName, userMobile, itemName, itemPrice, itemImageUrl, itemId, itemQuantity, itemSeedsQuantity;

    public OrderDetailsData(String userName, String userMobile, String itemName, String itemPrice, String itemImageUrl, String itemId, String itemQuantity, String itemSeedsQuantity) {
        this.userName = userName;
        this.userMobile = userMobile;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemImageUrl = itemImageUrl;
        this.itemId = itemId;
        this.itemQuantity = itemQuantity;
        this.itemSeedsQuantity = itemSeedsQuantity;
    }

    public OrderDetailsData() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemSeedsQuantity() {
        return itemSeedsQuantity;
    }

    public void setItemSeedsQuantity(String itemSeedsQuantity) {
        this.itemSeedsQuantity = itemSeedsQuantity;
    }
}