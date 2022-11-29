package com.example.test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private LinearLayout layout_login ;
    private Animation animation_fadein;
    private ProgressBar signinProgress;
    private FirebaseAuth mAuth;
    private TextView forget;
    EditText loginid,password;
    Button signin,signup;
    String id,pass ;
    Intent Login;



    private void init(){
        signin = (Button)  findViewById(R.id.signin);
        signup = (Button) findViewById(R.id.signup);
        forget =(TextView) findViewById(R.id.forgot_pass);
        loginid = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        signinProgress=findViewById(R.id.signin_progress);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
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
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = loginid.getText().toString();
                if (TextUtils.isEmpty(id)) {
                    loginid.setError("Enter Email");
                    return;
                }
                mAuth.sendPasswordResetEmail(id).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Password Reset Link Send Successfully",Toast.LENGTH_LONG).show();
                        }else{
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e){
                                loginid.setError("Invalid Id And Password");
                            } catch(Exception e) {
                                Log.e("Error", e.getMessage());
                            }
                            Toast.makeText(getApplicationContext(), "failed! Please try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    public void Signin(){
        /*signinProgress.setVisibility(View.VISIBLE);*/
        id = loginid.getText().toString().trim();
        pass = password.getText().toString();

        if (TextUtils.isEmpty(id)) {
            loginid.setError("Enter Email");
            return;
        }
        /*  if(Patterns.EMAIL_ADDRESS.matcher(id).matches()){
            loginid.setError("Enter Valid Email");
            return;
        }*/
        if (TextUtils.isEmpty(pass)) {
            password.setError("Enter Password");
            return;
        }
        mAuth.signInWithEmailAndPassword(id,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();//current loged in user
                    if (user.isEmailVerified()){
                        Login = new Intent(LoginActivity.this,Dashbord.class);
                        startActivity(Login);
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(getApplicationContext(),"Check Email For Verification Link",Toast.LENGTH_LONG).show();
                    }
                    /*signinProgress.setVisibility(View.GONE);*/
                }else {
                    try {
                        throw task.getException();
                    } catch(FirebaseAuthInvalidCredentialsException e) {
                        loginid.setError("Invalid Id And Password");
                        password.setError("Invalid Id And Password");
                    }catch(Exception e) {
                        Log.e("Error", e.getMessage());
                    }
                    Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                    /*signinProgress.setVisibility(View.GONE);*/
                }

            }
        });


        /*SharedPreferences db = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
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
        }*/
    }
}