package com.example.rubamazzo;

public class Carta {
    private String id;
    private int idImmagine;
    private int valore;
    private boolean estratta;

    public Carta(String id, int idImmagine, int valore) {
        estratta = false;
        this.id=id;
        this.idImmagine=idImmagine;
        this.valore=valore;
    }
    public String getId(){return id;}
    public int getValore(){return valore;}
    public boolean isEstratta(){return estratta;}
    public int getIdImmagine(){return idImmagine;}


    public void setEstrazione(){ estratta=true;}

    public String toString(){return "id: " + id + ", valore: " + valore + ", estratta: "+ estratta + ", idImmagine: "+idImmagine;}

}
