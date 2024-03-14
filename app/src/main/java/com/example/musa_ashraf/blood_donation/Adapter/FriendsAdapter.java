package com.example.musa_ashraf.blood_donation.Adapter;

public class FriendsAdapter {
    private String userid;
    private String Name;
    private String image;
    private String BloodGroup;


    public FriendsAdapter() {

    }

    public FriendsAdapter(String userid, String name, String image, String bloodGroup) {
        this.userid = userid;
        Name = name;
        this.image = image;
        BloodGroup = bloodGroup;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
