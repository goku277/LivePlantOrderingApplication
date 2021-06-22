package com.simi.plantorderingapp.ProductModel;

public class MyOrderData {
    private String OrderedTotalItems, itemName, itemPrice, itemQuantity, itemImageUrl, userName, userMobile, orderStatus, orderId;

    public MyOrderData(String orderedTotalItems, String itemName, String itemPrice, String itemQuantity, String itemImageUrl, String userName, String userMobile, String orderStatus, String orderId) {
        OrderedTotalItems = orderedTotalItems;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.itemImageUrl = itemImageUrl;
        this.userName = userName;
        this.userMobile = userMobile;
        this.orderStatus= orderStatus;
        this.orderId= orderId;
    }

    public MyOrderData() {

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderedTotalItems() {
        return OrderedTotalItems;
    }

    public void setOrderedTotalItems(String orderedTotalItems) {
        OrderedTotalItems = orderedTotalItems;
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

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
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
}