package com.example.as;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.as.classes.database.ConstantsDataBase;
import com.example.as.classes.database.SARData;
import com.example.as.classes.database.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.as.classes.database.ConstantsDataBase.ASSOCIATE_NUMBER;
import static com.example.as.classes.database.ConstantsDataBase.DELEGATION;
import static com.example.as.classes.database.ConstantsDataBase.EMAIL;
import static com.example.as.classes.database.ConstantsDataBase.ID;
import static com.example.as.classes.database.ConstantsDataBase.LAST_NAME;
import static com.example.as.classes.database.ConstantsDataBase.NAME;
import static com.example.as.classes.database.ConstantsDataBase.PASSWORD;
import static com.example.as.classes.database.ConstantsDataBase.SARS;
import static com.example.as.classes.database.ConstantsDataBase.USERS;

public class MyAccountActivity extends AppCompatActivity {

    private final List<UserData> userDataList = new ArrayList<>();
    private UserData userData;


    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        Button buttonBack = findViewById(R.id.button_back);
        Button buttonChange = findViewById(R.id.button_change);
        Button buttonSave = findViewById(R.id.button_save);
        EditText Name = findViewById(R.id.txt_Nombre_micuenta);
        EditText LastName = findViewById(R.id.txt_apellido_micuenta);
        EditText Email = findViewById(R.id.txt_correo_micuenta);
        EditText Pass = findViewById(R.id.txt_contraseña_micuenta);
        EditText Delegation = findViewById(R.id.txt_delegacion_micuenta);


        Name.setFocusable(false);
        Name.setEnabled(false);
        LastName.setFocusable(false);
        LastName.setEnabled(false);
        Email.setFocusable(false);
        Email.setEnabled(false);
        Pass.setFocusable(false);
        Pass.setEnabled(false);
        Delegation.setEnabled(false);
        Delegation.setFocusable(false);
        buttonSave.setEnabled(false);


        databaseReference=FirebaseDatabase.getInstance().getReference(USERS);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> keys = new ArrayList<>();
                userDataList.clear();
                for(DataSnapshot key : snapshot.getChildren()) {
                    keys.add(key.getKey());
                    UserData userData = key.getValue(UserData.class);
                    if(userData.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        userDataList.add(userData);

                        String nombre = userData.getName();
                        String apeliido= userData.getLastName();
                        String correo = userData.getEmail();
                        String na= userData.getAssociateNumber();
                        String delegacion= userData.getDelegation();

                        Name.setText(nombre);
                        LastName.setText(apeliido);
                        Email.setText(correo);
                        Pass.setText(na);
                        Delegation.setText(delegacion);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        

        buttonBack.setOnClickListener(view -> {
            startActivity(new Intent(MyAccountActivity.this, UserMainActivity_C.class));
            finish();
        });

        buttonChange.setOnClickListener(v -> {
            buttonSave.setEnabled(true);
            Name.setEnabled(true);
            Name.setFocusableInTouchMode(true);
            LastName.setEnabled(true);
            LastName.setFocusableInTouchMode(true);
            Delegation.setEnabled(true);
            Delegation.setFocusableInTouchMode(true);


        });


        buttonSave.setOnClickListener(v -> {

      Map<String, Object> mapDatos = new HashMap<>();
       String nombre= Name.getText().toString().trim();
       String apellido= LastName.getText().toString().trim();
       String delegacion= Delegation.getText().toString().trim();

       mapDatos.put(NAME, nombre);
       mapDatos.put(LAST_NAME, apellido);
       mapDatos.put(DELEGATION, delegacion);

       FirebaseDatabase.getInstance().getReference().child(USERS).child(FirebaseAuth.getInstance()
               .getCurrentUser().getProviderId()).updateChildren(mapDatos);




    });

    }



}
