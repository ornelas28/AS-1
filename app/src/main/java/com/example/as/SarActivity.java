package com.example.as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sar);

        final Button buttonFinish = findViewById(R.id.button_finish);
        final EditText textHourSarEnd = findViewById(R.id.txt_hora_conclusion_sar);
        final EditText textNumberSar = findViewById(R.id.txt_numFrap_sar);
        final EditText textPersonalServiceSar = findViewById(R.id.txt_personal_servicio_sar);
        final EditText textPersonalSar = findViewById(R.id.txt_unidades_servicio_sar);
        final EditText textDescriptionSar = findViewById(R.id.txt_desc_sar);
        final EditText textNumberPatientsSar = findViewById(R.id.txt_cant_pac_sar);
        final EditText textTypePatient = findViewById(R.id.txt_tipo_paciente);
        final EditText textTransferPatinet = findViewById(R.id.txt_traspac_sar);
        final EditText textObservations = this.findViewById(R.id.txt_observaciones_Sar);
        final Spinner spinnerAuthoritiesCrm = findViewById(R.id.autoridades_crm);
        final Spinner spinnerAuthoritiesPubs = findViewById(R.id.autoridadespubs);
        final Spinner spinnerHospitalsTrassar = findViewById(R.id.hosp_trassar);

        ArrayAdapter<String> stringArrayAdapter;
        ArrayAdapter<String> stringArrayAdapter1;
        ArrayAdapter<String> stringArrayAdapter2;

        buttonFinish.setOnClickListener(view -> {
            if (!textHourSarEnd.getText().toString().isEmpty()
                    && !textNumberSar.getText().toString().isEmpty()
                    && !textPersonalServiceSar.getText().toString().isEmpty()
                    && !textPersonalSar.getText().toString().isEmpty()
                    && !textDescriptionSar.getText().toString().isEmpty()
                    && !textNumberPatientsSar.getText().toString().isEmpty()
                    && !textTransferPatinet.getText().toString().isEmpty()
                    && !spinnerAuthoritiesPubs.getSelectedItem()
                    .toString().equalsIgnoreCase("Autoridad publica presente")
                    && !spinnerAuthoritiesCrm.getSelectedItem().toString()
                    .equalsIgnoreCase("Autoridad CRM informadas")
                    && !textTypePatient.toString().isEmpty()
                    && !spinnerHospitalsTrassar.getSelectedItem().toString()
                    .equalsIgnoreCase("Hospital de Traslado")
                    && !textObservations.getText().toString().isEmpty()) {

                Toast.makeText(SarActivity.this,
                        "Reporte finalizado con exito", Toast.LENGTH_SHORT).show();
                Toast.makeText(SarActivity.this, "Se ha notificado", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SarActivity.this, UserMainActivity_C.class));
                finish();
            } else {
                Toast.makeText(SarActivity.this,
                        "Favor de llenar los campos correspondientes", Toast.LENGTH_SHORT).show();
            }
        });

        stringArrayAdapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.Hospital_Traslado));
        stringArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHospitalsTrassar.setAdapter(stringArrayAdapter1);

        stringArrayAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.Autoridadpub));
        stringArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAuthoritiesPubs.setAdapter(stringArrayAdapter2);

        stringArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.autoridades_crm));
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAuthoritiesCrm.setAdapter(stringArrayAdapter);
    }

    public void onBack(View view){
        startActivity(new Intent(this, UserMainActivity_C.class));
        finish();
    }

}
