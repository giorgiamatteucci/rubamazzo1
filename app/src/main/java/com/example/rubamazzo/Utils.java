package com.example.rubamazzo;

import android.util.Log;

import java.util.HashMap;

public class Utils {

    public static Giocatore getGiocatoreFromHashMap(HashMap hashmap){
        return new Giocatore((String) hashmap.get("key"), (String) hashmap.get("username"),(String) hashmap.get("email"),(String) hashmap.get("password"));
    }

    public static Partita getPartitaFromHashMap(HashMap hashmap){
        return new Partita((String) hashmap.get("idClient"), (String) hashmap.get("idServer"));
    }

    public static String addCarteCentrali(String[] carteCentrali, String id) {
        Log.d("carteCentrali input ",carteCentrali.toString());
        String output = "";
        for (int i = 0; i < carteCentrali.length; i++) {
            if (!carteCentrali[i].equals(id)) {
                output += carteCentrali[i] + " ";
            } else {
                output += id + " ";
            }
        }
        Log.d("carteCentrali output ",output);
        return output;
    }

}
