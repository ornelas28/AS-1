package com.example.as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static com.example.as.classes.ConstantsDataBase.*;

public class LogUpActivity extends AppCompatActivity {

    private EditText textNameReg;
    private EditText textLastName;
    private EditText textEmail;
    private EditText textConfirmEmail;
    private EditText textAssociateNumber;
    private EditText textPassword;
    private EditText textConfirmPassword;
    private Spinner spinnerDelegation;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_up);

        String [] opcionesdel;
        ArrayAdapter <String> adapter;

        Spinner spinnerDelegation = findViewById(R.id.spinner_delegation);

        opcionesdel = new String[]{"Delegación:", "Ameca", "Atotonilco","Autlán",
                "Capilla de Guadalupe","Cd. Guzman","Chapala","Cihutlán",
                "El Grullo","Encarnación de Díaz","Guadalajara","Jalostotitlán",
                "Jesús María","Lagos de Moreno","Ocotlán","Pegueros","Puerto Vallarta",
                "San Ignacio Cerro Gordo","San José de Gracia","San Juan de los Lagos",
                "Sayula","Teocaltiche","Tepatitlán","Tequila","Tomatlán","Valle de Guadalupe",
                "Villa Hidalgo","Yahualica","Zapotlanejo","Estatal"};

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, opcionesdel);
        spinnerDelegation.setAdapter(adapter);

        textNameReg =findViewById(R.id.textName);
        textLastName =findViewById(R.id.text_last_name);
        textConfirmEmail =findViewById(R.id.text_confirm_email);
        textAssociateNumber =findViewById(R.id.text_associate_number);
        textConfirmPassword =findViewById(R.id.text_confirm_password);
        this.spinnerDelegation =findViewById(R.id.spinner_delegation);
        textEmail = findViewById(R.id.text_email);
        textPassword =findViewById(R.id.text_password);
        Button buttonLogUp = findViewById(R.id.button_logUp);

        firebaseAuth =FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        buttonLogUp.setOnClickListener(v -> CreateUSer());

    }

    private void CreateUSer(){
        String name;
        String lastName;
        String email;
        String confirmEmail;
        String associateNumber;
        String password;
        String confirmPassword;
        String delegation;

        name = textNameReg.getText().toString();
        lastName = textLastName.getText().toString();
        email = textEmail.getText().toString();
        confirmEmail = textConfirmEmail.getText().toString();
        associateNumber = this.textAssociateNumber.getText().toString();
        password = textPassword.getText().toString();
        confirmPassword = textConfirmPassword.getText().toString();
        delegation = spinnerDelegation.getSelectedItem().toString();

        if(name.isEmpty() || lastName.isEmpty() || email.isEmpty() || confirmEmail.isEmpty()
                || associateNumber.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
                || delegation.equals(DELEGATION)) {
            Toast.makeText(this,
                    "¡Campos vacios! verifica que todos los campos contengan información",
                    Toast.LENGTH_SHORT).show();
        }
        if(!email.equals(confirmEmail)) {
            Toast.makeText(this,
                    "Los correos no coincide, favor de verificar e intentar de nuevo",
                    Toast.LENGTH_SHORT).show();
        }
        if(!password.equals(confirmPassword)) {
            Toast.makeText(this,
                    "Las contraseñas no coinciden, favor de verificar e intentar de nuevo",
                    Toast.LENGTH_SHORT).show();
        }
        if(!email.endsWith("@cruzrojamexicana.org.mx")){
            Toast.makeText(this,
                    "El correo ingresado no es un correo institucional, verificalo e intenta de nuevo",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(LogUpActivity.this, "" +
                                    "Usuario creado con exito", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LogUpActivity.this,
                                    LogInActivity_C.class));
                        }else{
                            Toast.makeText(LogUpActivity.this,
                                    "Error al crear el usuario", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(e -> {
                if( e instanceof FirebaseAuthUserCollisionException){
                    Toast.makeText(LogUpActivity.this,
                            "Este Usuario actualmente se encuentra en uso",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LogUpActivity.this,
                            "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        Map<String, Object> registroMap = new HashMap<>();

        registroMap.put(NAME, name);
        registroMap.put(LAST_NAME, lastName);
        registroMap.put(EMAIL, email);
        registroMap.put(CONFIRM_EMAIL, confirmEmail);
        registroMap.put(ASSOCIATE_NUMBER, associateNumber);
        registroMap.put(PASSWORD, password);
        registroMap.put(CONFIRM_PASSWORD, confirmPassword);
        registroMap.put(DELEGATION, delegation);
        registroMap.put(TYPE, USER);

        databaseReference.child(ASSOCIATES)
                .child(USERS).push().setValue(registroMap);

    }

    private boolean checkFields(){
        String correocheck = textEmail.getText().toString();
        String passcheck = textPassword.getText().toString();

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
