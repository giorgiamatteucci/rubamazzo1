package com.example.rubamazzo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView tvTitolo;
    Button btnRegistrati, btnAccedi;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){

            Intent i = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        //binding elementi layout con elementi java (oggetti)
        tvTitolo = findViewById(R.id.tvTitolo);

        btnRegistrati = findViewById(R.id.btnRegistratiMain);
        btnAccedi = findViewById(R.id.btnAccediMain);

        btnRegistrati.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, MenuActivity.class);
            //Intent i = new Intent(MainActivity.this, RegistrazioneActivity.class);
            startActivity(i);
        });

        btnAccedi.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, EntraActivity.class);
            startActivity(i);
        });

    }
}