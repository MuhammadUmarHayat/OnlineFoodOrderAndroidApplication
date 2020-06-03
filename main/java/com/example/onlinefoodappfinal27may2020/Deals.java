package com.example.onlinefoodappfinal27may2020;

public class Deals
{
    private String restaurantiD;
    private  String category;
    private  String title;
    private int price;
    private  String imgpath;

    public Deals()
    {
    }

    public Deals(String restaurantiD, String category, String title, int price, String imgpath)
    {
        this.restaurantiD = restaurantiD;
        this.category = category;
        this.title = title;
        this.price = price;
        this.imgpath = imgpath;
    }

    public Deals(String restaurantiD, String category, String title, int price)
    {
        this.restaurantiD = restaurantiD;
        this.category = category;
        this.title = title;
        this.price = price;
    }

    public String getRestaurantiD() {
        return restaurantiD;
    }

    public void setRestaurantiD(String restaurantiD) {
        this.restaurantiD = restaurantiD;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }
}
