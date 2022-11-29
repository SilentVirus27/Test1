package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.util.Pair;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private View img_logo,img_bottom,main_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utils.blackIconStatusBar(MainActivity.this,R.color.light_background);

        img_logo = findViewById(R.id.img_logo);
        img_bottom = findViewById(R.id.img_bottom);
        main_name = findViewById(R.id.main_name);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                        Pair.create(img_logo,"logo"),
                        Pair.create(img_bottom,"img_tree"),
                        Pair.create(main_name,"logo_text"));
                startActivity(intent,options.toBundle());
            }

        }, 1000);
    }
}