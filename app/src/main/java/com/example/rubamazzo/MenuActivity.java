package com.example.rubamazzo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuActivity extends AppCompatActivity {

    Button btnGioca, btnCrea, btnClassifica, btnLogout;
    TextView textViewEmail;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        textViewEmail = findViewById(R.id.textViewEmail);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        /*
            S.E. capisco l'intento di questo controllo ma penso che se non sei loggato è impossibile arrivare a questa activity.
                 al momento ti direi di non eliminarlo magari ne parliamo insieme e vediamo se serve
         */
        if(user == null){
            Intent i = new Intent(getApplicationContext(), EntraActivity.class);
            startActivity(i);
            finish();
        }
        else{
            textViewEmail.setText(user.getEmail());
        }

        btnGioca = findViewById(R.id.btnGioca);
        btnCrea = findViewById(R.id.btnCrea);
        btnClassifica = findViewById(R.id.btnClassifica);
        btnLogout = findViewById(R.id.btnLogout);

        btnGioca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //andrà ad attendere di essere aggiunto ad una partita già creata

                Intent i = new Intent(MenuActivity.this, AttesaActivity.class);
                i.putExtra("testo","in attesa di essere aggiunto ad una partita");
                startActivity(i);
            }
        });

        btnCrea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //verrà creata una partita e verrà messo in attesa che un altro giocatore venga aggiunto ad essa
                Intent i = new Intent(MenuActivity.this, AttesaActivity.class);
                i.putExtra("testo","in attesa di uno sfidante");
                startActivity(i);
            }
        });


        // S.E. brava questa è una funzionalità che andava inserita
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(), EntraActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}