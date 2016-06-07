package com.baelight.stock;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.baelight.stock.app.StockApplication;
import com.baelight.stock.entity.Stock;
import com.baelight.stock.helper.MessageHelper;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Nick on 2016/6/7.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class MessageHelperTest{

    @Test
    public void testUpdateStockInfo() {
        MessageHelper helper = MessageHelper.getInstance(StockApplication.getAppContext());
        assertNotNull(helper.getMessages());
        List<Stock> stocks = new ArrayList<>();

        Stock stock1 = new Stock();
        stock1.name = "荣信股份";
        stock1.symbol = "002123.SZ";
        stocks.add(stock1);

        Stock stock2 = new Stock();
        stock2.name = "久立特材";
        stock2.symbol = "002318.SZ";
        stocks.add(stock2);

        Stock stock3 = new Stock();
        stock3.name = "永鼎股份";
        stock3.symbol = "600105.SS";
        stocks.add(stock3);

        List<Stock> stockList = helper.updateStockInfo(stocks);
        assertNotNull(stockList);
        List<String> stockNames = new ArrayList<>();
        for (Stock stock : stockList) {
            stockNames.add(stock.name);
        }
        assertTrue(stockNames.contains(stock1.name));
        assertTrue(stockNames.contains(stock2.name));
        assertTrue(stockNames.contains(stock3.name));
    }

    @Test
    public void updateMessageStockInfo() {
        MessageHelper helper = MessageHelper.getInstance(StockApplication.getAppContext());
        Map<String, String> info = helper.updateMessageStockInfo();
        assertNotEquals(0, info.size());
    }
}
