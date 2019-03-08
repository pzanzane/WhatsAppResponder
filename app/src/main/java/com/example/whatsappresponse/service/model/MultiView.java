package com.example.whatsappresponse.service.model;

public class MultiView {

    private String message;
    private int id;
    private EnumViewTypes enumViewTypes;

    public MultiView(String message, int id, EnumViewTypes enumViewTypes) {
        this.message = message;
        this.id = id;
        this.enumViewTypes = enumViewTypes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public int getId() {
        return id;
    }

    public EnumViewTypes getEnumViewTypes() {
        return enumViewTypes;
    }
}
