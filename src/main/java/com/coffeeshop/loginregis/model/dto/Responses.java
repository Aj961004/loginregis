package com.coffeeshop.loginregis.model.dto;

public class Responses {
    private String message;

    public Responses(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
