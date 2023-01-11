package com.example.rubamazzo;

public class Carta {
    private String id;
    private int idImmagine;
    private double valore;
    private boolean estratta;

    public Carta(String id,int idImmagine,double valore) {
        estratta = false;
        this.id=id;
        this.idImmagine=idImmagine;
        this.valore=valore;
    }
    public String getId(){return id;}
    public double getValore(){return valore;}
    public boolean isEstratta(){return estratta;}
    public int getIdImmagine(){return idImmagine;}


    public void setEstrazione(){ estratta=true;}

}
