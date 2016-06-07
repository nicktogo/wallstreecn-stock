package com.baelight.stock.entity;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baelight.stock.R;
import com.baelight.stock.app.StockApplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Message {
    public String id;
    public String authorId;
    public String title;
    public String summary;
    public String content;
    public String image;
    public String imageType;
    public String url;
    public String source;
    public boolean liked;
    public int likeCount;
    public int style;// MessageStyleShort 1 MessageStyleLong 2  MessageStyleUrl 3
    public int type;//1 new //2 hot
    public long createdAt;
    //public String[] subjectIds;
    public ArrayList<Stock> stocks;
    public String shareUrl;

    @BindingAdapter("appendViews")
    public static void appendViews(FrameLayout container, LinearLayout childLayout) {
        container.removeAllViews();
        container.addView(childLayout);
    }

    public LinearLayout getAppendViews() {
        Context context = StockApplication.getAppContext();
        LinearLayout childLayout = new LinearLayout(context);
        childLayout.setPaddingRelative(4, 4, 4, 4);
        LinearLayout.LayoutParams nameLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        for (Stock stock : stocks) {

            TextView nameTextView = new TextView(context);
            nameTextView.setLayoutParams(nameLayoutParams);
            nameTextView.setTextColor(ContextCompat.getColor(context, R.color.stock_up));
            nameTextView.setText(stock.name);
            childLayout.addView(nameTextView);
//            Log.d("stock.name", stock.name);
        }
        return childLayout;
    }

    @BindingAdapter("android:text")
    public static void setTime(TextView textView, long createdAt) {
        Date date = new Date(createdAt);
        String hourMinute = new SimpleDateFormat("HH:mm").format(date);
        textView.setText(hourMinute);
    }

    @BindingAdapter("appendText")
    public static void appendText(TextView textView, String text) {
        textView.setText("来自 " + text);
    }
}
