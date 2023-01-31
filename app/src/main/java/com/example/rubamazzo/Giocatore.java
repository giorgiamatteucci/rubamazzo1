package com.example.rubamazzo;

import android.util.Log;

public class Giocatore {
    private String key, username, email, password;
    private int nVittorie, nPartite;

    public Giocatore(String key, String username,String email,String password) {
        nVittorie = 0;
        nPartite = 0;
        this.key=key;
        this.username=username;
        this.email=email;
        this.password=password;
    }

    public Giocatore(String key, String username,String email,String password, int nPartite, int nVittorie) {
        this.nPartite = nPartite;
        this.nVittorie = nVittorie;
        this.key=key;
        this.username=username;
        this.email=email;
        this.password=password;
    }

    public String getKey(){return key;}
    public String getUsername(){return username;}
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public int getNVittorie(){return nVittorie;}
    public int getNPartite(){return nPartite;}

    public void setNVittorie(int nVittorie){this.nVittorie=nVittorie;}
    public void setNPartite(int nPartite){this.nPartite=nPartite;}

    //Metodi per incrementare il numero delle vittorie e il numero di patite giocate
    public void incNVittorie(){nVittorie++;}
    public void incNPartite(){nPartite++;}

    public String toStringCustom(){
        return "email: "+this.email+" password: "+this.password+" username: "+this.username+" nPartite: "+this.nPartite+" nVittorie: "+this.nVittorie;
    }

    public boolean minCustom(Giocatore g2){
        Float myPerc = ((float) this.getNVittorie() / this.getNPartite()) * 100;
        Float percG2 = ((float) g2.getNVittorie() / g2.getNPartite()) * 100;
        //Log.d("ordinamento","myPerc<percG2 : "+myPerc+"<"+percG2+ " : "+(myPerc<percG2));

        if(myPerc.isNaN())
            return true;

        if(percG2.isNaN())
            return false;

        return myPerc<percG2;
    }
}
