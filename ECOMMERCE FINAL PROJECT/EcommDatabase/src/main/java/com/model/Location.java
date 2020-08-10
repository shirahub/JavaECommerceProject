package com.model;

public class Location {
    int loc_id;
    String location;

    public Location(int loc_id, String location) {
        this.loc_id = loc_id;
        this.location = location;
    }

    public int getLoc_id() {
        return loc_id;
    }

    public void setLoc_id(int loc_id) {
        this.loc_id = loc_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
