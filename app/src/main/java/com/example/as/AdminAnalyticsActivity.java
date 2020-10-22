package com.example.as;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.as.classes.ConstantsDataBase;

import static com.example.as.classes.ConstantsDataBase.*;

public class AdminAnalyticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_analytics);
        String pass = PASSWORD;
    }
}
