package com.example.as;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.as.classes.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity_C extends AppCompatActivity {
EditText txt_correo, txt_pass;
Button btn_iniciar;
private FirebaseAuth mAuth;

private FirebaseAuth.AuthStateListener authStateListener;
private DatabaseReference mDatabase;
private String Tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_correo=findViewById(R.id.txt_user_name_login);
        txt_pass=findViewById(R.id.txt_password_login);
        btn_iniciar=findViewById(R.id.btn_iniciarsesion_login);
        mAuth=FirebaseAuth.getInstance();
        btn_iniciar.setOnClickListener(v -> logIn() );
        mDatabase= FirebaseDatabase.getInstance().getReference();
    }

    private void logIn () {

    }

    private void iniciar_Sesion() {

        final String email, passw;

        Toast.makeText(this, "Sospecha", Toast.LENGTH_SHORT).show();
        if (!check()) {
            Toast.makeText(this, "Datos incorrectos, ingresa unos datos validos", Toast.LENGTH_SHORT).show();
        }
        else {
            email = txt_correo.getText().toString();
            passw = txt_pass.getText().toString();

            mDatabase.child("Usuarios").addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (final DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        mDatabase.child("Usuarios").child(dataSnapshot.getKey())
                                .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                try {
                                    UserData tipo = dataSnapshot.getValue(UserData.class);
                                    String ttipo = tipo.getType();
                                    Tipo = ttipo;
                                    Toast.makeText(LogInActivity_C.this, "Tipo: "
                                            + ttipo, Toast.LENGTH_SHORT).show();
                                } catch (Exception ignored) { }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            mAuth.signInWithEmailAndPassword(email, passw).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (Tipo.equals("Usuarios")) {
                        Toast.makeText(LogInActivity_C.this, "!Bienvenido:\n " + email + "!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogInActivity_C.this, UserMainActivity_C.class));
                    } else if (Tipo.equals("Administrador")) {
                        Toast.makeText(LogInActivity_C.this, "!Bienvenido:\n " + email + "!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogInActivity_C.this, AdminMainActivity.class));
                    }
                }
            }).addOnFailureListener(e -> {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(LogInActivity_C.this, "Contraseña Invalida, verifica e intenta de nuevo", Toast.LENGTH_LONG).show();
                } else if (e instanceof FirebaseAuthInvalidUserException) {
                    Toast.makeText(LogInActivity_C.this, "Error, el usuario no existe en nuestros registros", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LogInActivity_C.this, " " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

//mmenu 3 puntos
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.opciones_login, menu);
        return true;
    }
//Selección de opciones del menu 3 puntos
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id== R.id.Item1){
            Toast.makeText(this, "Opcion 1: olvide la contraseña", Toast.LENGTH_SHORT).show();
        }
        else if (id==R.id.Item2){
            Toast.makeText(this, "Opcion 2: algo más", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
//Intent para pasar al activity del registro (onClic del boton registrarme)
    public void registro(View view){
        startActivity(new Intent(this, LogUpActivity.class));
    }
    private boolean check() {
        try {
            String cor, pas, confpass;
            cor = txt_correo.getText().toString();
            pas = txt_pass.getText().toString();

            if (cor.isEmpty()) {

                Toast.makeText(this, "Por favor ingrese un correo", Toast.LENGTH_SHORT).show();
                return false;
            } else if (pas.isEmpty()) {

                Toast.makeText(this, "Por favor ingrese una contraseña", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(this, "error:" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
            return true;
    }

}
