package com.example.rubamazzo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    TextView tvTitolo;
    Button btnRegistrati, btnAccedi;
    private FirebaseAnalytics analytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        analytics = FirebaseAnalytics.getInstance(this);
        //binding elementi layout con elementi java (oggetti)
        tvTitolo = findViewById(R.id.tvTitolo);

        btnRegistrati = findViewById(R.id.btnRegistratiMain);
        btnAccedi = findViewById(R.id.btnAccediMain);

        btnRegistrati.setOnClickListener(v -> {
            // S.E. non lo conosco e domani ti chiederò a cosa serve (per mia curiosità)
            analytics.logEvent("button_clicked", null);
            Intent i = new Intent(MainActivity.this, RegistrazioneActivity.class);
            startActivity(i);
        });

        btnAccedi.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, EntraActivity.class);
            startActivity(i);
        });

    }
}