package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    private LinearLayout layout_register ;
    private Animation animation_fadein;
    EditText name,email,contactno,password;
    Button signin,signup;

    private void init(){
        signin = (Button)  findViewById(R.id.signin);
        signup = (Button) findViewById(R.id.signup);
        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        contactno = findViewById(R.id.et_contactno);
        password = findViewById(R.id.et_password);

        name.setError("Cannot be Empty");
        email.setError("Cannot be Empty");
        contactno.setError("Cannot be Empty");
        password.setError("Cannot be Empty");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        Utils.blackIconStatusBar(Register.this,R.color.light_background);

        layout_register = findViewById(R.id.layout_register);
        animation_fadein = AnimationUtils.loadAnimation(Register.this,R.anim.fade_in);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layout_register.setVisibility(View.VISIBLE);
                layout_register.setAnimation(animation_fadein);
            }
        },500);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences regs = PreferenceManager.getDefaultSharedPreferences(Register.this);
                SharedPreferences.Editor editor = regs.edit();
                editor.putString("Name",name.getText().toString());
                editor.putString("Email",email.getText().toString());
                editor.putString("Contact",contactno.getText().toString());
                editor.putString("Password",password.getText().toString());

                editor.commit();
                Intent signup_Intent = new Intent(Register.this,LoginActivity.class);
                startActivity(signup_Intent);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signin_Intent = new Intent(Register.this,LoginActivity.class);
                startActivity(signin_Intent);
            }
        });


    }
}