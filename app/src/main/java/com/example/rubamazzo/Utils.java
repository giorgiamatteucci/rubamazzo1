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
        Log.d("UTILS","addCarteCentrali input "+carteCentrali.toString());
        String output = "";
        for (int i = 0; i < carteCentrali.length; i++) {
            output += carteCentrali[i] + " ";
        }
        output += id;
        Log.d("UTILS","addCarteCentrali output "+output);
        return output;
    }

    public static String removeCartaDalCentro(String[] carteCentrali, String id) {
        Log.d("UTILS","removeCartaDalCentro input "+carteCentrali.toString());
        String output = "";
        for (int i = 0; i < carteCentrali.length; i++) {
            if (!carteCentrali[i].equals(id)) {
                output += carteCentrali[i] + " ";
            }
        }
        Log.d("UTILS","removeCartaDalCentro output "+output);
        return output;
    }

    public static String removeCartaGiocatore(String[] carteGiocatore, String id) {
        Log.d("UTILS","carteGiocatore input "+carteGiocatore.toString());
        String output = "";
        for (int i = 0; i < carteGiocatore.length; i++) {
            if (!carteGiocatore[i].equals(id)) {
                output += carteGiocatore[i] + " ";
            }else{
                output += "VUOTO ";
            }
        }
        Log.d("UTILS","carteGiocatore output "+output);
        return output;
    }

}
