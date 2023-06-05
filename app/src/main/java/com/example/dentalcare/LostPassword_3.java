package com.example.dentalcare;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

public class LostPassword_3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_password3);
    }

    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void NewPassword(View view){
        Intent newPassword= new Intent(this, Login.class);
        startActivity(newPassword);
    }
    public void Back(View view){
        Intent back = new Intent(this, LostPassword_2.class);
        startActivity(back);

    }
    public void Cancel(View view){
        Intent cancel = new Intent(this, Login.class);
        cancel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(cancel);
        finishAffinity();
    }
}