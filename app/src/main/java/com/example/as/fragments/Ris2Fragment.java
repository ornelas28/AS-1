package com.example.as.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.as.R;
import com.example.as.classes.database.RISData;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

import static com.example.as.classes.database.ConstantsDataBase.*;
import static com.google.firebase.database.FirebaseDatabase.*;

public class Ris2Fragment extends Fragment {

    private final String code;
    private RISData risData;
    private boolean stateConstructor;
    private final String args;
    private final boolean stateAdmin;

    private TextInputLayout inputHechos;
    private TextInputLayout inputCausa;
    private TextInputLayout inputState;
    private TextInputLayout inputDetails;
    private TextInputLayout inputDanos;
    private TextInputLayout inputSalud;
    private TextInputLayout inputObservaciones;
    private CheckBox checkPoliciaFederal;
    private CheckBox checkPoliciaEstatal;
    private CheckBox checkPoliciaMunicipal;
    private CheckBox checkEjercito;
    private CheckBox checkMarina;
    private CheckBox checkGuardia;
    private CheckBox checkActores;
    private CheckBox checkManifestante;
    private CheckBox checkDelincuencia;
    private CheckBox checkOthers;
    private CheckBox checkComunidades;
    private Button buttonSave;
    private Button buttonBack;
    private Button buttonFinalise;
    private View view;

    public Ris2Fragment (String code, String args, RISData risData, boolean stateAdmin) {
        this.code = code;
        this.args = args;
        this.risData = risData;
        this.stateConstructor=true;
        this.stateAdmin = stateAdmin;
    }

