package com.example.leena.calkwalkcaketoyinventory;

public class OrderData {
    String date;
    String toyName;
    String toyID;
    String cakeFlavor;
    String cakeWeight;
    String splRequest;
    String msg;
    String comment;
    String deliveryTime;
    String location;
    String orderID;

    public OrderData(String date, String toyName, String toyID, String deliveryTime, String location, String cakeWeight, String cakeFlavor, String msg, String splRequest, String comment, String orderID) {
        this.date = date;
        this.toyName = toyName;
        this.toyID = toyID;
        this.cakeFlavor = cakeFlavor;
        this.cakeWeight = cakeWeight;
        this.splRequest = splRequest;
        this.msg = msg;
        this.comment = comment;
        this.deliveryTime = deliveryTime;
        this.location = location;
        this.orderID = orderID;
    }

    public String getDate() {
        return date;
    }

    public String getCakeFlavor() {
        return cakeFlavor;
    }

    public String getCakeWeight() {
        return cakeWeight;
    }

    public String getSplRequest() {
        return splRequest;
    }

    public String getMsg() {
        return msg;
    }

    public String getComment() {
        return comment;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public String getLocation() {
        return location;
    }

    public String getToyName() {
        return toyName;
    }

    public String getToyID() {
        return toyID;
    }

    public String getOrderID() { return orderID; }
}
