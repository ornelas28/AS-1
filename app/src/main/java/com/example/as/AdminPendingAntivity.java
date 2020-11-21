package com.example.as;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.example.as.classes.adapters.SARAdapter;
import com.example.as.classes.database.ConstantsDataBase;
import com.example.as.classes.database.SARData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.as.classes.database.ConstantsDataBase.SARS;

public class AdminPendingAntivity extends AppCompatActivity implements SARAdapter.OnSARListener{
    Button action;
    RecyclerView recyclerView;
    List<SARData> sarDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pending);

        action=findViewById(R.id.button_action);
        recyclerView=findViewById(R.id.recycler);
        sarDataList= new ArrayList<>();
        Boolean stateButton=true;
        FirebaseDatabase.getInstance().getReference(SARS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot keynode: snapshot.getChildren()){
                    SARData sarData;
                    sarData=keynode.getValue(SARData.class);

                    if (!sarData.getState()){
                        sarDataList.add(sarData);
                    }
                }
                SARAdapter sarAdapter = new SARAdapter(AdminPendingAntivity.this,
                        sarDataList,AdminPendingAntivity.this);
                recyclerView.setAdapter(sarAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager
                        (AdminPendingAntivity.this, LinearLayoutManager.VERTICAL, false));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onSARClick(int position) {

    }
}
