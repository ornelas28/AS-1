package com.example.as.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.as.MyAccountActivity;
import com.example.as.R;
import com.example.as.classes.database.ConstantsDataBase;
import com.example.as.classes.database.RISData;
import com.example.as.classes.database.SARData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static com.example.as.classes.database.ConstantsDataBase.*;
import static com.example.as.classes.database.ConstantsDataBase.ACTS_DESCRIPTIONS;
import static com.example.as.classes.database.ConstantsDataBase.CRM_AUTHORITIES;
import static com.example.as.classes.database.ConstantsDataBase.FINISH_HOUR;
import static com.example.as.classes.database.ConstantsDataBase.FRAP_NUMBER;
import static com.example.as.classes.database.ConstantsDataBase.HOSPITAL_TRANSFER;
import static com.example.as.classes.database.ConstantsDataBase.NUMBER_PATIENTS;
import static com.example.as.classes.database.ConstantsDataBase.PATIENT_TRANSFER;
import static com.example.as.classes.database.ConstantsDataBase.PUBLIC_AUTHORITIES;
import static com.example.as.classes.database.ConstantsDataBase.SERVICE_PERSONAL;
import static com.example.as.classes.database.ConstantsDataBase.SERVICE_UNITS;
import static com.example.as.classes.database.ConstantsDataBase.TYPE_PATIENTS;

public class Sar2Fragment extends Fragment {
    private SARData sarData;
    private final String args;
    private Boolean stateConstructor = false;
    private Boolean stateAdmin = false;
    private final String code;
    private View view2;
    EditText HoraConclusion;
    EditText NumeroFrap;
    EditText PersonalServicio;
    EditText UnidadesServicio;
    EditText DescripcionHechos;
    EditText CantidadPacientes;
    EditText TipoPaciente;
    EditText TrasladoPaciente;
    Spinner HospitalTraslado;
    Spinner AutoridadesPublicas;
    Spinner AutoridadesCrm;
    EditText Observaciones;
    Button btn_Save;
    Button btn_Finish;

    public Sar2Fragment (String code, String args, SARData sarData, boolean stateAdmin) {
        this.code = code;
        this.args = args;
        this.sarData = sarData;
        this.stateConstructor=true;
        this.stateAdmin = stateAdmin;
    }

