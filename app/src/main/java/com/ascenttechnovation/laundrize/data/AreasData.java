package com.ascenttechnovation.laundrize.data;

/**
 * Created by ADMIN on 11-08-2015.
 */
public class AreasData {

    String id,areaName;

    public AreasData(String id, String areaName) {
        this.id = id;
        this.areaName = areaName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
