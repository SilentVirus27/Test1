package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;

import java.security.PrivateKey;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class Dashbord extends AppCompatActivity {
    private FirebaseAuth mAuth;
    MeowBottomNavigation bottomNavigation;
    private final int ID_HOME =1;
    private final int ID_REPORT =2;
    private final int ID_ADD =3;
    private final int ID_CATEGORY =4;
    private final int ID_PROFILE =5;

    private void inti(){
        mAuth = FirebaseAuth.getInstance();
        bottomNavigation = findViewById(R.id.bottom_nevigation);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);
        inti();
        Utils.blackIconStatusBar(Dashbord.this,R.color.light_background);

        bottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME,R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_REPORT,R.drawable.ic_baseline_report_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_ADD,R.drawable.ic_baseline_add_circle_outline_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_CATEGORY,R.drawable.ic_baseline_style_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_PROFILE,R.drawable.ic_baseline_account_circle_24));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener(){
            @Override
            public void onShowItem(MeowBottomNavigation.Model item){
                //Initialize Fragment
                Fragment fragment =null;
                //Check Condition
                switch (item.getId()){
                    case ID_HOME:fragment=new HomeFragment();
                        break;
                    case ID_REPORT:fragment=new ReportFragment();
                        break;
                    case ID_ADD:fragment=new IncomeExpenseFragment();
                        break;
                    case ID_CATEGORY:fragment=new CategoryFragment();
                        break;
                    case ID_PROFILE:fragment=new ProfileFragment();
                        break;
                }
                //load fragment
                loadFragment(fragment);
            }
        });
        //set notificaton count
        bottomNavigation.setCount(5,"0");
        //set home fragement initially selected
        bottomNavigation.show(1,true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //Display Toast
                Toast.makeText(getApplicationContext(), "You Clicked" + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                //Display Toast
                Toast.makeText(getApplicationContext(), "You Clicked" + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        //Replace Fragement
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_frame,fragment)
                .commit();
    }
}











