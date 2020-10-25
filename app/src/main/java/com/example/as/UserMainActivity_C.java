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

import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.Calendar;

public class UserMainActivity_C extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner;
    private Spinner spinnerSolic;
    private EditText date;
    private EditText hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        CardView cardViewSar = findViewById(R.id.crd_sar);
        CardView cardViewExit = findViewById(R.id.crd_exit);
        CardView cardViewEmail = findViewById(R.id.crd_email);
        CardView cardViewMyAccount = findViewById(R.id.crd_micuenta);
        CardView cardViewRis = findViewById(R.id.crd_ris);

        spinner = findViewById(R.id.spinner_dels);
        date = findViewById(R.id.txt_fecha_er);
        hour = findViewById(R.id.txt_hora);
        spinnerSolic = findViewById(R.id.spinner_solicitud);

        cardViewSar.setOnClickListener(this);
        cardViewExit.setOnClickListener(this);
        cardViewEmail.setOnClickListener(this);
        cardViewMyAccount.setOnClickListener(this);
        cardViewRis.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.crd_sar: {

                View view1 = getLayoutInflater().inflate(R.layout.activity_send_sar, null);
                Button buttonSend = view1.findViewById(R.id.btn_ris_alert);
                ArrayAdapter<String> stringArrayAdapter;
                ArrayAdapter<String> adapter;
                AlertDialog dialog;

                AlertDialog.Builder builder = new AlertDialog.Builder(UserMainActivity_C.this);
                Calendar calendar = Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

                stringArrayAdapter = new ArrayAdapter<>(UserMainActivity_C.this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.Solicitud));
                adapter = new ArrayAdapter<>(UserMainActivity_C.this,
                        android.R.layout.simple_spinner_item, getResources()
                        .getStringArray(R.array.delegaciones));

                builder.setTitle("Reporte de Servicio de Alto Riesgo (SAR)");
                builder.setIcon(R.drawable.salvar);
                builder.setMessage("\nIngresa los datos correspondientes.");
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSolic.setAdapter(stringArrayAdapter);
                date.setText(currentDate);

                buttonSend.setOnClickListener(this);

                builder.setView(view1);
                dialog = builder.create();
                dialog.show();
                break;
            }
            case R.id.crd_exit: {
                FirebaseAuth.getInstance().signOut();
                break;
            }
            case R.id.crd_email:
                startActivity(new Intent(UserMainActivity_C.this,
                        SendEmailActivity.class));
                finish();
                break;
            case R.id.crd_micuenta:
                startActivity(new Intent(UserMainActivity_C.this, MyAccountActivity.class));
                finish();
                break;
            case R.id.crd_ris: {
                final Button buttonSend;
                final Spinner spinnerNumber;
                AlertDialog.Builder builder;
                View view1;
                EditText textDate;
                Calendar calendar;
                String currentDate;
                AlertDialog dialog;

                builder = new AlertDialog.Builder(UserMainActivity_C.this);
                builder.setTitle("Reporte de Incidente de Seguridad(RIS)");
                builder.setIcon(R.drawable.salvar);
                builder.setMessage("\nIngresa los datos correspondientes");

                view1 = getLayoutInflater().inflate(R.layout.activity_envio_ris, null);
                spinnerNumber = view1.findViewById(R.id.Numero_delgacion_ris_inflate);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(UserMainActivity_C.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.num_y_del));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerNumber.setAdapter(adapter);

                textDate = view1.findViewById(R.id.txt_fecha_ris_inflate);
                calendar = Calendar.getInstance();
                currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                textDate.setText(currentDate);

                buttonSend = view1.findViewById(R.id.btn_enviar);
                buttonSend.setOnClickListener(view12 -> {
                    if (!spinnerNumber.getSelectedItem()
                            .toString().equalsIgnoreCase("Delegación")) {
                        startActivity(new Intent(UserMainActivity_C.this, RisActivity.class));
                    } else {
                        Toast.makeText(UserMainActivity_C.this, "Eh we el spinner", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setView(view1);
                dialog = builder.create();
                dialog.show();
                break;
            }
            case R.id.btn_ris_alert: {
                if (!date.getText().toString().isEmpty()
                        && !hour.getText().toString().isEmpty()
                        && !spinner.getSelectedItem().toString()
                        .equalsIgnoreCase("Delegación")
                        && !spinnerSolic.getSelectedItem().toString()
                        .equalsIgnoreCase("Servicio Solicitado Por:")) {
                    startActivity(new Intent(UserMainActivity_C.this,
                            SarActivity.class));
                    finish();
                } else {
                    Toast.makeText(UserMainActivity_C.this, "Llena los campos correspondientes",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

    }

}
