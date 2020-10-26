package com.example.lemntelattendancechecker.HelperClass;

public class Model {
    String name, id, photoUrl;

    public Model() {

    }

    public Model(String name, String id, String photoUrl) {
        this.name = name;
        this.id = id;
        this.photoUrl = photoUrl;
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
