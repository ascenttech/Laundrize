package com.ascenttechnovation.laundrize.data;

/**
 * Created by ADMIN on 27-07-2015.
 */
public class TrackOrdersData {

    String serviceId,serviceName,collectionSlot,actualCollected,actualOpsSubmissionTime,actualOpsCollectionTime,price,actualDelivery,deliverySlot,deliveryDate,orderId,quantity,typeOfService;
    int orderProgress;

    public TrackOrdersData(String serviceId,String orderId,String serviceName,String quantity,String collectionSlot, String actualCollected, String actualOpsSubmissionTime, String actualOpsCollectionTime, String price, String actualDelivery, String deliverySlot, String deliveryDate,String typeOfService, int orderProgress) {
        this.serviceId = serviceId;
        this.orderId = orderId;
        this.serviceName = serviceName;
        this.quantity = quantity;
        this.collectionSlot = collectionSlot;
        this.actualCollected = actualCollected;
        this.actualOpsSubmissionTime = actualOpsSubmissionTime;
        this.actualOpsCollectionTime = actualOpsCollectionTime;
        this.price = price;
        this.actualDelivery = actualDelivery;
        this.deliverySlot = deliverySlot;
        this.deliveryDate = deliveryDate;
        this.orderProgress = orderProgress;
        this.typeOfService = typeOfService;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public String getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(String typeOfService) {
        this.typeOfService = typeOfService;
    }
}
