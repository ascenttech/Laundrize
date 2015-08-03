package com.ascenttechnovation.laundrize.data;

/**
 * Created by ADMIN on 27-07-2015.
 */
public class TrackOrdersData {

    String id,serviceName,collectionSlot,actualCollected,actualOpsSubmissionTime,actualOpsCollectionTime,price,actualDelivery,deliverySlot,deliveryDate;
    int orderProgress;

    public TrackOrdersData(String id, String serviceName, String collectionSlot, String actualCollected, String actualOpsSubmissionTime, String actualOpsCollectionTime, String price, String actualDelivery, String deliverySlot, String deliveryDate, int orderProgress) {
        this.id = id;
        this.serviceName = serviceName;
        this.collectionSlot = collectionSlot;
        this.actualCollected = actualCollected;
        this.actualOpsSubmissionTime = actualOpsSubmissionTime;
        this.actualOpsCollectionTime = actualOpsCollectionTime;
        this.price = price;
        this.actualDelivery = actualDelivery;
        this.deliverySlot = deliverySlot;
        this.deliveryDate = deliveryDate;
        this.orderProgress = orderProgress;
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

    public String getCollectionSlot() {
        return collectionSlot;
    }

    public void setCollectionSlot(String collectionSlot) {
        this.collectionSlot = collectionSlot;
    }

    public String getActualCollected() {
        return actualCollected;
    }

    public void setActualCollected(String actualCollected) {
        this.actualCollected = actualCollected;
    }

    public String getActualOpsSubmissionTime() {
        return actualOpsSubmissionTime;
    }

    public void setActualOpsSubmissionTime(String actualOpsSubmissionTime) {
        this.actualOpsSubmissionTime = actualOpsSubmissionTime;
    }

    public String getActualOpsCollectionTime() {
        return actualOpsCollectionTime;
    }

    public void setActualOpsCollectionTime(String actualOpsCollectionTime) {
        this.actualOpsCollectionTime = actualOpsCollectionTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getActualDelivery() {
        return actualDelivery;
    }

    public void setActualDelivery(String actualDelivery) {
        this.actualDelivery = actualDelivery;
    }

    public String getDeliverySlot() {
        return deliverySlot;
    }

    public void setDeliverySlot(String deliverySlot) {
        this.deliverySlot = deliverySlot;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getOrderProgress() {
        return orderProgress;
    }

    public void setOrderProgress(int orderProgress) {
        this.orderProgress = orderProgress;
    }
}
