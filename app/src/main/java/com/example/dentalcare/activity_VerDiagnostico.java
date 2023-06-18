package com.example.dentalcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class activity_VerDiagnostico extends AppCompatActivity {

    TextView txtHora,txtFecha,txtRP1,txtRP2,txtRP3,txtRP4,txtRP5;
    Button btnEliminarRegistro;
    ImageView imgFoto;
    FirebaseFirestore fStore;
    String key ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_diagnostico);

        btnEliminarRegistro = findViewById(R.id.btnEliminarRegistro);

        txtHora = findViewById(R.id.txtView_Hora);
        txtFecha = findViewById(R.id.txtView_Fecha);
        txtRP1 = findViewById(R.id.edTxtPregunta1);
        txtRP2 = findViewById(R.id.edTxtPregunta2);
        txtRP3 = findViewById(R.id.edTxtPregunta3);
        txtRP4 = findViewById(R.id.edTxtPregunta4);
        txtRP5 = findViewById(R.id.edTxtPregunta5);
        imgFoto = findViewById(R.id.imgFotoFinal);
        key = getIntent().getStringExtra("key");

        btnEliminarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarRegistro(key);
            }
        });

        fStore = FirebaseFirestore.getInstance();



        DocumentReference documentReference = fStore.collection("Diagnosticos").document(key);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                String urlImg = value.getString("urlHSV");
                txtFecha.setText(value.getString("Fecha"));
                txtHora.setText(value.getString("Hora"));
                txtRP1.setText(value.getString("RP1"));
                txtRP2.setText(value.getString("RP2"));
                txtRP3.setText(value.getString("RP3"));
                txtRP4.setText(value.getString("RP4"));
                txtRP5.setText(value.getString("RP5"));

                Glide.with(activity_VerDiagnostico.this)
                        .load(urlImg)
                        .into(imgFoto);
            }
        });
    }

    public void Back(View view){
        onBackPressed();
    }

    public void eliminarRegistro(String key){
        fStore.collection("Diagnosticos").document(key).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(activity_VerDiagnostico.this, "Se eliminó correctamente", Toast.LENGTH_SHORT).show();
                Intent home = new Intent(getApplicationContext(), Home.class);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
                finishAffinity();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity_VerDiagnostico.this, "Error al eliminar el registro.", Toast.LENGTH_SHORT).show();
            }
        });
    }


}