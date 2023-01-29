package com.example.rubamazzo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AttesaActivity extends AppCompatActivity {

    TextView tvAttesaPartita;
    Button btnAnnulla;
    DatabaseReference dbReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://rubamazzo-735b7-default-rtdb.firebaseio.com/");
    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

    Partita partita;
    Mazzo mazzo = Mazzo.getIstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attesa);

        tvAttesaPartita = findViewById(R.id.tvAttesaPartita);
        tvAttesaPartita.setText(getIntent().getStringExtra("testo"));
        btnAnnulla = findViewById(R.id.btnGioca);
        btnAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu = new Intent(AttesaActivity.this, MenuActivity.class);
                startActivity(menu);
                finish();
            }
        });

        if (getIntent().getStringExtra("testo").equals("in attesa di essere aggiunto ad una partita")) {//CLIENT

            dbReference.child("Partita/").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildrenCount() == 0) {
                        Toast.makeText(AttesaActivity.this, "Ancora non ci sono partite a cui unirsi.", Toast.LENGTH_SHORT).show();
                    }else {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String idPartita = snapshot.getKey();
                            Partita partita = Utils.getPartitaFromHashMap((HashMap) snapshot.getValue());

                            if(partita.getIdClient().isEmpty() && !(id.equals(snapshot.child("idServer").getValue()))){
                                FirebaseDatabase.getInstance().getReference("Partita/" + idPartita+"/idClient").setValue(id);
                                Toast.makeText(AttesaActivity.this, "Ti sei unito alla partita.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AttesaActivity.this, ActivityGiocoClient.class);
                                intent.putExtra("idPartita", idPartita);
                                intent.putExtra("npartite", getIntent().getStringExtra("npartite"));
                                intent.putExtra("nvittorie", getIntent().getStringExtra("nvittorie"));
                                startActivity(intent);
                                finish();
                                break;
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } else {//SERVER
            Log.d("TAGFINE","getIntent().getStringExtra(\"npartite\"): "+ getIntent().getStringExtra("npartite"));
            Log.d("TAGFINE","getIntent().getStringExtra(\"nvittorie\"): "+ getIntent().getStringExtra("nvittorie"));
            dbReference.child("Partita/").addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String idPartita = String.valueOf(System.currentTimeMillis());
                    partita = new Partita("", id);
                    FirebaseDatabase.getInstance().getReference("Partita/" + idPartita).setValue(partita);
                    Toast.makeText(AttesaActivity.this, "Partita creata.", Toast.LENGTH_SHORT).show();

                    dbReference.child("Partita/"+idPartita).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Partita partita = Utils.getPartitaFromHashMap((HashMap) snapshot.getValue());
                            if (!partita.getIdClient().isEmpty()) {
                                String  c1client, c2client, c3client, c1server, c2server, c3server;
                                c1client = mazzo.estraiCarta().getId();
                                c2client = mazzo.estraiCarta().getId();
                                c3client = mazzo.estraiCarta().getId();
                                FirebaseDatabase.getInstance().getReference("Partita/" + idPartita +"/carteClient").setValue(c1client + " " + c2client+ " " +c3client);

                                c1server = mazzo.estraiCarta().getId();
                                c2server = mazzo.estraiCarta().getId();
                                c3server = mazzo.estraiCarta().getId();
                                FirebaseDatabase.getInstance().getReference("Partita/" + idPartita +"/carteServer").setValue(c1server + " " +c2server + " " + c3server);

                                FirebaseDatabase.getInstance().getReference("Partita/" + idPartita +"/carteCentrali").setValue(mazzo.estraiCarta().getId() + " " +mazzo.estraiCarta().getId() + " " + mazzo.estraiCarta().getId()+ " " + mazzo.estraiCarta().getId());

                                FirebaseDatabase.getInstance().getReference("Partita/" + idPartita +"/turno").setValue("client");
                                FirebaseDatabase.getInstance().getReference("Partita/" + idPartita +"/cartaMazzoC").setValue("");
                                FirebaseDatabase.getInstance().getReference("Partita/" + idPartita +"/nCarteMazzoC").setValue(0);
                                FirebaseDatabase.getInstance().getReference("Partita/" + idPartita +"/cartaMazzoS").setValue("");
                                FirebaseDatabase.getInstance().getReference("Partita/" + idPartita +"/nCarteMazzoS").setValue(0);

                                Intent i = new Intent(AttesaActivity.this, ActivityGiocoServer.class);
                                i.putExtra("idPartita", idPartita);
                                i.putExtra("npartite", getIntent().getStringExtra("npartite"));
                                i.putExtra("nvittorie", getIntent().getStringExtra("nvittorie"));
                                startActivity(i);
                                dbReference.child("Partita/"+idPartita).removeEventListener(this);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {   }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {   }
            });
        }
    }
}
