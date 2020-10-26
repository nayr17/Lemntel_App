package com.example.lemntelattendancechecker.HelperClass;

public class EmployeeHelper {

    String ID, name;

    public EmployeeHelper( ) {

    }

    public EmployeeHelper(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
