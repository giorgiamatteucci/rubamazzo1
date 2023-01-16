package com.example.rubamazzo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegistrazioneActivity extends AppCompatActivity {

    EditText etEmail, etPassword, etUsername;
    Button btnRegistrati;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        mAuth = FirebaseAuth.getInstance();
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmailRegistrazione);
        etPassword = findViewById(R.id.etPasswordRegistrati);

        btnRegistrati = findViewById(R.id.btnRegistrati);

        btnRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email, password, username;
                email = String.valueOf(etEmail.getText());
                password = String.valueOf(etPassword.getText());
                username = String.valueOf(etUsername.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegistrazioneActivity.this, "Inserisci email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegistrazioneActivity.this, "Inserisci password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(RegistrazioneActivity.this, "Inserisci username", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //creare e registrare su db l'account creato
                                    FirebaseDatabase.getInstance().getReference("Giocatore/"+FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new Giocatore(username, email, password));

                                    Toast.makeText(RegistrazioneActivity.this, "Autenticazione creata.", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext() , EntraActivity.class);
                                    startActivity(i);//poi andando in EntraActivity saremo subito rindirizzati a MenuActivity
                                    finish();
                                } else {
                                    // se la registrazione fallisce, visualizza un messaggio all'utente
                                    Toast.makeText(RegistrazioneActivity.this, "Autenticazione fallita.", Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(RegistrazioneActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}
