package com.example.rubamazzo;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Utils {

    public static Giocatore getGiocatoreFromHashMap(HashMap hashmap){

        Long npartite =  (Long) hashmap.get("npartite");
        Long nvittorie = (Long) hashmap.get("nvittorie");

        return new Giocatore((String) hashmap.get("key"), (String) hashmap.get("username"),(String) hashmap.get("email"),(String) hashmap.get("password"),npartite.intValue(),nvittorie.intValue());
    }

    public static Partita getPartitaFromHashMap(HashMap hashmap){
        return new Partita((String) hashmap.get("idClient"), (String) hashmap.get("idServer"));
    }

    public static String addCarteCentrali(String[] carteCentrali, String id) {
        String output = "";
        if(carteCentrali.length>0) {
            for (int i = 0; i < carteCentrali.length; i++) {
                output += carteCentrali[i] + " ";
            }
            output +=id;
            Log.d("FIX2"," if output+=id: -"+output+"-");
        }else{
            output=id;
            Log.d("FIX2"," else output=id: -"+output+"-");
        }

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

    public static ArrayList<Giocatore> getTopFive(ArrayList<Giocatore> giocatori){

        int N = giocatori.size();

        ArrayList<Giocatore> topFive = new ArrayList<>();
        for(int i=0;i<N;i++){
            Log.d("ordinamento"," i: "+i);
            int j = min(giocatori,i);
            Giocatore tmp = giocatori.get(i);
            giocatori.set(i,giocatori.get(j));
            giocatori.set(j,tmp);
        }

        if(N>5){
            for(int i=0;i<5;i++){
                topFive.add(giocatori.get(N-i-1));
            }
        }else {
            topFive = giocatori;
        }

        return topFive;
    }

    private static int min(ArrayList<Giocatore> giocatori, int i){
        int min = i;
        for(int h = i+1;h<giocatori.size();h++){
            Log.d("ordinamento"," h: "+h+" i: "+i);
            if(giocatori.get(h).minCustom(giocatori.get(min))){
                min = h;
            }
        }

        return min;
    }

}
