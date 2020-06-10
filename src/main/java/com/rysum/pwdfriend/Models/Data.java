package com.rysum.pwdfriend.Models;

public class Data {
    private String id;
    public String editName;
    public String editDetails;
    public String editCategory;
    public String editAddress;
    public double setLongitude;
    public double setLatitude;
    public String imageUrl;
    public boolean setBox1;
    public boolean setBox2;
    public boolean setBox3;
    public boolean setBox4;
    public boolean setBox5;
    public float rating;


    public Data() {

    }

    public Data(String id, String editName, String editDetails, String editCategory, String editAddress, double setLongitude, double setLatitude, String imageUrl, boolean setBox1, boolean setBox2, boolean setBox3, boolean setBox4, boolean setBox5, float rating) {
        this.id = id;
        this.editName = editName;
        this.editDetails = editDetails;
        this.editCategory = editCategory;
        this.editAddress = editAddress;
        this.setLongitude = setLongitude;
        this.setLatitude = setLatitude;
        this.imageUrl = imageUrl;
        this.setBox1 = setBox1;
        this.setBox2 = setBox2;
        this.setBox3 = setBox3;
        this.setBox4 = setBox4;
        this.setBox5 = setBox5;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getEditName() {
        return editName;
    }

    public String getEditDetails() {
        return editDetails;
    }

    public String getEditCategory() {
        return editCategory;
    }

    public String getEditAddress() {
        return editAddress;
    }

    public double getSetLongitude() {
        return setLongitude;
    }

    public double getSetLatitude() {
        return setLatitude;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isSetBox1() {
        return setBox1;
    }

    public boolean isSetBox2() {
        return setBox2;
    }

    public boolean isSetBox3() {
        return setBox3;
    }

    public boolean isSetBox4() {
        return setBox4;
    }

    public boolean isSetBox5() {
        return setBox5;
    }

    public float getRating() {
        return rating;
    }
}