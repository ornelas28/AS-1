package com.example.as.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.as.R;
import com.example.as.classes.adapters.SARAdapter;
import com.example.as.classes.database.ConstantsDataBase;
import com.example.as.classes.database.SARData;
import com.example.as.classes.database.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment implements SARAdapter.OnSARListener {

    private final String args;

    public ListFragment(String args) {
        this.args = args;
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
        RecyclerView listOld = view.findViewById(R.id.list_old);
        RecyclerView listNew = view.findViewById(R.id.list_new);

        FirebaseDatabase.getInstance().getReference(ConstantsDataBase.SARS).addValueEventListener
                (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<SARData> list = new ArrayList<>();
                for(DataSnapshot keynode : snapshot.getChildren()){
                    SARData sarData= keynode.getValue(SARData.class);

                    if (sarData.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        list.add(sarData);
                    }
                }
                SARAdapter sarAdapter = new SARAdapter(getContext(),list,ListFragment.this);
                listNew.setAdapter(sarAdapter);
                listNew.setLayoutManager(new LinearLayoutManager
                        (getContext(),LinearLayoutManager.VERTICAL,false));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (args.equals(ConstantsDataBase.SAR)) {
            textTitle.setText(R.string.reporte_de_servicio_de_alto_riesgo_sar);
            buttonAdd.setText("Agregar nuevo SAR");
        } else {
            buttonAdd.setText("Agregar nuevo RIS");
        }

        buttonAdd.setOnClickListener(v -> {
            getFragmentManager().beginTransaction()
                    .replace(R.id.content, new PagerFragment(args)).commit();
        });
    }

    @Override
    public void onSARClick(int position) {

    }
}