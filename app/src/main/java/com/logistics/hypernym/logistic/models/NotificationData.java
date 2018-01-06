package com.logistics.hypernym.logistic.models;

/**
 * Created by Metis on 14-Dec-17.
 */

public class NotificationData {

    private String title;
    int photoid;

    public NotificationData(String title,int photoid) {
        this.title = title;
        this.photoid=photoid;

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

}