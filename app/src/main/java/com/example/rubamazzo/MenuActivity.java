package com.example.rubamazzo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    Button btnGioca, btnCrea, btnClassifica, btnCome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnGioca = findViewById(R.id.btnGioca);
        btnCrea = findViewById(R.id.btnCrea);
        btnClassifica = findViewById(R.id.btnClassifica);
        btnCome = findViewById(R.id.btnCome);

        btnGioca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent menu = new Intent(AttesaActivity.this, MenuActivity.class);
                //startActivity(menu);
            }
        });

    }
}