package com.example.as.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.as.R;
import com.example.as.classes.adapters.RISAdapter;
import com.example.as.classes.adapters.SARAdapter;
import com.example.as.classes.database.ConstantsDataBase;
import com.example.as.classes.database.RISData;
import com.example.as.classes.database.SARData;
import com.example.as.classes.database.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.as.classes.database.ConstantsDataBase.*;
import static com.example.as.classes.database.ConstantsDataBase.NEW;
import static com.example.as.classes.database.ConstantsDataBase.SAR;

public class ListFragment extends Fragment implements SARAdapter.OnSARListener,
        SARAdapter.OnSarEditListener, RISAdapter.OnRisEditListener, RISAdapter.OnRisListener {

    private RecyclerView recyclerListOld;
    private RecyclerView recyclerListNew;

    private final String args;
    private List<SARData> listNewSar;
    private List<SARData> listOldSar;
    private List<RISData> listNewRIS;
    private List<RISData> listOldRIS;
    private List<String> listKeysNew;
    private List<String> listKeysOld;
    private Boolean stateAdmin=false;



    public ListFragment(String args) {
        this.args = args;
    }

    public ListFragment(String args, Boolean stateAdmin){
        this.args=args;
        this.stateAdmin=stateAdmin;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        TextView textTitle =  view.findViewById(R.id.text_title);
        Button buttonAdd = view.findViewById(R.id.button_add);
        recyclerListOld = view.findViewById(R.id.list_old);
        recyclerListNew = view.findViewById(R.id.list_new);

        if (args.equals(SAR)) {
            initSar();
            textTitle.setText(R.string.reporte_de_servicio_de_alto_riesgo_sar);
            buttonAdd.setText("Agregar nuevo SAR");
        } else {
            initRis();
            textTitle.setText("RIS");
            buttonAdd.setText("Agregar nuevo RIS");
        }

        buttonAdd.setOnClickListener(v -> {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_sar, new PagerFragment(args, stateAdmin)).commit();
        });
    }

    private void initSar() {
        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance().getReference(SARS);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listNewSar = new ArrayList<>();
                listOldSar = new ArrayList<>();
                listKeysNew= new ArrayList<>();
                listKeysOld= new ArrayList<>();
                for(DataSnapshot keyNode : snapshot.getChildren()){
                    SARData sarData = keyNode.getValue(SARData.class);

                    if (stateAdmin){
                        if (sarData.getState()) {
                            listOldSar.add(sarData);
                            listKeysOld.add(keyNode.getKey());
                        } else {
                            listNewSar.add(sarData);
                            listKeysNew.add(keyNode.getKey());
                        }
                    }else{
                        if (sarData.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                            if (sarData.getState()) {
                                listOldSar.add(sarData);
                                listKeysOld.add(keyNode.getKey());
                            } else {
                                listNewSar.add(sarData);
                                listKeysNew.add(keyNode.getKey());
                            }
                        }
                    }
                }
                SARAdapter sarAdapter = new SARAdapter(getContext(), listNewSar,ListFragment.this::onSARClick);
                recyclerListNew.setAdapter(sarAdapter);
                recyclerListNew.setLayoutManager(new LinearLayoutManager
                        (getContext(),LinearLayoutManager.HORIZONTAL,false));
                SARAdapter sarAdapter1 = new SARAdapter(getContext(), listOldSar,ListFragment.this::OnSarEditClick);
                recyclerListOld.setAdapter(sarAdapter1);
                recyclerListOld.setLayoutManager(new LinearLayoutManager
                        (getContext(),LinearLayoutManager.HORIZONTAL,false));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initRis() {
        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance().getReference(RISS);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RISAdapter risAdapter;
                RISAdapter risAdapter1;

                listNewRIS = new ArrayList<>();
                listOldRIS = new ArrayList<>();
                listKeysNew= new ArrayList<>();
                listKeysOld= new ArrayList<>();
                for(DataSnapshot keyNode : snapshot.getChildren()){
                    RISData risData = keyNode.getValue(RISData.class);
                    if (stateAdmin){
                        if (!risData.isState()) {
                            listNewRIS.add(risData);
                            listKeysNew.add(keyNode.getKey());
                        }
                        else {
                            listOldRIS.add(risData);
                            listKeysOld.add(keyNode.getKey());
                        }
                    }
                    else{
                        if (risData.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                            if (risData.isState()) {
                                listOldRIS.add(risData);
                                listKeysOld.add(keyNode.getKey());
                            } else {
                                listNewRIS.add(risData);
                                listKeysNew.add(keyNode.getKey());
                            }
                        }
                    }
                }
                risAdapter = new RISAdapter(getContext(), listNewRIS,
                        ListFragment.this::onRisClick);
                risAdapter1 = new RISAdapter(getContext(), listOldRIS,
                        ListFragment.this::OnRisEditClick);

                recyclerListNew.setAdapter(risAdapter);
                recyclerListOld.setAdapter(risAdapter1);

                recyclerListNew.setLayoutManager(new LinearLayoutManager
                        (getContext(),LinearLayoutManager.HORIZONTAL,false));
                recyclerListOld.setLayoutManager(new LinearLayoutManager
                        (getContext(),LinearLayoutManager.HORIZONTAL,false));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onSARClick(int position) {
        SARData sarData = listNewSar.get(position);
        sarData.setKey(listKeysNew.get(position));
        getFragmentManager().beginTransaction().replace(R.id.container_sar,
                new PagerFragment(args, sarData, NEW, stateAdmin)).commit();

    }

    @Override
    public void OnSarEditClick(int position) {

    }

    @Override
    public void onRisClick(int position) {
        RISData risData = listNewRIS.get(position);
        risData.setKey(listKeysNew.get(position));
        getFragmentManager().beginTransaction().replace(R.id.container_sar,
                new PagerFragment(args, risData, NEW, stateAdmin)).commit();
    }

    @Override
    public void OnRisEditClick(int position) {

    }
}