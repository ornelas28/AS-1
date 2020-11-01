package com.example.as;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import static com.example.as.classes.database.ConstantsDataBase.*;

public class AdminAnalyticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_analytics);
        String pass = PASSWORD;
    }
}
