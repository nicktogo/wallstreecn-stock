package com.baelight.stock.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Nick on 2016/6/3.
 */
public class StockApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }
}
