package com.example.as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.as.classes.ConstantsDataBase;
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

    private String name;
    private String lastName;
    private String email;
    private String confirmEmail;
    private String associateNumber;
    private String password;
    private String confirmPassword;
    private String delegation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_up);


        spinnerDelegation = findViewById(R.id.spinner_delegation);

        String[] arrayDelegation = new String[]{"Delegación:", "Ameca", "Atotonilco", "Autlán",
                "Capilla de Guadalupe", "Cd. Guzman", "Chapala", "Cihutlán",
                "El Grullo", "Encarnación de Díaz", "Guadalajara", "Jalostotitlán",
                "Jesús María", "Lagos de Moreno", "Ocotlán", "Pegueros", "Puerto Vallarta",
                "San Ignacio Cerro Gordo", "San José de Gracia", "San Juan de los Lagos",
                "Sayula", "Teocaltiche", "Tepatitlán", "Tequila", "Tomatlán", "Valle de Guadalupe",
                "Villa Hidalgo", "Yahualica", "Zapotlanejo", "Estatal"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, arrayDelegation);

        textNameReg =findViewById(R.id.textName);
        textLastName =findViewById(R.id.text_last_name);
        textConfirmEmail =findViewById(R.id.text_confirm_email);
        textAssociateNumber =findViewById(R.id.text_associate_number);
        textConfirmPassword =findViewById(R.id.text_confirm_password);
        textEmail = findViewById(R.id.text_email);
        textPassword =findViewById(R.id.text_password);
        Button buttonLogUp = findViewById(R.id.button_logUp);

        firebaseAuth =FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        spinnerDelegation.setAdapter(adapter);
        buttonLogUp.setOnClickListener(v -> logUp());
    }

    private void logUp(){
        name = textNameReg.getText().toString();
        lastName = textLastName.getText().toString();
        email = textEmail.getText().toString();
        confirmEmail = textConfirmEmail.getText().toString();
        associateNumber = textAssociateNumber.getText().toString();
        password = textPassword.getText().toString();
        confirmPassword = textConfirmPassword.getText().toString();
        delegation = spinnerDelegation.getSelectedItem().toString();

        if (validateFields()) {
            if (validateEmails()) {
                if (validatePass()){
                    if (email.endsWith("@cruzrojamexicana.org.mx")) {
                        createUsser();
                    } else {
                        Toast.makeText(this, "Ese correo no es valido", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    }

    private void createUsser() {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        // _______________________________________
                        Map<String, Object> registroMap = new HashMap<>();

                        registroMap.put(NAME, name);
                        registroMap.put(LAST_NAME, lastName);
                        registroMap.put(EMAIL, email);
                        registroMap.put(ASSOCIATE_NUMBER, associateNumber);
                        registroMap.put(PASSWORD, password);
                        registroMap.put(DELEGATION, delegation);
                        registroMap.put(TYPE, USER);

                        databaseReference.child(USERS).push().setValue(registroMap);
                        Toast.makeText(LogUpActivity.this,
                                "Usuario creado con exito", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogUpActivity.this,
                                LogInActivity_C.class));
                    }else{
                        Toast.makeText(LogUpActivity.this,
                                "Error al crear el usuario", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
            if( e instanceof FirebaseAuthUserCollisionException){
                Toast.makeText(LogUpActivity.this, "Este Usuario actualmente se encuentra en uso",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(LogUpActivity.this, "Error: "
                        + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validatePass() {
        boolean state = false;
        if (password.equals(confirmPassword)) {
            state = true;
        } else {
            Toast.makeText(this, "Las contraseñas no son iguales", Toast.LENGTH_SHORT).show();
        }
        return state;
    }

    private boolean validateEmails() {
        boolean state = false;
        if (email.equals(confirmEmail)) {
            state = true;
        } else {
            Toast.makeText(this, "Los correos deben ser iguales", Toast.LENGTH_SHORT).show();
        }
        return state;
    }

    private boolean validateFields() {
        boolean state = true;
        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || confirmEmail.isEmpty()
        || associateNumber.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
        || delegation.isEmpty()) {
            state = false;
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
        return state;
    }

}