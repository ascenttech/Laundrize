package com.ascenttechnovation.laundrize.data;

/**
 * Created by ADMIN on 11-08-2015.
 */
public class ZipCodeData {

    String id,zipCode;

    public ZipCodeData(String id, String zipCode) {
        this.id = id;
        this.zipCode = zipCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
