package com.bohemiamates.crcmngmt.other;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmSetter extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent i) {
        final String ACTION = "android.intent.action.BOOT_COMPLETED";

        if (ACTION.equals(i.getAction())) {

            PrefManager prefManager = new PrefManager(context);
            if (!prefManager.isFirstClanInit()) {
                Intent intent = new Intent(context, AlarmReceiver.class);
                intent.setAction("com.bohemiamates.crcmngmt.UP_TO_DATE");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                        0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarm.cancel(pendingIntent);
                alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + 3000,
                        AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }
}
