package com.baelight.stock.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.baelight.stock.app.StockApplication;
import com.baelight.stock.helper.MessageHelper;
import com.baelight.stock.service.AutoUpdateService;

/**
 * Created by Nick on 2016/6/7.
 */
public class AutoUpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AutoUpdateService.class);
        context.startService(i);
        Log.d("AutoUpdateReceiver", "AutoUpdateReceiver onReceive");
    }
}
