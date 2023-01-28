package com.example.rubamazzo;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Utils {

    public static Giocatore getGiocatoreFromHashMap(HashMap hashmap){
        return new Giocatore((String) hashmap.get("key"), (String) hashmap.get("username"),(String) hashmap.get("email"),(String) hashmap.get("password"));
    }

    public static Partita getPartitaFromHashMap(HashMap hashmap){
        return new Partita((String) hashmap.get("idClient"), (String) hashmap.get("idServer"));
    }

    public static String addCarteCentrali(String[] carteCentrali, String id) {
        String output = "";
        for (int i = 0; i < carteCentrali.length; i++) {
            output += carteCentrali[i] + " ";
        }
        output += id;
        return output;
    }

    public static String removeCartaDalCentro(String[] carteCentrali, String id) {
        String output = "";
        for (int i = 0; i < carteCentrali.length; i++) {
            if (!carteCentrali[i].equals(id)) {
                output += carteCentrali[i] + " ";
            }
        }
        return output;
    }

    public static String removeCartaGiocatore(String[] carteGiocatore, String id) {
        String output = "";
        for (int i = 0; i < carteGiocatore.length; i++) {
            if (!carteGiocatore[i].equals(id)) {
                output += carteGiocatore[i] + " ";
            }else{
                output += "VUOTO ";
            }
        }
        return output;
    }

    public static String getVincitore(int nCarteMazzoClient, int nCarteMazzoServer){
        String vincitore="";
        if(nCarteMazzoServer > nCarteMazzoClient){
            vincitore = "server";
        } else {
            vincitore = "client";
        }
        return vincitore;
    }

    /*public static void getInfoGiocatore(DatabaseReference dbRefGiocatore){

        dbRefGiocatore.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("TAG-REFRESH","sono dentro aggiornaStatoPartita()");
                final int npartite = snapshot.child("npartite").getValue(Integer.class);
                final int nvittorie = snapshot.child("nvittorie").getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }*/

}
