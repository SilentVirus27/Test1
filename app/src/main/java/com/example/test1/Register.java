package com.example.test1;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private LinearLayout layout_register ;
    private Animation animation_fadein;
    private ProgressBar signupProgress;
    private FirebaseAuth mAuth;
    EditText name,email,contactno,password;
    Button signin_reg,signup_reg;
    String mname,memail,mcontactno,mpassword;


    private void init(){
        signin_reg = (Button)  findViewById(R.id.signin_reg);
        signup_reg = (Button) findViewById(R.id.signup_reg);
        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        contactno = findViewById(R.id.et_contactno);
        password = findViewById(R.id.et_password);
        signupProgress=findViewById(R.id.signup_progress);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
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

        signup_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signup();
            }
        });

        signin_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signin_Intent = new Intent(Register.this,LoginActivity.class);
                startActivity(signin_Intent);
            }
        });


    }

    public void Signup(){
        /*signupProgress.setVisibility(View.VISIBLE);*/
        mname=name.getText().toString();
        memail=email.getText().toString().trim();
        mcontactno=contactno.getText().toString();
        mpassword=password.getText().toString();
        if (TextUtils.isEmpty(mname)) {
            name.setError("Please Enter Name");
            return;
        }
        if (TextUtils.isEmpty(memail)) {
            email.setError("Please Enter Email");
            return;
        }
        /*if(Patterns.EMAIL_ADDRESS.matcher(memail).matches()){
            email.setError("Enter Valid Email");
            return;
        }*/
        if (TextUtils.isEmpty(mcontactno)) {
            contactno.setError("Please Enter Contact");
            return;
        }
        if (TextUtils.isEmpty(mpassword)) {
            password.setError("Please Enter Password");
            return;
        }
        if (mpassword.length()<8) {
            password.setError("Minimum Password Length 8 Character");
            return;
        }
        if (mpassword.equals(mname)) {
            password.setError("Password should not contain name");
            return;
        }



        mAuth.createUserWithEmailAndPassword(memail,mpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
                    fuser.sendEmailVerification();
                    User user = new User(mname,mcontactno,memail);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Registration Successful!", Toast.LENGTH_LONG).show();
                                signupProgress.setVisibility(View.GONE);
                                Intent signup_Intent = new Intent(Register.this,LoginActivity.class);
                                startActivity(signup_Intent);
                            }else{
                                Toast.makeText(getApplicationContext(), "Registration Failed!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else {
                    try{
                        throw task.getException();
                    } catch (FirebaseAuthUserCollisionException e){
                        email.setError("User Already Exist");
                    } catch (Exception e) {
                        Log.e("Error",e.getMessage());
                    }
                    /*Toast.makeText(getApplicationContext(), "Failed! try again later", Toast.LENGTH_LONG).show();
                    signupProgress.setVisibility(View.GONE);*/
                }
            }
        });

        /*SharedPreferences regs = PreferenceManager.getDefaultSharedPreferences(Register.this);
        SharedPreferences.Editor editor = regs.edit();
        if(name.getText().toString().isEmpty()){
            name.setError("Please Enter Name");
        }else if(email.getText().toString().isEmpty()){
            email.setError("Please Enter Name");
        }else if(contactno.getText().toString().isEmpty()){
            contactno.setError("Please Enter Name");
        }else if(password.getText().toString().isEmpty()){
            password.setError("Please Enter Name");
        }else{
            editor.putString("Name",name.getText().toString());
            editor.putString("Email",email.getText().toString());
            editor.putString("Contact",contactno.getText().toString());
            editor.putString("Password",password.getText().toString());
            editor.commit();
            Intent signup_Intent = new Intent(Register.this,LoginActivity.class);
            startActivity(signup_Intent);
        }*/
    }
}