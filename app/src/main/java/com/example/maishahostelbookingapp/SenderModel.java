package com.example.maishahostelbookingapp;

public class SenderModel {
    String room;
    String capacity;
    String amount;


    public SenderModel() {
    }

    public SenderModel(String room, String capacity, String amount) {
        this.room = room;
        this.capacity = capacity;
        this.amount = amount;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}