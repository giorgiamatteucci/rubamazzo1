package com.example.rubamazzo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartaAdapter  extends RecyclerView.Adapter<CartaAdapter.CardViewHolder> {

    ArrayList<Carta> dataset;

    public CartaAdapter(ArrayList<Carta> dataset){
        this.dataset=dataset;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public CardViewHolder(View v){
            super(v);
            imageView = v.findViewById(R.id.ivCarta);
        }
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.carta,parent,false);
        CardViewHolder cardViewHolder = new CardViewHolder(v);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.imageView.setImageResource(dataset.get(position).getIdImmagine());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

}


