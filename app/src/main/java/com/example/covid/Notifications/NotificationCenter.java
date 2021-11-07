package com.example.covid.Notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.covid.R;

import java.util.Calendar;
import java.util.Date;

public class NotificationCenter {

    private static final String DAILY_CHALLENGE_CHANNEL_ID = "daily_id";
    public static final int DAILY_CHALLENGE_ID = 0;
    private static NotificationCompat.Builder dailyBuilder;

    private static final String COVID_ENCOUNTER_CHANNEL_ID = "enc_id";
    private static final int COVID_ENCOUNTER_ID = 1;
    private static NotificationCompat.Builder encounterBuilder;

    public static void createNotificationChannels(Context cx){


        encounterBuilder = createNotification(cx, COVID_ENCOUNTER_CHANNEL_ID, R.drawable.ic_launcher_background, cx.getString(R.string.push_notifications_notf_title),
                cx.getString(R.string.push_notifications_notf_text), NotificationManager.IMPORTANCE_HIGH);

        NotificationChannel dailyNotificationChannel = new NotificationChannel(DAILY_CHALLENGE_CHANNEL_ID,
                cx.getString(R.string.daily_challenge_channel_name), NotificationManager.IMPORTANCE_DEFAULT);
        dailyNotificationChannel.setDescription(cx.getString(R.string.daily_challenge_channel_desc));

        NotificationChannel pushNotificationChannel = new NotificationChannel(COVID_ENCOUNTER_CHANNEL_ID,
                cx.getString(R.string.push_notifications_channel_name), NotificationManager.IMPORTANCE_HIGH);
        pushNotificationChannel.setDescription(cx.getString(R.string.push_notifications_channel_desc));

        NotificationManager nm = cx.getSystemService(NotificationManager.class);
        nm.createNotificationChannel(dailyNotificationChannel);
        nm.createNotificationChannel(pushNotificationChannel);

    }

    public static Notification createDailyNotification(Context cx){
        return createNotification(cx, DAILY_CHALLENGE_CHANNEL_ID, R.drawable.ic_launcher_background, cx.getString(R.string.push_notifications_notf_title),
                cx.getString(R.string.push_notifications_notf_text), NotificationManager.IMPORTANCE_HIGH)
                .build();
    }
    private static NotificationCompat.Builder createNotification(Context cx, String channelId, int smallIcon, String title, String content, int priority){
        return new NotificationCompat.Builder(cx, channelId)
                .setSmallIcon(smallIcon)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(priority);
    }

    public static void scheduleDailyChallengeNotification(Context cx){
        Intent intent = new Intent(cx, NotificationReceiver.class);
        PendingIntent pending = PendingIntent.getBroadcast(cx, DAILY_CHALLENGE_ID,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager = (AlarmManager) cx.getSystemService(Context.ALARM_SERVICE);
        Date currentTime = Calendar.getInstance().getTime();
        long time = currentTime.getTime() + 10000;
        manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,time,pending);
    }

    public static void sendCovidEncounterNotification(Context cx){
        NotificationManagerCompat nm = NotificationManagerCompat.from(cx);
        nm.notify(COVID_ENCOUNTER_ID,encounterBuilder.build());
    }

}
