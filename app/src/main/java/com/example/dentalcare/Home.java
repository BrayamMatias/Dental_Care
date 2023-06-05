package com.example.dentalcare;


import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class Home extends AppCompatActivity {

    private BottomNavigationView bnvMenu;
    private Fragment fragment;
    private FragmentManager manager;
    FirebaseAuth auth;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth = FirebaseAuth.getInstance();

        initView();
        initValues();
        initListener();

    }

    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initView(){
        bnvMenu = findViewById(R.id.bnvMenu);
    }

    private void initValues(){
        manager = getSupportFragmentManager();
        loadFirstFragment();
    }

    private void initListener(){
        bnvMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int idMenu = item.getItemId();
                switch (idMenu){
                    case R.id.nav_home:
                        fragment = HomeFragment.newInstance();
                        openFragment(fragment);
                        return true;
                    case R.id.nav_diagnosticos:
                        fragment = DiagnosticsFragment.newInstance();
                        openFragment(fragment);
                        return true;
                    case R.id.nav_user:
                        fragment = UserFragment.newInstance();
                        openFragment(fragment);
                        return true;
                }
                return true;
            }
        });
    }
    private void openFragment(Fragment fragment){
        manager.beginTransaction()
                .replace(R.id.frameContainer, fragment)
                .commit();
    }

    private void loadFirstFragment(){
        fragment = HomeFragment.newInstance();
        openFragment(fragment);
    }

    public void Questionnaire(View view){
        Intent cuestionario = new Intent(this, Questionnaire.class);
        startActivity(cuestionario);
        finish();
    }

    public void cerrarSesion(View view){
        auth.signOut();
        Toast.makeText(this, "Se ha cerrado sesión", Toast.LENGTH_SHORT).show();
        enviarLogin();
    }

    public void enviarLogin(){
        Intent login = new Intent(getApplicationContext(), Login.class);
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(login);
        finishAffinity();
    }
}