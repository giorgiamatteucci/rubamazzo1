package com.example.rubamazzo;

import java.util.ArrayList;
import java.util.Random;

public class Mazzo extends ArrayList<Carta> {

    private static Mazzo istance;
    private static Random random = new Random();

    private Mazzo(){
        aggiornaCarte();
    }

    private void aggiornaCarte(){

        // carico tutte le carte
        add(new Carta("b1",R.drawable.b1,1));
        add(new Carta("b2",R.drawable.b2,2));
        add(new Carta("b3",R.drawable.b3,3));
        add(new Carta("b4",R.drawable.b4,4));
        add(new Carta("b5",R.drawable.b5,5));
        add(new Carta("b6",R.drawable.b6,6));
        add(new Carta("b7",R.drawable.b7,7));
        add(new Carta("b8",R.drawable.b8,8));
        add(new Carta("b9",R.drawable.b9,9));
        add(new Carta("b10",R.drawable.b10,10));
        add(new Carta("c1",R.drawable.c1,1));
        add(new Carta("c2",R.drawable.c2,2));
        add(new Carta("c3",R.drawable.c3,3));
        add(new Carta("c4",R.drawable.c4,4));
        add(new Carta("c5",R.drawable.c5,5));
        add(new Carta("c6",R.drawable.c6,6));
        add(new Carta("c7",R.drawable.c7,7));
        add(new Carta("c8",R.drawable.c8,8));
        add(new Carta("c9",R.drawable.c9,9));
        add(new Carta("c10",R.drawable.c10,10));
        add(new Carta("d1",R.drawable.d1,1));
        add(new Carta("d2",R.drawable.d2,2));
        add(new Carta("d3",R.drawable.d3,3));
        add(new Carta("d4",R.drawable.d4,4));
        add(new Carta("d5",R.drawable.d5,5));
        add(new Carta("d6",R.drawable.d6,6));
        add(new Carta("d7",R.drawable.d7,7));
        add(new Carta("d8",R.drawable.d8,8));
        add(new Carta("d9",R.drawable.d9,9));
        add(new Carta("d10",R.drawable.d10,10));
        add(new Carta("s1",R.drawable.s1,1));
        add(new Carta("s2",R.drawable.s2,2));
        add(new Carta("s3",R.drawable.s3,3));
        add(new Carta("s4",R.drawable.s4,4));
        add(new Carta("s5",R.drawable.s5,5));
        add(new Carta("s6",R.drawable.s6,6));
        add(new Carta("s7",R.drawable.s7,7));
        add(new Carta("s8",R.drawable.s8,8));
        add(new Carta("s9",R.drawable.s9,9));
        add(new Carta("s10",R.drawable.s10,10));
    }

    public static Mazzo getIstance(){
        if(istance==null)
            istance = new Mazzo();
        return istance;
    }

    //metodo per estrarre casualemente una carta
    public Carta estraiCarta(){
        Carta car;
        do{
            car = istance.get(random.nextInt(40));
        }while(car.isEstratta());
        car.setEstrazione();
        return car;
    }

    // id dell'oggetto carta (es. b3)
    public Carta getCartaById(String id){//public Carta getCarta(char seme, int valore){
        for(int i=0;i<istance.size();i++){
            Carta carta = istance.get(i);
            if(carta.getId().equals(id))
                return carta;
        }
        return null;
    }

    // 2569854785 -> id della risorsa (immagine) collegata ad una carta
    public Carta getCartaById(int id){//public Carta getCarta(char seme, int valore){
        for(int i=0;i<istance.size();i++){
            Carta carta = istance.get(i);
            if(carta.getIdImmagine() == id)
                return carta;
        }
        return null;
    }

    public boolean isEmpty(){
        // TODO DA VERIFICARE
        //  controllare l'istanza del mazzo e verificare che tutte le carte siano state estratte
        int countEstratte=0;
        for(int i=0;i<istance.size();i++){
            Carta carta = istance.get(i);
            if(carta.isEstratta())
                countEstratte++;
        }
        if(countEstratte==istance.size()){
            return true;
        }else{
            return false;
        }
    }
}
