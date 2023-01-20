package com.example.rubamazzo;

public class Partita {
    private Giocatore client, server;
    private boolean iniziata;

    public Partita(Giocatore server) {
        iniziata = false;
        this.server=server;
    }
    public Giocatore getServer(){return server;}
    public Giocatore getClient(){return client;}
    public boolean isIniziata(){return iniziata;}

    public void setIniziata(){ iniziata=true;}
    public void setClient(Giocatore client){this.client=client;}

}
