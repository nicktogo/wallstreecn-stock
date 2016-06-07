package com.baelight.stock.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baelight.stock.fragment.MessageFragment;
import com.baelight.stock.R;
import com.baelight.stock.receiver.AutoUpdateReceiver;
import com.baelight.stock.service.AutoUpdateService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        MessageFragment fragment = new MessageFragment();
        transaction.replace(R.id.message_fragment, fragment);
        transaction.commit();

        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);

    }
}
