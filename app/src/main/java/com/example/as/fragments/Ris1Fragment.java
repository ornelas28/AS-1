package com.example.as.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.as.R;
import com.example.as.classes.database.RISData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.as.classes.database.ConstantsDataBase.AGGRESSION_TO_TRANSPORTED;
import static com.example.as.classes.database.ConstantsDataBase.ARRESTS;
import static com.example.as.classes.database.ConstantsDataBase.ASSAULT;
import static com.example.as.classes.database.ConstantsDataBase.ASSAULT_ON_FACILITIES;
import static com.example.as.classes.database.ConstantsDataBase.ASSAULT_ON_STAFF;
import static com.example.as.classes.database.ConstantsDataBase.DATE;
import static com.example.as.classes.database.ConstantsDataBase.DELEGATION;
import static com.example.as.classes.database.ConstantsDataBase.EMBLEM_ABUSE;
import static com.example.as.classes.database.ConstantsDataBase.ENTITY;
import static com.example.as.classes.database.ConstantsDataBase.EXTORTION;
import static com.example.as.classes.database.ConstantsDataBase.HOUR;
import static com.example.as.classes.database.ConstantsDataBase.ID;
import static com.example.as.classes.database.ConstantsDataBase.IMPLICATE;
import static com.example.as.classes.database.ConstantsDataBase.KIDNAPPING;
import static com.example.as.classes.database.ConstantsDataBase.PERSONAL_ASSAULT;
import static com.example.as.classes.database.ConstantsDataBase.PREVENT_ACCESS;
import static com.example.as.classes.database.ConstantsDataBase.RISS;
import static com.example.as.classes.database.ConstantsDataBase.SARS;
import static com.example.as.classes.database.ConstantsDataBase.SHOOTING;
import static com.example.as.classes.database.ConstantsDataBase.STATE;
import static com.example.as.classes.database.ConstantsDataBase.THREAT;
import static com.example.as.classes.database.ConstantsDataBase.TRAFFIC_ACCIDENT;
import static com.example.as.classes.database.ConstantsDataBase.VIOLENCE;
import static com.google.firebase.database.FirebaseDatabase.*;
import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class Ris1Fragment extends Fragment {

    private final String code;
    private final String args;
    private RISData risData;
    private boolean stateConstructor;
    private final boolean stateAdmin;
    private Spinner spinnerDelegation;
    private TextView textDate;
    private EditText editEntity;
    private CheckBox checkAssault;
    private CheckBox checkViolence;
    private CheckBox checkShooting;
    private CheckBox checkKidnapping;
    private CheckBox checkEmblemAbuse;
    private CheckBox checkArrests;
    private CheckBox checkPersonalAssault;
    private CheckBox checkExtortion;
    private CheckBox checkThreat;
    private CheckBox checkPreventAccess;
    private CheckBox checkAssaultOnFacilities;
    private CheckBox checkAssaultOnStaff;
    private CheckBox checkTrafficAccident;
    private CheckBox checkAggression;
    private TimePicker pickerHour;
    private NumberPicker pickerImplicate;
    private Button buttonAdd1;
    private Button buttonBack;

    public Ris1Fragment (String code, String args, RISData risData, boolean stateAdmin) {
        this.code = code;
        this.args = args;
        this.risData = risData;
        this.stateConstructor=true;
        this.stateAdmin = stateAdmin;
    }

    public Ris1Fragment (String code, String args, boolean stateAdmin) {
        this.code = code;
        this.args = args;
        this.stateAdmin = stateAdmin;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_ris1, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> arrayDelegation;
        Calendar calendar = Calendar.getInstance();

        initWidgets();

        pickerImplicate.setMaxValue(10000);
        pickerImplicate.setMinValue(0);
        arrayDelegation = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.delegations));
        arrayDelegation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDelegation.setAdapter(arrayDelegation);
        textDate.setText(DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime()));

        if(stateConstructor){
            initOld();
        }

        buttonAdd1.setOnClickListener(v -> {
            if (stateConstructor){
                onUpdate();
            }else{
                onSave();
            }
        });
        buttonBack.setOnClickListener(v ->{
            if (stateAdmin) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container_sar, new ListFragment(args, stateAdmin)).commit();
            } else {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container_sar, new ListFragment(args)).commit();
            }
        });
    }

    private void initOld() {
        for (int i=0; i<=spinnerDelegation.getAdapter().getCount(); i++){
            if (!risData.getDelegation().equals(spinnerDelegation.getSelectedItem())){
                spinnerDelegation.setSelection(i);
            }
        }
        textDate.setText(risData.getDate());
        editEntity.setText(risData.getEntity());
        checkAssault.setChecked(risData.isAssault());
        checkViolence.setChecked(risData.isViolence());
        checkShooting.setChecked(risData.isShooting());
        checkKidnapping.setChecked(risData.isKidnapping());
        checkEmblemAbuse.setChecked(risData.isEmblem_abuse());
        checkArrests.setChecked(risData.isArrests());
        checkPersonalAssault.setChecked(risData.isPersonalAssault());
        checkExtortion.setChecked(risData.isExtortion());
        checkThreat.setChecked(risData.isThreat());
        checkPreventAccess.setChecked(risData.isPreventAccess());
        checkAssaultOnFacilities.setChecked(risData.isAssaultOnFacilities());
        checkAssaultOnStaff.setChecked(risData.isAssaultOnStaff());
        checkTrafficAccident.setChecked(risData.isTrafficAccident());
        checkAggression.setChecked(risData.isAggressionToTransported());
        pickerImplicate.setValue(risData.getImplicate());
    }

    private void initWidgets() {
        View view = getView();
        buttonAdd1 = view.findViewById(R.id.button_add1);
        buttonBack = view.findViewById(R.id.button_back);
        textDate = view.findViewById(R.id.text_date3);
        editEntity = view.findViewById(R.id.edit_entity);
        spinnerDelegation = view.findViewById(R.id.spinner_delegation_ris1);
        pickerImplicate = view.findViewById(R.id.piker_implicate);
        checkTrafficAccident = view.findViewById(R.id.check_traffic_accident);
        checkAssault = view.findViewById(R.id.check_assault);
        checkViolence = view.findViewById(R.id.check_violence);
        checkShooting = view.findViewById(R.id.check_shooting);
        checkKidnapping = view.findViewById(R.id.check_kidnapping);
        checkEmblemAbuse = view.findViewById(R.id.check_emblem_abuse);
        checkArrests = view.findViewById(R.id.check_arrests);
        checkPersonalAssault = view.findViewById(R.id.check_personalAssault);
        checkExtortion = view.findViewById(R.id.check_extortion);
        checkThreat = view.findViewById(R.id.check_threat);
        checkPreventAccess = view.findViewById(R.id.check_prevent_access);
        checkAssaultOnFacilities = view.findViewById(R.id.check_assault_on_facilities);
        checkAssaultOnStaff = view.findViewById(R.id.check_assault_on_staff);
        checkAggression = view.findViewById(R.id. check_aggression_to_transported);
        pickerHour = view.findViewById(R.id.piker_hour);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void onSave() {

        RISData risData = new RISData(textDate.getText().toString(), editEntity.getText().toString(),
                spinnerDelegation.getSelectedItem().toString(),
                FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                pickerHour.getHour() + ":" + pickerHour.getMinute(), pickerImplicate.getValue(),
                checkTrafficAccident.isChecked(), checkAssault.isChecked(), (checkViolence.isChecked()),
                checkShooting.isChecked(), checkKidnapping.isChecked(), checkEmblemAbuse.isChecked(),
                checkArrests.isChecked(), checkPersonalAssault.isChecked(), checkExtortion.isChecked(),
                checkThreat.isChecked(), checkPreventAccess.isChecked(), checkAssaultOnFacilities.isChecked(),
                checkAssaultOnStaff.isChecked(), checkAggression.isChecked(), false);

        getInstance().getReference().child(RISS).push().setValue(risData);

        if (stateAdmin) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_sar, new ListFragment(args, stateAdmin)).commit();
        } else {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_sar, new ListFragment(args)).commit();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void onUpdate() {
        Map<String, Object> map = new HashMap<>();
        map.put(DATE, textDate.getText().toString());
        map.put(ENTITY, editEntity.getText().toString());
        map.put(DELEGATION, spinnerDelegation.getSelectedItem().toString());
        map.put(ID, FirebaseAuth.getInstance().getCurrentUser().getEmail());
        map.put(HOUR, pickerHour.getHour() + ":" + pickerHour.getMinute());
        map.put(IMPLICATE, pickerImplicate.getValue());
        map.put(TRAFFIC_ACCIDENT, checkTrafficAccident.isChecked());
        map.put(ASSAULT, checkAssault.isChecked());
        map.put(VIOLENCE, checkViolence.isChecked());
        map.put(SHOOTING, checkShooting.isChecked());
        map.put(KIDNAPPING, checkKidnapping.isChecked());
        map.put(EMBLEM_ABUSE, checkEmblemAbuse.isChecked());
        map.put(ARRESTS, checkArrests.isChecked());
        map.put(PERSONAL_ASSAULT, checkPersonalAssault.isChecked());
        map.put(EXTORTION, checkExtortion.isChecked());
        map.put(THREAT, checkThreat.isChecked());
        map.put(PREVENT_ACCESS, checkPreventAccess.isChecked());
        map.put(ASSAULT_ON_FACILITIES, checkAssaultOnFacilities.isChecked());
        map.put(ASSAULT_ON_STAFF, checkAssaultOnStaff.isChecked());
        map.put(AGGRESSION_TO_TRANSPORTED, checkAggression.isChecked());
        map.put(STATE, false);

        getInstance().getReference().child(RISS).child(risData.getKey()).updateChildren(map);
    }

}