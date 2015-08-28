package com.tricerionservices.laundrize.data;

/**
 * Created by ADMIN on 12-08-2015.
 */
public class GeneralAddressRelatedData {

    String id,field;

    public GeneralAddressRelatedData(String id, String field) {
        this.id = id;
        this.field = field;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
