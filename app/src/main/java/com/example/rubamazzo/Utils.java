package com.example.rubamazzo;

import java.util.HashMap;

public class Utils {

    public static Giocatore getGiocatoreFromHashMap(HashMap hashmap){

        return new Giocatore((String) hashmap.get("key"), (String) hashmap.get("username"),(String) hashmap.get("email"),(String) hashmap.get("password"));

    }

}
