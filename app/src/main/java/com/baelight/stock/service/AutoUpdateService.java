package com.baelight.stock.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baelight.stock.app.StockApplication;
import com.baelight.stock.helper.MessageHelper;
import com.baelight.stock.receiver.AutoUpdateReceiver;

/**
 * Created by Nick on 2016/6/7.
 */
public class AutoUpdateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MessageHelper helper = MessageHelper.getInstance(StockApplication.getAppContext());
                helper.updateMessageStockInfo();
                Log.d("AutoUpdateService", "AutoUpdateService thread run");
            }
        }).start();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int updateGap = intent.getIntExtra("update_gap", (int) (30 * 1000));
        long triggerAtTime = SystemClock.elapsedRealtime() + updateGap;
        Intent i = new Intent(this, AutoUpdateReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }
}
