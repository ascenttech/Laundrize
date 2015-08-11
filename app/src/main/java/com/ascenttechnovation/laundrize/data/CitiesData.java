package com.ascenttechnovation.laundrize.data;

/**
 * Created by ADMIN on 11-08-2015.
 */
public class CitiesData {

    String id,name;

    public CitiesData(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
