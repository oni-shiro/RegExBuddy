package com.regexBuddy.regexhelper.dtos;

import java.util.List;

public class ChatRequest {
    private String model;
    private List<Message> messages;
    private Double temperature;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
