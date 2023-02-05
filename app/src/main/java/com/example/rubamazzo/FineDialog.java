package com.example.rubamazzo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FineDialog extends Dialog {

    TextView tvResultDialog, tvP1, tvP2;
    String result;
    String p1, p2;
    Button btnEsci;

    public FineDialog(@NonNull Context context, String result, String p1, String p2) {
        super(context);
        this.result=result;
        this.p1=p1;
        this.p2=p2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);

        tvResultDialog = findViewById(R.id.tvRisultato);
        tvP1 = findViewById(R.id.tvP1);
        tvP2 = findViewById(R.id.tvP2);
        btnEsci = findViewById(R.id.btnEsci);

        tvResultDialog.setText(result);
        tvP1.setText(p1);
        tvP2.setText(p2);

    }
    public Button getBtnEsci(){ return btnEsci;}

}