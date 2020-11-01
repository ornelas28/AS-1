package com.example.as.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.as.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.as.classes.database.ConstantsDataBase.*;

public class Sar1Fragment extends Fragment {

    private Spinner spinnerDelegation;
    private Spinner spinnerService;
    private EditText editTypePatient;
    private EditText editStartHour;
    private TextView textDate;
    private TextView textPlace;
    private Button buttonSave;
    private Button buttonBack;
    private int code;
    private View view;

    private String date;
    private double latitude;
    private double longitude;
    private String args;

    public Sar1Fragment (int code, String args) {
        this.code = code;
        this.args = args;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sar1, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        buttonSave.setOnClickListener(v -> onSave());
        buttonBack.setOnClickListener(v -> onBack());
    }

    private void init() {
        ArrayAdapter<String> arrayDelegation;
        ArrayAdapter<String> arrayService;
        Calendar calendar;
        LocationManager locationManager;
        LocationListener locationListener;

        spinnerDelegation = view.findViewById(R.id.spinner_delegation);
        spinnerService = view.findViewById(R.id.spinner_service);
        editTypePatient = view.findViewById(R.id.edit_type_of_patient);
        editStartHour = view.findViewById(R.id.edit_start_hour);
        textDate = view.findViewById(R.id.text_date);
        textPlace = view.findViewById(R.id.text_place);
        buttonSave = view.findViewById(R.id.button_save);
        buttonBack = view.findViewById(R.id.button_back);
        arrayDelegation = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.delegations));
        arrayService = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.services));
        calendar = Calendar.getInstance();
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        int permissionCheck= ContextCompat.checkSelfPermission
                (getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }

        arrayDelegation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayService.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                textPlace.setText(location.getLatitude() + " " + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }

            @Override
            public void onProviderEnabled(String provider) { }

            @Override
            public void onProviderDisabled(String provider) { }
        };
        locationManager.requestLocationUpdates
                (LocationManager.NETWORK_PROVIDER,0,0,locationListener);

        spinnerDelegation.setAdapter(arrayDelegation);
        spinnerService.setAdapter(arrayService);
        textDate.setText(DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime()));
        date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
    }

    private void onBack() {
        getFragmentManager().beginTransaction()
                .replace(R.id.content, new ListFragment(args)).commit();
    }

    private void onSave() {
        boolean stateSpinnerDelegation = spinnerDelegation.getSelectedItem().toString()
                .equalsIgnoreCase("Delegación");
        boolean stateSpinnerService = spinnerService.getSelectedItem().toString()
                .equalsIgnoreCase("Servicio Solicitado Por:");

        if(!stateSpinnerDelegation && !stateSpinnerService
                && !editTypePatient.getText().toString().isEmpty()
                && !editStartHour.getText().toString().isEmpty()) {

            Map<String, Object> mapSAR = new HashMap<>();
            String delegation = spinnerDelegation.getSelectedItem().toString();
            String service = spinnerService.getSelectedItem().toString();
            String typePatient = editTypePatient.getText().toString().trim();
            String startHour = editStartHour.getText().toString().trim();

            mapSAR.put(ID, FirebaseAuth.getInstance().getUid());
            mapSAR.put(DELEGATION, delegation);
            mapSAR.put(SERVICE, service);
            mapSAR.put(TYPE_PATIENT, typePatient);
            mapSAR.put(START_HOUR, startHour);
            mapSAR.put(DATE, date);
            mapSAR.put(LATITUDE, latitude);
            mapSAR.put(LONGITUDE, latitude);
            mapSAR.put(STATE, false);

            FirebaseDatabase.getInstance().getReference().child(SARS).push().setValue(mapSAR);

        }
    }
}