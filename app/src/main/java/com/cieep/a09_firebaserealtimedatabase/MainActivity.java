package com.cieep.a09_firebaserealtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText txtNombre;
    private Button btnSave;
    private TextView lblNombre;


    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre = findViewById(R.id.txtNombre);
        btnSave = findViewById(R.id.btnSave);
        lblNombre = findViewById(R.id.lblNombre);

        database = FirebaseDatabase.getInstance("https://pmdm-a-09-firebase-default-rtdb.europe-west1.firebasedatabase.app");

        DatabaseReference refNombre = database.getReference("nombre");

        DatabaseReference refPersona = database.getReference("persona");

        Persona p = new Persona("Eduard", 44);
        refPersona.setValue(p);

        refPersona.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Persona p = snapshot.getValue(Persona.class);
                Toast.makeText(MainActivity.this, p.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String texto = txtNombre.getText().toString();
                refNombre.setValue(texto);
            }
        });

        refNombre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nombre = snapshot.getValue(String.class);
                lblNombre.setText(nombre);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}