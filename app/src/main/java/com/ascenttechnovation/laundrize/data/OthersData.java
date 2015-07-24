package com.ascenttechnovation.laundrize.data;

/**
 * Created by ADMIN on 13-07-2015.
 */
public class OthersData {

    private String backgroundImageUrl,title,description,price,quantity;

    public OthersData(String backgroundImageUrl, String title, String description, String price, String quantity) {
        this.backgroundImageUrl = backgroundImageUrl;
        this.title = title;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
