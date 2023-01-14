package com.example.rubamazzo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RigaAdapter  extends RecyclerView.Adapter<RigaAdapter.CardViewHolder> {

    ArrayList<Giocatore> dataset;

    public RigaAdapter(ArrayList<Giocatore> dataset){
        this.dataset=dataset;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{

        TextView tvPosizione, tvEmail, tvPercVittorie;

        public CardViewHolder(View v){
            super(v);
            tvPosizione = v.findViewById(R.id.tvPosizioneI);
            tvEmail = v.findViewById(R.id.tvEmailClassificaI);
            tvPercVittorie = v.findViewById(R.id.tvPercVittorieI);
        }
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.riga_classifica,parent,false);
        CardViewHolder cardViewHolder = new CardViewHolder(v);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.tvPosizione.setText(""+(position+1));
        holder.tvEmail.setText(dataset.get(position).getEmail());
        Float perc = ((float)dataset.get(position).getNVittorie()/dataset.get(position).getNPartite())*100;

        String percVittorie;
        if(perc == 100.0){
            percVittorie = Float.toString(perc).substring(0,5)+" %";
        }else{
            percVittorie = Float.toString(perc).substring(0,4)+" %";
        }

        holder.tvPercVittorie.setText(percVittorie);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

}


