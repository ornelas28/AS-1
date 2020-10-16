package com.example.as;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.util.Calendar;

public class menu_users extends AppCompatActivity  {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_users);



        CardView crd_sar = (CardView) findViewById(R.id.crd_sar);
        //añadimos el onclick a nuestro cardview
        crd_sar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creamos un dialog alert para añadir los datos de primer contacto que seran enviados al Admin
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(menu_users.this);
                mBuilder.setTitle("Reporte de Servicio de Alto Riesgo (SAR)");
                mBuilder.setIcon(R.drawable.salvar);
                mBuilder.setMessage("\nIngresa los datos correspondientes.");

                View mView = getLayoutInflater().inflate(R.layout.activity_envio_sar, null);

                //Spinner delegaciones de la alerta de dialogo para enviar la alerta al administrador
                final Spinner spinn = (Spinner)mView.findViewById(R.id.spinner_dels);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(menu_users.this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.delegaciones));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinn.setAdapter(adapter);

                //Spinner tipo_serv de la alerta de dialogo para enviar la alerta al administrador



                //Spinner solicitud de la alerta de dialogo para enviar alerta al administrador

                final Spinner spinn_solic = (Spinner)mView.findViewById(R.id.spinner_solicitud);
                ArrayAdapter<String> myaadapter = new ArrayAdapter<String>(menu_users.this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.Solicitud));
                myaadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinn_solic.setAdapter(myaadapter);

                final EditText fecha = (EditText) mView.findViewById(R.id.txt_fecha_er);
                final EditText Hora = (EditText)mView.findViewById(R.id.txt_hora);
                Button enviar = (Button)mView.findViewById(R.id.btn_ris_alert);

                //Empieza asignacion de fecha por el dispositivo
                Calendar calenda = Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calenda.getTime());
                fecha.setText(currentDate);
                //Termina asignacion de fecha por el dispositivo

                //Añadimos onclick al boton para enviar la informacion y generar la alerta. Validando que ningun campo este vacio
                enviar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (!fecha.getText().toString().isEmpty()&& !Hora.getText().toString().isEmpty() && !spinn.getSelectedItem().toString().equalsIgnoreCase("Delegación") && !spinn_solic.getSelectedItem().toString().equalsIgnoreCase("Servicio Solicitado Por:")){
                            Intent intent = new Intent(menu_users.this,sar.class);
                            startActivity(intent);
                            //Toast.makeText(menu_users.this, "Alerta Enviada", Toast.LENGTH_SHORT).show();
                            finish();

                        }else{
                            Toast.makeText(menu_users.this, "Llena los campos correspondientes", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


                //mostramos nuestro dialogo

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

        CardView crd_exit = findViewById(R.id.crd_exit);
        crd_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menu_users.this, MainActivity.class);
                finish();




            }
        });

        CardView crd_email = findViewById(R.id.crd_email);
        crd_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menu_users.this,email.class);
                startActivity(intent);
                finish();
            }
        });

        CardView crd_micuenta=findViewById(R.id.crd_micuenta);
        crd_micuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (menu_users.this, mi_cuenta.class);
                startActivity(intent);
                finish();
            }
        });

        CardView crd_ris = findViewById(R.id.crd_ris);

        crd_ris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(menu_users.this);
                mBuilder.setTitle("Reporte de Incidente de Seguridad(RIS)");
                mBuilder.setIcon(R.drawable.salvar);
                mBuilder.setMessage("\nIngresa los datos correspondientes");

                View mView = getLayoutInflater().inflate(R.layout.activity_envio_ris,null);
                final Spinner numnomdel=(Spinner)mView.findViewById(R.id.Numero_delgacion_ris_inflate);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(menu_users.this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.num_y_del));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                numnomdel.setAdapter(adapter);

                EditText fecha = (EditText)mView.findViewById(R.id.txt_fecha_ris_inflate);
                Calendar calenda = Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calenda.getTime());
                fecha.setText(currentDate);


               /*EditText entidad= (EditText)findViewById(R.id.txt_entidad_ris_inflate);
                entidad.setText("Jalisco");*/
                
               

                final Button enviar=(Button)mView.findViewById(R.id.btn_enviar);
                enviar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!numnomdel.getSelectedItem().toString().equalsIgnoreCase("Delegación")){

                            Intent intent = new Intent(menu_users.this,ris.class);
                            startActivity(intent);

                        }else{
                            Toast.makeText(menu_users.this, "Eh we el spinner", Toast.LENGTH_SHORT).show();
                        }
                        
                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }

        });

    }



}
