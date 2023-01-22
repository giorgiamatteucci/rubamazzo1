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

                        int i =0;

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            i++;
                            Log.d("TAG02"," partita n"+i);

                            String idPartita = snapshot.getKey();
                            Partita partita = Utils.getPartitaFromHashMap((HashMap) snapshot.getValue());

                            if(partita.getIdClient().isEmpty()){
                                Log.d("TAG02"," partita n"+i+" libera");
                                FirebaseDatabase.getInstance().getReference("Partita/" + idPartita+"/idClient").setValue(id);
                                Intent intent = new Intent(AttesaActivity.this, ActivityGiocoClient.class);
                                intent.putExtra("idPartita", idPartita);
                                startActivity(intent);
                                finish();
                                break;
                            }
                        }
                    }

                    if (dataSnapshot.getChildrenCount() == 1) {//CONTROLLARE CHE IL CLIENT NON SIA UGUALE AL SERVER

                        Log.d("TAG01", dataSnapshot.toString());
                        //(HashMap) dataSnapshot.getValue().get("username")
                        //dbReference.child("Partita/").child("idClient").push().setValue(id);
                        //dbReference.child("Partita/").child("idClient").child("id_giocatore").getRef().setValue(id);
                        dbReference.child("Partita/").child(id).child("tipo_giocatore").getRef().setValue("client");
                        Toast.makeText(AttesaActivity.this, "Ti sei unito alla partita.", Toast.LENGTH_SHORT).show();
                    }
                    if (dataSnapshot.getChildrenCount() == 2) {
                        Intent i = new Intent(AttesaActivity.this, ActivityGiocoClient.class);
                        //i.putExtra();
                        startActivity(i);
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } else {//SERVER

            // ok
            dbReference.child("Partita/").addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String idPartita = String.valueOf(System.currentTimeMillis());

                    FirebaseDatabase.getInstance().getReference("Partita/" + idPartita).setValue(new Partita("", id));

                    // Da testare
                    dbReference.child("Partita/" + idPartita).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Partita partita = Utils.getPartitaFromHashMap((HashMap) snapshot.getValue());
                            if (!partita.getIdClient().isEmpty()) {
                                Intent i = new Intent(AttesaActivity.this, ActivityGiocoServer.class);
                                i.putExtra("idPartita", idPartita);
                                startActivity(i);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
