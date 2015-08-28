package com.tricerionservices.laundrize.data;

/**
 * Created by ADMIN on 29-07-2015.
 */
public class GeneralData {


    String code, description,title,largeImage,smallImage,regular,regularCost,extraCare,extraCost,quantity;

    public GeneralData(String code, String title,String description, String largeImage, String smallImage, String regular, String regularCost, String extraCare, String extraCost, String quantity) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.largeImage = largeImage;
        this.smallImage = smallImage;
        this.regular = regular;
        this.regularCost = regularCost;
        this.extraCare = extraCare;
        this.extraCost = extraCost;
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
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

    public String getExtraCost() {
        return extraCost;
    }

    public void setExtraCost(String extraCost) {
        this.extraCost = extraCost;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
