package com.ascenttechnovation.laundrize.data;

/**
 * Created by ADMIN on 27-07-2015.
 */
public class LaundryServicesMainCategoryData {

    String id,serviceName,shortDescription,longDescription,smallImage,largeImage;

    public LaundryServicesMainCategoryData(String id, String serviceName, String shortDescription, String longDescription, String smallImage, String largeImage) {
        this.id = id;
        this.serviceName = serviceName;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.smallImage = smallImage;
        this.largeImage = largeImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }
}
