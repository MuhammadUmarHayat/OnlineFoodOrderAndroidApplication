package com.example.onlinefoodappfinal27may2020;

public class Users
{

    private String name;
    private String fName;
    private String userid;
    private  String password;
    private  String area;
    private  String city;
    private  String address;
    private String email;
    private  String mobile;

    public Users()
    {

    }

    public Users(String name, String fName, String userid, String password, String area, String city, String address, String email, String mobile) {
        this.name = name;
        this.fName = fName;
        this.userid = userid;
        this.password = password;
        this.area = area;
        this.city = city;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
