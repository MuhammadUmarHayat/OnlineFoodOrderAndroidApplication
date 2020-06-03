package com.example.onlinefoodappfinal27may2020;

public class Orders
{
    String CustomerID;
    String RestaurantID;
    String RestaurantName;
    String DeaLTitle;
    String quantity;
    String unitPrice;
    int grandTotal;
    String date;
    String DeliveryTime;

    public Orders()
    {

    }

    public Orders(String customerID, String restaurantID, String restaurantName, String deaLTitle, String quantity, String unitPrice, int grandTotal, String date, String deliveryTime) {
        CustomerID = customerID;
        RestaurantID = restaurantID;
        RestaurantName = restaurantName;
        DeaLTitle = deaLTitle;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.grandTotal = grandTotal;
        this.date = date;
        DeliveryTime = deliveryTime;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getRestaurantID() {
        return RestaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        RestaurantID = restaurantID;
    }

    public String getRestaurantName() {
        return RestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        RestaurantName = restaurantName;
    }

    public String getDeaLTitle() {
        return DeaLTitle;
    }

    public void setDeaLTitle(String deaLTitle) {
        DeaLTitle = deaLTitle;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(int grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeliveryTime() {
        return DeliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        DeliveryTime = deliveryTime;
    }
}
