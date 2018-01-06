package com.logistics.hypernym.logistic.models;

/**
 * Created by Metis on 08-Dec-17.
 */

public class Drawer {
    public int icon;
    public String title;
    public boolean isSelected;

    public Drawer(int icon, String title, boolean isSelected) {
        this.icon = icon;
        this.title = title;
        this.isSelected = isSelected;
    }
}
