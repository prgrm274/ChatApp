package org.chat.notifications.models;

public class NotifToken {
    private String notifToken;

    public NotifToken(String notifToken) {
        this.notifToken = notifToken;
    }

    public NotifToken(){}

    public String getNotifToken() {
        return notifToken;
    }

    public void setNotifToken(String notifToken) {
        this.notifToken = notifToken;
    }
}
