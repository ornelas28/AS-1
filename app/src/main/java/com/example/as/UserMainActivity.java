package com.example.as;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.text.DateFormat;
import java.util.Calendar;

public class UserMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_users);

        CardView crd_sar = (CardView) findViewById(R.id.crd_sar);
        CardView crd_exit = findViewById(R.id.crd_exit);
        CardView crd_email = findViewById(R.id.crd_email);
        CardView crd_micuenta=findViewById(R.id.crd_micuenta);
        CardView crd_ris = findViewById(R.id.crd_ris);

        crd_sar.setOnClickListener((View.OnClickListener) view -> {
            final Spinner spinn;
            final Spinner spinn_solic;
            final EditText fecha;
            final EditText Hora;
            AlertDialog.Builder mBuilder;
            ArrayAdapter<String> adapter;
            View mView;
            ArrayAdapter<String> myaadapter;
            Calendar calenda;
            Button enviar;
            AlertDialog dialog;
            String currentDate;

            mBuilder = new AlertDialog.Builder(UserMainActivity.this);
            mView = getLayoutInflater().inflate(R.layout.activity_envio_sar, null);
            spinn_solic = (Spinner)mView.findViewById(R.id.spinner_solicitud);
            myaadapter = new ArrayAdapter<String>(UserMainActivity.this,
                    android.R.layout.simple_spinner_item,
                    getResources().getStringArray(R.array.Solicitud));
            spinn = (Spinner)mView.findViewById(R.id.spinner_dels);
            adapter = new ArrayAdapter<>(UserMainActivity.this,
                    android.R.layout.simple_spinner_item, getResources()
                    .getStringArray(R.array.delegaciones));
            fecha = (EditText) mView.findViewById(R.id.txt_fecha_er);
            Hora = (EditText)mView.findViewById(R.id.txt_hora);
            enviar = (Button)mView.findViewById(R.id.btn_ris_alert);
            calenda = Calendar.getInstance();
            currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calenda.getTime());

            mBuilder.setTitle("Reporte de Servicio de Alto Riesgo (SAR)");
            mBuilder.setIcon(R.drawable.salvar);
            mBuilder.setMessage("\nIngresa los datos correspondientes.");
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinn.setAdapter(adapter);
            myaadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinn_solic.setAdapter(myaadapter);
            fecha.setText(currentDate);

            enviar.setOnClickListener(view1 -> {
                if (!fecha.getText().toString().isEmpty()
                        && !Hora.getText().toString().isEmpty()
                        && !spinn.getSelectedItem().toString()
                        .equalsIgnoreCase("Delegación")
                        && !spinn_solic.getSelectedItem().toString()
                        .equalsIgnoreCase("Servicio Solicitado Por:")) {
                    Intent intent = new Intent(UserMainActivity.this,sar.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(UserMainActivity.this, "Llena los campos correspondientes",
                            Toast.LENGTH_SHORT).show();
                }

            });

            mBuilder.setView(mView);
            dialog = mBuilder.create();
            dialog.show();
        });

        crd_exit.setOnClickListener(view -> {
            Intent intent = new Intent(UserMainActivity.this, MainActivity.class);
            finish();
        });

        crd_email.setOnClickListener(view -> {
            Intent intent = new Intent(UserMainActivity.this, SendEmailActivity.class);
            startActivity(intent);
            finish();
        });

        crd_micuenta.setOnClickListener(view -> {
            Intent intent = new Intent (UserMainActivity.this, mi_cuenta.class);
            startActivity(intent);
            finish();
        });

        crd_ris.setOnClickListener((View.OnClickListener) view -> {
            final Button enviar;
            final Spinner numnomdel;
            AlertDialog.Builder mBuilder;
            View mView;
            ArrayAdapter<String> adapter;
            EditText fecha;
            Calendar calenda;
            String currentDate;
            AlertDialog dialog;

            mBuilder = new AlertDialog.Builder(UserMainActivity.this);
            mBuilder.setTitle("Reporte de Incidente de Seguridad(RIS)");
            mBuilder.setIcon(R.drawable.salvar);
            mBuilder.setMessage("\nIngresa los datos correspondientes");

            mView = getLayoutInflater().inflate(R.layout.activity_envio_ris,null);
            numnomdel = (Spinner)mView.findViewById(R.id.Numero_delgacion_ris_inflate);
            adapter = new ArrayAdapter<String>(UserMainActivity.this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.num_y_del));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            numnomdel.setAdapter(adapter);

            fecha = (EditText)mView.findViewById(R.id.txt_fecha_ris_inflate);
            calenda = Calendar.getInstance();
            currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calenda.getTime());
            fecha.setText(currentDate);

            enviar = (Button)mView.findViewById(R.id.btn_enviar);
            enviar.setOnClickListener(view12 -> {
                if (!numnomdel.getSelectedItem()
                        .toString().equalsIgnoreCase("Delegación")){
                    Intent intent = new Intent(UserMainActivity.this,ris.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(UserMainActivity.this, "Eh we el spinner", Toast.LENGTH_SHORT).show();
                }

            });

            mBuilder.setView(mView);
            dialog = mBuilder.create();
            dialog.show();
        });

    }
    
}
