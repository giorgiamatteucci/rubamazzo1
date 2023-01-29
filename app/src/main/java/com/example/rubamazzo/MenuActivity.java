package com.example.rubamazzo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MenuActivity extends AppCompatActivity {

    Button btnRegole, btnGioca, btnCrea, btnClassifica, btnLogout;
    TextView tvUser;
    int npartite, nvittorie;
    DatabaseReference dbRefGiocatore = FirebaseDatabase.getInstance().getReferenceFromUrl("https://rubamazzo-735b7-default-rtdb.firebaseio.com/Giocatore/"+FirebaseAuth.getInstance().getCurrentUser().getUid());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tvUser = findViewById(R.id.tvUser);

        dbRefGiocatore.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Giocatore giocatore = Utils.getGiocatoreFromHashMap((HashMap) snapshot.getValue());
                tvUser.setText(giocatore.getUsername());
                npartite = snapshot.child("npartite").getValue(Integer.class);
                nvittorie = snapshot.child("nvittorie").getValue(Integer.class);
                Log.d("TAGFINE","MenuActivity ---- npartite: "+ npartite+", nvittorie: "+ nvittorie);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnRegole = findViewById(R.id.btnRegole);
        btnGioca = findViewById(R.id.btnGioca);
        btnCrea = findViewById(R.id.btnCrea);
        btnClassifica = findViewById(R.id.btnClassifica);
        btnLogout = findViewById(R.id.btnLogout);

        btnRegole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, RegoleActivity.class));
                finish();
            }
        });

        btnGioca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //andrà ad attendere di essere aggiunto ad una partita già creata
                Intent i = new Intent(MenuActivity.this, AttesaActivity.class);
                i.putExtra("testo","in attesa di essere aggiunto ad una partita");
                startActivity(i);
                finish();
            }
        });

        btnCrea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verrà creata una partita e verrà messo in attesa che un altro giocatore venga aggiunto ad essa
                Intent i = new Intent(MenuActivity.this, AttesaActivity.class);
                i.putExtra("testo","in attesa di uno sfidante");
                i.putExtra("npartite",npartite);
                i.putExtra("nvittorie",nvittorie);
                startActivity(i);
                finish();
            }
        });

        btnClassifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ActivityClassifica.class);
                startActivity(i);
                finish();
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