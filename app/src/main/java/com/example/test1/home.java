package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Utils.blackIconStatusBar(home.this,R.color.light_background);
        Bundle credential = getIntent().getExtras();
        String logid = credential.getString("Username","EmptyUID");
        String password = credential.getString("Password","EmptyPassword");

        Toast.makeText(home.this,"Welcome",Toast.LENGTH_LONG).show();

    }
}