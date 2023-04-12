package com.example.maishahostelbookingapp;

public class roomModel {

    String name;
    String phone;
    String room;
    String amount;

    public roomModel() {
    }

    public roomModel(String name, String phone,String room,String amount) {

        this.name = name;
        this.phone = phone;
        this.room=room;
        this.amount=amount;

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
