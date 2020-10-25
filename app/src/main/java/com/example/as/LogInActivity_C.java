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

    EditText textEmail, textPass;
    Button buttonLogIn;
    private FirebaseAuth mAuth;

    private final List<UserData> userDataList = new ArrayList<>();

    private FirebaseAuth.AuthStateListener authStateListener;

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textEmail =findViewById(R.id.txt_user_name_login);
        textPass =findViewById(R.id.txt_password_login);
        buttonLogIn =findViewById(R.id.btn_iniciarsesion_login);
        mAuth=FirebaseAuth.getInstance();
        buttonLogIn.setOnClickListener(v -> logIn() );
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        authStateListener = firebaseAuth -> {
            FirebaseUser user;
            user = mAuth.getCurrentUser();
            if (null != user) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference(USERS);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String type;
                        List<String> keys = new ArrayList<>();
                        userDataList.clear();
                        for(DataSnapshot keyNode : snapshot.getChildren()){
                            keys.add(keyNode.getKey());
                            UserData userData = keyNode.getValue(UserData.class);
                            userDataList.add(userData);

                            String email = userData.getEmail();
                            Toast.makeText(LogInActivity_C.this, "Email 1 " + email,
                                    Toast.LENGTH_SHORT).show();
                            String email2 = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                            if (email.equals(email2)) {
                                type = userData.getType();
                                if (type.equals(ADMIN)) {
                                    startActivity(new Intent(LogInActivity_C.this,
                                            AdminMainActivity.class));
                                } else {
                                    startActivity(new Intent(LogInActivity_C.this,
                                            UserMainActivity_C.class));
                                }
                                Toast.makeText(LogInActivity_C.this, "type: " + type,
                                        Toast.LENGTH_SHORT).show();
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
        String email = textEmail.getText().toString();
        String passw = textPass.getText().toString();
        if (email.isEmpty() && passw.isEmpty()) {
            Toast.makeText(this, "Debe introducir la contraseña y el correo", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, passw).addOnFailureListener(e ->
                    Toast.makeText(LogInActivity_C.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.opciones_login, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.Item1) {
            Toast.makeText(this, "Opcion 1: olvide la contraseña", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.Item2) {
            Toast.makeText(this, "Opcion 2: algo más", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void logUp(View view){
        startActivity(new Intent(this, LogUpActivity.class));
    }

}
