package com.example.rubamazzo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MenuActivity extends AppCompatActivity {

    Button btnGioca, btnCrea, btnClassifica, btnLogout;
    TextView tvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tvUser = findViewById(R.id.tvUser);
        tvUser.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        //HO PROVATO A STAMPARE L'USERNAME MA NON CI SONO RIUSCITA
        //tvUser.setText(FirebaseDatabase.getInstance().getReference("Giocatore/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/username").toString());
        //DatabaseReference user = FirebaseDatabase.getInstance().getReference("Giocatore/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/username");

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

        btnClassifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ActivityClassifica.class);
                startActivity(i);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}