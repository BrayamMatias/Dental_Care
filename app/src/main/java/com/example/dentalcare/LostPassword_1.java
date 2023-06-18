package com.example.dentalcare;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class LostPassword_1 extends AppCompatActivity {

    EditText edtEmail;

    Button btnEnviarMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_password1);

        edtEmail = findViewById(R.id.EdTxtEmail);
        btnEnviarMail = findViewById(R.id.btnEnviarCorreo);

        btnEnviarMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }

    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void validate() {
        String email = edtEmail.getText().toString().trim();
        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("El correo no es valido.");
        }

        sendEmail(email);
    }

    private void sendEmail(String email) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String correo = email;

        auth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LostPassword_1.this, "Correo enviado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LostPassword_1.this, Login.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(LostPassword_1.this, "Correo invalido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(LostPassword_1.this, Login.class);
        startActivity(intent);
        finish();
    }

    public void Back(View view){
        Intent back = new Intent(this, Login.class);
        back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(back);
        finishAffinity();
    }
    public void Cancel(View view){
        Intent cancel = new Intent(this, Login.class);
        cancel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(cancel);
        finishAffinity();
    }
}