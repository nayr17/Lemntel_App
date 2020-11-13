package com.example.lemntelattendancechecker.HelperClass;

public class ScanActivityGetResult {

    String id, name, photoUrl, time;

    public ScanActivityGetResult() {
    }

    public ScanActivityGetResult(String id, String name, String photoUrl, String time) {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
