package com.tricerionservices.laundrize.data;

/**
 * Created by ADMIN on 01-08-2015.
 */
public class WashingOrderData {

    String orderId,amount,quantity;

    public WashingOrderData(String orderId, String amount, String quantity) {
        this.orderId = orderId;
        this.amount = amount;
        this.quantity = quantity;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
