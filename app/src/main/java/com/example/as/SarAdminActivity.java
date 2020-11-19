package com.example.as;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.os.Bundle;

import com.example.as.classes.database.ConstantsDataBase;
import com.example.as.fragments.ListFragment;

import static com.example.as.classes.database.ConstantsDataBase.CODE;
import static com.example.as.classes.database.ConstantsDataBase.SAR;

public class SarAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sar_admin);
        String args = getIntent().getExtras().getString(CODE);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_sar, new ListFragment(args, true)).commit();

    }
}
