package com.example.lemntelattendancechecker.HelperClass;

public class CAbalanceHelper {
    String balance, name, id, photoUrl;

    public CAbalanceHelper() {

    }

    public CAbalanceHelper(String balance, String name, String id, String photoUrl) {
        this.balance = balance;
        this.name = name;
        this.id = id;
        this.photoUrl = photoUrl;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
