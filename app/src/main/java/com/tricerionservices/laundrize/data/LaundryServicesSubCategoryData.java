package com.tricerionservices.laundrize.data;

/**
 * Created by ADMIN on 27-07-2015.
 */
public class LaundryServicesSubCategoryData {

//
//    "code": "001001000",
//            "desc": "Shirt, t-shirt, top, blouse, plain kurta &amp; kameez",
//            "name": "Shirt &amp; top",
//            "imglarge": "http://images.laundrize.com/og/2021e6fa2f0fa9083f0de2cec317d758.jpg",
//            "imgsmall": "http://images.laundrize.com/og/2021e6fa2f0fa9083f0de2cec317d758.jpg",
//            "regular": "1",
//            "regularcost": "6",
//            "extracare": "NA",
//            "extracarecost": "NA"

    private String code,title,description,smallImage,largeImage,regular,regularCost,extraCare,extraCareCost;

    public LaundryServicesSubCategoryData(String code, String title, String description, String smallImage, String largeImage, String regular, String regularCost, String extraCare, String extraCareCost) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.smallImage = smallImage;
        this.largeImage = largeImage;
        this.regular = regular;
        this.regularCost = regularCost;
        this.extraCare = extraCare;
        this.extraCareCost = extraCareCost;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getRegularCost() {
        return regularCost;
    }

    public void setRegularCost(String regularCost) {
        this.regularCost = regularCost;
    }

    public String getExtraCare() {
        return extraCare;
    }

    public void setExtraCare(String extraCare) {
        this.extraCare = extraCare;
    }

    public String getExtraCareCost() {
        return extraCareCost;
    }

    public void setExtraCareCost(String extraCareCost) {
        this.extraCareCost = extraCareCost;
    }
}
