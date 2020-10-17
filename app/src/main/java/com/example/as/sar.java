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

public class sar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sar);


        
        final Button enviar = (Button) findViewById(R.id.btn_finalizar);
        final EditText hora_conclusion_sar = (EditText)findViewById(R.id.txt_hora_conclusion_sar);
        final EditText numFrap_sar = (EditText)findViewById(R.id.txt_numFrap_sar);
        final EditText personal_servicio_sar = (EditText)findViewById(R.id.txt_personal_servicio_sar);
        final EditText personal_sar = (EditText)findViewById(R.id.txt_unidades_servicio_sar);
        final EditText descripcion_sar=(EditText)findViewById(R.id.txt_desc_sar);
        final EditText cantidad_pacientes_sar = (EditText)findViewById(R.id.txt_cant_pac_sar);
        final EditText tipo_paciente_sar=(EditText)findViewById(R.id.txt_tipo_paciente);
        final EditText traslado_paciente_sar = (EditText)findViewById(R.id.txt_traspac_sar);
        //final EditText otro_hops = (EditText)findViewById(R.id.txt_otro_hosp);
        final EditText observaciones=(EditText)findViewById(R.id.txt_observaciones_Sar);
        final Spinner autoridades_crm = (Spinner)findViewById(R.id.autoridades_crm);
        final Spinner autoridadespubs_sar=(Spinner)findViewById(R.id.autoridadespubs);
        final Spinner hosp_trassar = (Spinner)findViewById(R.id.hosp_trassar);
        //final TextView otro_hosp=(TextView)findViewById(R.id.lbl_otro_hosp);

        /*if(otro_hops.getVisibility() == View.VISIBLE && otro_hosp.getVisibility() == View.VISIBLE){
            otro_hops.setVisibility(View.INVISIBLE);
            otro_hosp.setVisibility(View.INVISIBLE);
        }*/
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hora_conclusion_sar.getText().toString().isEmpty() && !numFrap_sar.getText().toString().isEmpty() && !personal_servicio_sar.getText().toString().isEmpty() && !personal_sar.getText().toString().isEmpty() && !descripcion_sar.getText().toString().isEmpty() && !cantidad_pacientes_sar.getText().toString().isEmpty() && !traslado_paciente_sar.getText().toString().isEmpty() && !autoridadespubs_sar.getSelectedItem().toString().equalsIgnoreCase("Autoridad publica presente") && !autoridades_crm.getSelectedItem().toString().equalsIgnoreCase("Autoridad CRM informadas") && !tipo_paciente_sar.toString().isEmpty() && !hosp_trassar.getSelectedItem().toString().equalsIgnoreCase("Hospital de Traslado") && !observaciones.getText().toString().isEmpty()) {

                    Toast.makeText(sar.this, "Reporte finalizado con exito", Toast.LENGTH_SHORT).show();
                    Toast.makeText(sar.this, "Se ha notificado", Toast.LENGTH_SHORT).show();
                    Intent finalizar = new Intent(sar.this, UserMainActivity.class);
                    startActivity(finalizar);
                    finish();
                }else{
                    Toast.makeText(sar.this, "Favor de llenar los campos correspondientes", Toast.LENGTH_SHORT).show();
                }
            }
        });







        final Spinner spinn_hosptras = (Spinner)findViewById(R.id.hosp_trassar);
        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.Hospital_Traslado));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinn_hosptras.setAdapter(myadapter);

        /*spinn_hosptras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
              if (position== 10){
                    otro_hops.setVisibility(View.VISIBLE);

                }else {
                    otro_hops.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        final Spinner autpubs = (Spinner)findViewById(R.id.autoridadespubs);
        ArrayAdapter<String> aadapte = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.Autoridadpub));
        aadapte.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autpubs.setAdapter(aadapte);

        final Spinner autcrm=(Spinner)findViewById(R.id.autoridades_crm);
        ArrayAdapter<String> aaadapte = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.autoridades_crm));
        aaadapte.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autcrm.setAdapter(aaadapte);





    }

    public void onatras(View view){
        Intent next =  new Intent(this, UserMainActivity.class);
        startActivity(next);
        finish();

    }



}
