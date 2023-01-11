package com.example.rubamazzo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EntraActivity extends AppCompatActivity {

    TextView etEmail, etPassword;
    Button btnEntra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnEntra = findViewById(R.id.btnEntra);
        btnEntra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent menu2 = new Intent(EntraActivity.this, MenuActivity.class);
                //startActivity(menu2);
            }
        });

    }
}
