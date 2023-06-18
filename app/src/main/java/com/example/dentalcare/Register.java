package com.example.dentalcare;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private EditText Nombre, ApPaterno,ApMaterno,correo,contrasena;
    private TextView fechaNacimiento;
    private Button btnRegistrar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private boolean isCalendarDialogOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Nombre = findViewById(R.id.edTxtNombre);
        ApPaterno = findViewById(R.id.edTxtApPaterno);
        ApMaterno = findViewById(R.id.edTxtApMaterno);
        correo = findViewById(R.id.edTxtEmail);
        contrasena = findViewById(R.id.edTxtPassword);
        fechaNacimiento = findViewById(R.id.cvCalendar);
        btnRegistrar = findViewById(R.id.BtnIngresar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inicializarFirebase();
                registerUser();
            }
        });

    }

    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void Login(View view){
        Intent login = new Intent(this, Login.class);
        startActivity(login);
        finish();
    }
    public void Back(View view){
        Intent back = new Intent(this, Login.class);
        startActivity(back);
        finish();
    }

    public void registerUser(){

        String nombre = Nombre.getText().toString();
        String apPaterno = ApPaterno.getText().toString();
        String apMaterno = ApMaterno.getText().toString();
        String email = correo.getText().toString();
        String password = contrasena.getText().toString();
        String fecha = fechaNacimiento.getText().toString();

        if (nombre.equals("") || apPaterno.equals("") || apMaterno.equals("") || email.equals("")||!Patterns.EMAIL_ADDRESS.matcher(email).matches() || password.equals("") || fecha.equals("")) {
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                correo.setError("El correo no es valido.");
            }else{
                Toast.makeText(getApplicationContext(), "Complete los campos", Toast.LENGTH_SHORT).show();
            }
            validacion();
        } else {
            subirDatos(nombre, apPaterno, apMaterno, email, password, fecha);
        }
    }

    private void validacion() {
        String nombre = Nombre.getText().toString();
        String apPaterno = ApPaterno.getText().toString();
        String apMaterno = ApMaterno.getText().toString();
        String email = correo.getText().toString();
        String password = contrasena.getText().toString();
        String fecha = fechaNacimiento.getText().toString();

        if (nombre.equals("")){
            Nombre.setError("Requerido");
        } else if (apPaterno.equals("")) {
            ApPaterno.setError("Requerido");
        } else if (apMaterno.equals("")) {
            ApMaterno.setError("Requerido");
        } else if (email.equals("")) {
            correo.setError("Requerido");
        } else if (password.equals("")) {
            contrasena.setError("Requerido");
        } else if (fecha.equals("")) {
            fechaNacimiento.setError("Requerido");
        }
    }

    public void abrirCalendario(View view) {

        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        int anioInicio = 1994;
        int mesInicio = Calendar.JANUARY; // Ten en cuenta que los meses en Calendar se representan como valores enteros (0 - 11)
        int diaInicio = 1;

        DatePickerDialog dpd = new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                String fecha = dayOfMonth + "/" + (month+1) + "/" + year;
                fechaNacimiento.setText(fecha);
            }
        }, dia, mes, anio);

        dpd.getDatePicker().init(anioInicio,mesInicio,diaInicio, null);
        dpd.show();

    }

    public void subirDatos(String Nombre, String apPaterno, String apMaterno, String Correo, String Password, String Fecha){
        auth.createUserWithEmailAndPassword(Correo,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser us = auth.getCurrentUser();
                Map<String, Object> usr = new HashMap<>();
                usr.put("nombre", Nombre);
                usr.put("apPaterno", apPaterno);
                usr.put("apMaterno", apMaterno);
                usr.put("correo", Correo);
                usr.put("password", Password);
                usr.put("fechaNacimiento", Fecha);
                usr.put("uid", auth.getUid());
                db.collection("Usuario").document(auth.getUid()).set(usr).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Usuario registrado con exito ", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), Login.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finishAffinity();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error al registrar usuario ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al registrar usuario ", Toast.LENGTH_SHORT).show();
            }
        });
    }

}