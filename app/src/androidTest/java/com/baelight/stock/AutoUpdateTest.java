package com.baelight.stock;

import android.content.Intent;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.baelight.stock.app.StockApplication;
import com.baelight.stock.service.AutoUpdateService;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Nick on 2016/6/7.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class AutoUpdateTest {
    @Test
    public void testAutoUpdateService() {
        Intent intent = new Intent(StockApplication.getAppContext(), AutoUpdateService.class);
        intent.putExtra("update_gap", 10 * 1000);
        StockApplication.getAppContext().startService(intent);
    }
}
