package com.example.as;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import static com.example.as.classes.database.ConstantsDataBase.CODE;
import static com.example.as.classes.database.ConstantsDataBase.SAR;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_administrado);

        CardView singOut = findViewById(R.id.sing_out);
        CardView sar= findViewById(R.id.crd_sar);

        sar.setOnClickListener( v -> {
            Intent intent = new Intent(AdminMainActivity.this, SarAdminActivity.class);
            intent.putExtra(CODE, SAR);
            startActivity(intent);
        });

        singOut.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(AdminMainActivity.this, SplashActivity.class));
        });



    }
}
