package com.example.trainingapp;

public class ActivityItem {
    private String userID;
    private String name;
    private int duration;
    private int burnedcalories;

    ActivityItem() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getBurnedcalories() {
        return burnedcalories;
    }

    public void setBurnedcalories(int burnedcalories) {
        this.burnedcalories = burnedcalories;
    }
}
