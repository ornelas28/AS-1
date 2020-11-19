package com.example.as;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.as.classes.Delegations;
import com.example.as.classes.database.ConstantsDataBase;
import com.example.as.classes.database.SARData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.as.classes.database.ConstantsDataBase.*;
import static java.security.AccessController.getContext;


public class AdminAnalyticsActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.R)
    List<Delegations> delegationList;
    List<String> delegations;
    TextView terminal;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_analytics);
        terminal = findViewById(R.id.add_text);
        terminal.setMovementMethod(ScrollingMovementMethod.getInstance());

        delegationList = new ArrayList<>();

        delegations = List.of("Ameca", "Atotonilco", "Autlán", "Capilla de Guadalupe",
                "Cd. Guzmán", "Chapala", "Cihuatlán", "El Grullo", "Estatal", "Encarnación de Díaz",
                "Guadalajara", "Jalostotitlán", "Jesús María", "Lagos de Moreno", "Ocotlán",
                "Pegueros", "Puerto Vallarta", "San José de Gracia", "San Juan de los Lagos",
                "Sayula", "Teocaltiche", "Tepatitlán", "Tequila", "Tomatlán", "Valle de Guadalupe",
                "Villa Hidalgo", "Yahualica", "Zapotlanejo");

        ArrayAdapter<String> arrayMoths = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.months));

        Spinner spinnerMonths = findViewById(R.id.spinner_month);
        spinnerMonths.setAdapter(arrayMoths);

        spinnerMonths.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                setDelegations(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void setDelegations(String toString) {
        terminal.setText("");
        delegationList.clear();
        for (int i = 0; i < delegations.size(); i ++) {
            delegationList.add(new Delegations(delegations.get(i)));
        }

        FirebaseDatabase.getInstance().getReference(SARS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SARData sarData = dataSnapshot.getValue(SARData.class);
                    for (int i = 0; i < delegationList.size(); i ++) {
                        if (sarData.getDelegation().equals(delegationList.get(i).getDelegations())) {

                           String string=sarData.getDate();

                               if (string.contains(toString.toLowerCase())) {
                                   delegationList.get(i).incrementSar();
                              }


                        }
                    }
                }

                for (int i = 0; i < delegationList.size(); i++) {
                    SpannableStringBuilder spn = new SpannableStringBuilder(delegationList.get(i).getDelegations());
                    spn.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark)), 0,
                            spn.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    terminal.append(spn);
                    terminal.append("\n");
                    terminal.append("Sar:" + delegationList.get(i).getSAR() + "\n");
                    terminal.append("Ris:" + delegationList.get(i).getRIS() + "\n \n");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
