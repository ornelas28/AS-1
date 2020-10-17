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

        CardView crd_micuenta=findViewById(R.id.crd_micuenta_admin);
        crd_micuenta.setOnClickListener(view -> {
            Intent intent = new Intent (AdminMainActivity.this, mi_cuenta.class);
            startActivity(intent);
            finish();
        });
    }
}
