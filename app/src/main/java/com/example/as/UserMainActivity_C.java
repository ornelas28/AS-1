package com.example.as;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(UserMainActivity_C.this);
                mBuilder.setTitle("Reporte de Servicio de Alto Riesgo (SAR)");
                mBuilder.setIcon(R.drawable.salvar);
                mBuilder.setMessage("\nIngresa los datos correspondientes.");

                View mView = getLayoutInflater().inflate(R.layout.activity_send_sar, null);
                final Spinner spinn = (Spinner)mView.findViewById(R.id.spinner_dels);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserMainActivity_C.this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.delegaciones));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinn.setAdapter(adapter);

                final Spinner spinn_solic = (Spinner)mView.findViewById(R.id.spinner_solicitud);
                ArrayAdapter<String> myaadapter = new ArrayAdapter<String>(UserMainActivity_C.this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.Solicitud));
                myaadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinn_solic.setAdapter(myaadapter);

                final EditText fecha = (EditText) mView.findViewById(R.id.txt_fecha_er);
                final EditText Hora = (EditText)mView.findViewById(R.id.txt_hora);
                final TextView ubication=(TextView)mView.findViewById(R.id.txt_ubication);
                Button enviar = (Button)mView.findViewById(R.id.btn_ris_alert);
                Calendar calenda = Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calenda.getTime());
                fecha.setText(currentDate);

                int permissionCheck= ContextCompat.checkSelfPermission
                        (this, Manifest.permission.ACCESS_FINE_LOCATION);
                if(permissionCheck== PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions
                            (this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                }
                LocationManager locationManager= (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                LocationListener locationListener= new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        ubication.setText(location.getLatitude() + "" + location.getLongitude());
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                };
                locationManager.requestLocationUpdates
                        (LocationManager.NETWORK_PROVIDER,0,0,locationListener);

                enviar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (!fecha.getText().toString().isEmpty()&& !Hora.getText().toString().isEmpty() && !spinn.getSelectedItem().toString().equalsIgnoreCase("Delegación") && !spinn_solic.getSelectedItem().toString().equalsIgnoreCase("Servicio Solicitado Por:")){
                            Intent intent = new Intent(UserMainActivity_C.this,SendSarActivity.class);
                            startActivity(intent);
                            //Toast.makeText(menu_users.this, "Alerta Enviada", Toast.LENGTH_SHORT).show();
                            finish();

                        }else{
                            Toast.makeText(UserMainActivity_C.this, "Llena los campos correspondientes", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                break;
            }
            case R.id.crd_exit: {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(UserMainActivity_C.this,SplashActivity.class));
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
