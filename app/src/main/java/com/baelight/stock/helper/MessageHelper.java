package com.baelight.stock.helper;

import android.content.Context;
import android.util.Log;

import com.baelight.stock.entity.Message;
import com.baelight.stock.entity.Stock;
import com.baelight.stock.util.HSJsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Nick on 2016/6/6.
 */
public class MessageHelper {
    private Context context;
    private List<Message> messages;
    private static MessageHelper helper = null;

    private MessageHelper(Context context) {
        this.context = context;
        this.messages = loadMessages();
    }

    private List<Message> loadMessages() {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open("data.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Message> messages = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONObject(json).getJSONArray("Messages");
            for (int i = 0; i < jsonArray.length(); i++) {
                Message message = new Message();
                JSONObject messageJSONObject = jsonArray.getJSONObject(i);
                message.id = messageJSONObject.getString("Id");
                message.authorId = messageJSONObject.getString("AuthorId");
                message.title = messageJSONObject.getString("Title");
                message.summary = messageJSONObject.getString("Summary");
                message.image = messageJSONObject.getString("Image");
                message.imageType = messageJSONObject.getString("ImageType");
                message.url = messageJSONObject.getString("Url");
                message.source = messageJSONObject.getString("Source");
                message.liked = messageJSONObject.getBoolean("Liked");
                message.likeCount = messageJSONObject.getInt("LikeCount");
                message.style = messageJSONObject.getInt("Style");
                message.type = messageJSONObject.getInt("Type");
                message.createdAt = messageJSONObject.getLong("CreatedAt");
                message.shareUrl = messageJSONObject.getString("ShareUrl");

                ArrayList<Stock> stocks = new ArrayList<>();
                JSONArray stockJSONArray = messageJSONObject.getJSONArray("Stocks");
                for (int j = 0; j < stockJSONArray.length(); j++) {
                    Stock stock = new Stock();
                    JSONObject stockJSONObject = stockJSONArray.getJSONObject(j);
                    stock.name = stockJSONObject.getString("Name");
                    stock.symbol = stockJSONObject.getString("Symbol");
                    stocks.add(stock);
                }
                message.stocks = stocks;
                messages.add(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return messages;
        }
        return messages;
    }

    public static MessageHelper getInstance(Context context) {
        if (helper == null) {
            synchronized (MessageHelper.class) {
                if (helper == null) {
                    helper = new MessageHelper(context);
                }
            }
        }
        return helper;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public Map<String, String> updateMessageStockInfo() {

        Map<String, String> info = new HashMap<>();
        List<Stock> allStocks = getAllStock();
        List<Stock> stockList = updateStockInfo(allStocks);
        if (stockList != null) {
            for (Stock stock : stockList) {
            Log.d(stock.symbol, stock.px_change_rate);
                info.put(stock.symbol, stock.px_change_rate);
            }
        }
        return info;
    }

    public List<Stock> updateStockInfo(List<Stock> stocks) {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder builder = HttpUrl.parse("https://bao.wallstreetcn.com/q/quote/v1/real").newBuilder();
        builder.addQueryParameter("fields", "prod_name,px_change,last_px,px_change_rate,trade_status");
        StringBuilder stringBuilder = new StringBuilder();
        for (Stock stock : stocks) {
            stringBuilder.append(stock.symbol).append(",");
        }
        builder.addQueryParameter("en_prod_code", stringBuilder.toString());
        Request request = new Request.Builder().url(builder.build()).build();
        try {
            String response = client.newCall(request).execute().body().string();
            return HSJsonUtil.getRealStockList(response, "snapshot");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Stock> getAllStock(){
        List<Stock> allStocks = new ArrayList<>();
        for (Message msg : messages) {
            allStocks.addAll(msg.stocks);
        }
        return allStocks;
    }
}
