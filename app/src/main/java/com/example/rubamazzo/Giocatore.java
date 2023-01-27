package com.example.rubamazzo;

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

    public String getKey(){return key;}
    public String getUsername(){return username;}
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public int getNVittorie(){return nVittorie;}
    public int getNPartite(){return nPartite;}


    public String toStringCustom(){
        return "email: "+this.email+" password: "+this.password+" username: "+this.username+" nPartite: "+this.nPartite+" nVittorie: "+this.nVittorie;
    }
}
