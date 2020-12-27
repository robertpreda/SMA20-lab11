package com.upt.cti.smartwallet;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Calendar;

@IgnoreExtraProperties
public class Payment implements Serializable {

    public String timestamp;
    private double cost;
    private String name;
    private String type;

    public Payment() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Payment(String name, String type, double cost){
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.timestamp = Calendar.getInstance().getTime().toString();
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public String getType() {
        return type;
    }

    public void setCost(double cost) { this.cost = cost;};
    public void setName(String name) {this.name = name;}
    public void setType(String type){this.type = type;}
    public void setTimestamp(String timestamp){this.timestamp = timestamp;}

}