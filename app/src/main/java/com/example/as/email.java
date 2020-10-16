package com.example.as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class email extends AppCompatActivity {
    EditText txtPara, txtAsunto, txtMensaje;
    Button atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        txtPara = (EditText)findViewById(R.id.txt_para);
        txtAsunto = (EditText)findViewById(R.id.txt_asunto);
        txtMensaje = (EditText)findViewById(R.id.txt_mensaje);
        atras=(Button)findViewById(R.id.btn_atras_email);
        txtPara.setText("fmadrigal.jal@cruzrojamexicana.org.mx");

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(email.this,menu_users.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public String para,mensaje,asunto;
    public void onEnviar(View view){

        para=txtPara.getText().toString();
        mensaje=txtMensaje.getText().toString();
        asunto=txtAsunto.getText().toString();

        enviarEmail();

        Toast.makeText(this,"Enviando datos al correo",Toast.LENGTH_SHORT).show();

    }

    public void enviarEmail(){

        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String []{para} );
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto );
        emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje + getString(R.string.Firma));

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar email."));
            Log.i("EMAIL", "Enviando email...");
        }
        catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(this, "NO existe ningún cliente de email instalado!.", Toast.LENGTH_SHORT).show();
        }


    }


}
