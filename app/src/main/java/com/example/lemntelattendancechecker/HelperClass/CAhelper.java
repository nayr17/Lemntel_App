package com.example.lemntelattendancechecker.HelperClass;

public class CAhelper {
    String id, name, photoUrl, CA_Amount, date;

    public CAhelper() {

    }

    public CAhelper(String id, String name, String photoUrl, String CA_Amount, String date) {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
        this.CA_Amount = CA_Amount;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCA_Amount() {
        return CA_Amount;
    }

    public void setCA_Amount(String CA_Amount) {
        this.CA_Amount = CA_Amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
