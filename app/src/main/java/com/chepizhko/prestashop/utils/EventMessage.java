package com.chepizhko.prestashop.utils;

public class EventMessage {
    private String notification;

    public EventMessage(String notification) {
        this.notification = notification;
    }
    public String getNotification() {
        return notification;
    }
}