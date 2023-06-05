package com.example.dentalcare;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

public class LostPassword_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_password2);
    }

    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void SendCode(View view){
        Intent sendCode = new Intent(this, LostPassword_3.class);
        startActivity(sendCode);
    }
    public void Back(View view){
        Intent back = new Intent(this, LostPassword_1.class);
        startActivity(back);

    }
    public void Cancel(View view){
        Intent cancel = new Intent(this, Login.class);
        cancel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(cancel);
        finishAffinity();
    }
}