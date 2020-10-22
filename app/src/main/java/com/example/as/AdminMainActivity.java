package com.example.as;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_administrado);

        CardView cardViewMyAccountAdmin = findViewById(R.id.activity_main_admin);
        cardViewMyAccountAdmin.setOnClickListener(view -> {
            startActivity(new Intent(AdminMainActivity.this, MyAccountActivity.class));
            finish();
        });
    }
}
