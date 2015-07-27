package com.ascenttechnovation.laundrize.data;

/**
 * Created by ADMIN on 27-07-2015.
 */
public class AddressData {

    String id,address,city,zipCode,areaName,mobileNumber,fullAddress;

    public AddressData(String id, String address, String city, String zipCode, String areaName, String mobileNumber,String fullAddress) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.areaName = areaName;
        this.mobileNumber = mobileNumber;
        this.fullAddress = fullAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }
}
