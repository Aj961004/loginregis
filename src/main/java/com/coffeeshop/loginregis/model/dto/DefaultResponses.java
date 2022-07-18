package com.coffeeshop.loginregis.model.dto;

public class DefaultResponses<T> {
    private String messages;
    private T data;

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
