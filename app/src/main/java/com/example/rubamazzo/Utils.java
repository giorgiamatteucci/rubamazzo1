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

}
