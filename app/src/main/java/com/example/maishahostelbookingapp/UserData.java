package com.example.maishahostelbookingapp;

public class UserData {

    public UserData() {
    }

    int usertype;
    String uid;
    String name;
    String id;
    String phone;
    String email;

    public UserData(String uid, String name, String id, String phone, String email,int usertype) {
        this.uid = uid;
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.usertype=usertype;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }
}
