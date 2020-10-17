package com.example.as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class mi_cuenta extends AppCompatActivity {
    Button btn_atras, btn_cambiar, btn_guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_cuenta);

        btn_atras=findViewById(R.id.btn_regresar);
        btn_cambiar=findViewById(R.id.btn_cambios);
        btn_guardar=findViewById(R.id.btn_salvar);

        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent atras = new Intent(mi_cuenta.this, UserMainActivity.class);
                startActivity(atras);
                finish();
            }
        });
        
        btn_cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mi_cuenta.this, "Se presiono boton cambiar", Toast.LENGTH_SHORT).show();
            }
        });
        
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mi_cuenta.this, "Datos guardados :)", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
