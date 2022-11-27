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

public class LoginActivity extends AppCompatActivity {

    private LinearLayout layout_login ;
    private Animation animation_fadein;
    EditText loginid,password;
    Button signin,signup;
    String dbloginid,dbpassword ;


    private void init(){
        signin = (Button)  findViewById(R.id.signin);
        signup = (Button) findViewById(R.id.signup);
        loginid = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        Utils.blackIconStatusBar(LoginActivity.this,R.color.light_background);

        //Animation Start
        layout_login = findViewById(R.id.layout_login);
        animation_fadein = AnimationUtils.loadAnimation(LoginActivity.this,R.anim.fade_in);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            layout_login.setVisibility(View.VISIBLE);
            layout_login.setAnimation(animation_fadein);
            }
        },500);
        //Animation End

        signin.setOnClickListener(view -> Signin());

        signup.setOnClickListener(view -> {
            Intent signupIntent = new Intent(LoginActivity.this,Register.class);
            startActivity(signupIntent);
        });
    }

    public void Signin(){
        SharedPreferences db = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        String id=loginid.getText().toString();
        String pass=password.getText().toString();
        dbloginid = db.getString("Email","");
        dbpassword = db.getString("Password","");

        if(id.isEmpty()){
            loginid.setError("Please Enter Email");
        }else if(pass.isEmpty()){
            password.setError("Please Enter Password");
        }else{
            if(loginid.getText().toString().equals(dbloginid)){
                if (password.getText().toString().equals(dbpassword)){
                    Intent FirstIntent = new Intent(LoginActivity.this,home.class);
                    FirstIntent.putExtra("Username",id);
                    FirstIntent.putExtra("Password",pass);
                    startActivity(FirstIntent);
                }else password.setError("Wrong Password");
            }else loginid.setError("Wrong Email Id");
        }
    }
}