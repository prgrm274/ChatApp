package org.chat.notifications.oreo;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;

import java.util.NavigableMap;

public class NotifOreo extends ContextWrapper {

    private static final String CHANNEL_ID = "org.chat.";
    private static final String CHANNEL_NAME = "chat";

    private NotificationManager notificationManager;

    public NotifOreo(Context base) {
        super(base);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            makeChannel();
        }
    }


    @TargetApi(Build.VERSION_CODES.O)
    private void makeChannel() {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
        );
        channel.enableLights(false);
        channel.enableVibration(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel);
    }


    public NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return notificationManager;
    }


    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getOreoNotif(String title, String body, PendingIntent pendingIntent, Uri soundUri, String icon) {
        return new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(Integer.parseInt(icon))
                .setSound(soundUri)
                .setAutoCancel(true);
    }
}
