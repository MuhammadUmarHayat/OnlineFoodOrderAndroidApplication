package com.example.onlinefoodappfinal27may2020;

public class Restaurant
{
    private String restaurantiD;
    private   String title;
    private   String area;
    private    String city;

    private    String contactNo;
    private String Address;
    private String openingTime;
    private String ClosingTime;

    private  String imagepath;

    String id;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Restaurant()
    {
    }

    public Restaurant(String restaurantiD, String title, String area, String city, String contactNo, String address, String openingTime, String closingTime)
    {
        this.restaurantiD = restaurantiD;
        this.title = title;
        this.area = area;
        this.city = city;
        this.contactNo = contactNo;
        Address = address;
        this.openingTime = openingTime;
        ClosingTime = closingTime;
    }

    public Restaurant(String restaurantiD, String title, String area, String city, String contactNo, String address, String openingTime, String closingTime, String imagepath)
    {
        this.restaurantiD = restaurantiD;
        this.title = title;
        this.area = area;
        this.city = city;
        this.contactNo = contactNo;
        Address = address;
        this.openingTime = openingTime;
        ClosingTime = closingTime;
        this.imagepath = imagepath;
    }

    public String getRestaurantiD()
    {
        return restaurantiD;
    }

    public void setRestaurantiD(String restaurantiD) {
        this.restaurantiD = restaurantiD;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return ClosingTime;
    }

    public void setClosingTime(String closingTime) {
        ClosingTime = closingTime;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
}
