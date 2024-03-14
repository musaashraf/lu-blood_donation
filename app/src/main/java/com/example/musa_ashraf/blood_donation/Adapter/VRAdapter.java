package com.example.musa_ashraf.blood_donation.Adapter;

public class VRAdapter {
    private String uid;
    private String status;
    private String blood;
    private String hospital;
    private String location;
    private String details;
    private String time;
    private String phone;
    private String stime;
    private String endtime;

    public VRAdapter() {
    }

    public VRAdapter(String uid, String status, String blood, String hospital, String location, String details,
                     String time, String phone,String stimes, String endtime) {
        this.uid = uid;
        this.status = status;
        this.blood = blood;
        this.hospital = hospital;
        this.location = location;
        this.details = details;
        this.time = time;
        this.phone = phone;
        this.stime = stimes;
        this.endtime = endtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}
