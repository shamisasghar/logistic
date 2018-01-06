package com.logistics.hypernym.logistic.models;

/**
 * Created by Metis on 17-Dec-17.
 */

public class AssestData {


    private String itemname;
    private String itemtype;

    public AssestData(String itemname,String itemtype) {
        this.itemname = itemname;
        this.itemtype=itemtype;

    }
    public String getItemname() {
        return itemname;
    }

    public void setItemname(String name) {
        this.itemname = name;
    }

}
