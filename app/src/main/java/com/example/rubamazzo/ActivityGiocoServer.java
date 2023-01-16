package com.example.rubamazzo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;

public class ActivityGiocoServer extends AppCompatActivity {

    ImageView ivC1Server, ivC2Server, ivC3Server, ivC1Client, ivC2Client, ivC3Client, ivMazzoServer, ivMazzoClient;
    RecyclerView rvSopra, rvSotto;
    RecyclerView.LayoutManager layoutManager;
    CartaAdapter adapterSopra, adapterSotto;
    ArrayList<Carta> carteSotto, carteSopra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioco);

        ivC1Server = findViewById(R.id.ivC1_sotto);
        ivC2Server = findViewById(R.id.ivC2_sotto);
        ivC3Server = findViewById(R.id.ivC3_sotto);
        ivMazzoServer = findViewById(R.id.ivMazzo_sotto);

        ivC1Client = findViewById(R.id.ivC1_sopra);
        ivC2Client = findViewById(R.id.ivC2_sopra);
        ivC3Client = findViewById(R.id.ivC3_sopra);
        ivMazzoClient = findViewById(R.id.ivMazzo_sopra);

        rvSopra = findViewById(R.id.rvSopra);
        rvSotto = findViewById(R.id.rvSotto);
        layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
        rvSopra.setLayoutManager(layoutManager);
        layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
        rvSotto.setLayoutManager(layoutManager);

        carteSotto = new ArrayList<>();
        adapterSotto = new CartaAdapter(carteSotto);
        rvSotto.setAdapter(adapterSotto);

        carteSopra = new ArrayList<>();
        adapterSopra = new CartaAdapter(carteSopra);
        rvSopra.setAdapter(adapterSopra);
    }
}