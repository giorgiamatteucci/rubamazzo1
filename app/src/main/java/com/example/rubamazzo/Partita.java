package com.example.rubamazzo;

public class Partita {
    private String idClient, idServer;

    public Partita(String idClient, String idServer){
        this.idClient=idClient;
        this.idServer=idServer;
    }

    public String getIdClient(){return idClient;}
    public String getIdServer(){return idServer;}

    public void setIdClient(String idClient){this.idClient=idClient;}
    public void setIdServer(String idServer){this.idServer=idServer;}

    public String toString(){
        return "idClient: " + idClient + ", idServer: " + idServer;
    }

}
