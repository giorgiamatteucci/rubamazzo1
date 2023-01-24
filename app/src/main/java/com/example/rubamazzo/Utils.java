package com.example.rubamazzo;

import java.util.HashMap;

public class Utils {

    public static Giocatore getGiocatoreFromHashMap(HashMap hashmap){
        return new Giocatore((String) hashmap.get("key"), (String) hashmap.get("username"),(String) hashmap.get("email"),(String) hashmap.get("password"));
    }

    public static Partita getPartitaFromHashMap(HashMap hashmap){
        return new Partita((String) hashmap.get("idClient"), (String) hashmap.get("idServer"),(String) hashmap.get("carteClient"), (String) hashmap.get("carteServer"));
    }

}
