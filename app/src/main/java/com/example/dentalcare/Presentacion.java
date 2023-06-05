package com.example.dentalcare;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Presentacion extends AppCompatActivity {

    Button btnEnviarDatos;
    TextView txtPrueba;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_presentacion);
        btnEnviarDatos = findViewById(R.id.btnEnviarDatos);
        txtPrueba = findViewById(R.id.txtViewPrueba);
        imgView = findViewById(R.id.imgViewFoto);



//        Bundle recibeRuta = getIntent().getExtras();
//        String rutaImagen = recibeRuta.getString("keyRuta");
//
//        Bitmap imgBitmap = BitmapFactory.decodeFile(rutaImagen);
//
//        imgView.setImageBitmap(imgBitmap);
//
//        Bundle recibeDatos = getIntent().getExtras();
//        String[] datos = recibeDatos.getStringArray("keyDatos");
//
//        txtPrueba.setText("Pregunta 1: " + datos[0]+ "\nPregunta 2: " + datos[1]+ "\nPregunta 3: " + datos[2]+ "\nPregunta 4: " + datos[3]+ "\nPregunta 5: " + datos[4]);
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void Cancel(View view){
        Intent cancel = new Intent(this, Home.class);
        cancel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(cancel);
        finishAffinity();
    }

    public void Diagnostics(View view){
        Intent diagnosticos = new Intent(this, DiagnosticsFragment.class);
        startActivity(diagnosticos);
    }
}