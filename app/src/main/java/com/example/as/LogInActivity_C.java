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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.as.classes.ConstantsDataBase.*;
import static com.example.as.classes.ConstantsDataBase.USERS;

public class LogInActivity_C extends AppCompatActivity {

    EditText txt_correo, txt_pass;
    Button btn_iniciar;
    private FirebaseAuth mAuth;

    private List<UserData> userDataList = new ArrayList<>();

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

        authStateListener = firebaseAuth -> {
                FirebaseUser user;
                user = mAuth.getCurrentUser();
                if (null != user) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference(USERS);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String type = "";
                            List<String> keys = new ArrayList<>();
                            userDataList.clear();
                            for(DataSnapshot keyNode : snapshot.getChildren()){
                                keys.add(keyNode.getKey());
                                UserData userData = keyNode.getValue(UserData.class);
                                userDataList.add(userData);

                                String email = userData.getEmail();
                                Toast.makeText(LogInActivity_C.this, "Email 1 " + email, Toast.LENGTH_SHORT).show();
                                String email2 = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                                if (email.equals(email2)) {
                                    type = userData.getType();
                                    if (type.equals(ADMIN)) {
                                        startActivity(new Intent(LogInActivity_C.this, AdminMainActivity.class));
                                    } else {
                                        startActivity(new Intent(LogInActivity_C.this, UserMainActivity_C.class));
                                    }
                                    Toast.makeText(LogInActivity_C.this, "type: " + type, Toast.LENGTH_SHORT).show();
                                }

                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }

    private void logIn () {
        final String email;
        final String passw;
        email = txt_correo.getText().toString();
        passw = txt_pass.getText().toString();
        mAuth.signInWithEmailAndPassword(email, passw).addOnFailureListener(e ->
                Toast.makeText(LogInActivity_C.this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.opciones_login, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id== R.id.Item1){
            //Toast.makeText(this, "Opcion 1: olvide la contraseña", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            Toast.makeText(this, "Seción cerrada", Toast.LENGTH_SHORT).show();
        }
        else if (id==R.id.Item2){
            //Toast.makeText(this, "Opcion 2: algo más", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "User: " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

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
