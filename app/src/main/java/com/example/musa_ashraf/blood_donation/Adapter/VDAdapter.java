package com.example.musa_ashraf.blood_donation.Adapter;

public class VDAdapter {
    private String blood;
    private String address;
    private String location;
    private String university;
    private String phone;
    private String userid;


    public VDAdapter() {
    }

    public VDAdapter(String blood, String address, String location, String university, String phone, String userid) {
        this.blood = blood;
        this.address = address;
        this.location = location;
        this.university = university;
        this.phone = phone;
        this.userid = userid;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}

