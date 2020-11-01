package com.example.as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MyAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        Button buttonBack = findViewById(R.id.button_back);
        Button buttonChange = findViewById(R.id.button_save);
        Button buttonSave = findViewById(R.id.button_save);

        buttonBack.setOnClickListener(view -> {
            startActivity(new Intent(MyAccountActivity.this, UserMainActivity_C.class));
            finish();
        });
        
        buttonChange.setOnClickListener(v ->
                Toast.makeText(MyAccountActivity.this,
                        "Se presiono boton cambiar", Toast.LENGTH_SHORT).show());
        
        buttonSave.setOnClickListener(v ->
                Toast.makeText(MyAccountActivity.this,
                        "Datos guardados :)", Toast.LENGTH_SHORT).show());

    }
}
