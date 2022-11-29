package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import java.security.PrivateKey;

public class Dashbord extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private void inti(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);
        mAuth = FirebaseAuth.getInstance();
        inti();
        Utils.blackIconStatusBar(Dashbord.this,R.color.light_background);

    }

}