    public Sar2Fragment (String code, String args, boolean stateAdmin) {
        this.code = code;
        this.args = args;
        this.stateAdmin = stateAdmin;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view2 = inflater.inflate(R.layout.fragment_sar2, container, false);
        return view2;


    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> arrayHospital;
        ArrayAdapter<String> arrayAutoridadesPub;
        ArrayAdapter<String> arrayAutoridadesCRM;
        HoraConclusion = view2.findViewById(R.id.txt_hora_conclusion_sar);
        NumeroFrap = view2.findViewById(R.id.txt_numFrap_sar);
        PersonalServicio = view2.findViewById(R.id.txt_personal_servicio_sar);
        UnidadesServicio = view2.findViewById(R.id.txt_unidades_servicio_sar);
        DescripcionHechos = view2.findViewById(R.id.txt_desc_sar);
        CantidadPacientes = view2.findViewById(R.id.txt_cant_pac_sar);
        TipoPaciente = view2.findViewById(R.id.txt_tipo_paciente);
        TrasladoPaciente = view2.findViewById(R.id.txt_traspac_sar);
        HospitalTraslado = view2.findViewById(R.id.hosp_trassar);
        AutoridadesPublicas = view2.findViewById(R.id.autoridadespubs);
        AutoridadesCrm = view2.findViewById(R.id.autoridades_crm);
        Observaciones = view2.findViewById(R.id.txt_observaciones_Sar);
        btn_Save = view2.findViewById(R.id.btn_atras);
        btn_Finish = view2.findViewById(R.id.button_finish);

        arrayHospital = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Hospital_Traslado));
        arrayAutoridadesPub = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Autoridadpub));
        arrayAutoridadesCRM = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.autoridades_crm));

        arrayHospital.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAutoridadesPub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAutoridadesCRM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        HospitalTraslado.setAdapter(arrayHospital);
        AutoridadesPublicas.setAdapter(arrayAutoridadesPub);
        AutoridadesCrm.setAdapter(arrayAutoridadesCRM);

        if (stateConstructor) {

            initOld();
        }

        btn_Save.setOnClickListener(view -> {
          if (stateConstructor){
              onUpdate(false);
          }else{
              onSave();
          }
        });

        btn_Finish.setOnClickListener(view -> onUpdate(true));
    }

    private void onSave() {
        Map<String, Object> mapsar = new HashMap<>();

        String HT = HospitalTraslado.getSelectedItem().toString();
        String AP = AutoridadesPublicas.getSelectedItem().toString();
        String AC = AutoridadesCrm.getSelectedItem().toString();
        String HC = HoraConclusion.getText().toString().trim();
        String NF = NumeroFrap.getText().toString().trim();
        String PS = PersonalServicio.getText().toString().trim();
        String US = UnidadesServicio.getText().toString().trim();
        String DH = DescripcionHechos.getText().toString().trim();
        String CP = CantidadPacientes.getText().toString().trim();
        String TP = TipoPaciente.getText().toString().trim();
        String TSP = TrasladoPaciente.getText().toString().trim();
        String OB = Observaciones.getText().toString().trim();

        mapsar.put(HOSPITAL_TRANSFER, HT);
        mapsar.put(PUBLIC_AUTHORITIES, AP);
        mapsar.put(CRM_AUTHORITIES, AC);
        mapsar.put(FINISH_HOUR, HC);
        mapsar.put(FRAP_NUMBER, NF);
        mapsar.put(SERVICE_PERSONAL, PS);
        mapsar.put(SERVICE_UNITS, US);
        mapsar.put(ACTS_DESCRIPTIONS, DH);
        mapsar.put(NUMBER_PATIENTS, CP);
        mapsar.put(TYPE_PATIENTS, TP);
        mapsar.put(PATIENT_TRANSFER, TSP);
        mapsar.put(OBSERVATIONS, OB);
        mapsar.put(STATE, false);

    FirebaseDatabase.getInstance().getReference().child(SARS).push().setValue(mapsar);

    if (stateAdmin){
        getFragmentManager().beginTransaction().replace(R.id.container_sar, new ListFragment(args)).commit();
    }else {
        getFragmentManager().beginTransaction().replace(R.id.container_sar, new ListFragment(args)).commit();
    }
    }

    private void initOld(){
        String HT = HospitalTraslado.getSelectedItem().toString();
        String AP = AutoridadesPublicas.getSelectedItem().toString();
        String AC = AutoridadesCrm.getSelectedItem().toString();
        String HC = HoraConclusion.getText().toString().trim();
        String NF = NumeroFrap.getText().toString().trim();
        String PS = PersonalServicio.getText().toString().trim();
        String US = UnidadesServicio.getText().toString().trim();
        String DH = DescripcionHechos.getText().toString().trim();
        String CP = CantidadPacientes.getText().toString().trim();
        String TP = TipoPaciente.getText().toString().trim();
        String TSP = TrasladoPaciente.getText().toString().trim();
        String OB = Observaciones.getText().toString().trim();

    }

    private void onUpdate(Boolean b){
        Map<String, Object> mapsar = new HashMap<>();

        String HT = HospitalTraslado.getSelectedItem().toString();
        String AP = AutoridadesPublicas.getSelectedItem().toString();
        String AC = AutoridadesCrm.getSelectedItem().toString();
        String HC = HoraConclusion.getText().toString().trim();
        String NF = NumeroFrap.getText().toString().trim();
        String PS = PersonalServicio.getText().toString().trim();
        String US = UnidadesServicio.getText().toString().trim();
        String DH = DescripcionHechos.getText().toString().trim();
        String CP = CantidadPacientes.getText().toString().trim();
        String TP = TipoPaciente.getText().toString().trim();
        String TSP = TrasladoPaciente.getText().toString().trim();
        String OB = Observaciones.getText().toString().trim();

        mapsar.put(HOSPITAL_TRANSFER, HT);
        mapsar.put(PUBLIC_AUTHORITIES, AP);
        mapsar.put(CRM_AUTHORITIES, AC);
        mapsar.put(FINISH_HOUR, HC);
        mapsar.put(FRAP_NUMBER, NF);
        mapsar.put(SERVICE_PERSONAL, PS);
        mapsar.put(SERVICE_UNITS, US);
        mapsar.put(ACTS_DESCRIPTIONS, DH);
        mapsar.put(NUMBER_PATIENTS, CP);
        mapsar.put(TYPE_PATIENTS, TP);
        mapsar.put(PATIENT_TRANSFER, TSP);
        mapsar.put(OBSERVATIONS, OB);
        mapsar.put(STATE, false);

        FirebaseDatabase.getInstance().getReference().child(SARS).child(sarData.getKey()).updateChildren(mapsar);


    }
}