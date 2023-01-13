package com.example.rubamazzo;

public class Giocatore {
    //private String nome;
    //private String cognome;
    private String email;
    private String password;
    private int nVittorie;
    private int nPartite;

    public Giocatore(String email,String password) {//(String nome,String cognome,String email,String password) {
        nVittorie = 0;
        nPartite = 0;
        //this.nome=nome;
        //this.cognome=cognome;
        this.email=email;
        this.password=password;
    }
    //public String getNome(){return nome;}
    //public String getCognome(){return cognome;}
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public int getNVittorie(){return nVittorie;}
    public int getNPartite(){return nPartite;}

    //public void setNome(String nome){ this.nome=nome;}
    //public void setCognome(String cognome){ this.cognome=cognome;}
    public void setEmail(String email){ this.email=email;}
    public void setPassword(String password){ this.password=password;}
    public void incNVittorie(){nVittorie++;}
    public void incNPartite(){nPartite++;}
}
