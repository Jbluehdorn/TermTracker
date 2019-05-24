package com.jbluehdorn.termtracker.util;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import com.jbluehdorn.termtracker.R;
import com.jbluehdorn.termtracker.receivers.NotificationReceiver;

public class NotificationsClient {
    private static NotificationsClient sIntance;
    private Context context;

    private static final String CHANNEL_NAME = "notifications_channel";
    private static final String CHANNEL_DESCRIPTION = "channel for app notifications";
    private static final String CHANNEL_ID = "ch_main";

    public static synchronized  NotificationsClient getInstance(Context context) {
        if(sIntance == null) {
            sIntance = new NotificationsClient(context);
        }
        return sIntance;
    }

    private NotificationsClient(Context context) {
        this.context = context;
        createNotificationsChannel();
    }

    public void scheduleNotification(String title, String content, int delay) {
        Intent notificationIntent = new Intent(this.context, NotificationReceiver.class);

        Notification notification = this.getNotification(title, content);

        notificationIntent.putExtra(NotificationReceiver.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationReceiver.NOTIFICATION, notification);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String title, String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.context, CHANNEL_ID);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.wgu_logo_icon);
        builder.setColor(this.context.getColor(R.color.colorPrimary));
        return builder.build();
    }

    private void createNotificationsChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            channel.setDescription(CHANNEL_DESCRIPTION);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = this.context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
}
