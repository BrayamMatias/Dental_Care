package com.example.dentalcare;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dentalcare.modelo.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    FirebaseAuth auth;
    EditText correo;
    EditText contrasena;

    SharedPreferences preferences;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        correo = findViewById(R.id.edTxtCorreo);
        contrasena = findViewById(R.id.edTxtPassword);

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void onStart() {
        verificacionInicioSesion();
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        // updateUI(currentUser);
    }

    private void verificacionInicioSesion(){
        if (firebaseUser != null){
            Toast.makeText(this, "Se ha iniciado sesión", Toast.LENGTH_SHORT).show();
            enviarHome();
        }
    }

    public void Register(View view){
        Intent registrar = new Intent(this, Register.class);
        startActivity(registrar);
    }

    public void Ingresar(View view){

        String email = correo.getText().toString();
        String password = contrasena.getText().toString();

        if (email.equals("") || password.equals("") || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                correo.setError("El correo no es valido.");
            }else{
                Toast.makeText(getApplicationContext(), "Complete los campos", Toast.LENGTH_SHORT).show();
            }
            validacion();
        } else {
            auth.signInWithEmailAndPassword(correo.getText().toString().trim(), contrasena.getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser user = auth.getCurrentUser();
                                enviarHome();
                            }else {
                                Toast.makeText(getApplicationContext(), "Inicio de sesión fallido", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    private void validacion() {
        String email = correo.getText().toString();
        String password = contrasena.getText().toString();

        if (email.equals("")) {
            correo.setError("Requerido");
        } else if (password.equals("")) {
            contrasena.setError("Requerido");
        }
    }

    public void LostPassword(View view){
        Intent lostPassword = new Intent(this, LostPassword_1.class);
        startActivity(lostPassword);
    }

    public void enviarHome(){
        Intent ingresar = new Intent(getApplicationContext(), Home.class);
        ingresar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(ingresar);
        finishAffinity();
    }

//    public void enviarLogin(){
//        Intent login = new Intent(getApplicationContext(), Login.class);
//        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(login);
//        finishAffinity();
//    }
}