    public Ris2Fragment (String code, String args, boolean stateAdmin) {
        this.code = code;
        this.args = args;
        this.stateAdmin = stateAdmin;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ris2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        initWid();

        if(stateConstructor){
            initOld();
        }

        buttonSave.setOnClickListener(v -> {
            if (stateConstructor){
                onUpdate(false);
            }else{
                onSave();
            }
        });
        buttonFinalise.setOnClickListener(v -> onUpdate(true));

        buttonBack.setOnClickListener(v -> {
            if (stateAdmin) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container_sar, new ListFragment(args, stateAdmin)).commit();
            } else {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container_sar, new ListFragment(args)).commit();
            }
        });
    }

    private void onSave() {
        Map<String, Object> map = new HashMap<>();

        map.put(STATE, false);

        map.put(HECHOS, inputHechos.getEditText().getText().toString());
        map.put(CAUSA, inputCausa.getEditText().getText().toString());
        map.put(STATE_SALUD, inputState.getEditText().getText().toString());
        map.put(DETAILS, inputDetails.getEditText().getText().toString());
        map.put(DANOS, inputDanos.getEditText().getText().toString());
        map.put(SALUD, inputSalud.getEditText().getText().toString());
        map.put(OBSERVACIONES, inputObservaciones.getEditText().getText().toString());

        map.put(POLICIAFEDERAL, checkPoliciaFederal.isChecked());
        map.put(POLICIAESTATAL, checkPoliciaEstatal.isChecked());
        map.put(POLICIAMUNICIPAL, checkPoliciaMunicipal.isChecked());
        map.put(EJERCITO, checkEjercito.isChecked());
        map.put(MARINA, checkMarina.isChecked());
        map.put(GUARDIA, checkGuardia.isChecked());
        map.put(ACTORES, checkActores.isChecked());
        map.put(MANIFESTANTE, checkManifestante.isChecked());
        map.put(DELINCUENCIA, checkDelincuencia.isChecked());
        map.put(OTHERS, checkOthers.isChecked());
        map.put(COMUNIDADES, checkComunidades.isChecked());

        getInstance().getReference().child(RISS).push().setValue(map);
        if (stateAdmin) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_sar, new ListFragment(args, stateAdmin)).commit();
        } else {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_sar, new ListFragment(args)).commit();
        }
    }

    private void initOld() {
        inputHechos.getEditText().setText(risData.getHechos());
        inputCausa.getEditText().setText(risData.getCausa());
        inputState.getEditText().setText(risData.getStateSalud());
        inputDanos.getEditText().setText(risData.getDanos());
        inputDetails.getEditText().setText(risData.getDetails());
        inputSalud.getEditText().setText(risData.getSalud());
        inputObservaciones.getEditText().setText(risData.getObservaciones());

        checkPoliciaFederal.setChecked(risData.isPoliciaFederal());
        checkPoliciaEstatal.setChecked(risData.isPoliciaEstatal());
        checkPoliciaMunicipal.setChecked(risData.isPoliciaMunicipal());
        checkEjercito.setChecked(risData.isEjercito());
        checkMarina.setChecked(risData.isMarina());
        checkGuardia.setChecked(risData.isGuardia());
        checkActores.setChecked(risData.isActores());
        checkManifestante.setChecked(risData.isManifestante());
        checkDelincuencia.setChecked(risData.isDelincuencia());
        checkOthers.setChecked(risData.isOthers());
        checkComunidades.setChecked(risData.isComunidades());
    }

    private void onUpdate(boolean b) {
        Map<String, Object> map = new HashMap<>();

        map.put(STATE, b);

        map.put(HECHOS, inputHechos.getEditText().getText().toString());
        map.put(CAUSA, inputCausa.getEditText().getText().toString());
        map.put(STATE_SALUD, inputState.getEditText().getText().toString());
        map.put(DETAILS, inputDetails.getEditText().getText().toString());
        map.put(DANOS, inputDanos.getEditText().getText().toString());
        map.put(SALUD, inputSalud.getEditText().getText().toString());
        map.put(OBSERVACIONES, inputObservaciones.getEditText().getText().toString());

        map.put(POLICIAFEDERAL, checkPoliciaFederal.isChecked());
        map.put(POLICIAESTATAL, checkPoliciaEstatal.isChecked());
        map.put(POLICIAMUNICIPAL, checkPoliciaMunicipal.isChecked());
        map.put(EJERCITO, checkEjercito.isChecked());
        map.put(MARINA, checkMarina.isChecked());
        map.put(GUARDIA, checkGuardia.isChecked());
        map.put(ACTORES, checkActores.isChecked());
        map.put(MANIFESTANTE, checkManifestante.isChecked());
        map.put(DELINCUENCIA, checkDelincuencia.isChecked());
        map.put(OTHERS, checkOthers.isChecked());
        map.put(COMUNIDADES, checkComunidades.isChecked());



        getInstance().getReference().child(RISS).child(risData.getKey()).updateChildren(map);
    }

    private void initWid() {
        inputHechos = view.findViewById(R.id.input_hechos);
        inputCausa = view.findViewById(R.id.input_causa);
        inputState = view.findViewById(R.id.input_state);
        inputDetails = view.findViewById(R.id.input_details);
        inputDanos = view.findViewById(R.id.input_daños);
        inputSalud = view.findViewById(R.id.input_state_salud);
        inputObservaciones = view.findViewById(R.id.input_observaciones);

        checkPoliciaFederal = view.findViewById(R.id.check_policia_federal);
        checkPoliciaEstatal = view.findViewById(R.id.check_policia_estatal);
        checkPoliciaMunicipal = view.findViewById(R.id.check_policia_municipal);
        checkEjercito = view.findViewById(R.id.check_ejecito);
        checkMarina = view.findViewById(R.id.check_marina);
        checkGuardia = view.findViewById(R.id.check_guadia);
        checkActores = view.findViewById(R.id.check_actores);
        checkManifestante = view.findViewById(R.id.check_manifestante);
        checkDelincuencia = view.findViewById(R.id.check_delincuencia);
        checkOthers = view.findViewById(R.id.check_others);
        checkComunidades = view.findViewById(R.id.check_comunidades);

        buttonSave = view.findViewById(R.id.button_add_2);
        buttonFinalise = view.findViewById(R.id.button_finish);
        buttonBack = view.findViewById(R.id.button_back1);
    }
}