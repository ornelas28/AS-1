package com.example.as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SendEmailActivity extends AppCompatActivity {

    private TextInputEditText editTextTo;
    private TextInputEditText editTextTop;
    private TextInputEditText editTextMessage;
    private String stringTo;
    private String stringMessage;
    private String stringTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        editTextTo = findViewById(R.id.txt_para);
        editTextTop = findViewById(R.id.txt_asunto);
        editTextMessage = findViewById(R.id.txt_mensaje);
        Button atras = findViewById(R.id.btn_atras_email);
        editTextTo.setText("fmadrigal.jal@cruzrojamexicana.org.mx");

        atras.setOnClickListener(view -> {
            Intent intent = new Intent(SendEmailActivity.this, UserMainActivity.class);
            startActivity(intent);
            finish();
        });

    }

    public void onSend(View view){

        stringTo = editTextTo.getText().toString();
        stringMessage = editTextMessage.getText().toString();
        stringTop = editTextTop.getText().toString();

        sendEmail();

        Toast.makeText(this,"Enviando datos al correo",Toast.LENGTH_SHORT).show();

    }

    public void sendEmail(){

        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String []{stringTo} );
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, stringTop);
        emailIntent.putExtra(Intent.EXTRA_TEXT, stringMessage + getString(R.string.Firma));

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar email."));
            Log.i("EMAIL", "Enviando email...");
        }
        catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(this, "NO existe ningún cliente de email instalado!.", Toast.LENGTH_SHORT).show();
        }


    }


}
