package com.example.as;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import static com.example.as.classes.database.ConstantsDataBase.*;

public class UserMainActivity_C extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        CardView cardViewSar = findViewById(R.id.crd_sar);
        CardView cardViewExit = findViewById(R.id.crd_exit);
        CardView cardViewEmail = findViewById(R.id.crd_email);
        CardView cardViewMyAccount = findViewById(R.id.crd_micuenta);
        CardView cardViewRis = findViewById(R.id.crd_ris);
        CardView cardViewTelegram= findViewById(R.id.crd_telegram);
        CardView cardViewfaqs=findViewById(R.id.crd_faqs);


        cardViewSar.setOnClickListener(this);
        cardViewExit.setOnClickListener(this);
        cardViewEmail.setOnClickListener(this);
        cardViewMyAccount.setOnClickListener(this);
        cardViewRis.setOnClickListener(this);
        cardViewTelegram.setOnClickListener(this);
        cardViewfaqs.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.crd_sar: {
                Intent intentSendActivity;
                intentSendActivity = new Intent(UserMainActivity_C.this, SendActivity.class);
                intentSendActivity.putExtra(CODE, SAR);
                startActivity(intentSendActivity);
                break;
            }
            case R.id.crd_exit: {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(UserMainActivity_C.this,SplashActivity.class));
                break;
            }
            case R.id.crd_email:
                startActivity(new Intent(UserMainActivity_C.this,
                        SendEmailActivity.class));
                finish();
                break;
            case R.id.crd_micuenta:
                startActivity(new Intent(UserMainActivity_C.this, MyAccountActivity.class));
                finish();
                break;
            case R.id.crd_telegram:{
                Uri url = Uri.parse("https://t.me/joinchat/AAAAAFSLKcXdjom0C2uFnA");
                Intent telegram = new Intent(Intent.ACTION_VIEW, url);
                PackageManager packageManager = getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(telegram, 0);
                boolean isIntentSafe = activities.size() > 0;

                if (isIntentSafe) {
                    startActivity(telegram);
                }
                break;
            }

            case R.id.crd_faqs:{
                startActivity(new Intent(UserMainActivity_C.this, FaqsActivity.class));
                finish();
                break;
            }
            case R.id.crd_ris: {
                Intent intentSendActivity;
                intentSendActivity = new Intent(UserMainActivity_C.this, SendActivity.class);
                intentSendActivity.putExtra(CODE, RIS);
                startActivity(intentSendActivity);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

    }

}
