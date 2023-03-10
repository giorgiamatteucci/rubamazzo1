package com.example.rubamazzo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class EntraActivity extends AppCompatActivity {
    TextView etEmail, etPassword;
    Button btnAccedi;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accesso);

        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnAccedi = findViewById(R.id.btnAccedi);
        btnAccedi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String email, password;
                email = String.valueOf(etEmail.getText());
                password = String.valueOf(etPassword.getText());

                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
                    Toast.makeText(EntraActivity.this, "Inserisci credenziali", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(EntraActivity.this, "Inserisci email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(EntraActivity.this, "Inserisci password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Login ok.",Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(EntraActivity.this, MenuActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(EntraActivity.this, "Credenziali errrate.",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}
