package com.ascenttechnovation.laundrize.data;

/**
 * Created by ADMIN on 13-07-2015.
 */
public class GeneralData {

    String backgroundImageUrl,title,description;

    public GeneralData(String backgroundImageUrl, String title, String description) {
        this.backgroundImageUrl = backgroundImageUrl;
        this.title = title;
        this.description = description;
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
}
