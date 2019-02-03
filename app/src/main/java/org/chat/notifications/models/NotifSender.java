package org.chat.notifications.models;

public class NotifSender {
    public NotifData data;
    public String to;

    public NotifSender(NotifData data, String to) {
        this.data = data;
        this.to = to;
    }
}
