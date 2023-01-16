package com.example.rubamazzo;

public class Giocatore {
    private String username;
    private String email;
    private String password;
    private int nVittorie;
    private int nPartite;

    public Giocatore(String username,String email,String password) {
        nVittorie = 0;
        nPartite = 0;
        this.username=username;
        this.email=email;
        this.password=password;
    }

    //per prova Enrico, andrà rimosso
    public Giocatore(String email,String password,int nVittorie, int nPartite) {
        this.nVittorie = nVittorie;
        this.nPartite = nPartite;
        //this.nome=nome;
        //this.cognome=cognome;
        this.email=email;
        this.password=password;
    }

    public String getUsername(){return username;}
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public int getNVittorie(){return nVittorie;}
    public int getNPartite(){return nPartite;}

    public void setUsername(String username){ this.username=username;}
    public void setEmail(String email){ this.email=email;}
    public void setPassword(String password){ this.password=password;}
    public void incNVittorie(){nVittorie++;}
    public void incNPartite(){nPartite++;}
}
