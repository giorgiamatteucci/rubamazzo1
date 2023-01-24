package com.example.rubamazzo;

public class Partita {
    private String idClient, idServer;
    private String carteClient, carteServer;
   // private boolean iniziata;

    public Partita(String idClient, String idServer, String carteClient,String carteServer) {
        this.idClient=idClient;
        this.idServer=idServer;
        this.carteClient=carteClient;
        this.carteServer=carteServer;
    }

   // public boolean isIniziata(){return iniziata;}
    public String getIdClient(){return idClient;}
    public String getIdServer(){return idServer;}

   // public void setIniziata(){ iniziata=true;}
    public void setIdClient(String idClient){this.idClient=idClient;}
    public void setIdServer(String idServer){this.idServer=idServer;}

}
