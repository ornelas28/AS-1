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
import com.example.as.classes.adapters.SARAdapter;
import com.example.as.classes.database.ConstantsDataBase;
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

import static com.example.as.classes.database.ConstantsDataBase.NEW;

public class ListFragment extends Fragment implements SARAdapter.OnSARListener, SARAdapter.OnSarEditListener {

    private final String args;
    private List<SARData> listNew;
    private List<SARData> listOld;
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
        RecyclerView recyclerListOld = view.findViewById(R.id.list_old);
        RecyclerView recyclerListNew = view.findViewById(R.id.list_new);

        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance().getReference(ConstantsDataBase.SARS);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listNew= new ArrayList<>();
                listOld= new ArrayList<>();
                listKeysNew= new ArrayList<>();
                listKeysOld= new ArrayList<>();
                for(DataSnapshot keynode : snapshot.getChildren()){
                    SARData sarData = null;
                    sarData = keynode.getValue(SARData.class);

                    if (stateAdmin){
                        if (sarData.getState() == false) {
                            listNew.add(sarData);
                            listKeysNew.add(keynode.getKey());
                        }
                        else {
                            listOld.add(sarData);
                            listKeysOld.add(keynode.getKey());
                        }
                    }else{
                        if (sarData.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                            if (sarData.getState() == false) {
                                listNew.add(sarData);
                                listKeysNew.add(keynode.getKey());
                            }
                            else {
                                listOld.add(sarData);
                                listKeysOld.add(keynode.getKey());
                            }
                        }
                    }
                }
                SARAdapter sarAdapter = new SARAdapter(getContext(),listNew,ListFragment.this::onSARClick);
               /* DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL);
                dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));
                recyclerListNew.addItemDecoration(dividerItemDecoration);*/
                recyclerListNew.setAdapter(sarAdapter);
                recyclerListNew.setLayoutManager(new LinearLayoutManager
                        (getContext(),LinearLayoutManager.HORIZONTAL,false));
                SARAdapter sarAdapter1 = new SARAdapter(getContext(),listOld,ListFragment.this::OnSarEditClick);
                recyclerListOld.setAdapter(sarAdapter1);
                recyclerListOld.setLayoutManager(new LinearLayoutManager
                        (getContext(),LinearLayoutManager.HORIZONTAL,false));



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
           if (stateAdmin){
               getFragmentManager().beginTransaction()
                       .replace(R.id.container_sar, new PagerFragment(args,true)).commit();
           }else{
               getFragmentManager().beginTransaction()
                       .replace(R.id.container_sar, new PagerFragment(args)).commit();
           }
        });
    }

    @Override
    public void onSARClick(int position) {
        SARData sarData = listNew.get(position);
        sarData.setKey(listKeysNew.get(position));
        if (stateAdmin){
            getFragmentManager().beginTransaction().replace(R.id.container_sar,
                    new PagerFragment(args, sarData, NEW, true)).commit();
        }else{
            getFragmentManager().beginTransaction().replace(R.id.container_sar,
                    new PagerFragment(args, sarData, NEW, false)).commit();
        }


    }

    @Override
    public void OnSarEditClick(int position) {

    }
}