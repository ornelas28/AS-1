package com.example.as;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    EditText txt_nombre, txt_apellido, txt_correo, txt_confcorreo, txt_numasociado, txt_pass, txt_confpass;
    Spinner sp_delegaciones;
    Button btn_registro;
    private Spinner spinner;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        spinner = (Spinner)findViewById(R.id.delegacion_reg);

        String [] opcionesdel = {"Delegación:","Ameca","Atotonilco","Autlán","Capilla de Guadalupe","Cd. Guzman","Chapala","Cihutlán","El Grullo","Encarnación de Díaz","Guadalajara","Jalostotitlán","Jesús María","Lagos de Moreno","Ocotlán","Pegueros","Puerto Vallarta","San Ignacio Cerro Gordo","San José de Gracia","San Juan de los Lagos","Sayula","Teocaltiche","Tepatitlán","Tequila","Tomatlán","Valle de Guadalupe","Villa Hidalgo","Yahualica","Zapotlanejo","Estatal"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opcionesdel);
        spinner.setAdapter(adapter);

        txt_nombre=findViewById(R.id.txt_nombreReg);
        txt_apellido=findViewById(R.id.txt_apellidoReg);

        txt_confcorreo=findViewById(R.id.txt_confcorreoReg);
        txt_numasociado=findViewById(R.id.txt_numasociadoReg);

        txt_confpass=findViewById(R.id.txt_confpassReg);
        sp_delegaciones=findViewById(R.id.delegacion_reg);
        txt_correo = findViewById(R.id.txt_correoReg);
        txt_pass=findViewById(R.id.txt_passReg);
        btn_registro=findViewById(R.id.btn_registrarme);
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        authStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user !=null){

                    Toast.makeText(Registro.this, "Identificado como: "+ user.getEmail(), Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(Registro.this, "Sin identificar", Toast.LENGTH_SHORT).show();
                }
            }
        };


        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateUSer();
            }
        });



    }

    private void CreateUSer(){
        String nombre, apellido, correo, confcorreo, numeroasociado,contraseña, confcontraseña,delegacion,dominio;

        nombre = txt_nombre.getText().toString();
        apellido = txt_apellido.getText().toString();
        correo = txt_correo.getText().toString();
        confcorreo = txt_confcorreo.getText().toString();
        numeroasociado = txt_numasociado.getText().toString();
        contraseña = txt_pass.getText().toString();
        confcontraseña = txt_confpass.getText().toString();
        delegacion = sp_delegaciones.getSelectedItem().toString();

        if(nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty()|| confcorreo.isEmpty() || numeroasociado.isEmpty() || contraseña.isEmpty() || confcontraseña.isEmpty() || delegacion.equals("Delegación:")){

            Toast.makeText(this, "¡Campos vacios! verifica que todos los campos contengan información", Toast.LENGTH_SHORT).show();

        }else if(!correo.equals(confcorreo)){
            Toast.makeText(this, "Los correos no coincide, favor de verificar e intentar de nuevo", Toast.LENGTH_SHORT).show();
        }else if(!contraseña.equals(confcontraseña)){
            Toast.makeText(this, "Las contraseñas no coinciden, favor de verificar e intentar de nuevo", Toast.LENGTH_SHORT).show();
        }else if(!correo.endsWith("@cruzrojamexicana.org.mx")){
            Toast.makeText(this, "El correo ingresado no es un correo institucional, verificalo e intenta de nuevo", Toast.LENGTH_SHORT).show();
        }
        else{

            //correo=txt_correo.getText().toString();
            //contraseña=txt_pass.getText().toString();

            mAuth.createUserWithEmailAndPassword(correo, contraseña).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Registro.this, "Usuario creado con exito", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Registro.this, MainActivity.class));
                    }else{
                        Toast.makeText(Registro.this, "Error al crear el usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if( e instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(Registro.this, "Este Usuario actualmente se encuentra en uso", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Registro.this, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        Map<String, Object> registroMap = new HashMap<>();

        registroMap.put("Nombre:", nombre);
        registroMap.put("Apellido:", apellido);
        registroMap.put("Correo:", correo);
        registroMap.put("Correo confirmado:", confcorreo);
        registroMap.put("Numero de Asociado:", numeroasociado);
        registroMap.put("Contraseña:", contraseña);
        registroMap.put("Contraseña Confirmada:", confcontraseña);
        registroMap.put("Delegacion:", delegacion);
        registroMap.put("Tipo:", "Usuario");


        mDatabase.child("Asociados").child("Usuarios").push().setValue(registroMap);


    }



    private boolean checkFields(){

        String correocheck, passcheck;
        correocheck= txt_correo.getText().toString();
        passcheck=txt_pass.getText().toString();

        if(correocheck.isEmpty()){
            Toast.makeText(this, "Por favor ingrese un correo", Toast.LENGTH_SHORT).show();
            return false;
        }else if(passcheck.isEmpty()){
            Toast.makeText(this, "Por favor ingrese una contraseña", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

   }





}
