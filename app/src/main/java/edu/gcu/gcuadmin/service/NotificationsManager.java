package edu.gcu.gcuadmin.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import java.util.Calendar;

import edu.gcu.gcuadmin.R;
import edu.gcu.gcuadmin.login.LoginActivity;

/**
 * Created by Shrivats on 4/7/2017.
 */

public class NotificationsManager {
    private Context context;
    private static PendingIntent pendingIntent;

    public NotificationsManager(Context context) {
        this.context = context;
    }


    public Notification createNotification(String contentTitle, String contentText){
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent intent = PendingIntent.getActivity(context, 0, new Intent(context,
                LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).putExtra
                ("pager-position", 1), 0);
        Notification.Builder build = new Notification.Builder(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            build.setSmallIcon(R.drawable.ic_not_logo);
            build.setColor(context.getResources().getColor(R.color.colorLogo));
        }
        else{
            build.setSmallIcon(R.mipmap.ic_launcher_home);
        }
        build.setContentTitle(contentTitle);
        build.setContentText(contentText);
        build.setSound(alarmSound);
        build.setContentIntent(intent);

        return build.build();
    }
    public void scheduleNotification(Notification notification){
        Calendar cal = Calendar.getInstance();

        Intent notIntent = new Intent(context, NotificationPublisher.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notIntent.putExtra("notification-id", 1);
        notIntent.putExtra("notification", notification);
        pendingIntent = PendingIntent.getBroadcast(context, 0, notIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC, cal.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                pendingIntent);
    }
    public void cancelNotifications(){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(pendingIntent != null)
        alarmManager.cancel(pendingIntent);
    }
}
