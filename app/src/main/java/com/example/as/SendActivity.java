package com.example.as;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.as.fragments.ListFragment;

import static com.example.as.classes.database.ConstantsDataBase.*;

public class SendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        String args = getIntent().getExtras().getString(CODE);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, new ListFragment(args)).commit();
    }
}