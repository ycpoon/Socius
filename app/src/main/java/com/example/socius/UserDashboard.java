package com.example.socius;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class UserDashboard extends AppCompatActivity {

    //authenticating firebase
    FirebaseAuth firebaseAuth;

    FragmentManager fragmentManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        //intialising
        firebaseAuth = FirebaseAuth.getInstance();

        //for bottom navigaiton
        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);




    }
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                    switch(item.getItemId()){
                        case R.id.nav_groups:
                            GroupFragment groupFragment = new GroupFragment();
                            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                            ft1.replace(R.id.content, groupFragment,"");
                            ft1.commit();
                            return true;
                        case R.id.nav_timer:
                            TimerFragment timerFragment = new TimerFragment();
                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.content, timerFragment,"");
                            ft2.commit();
                            return true;
                        case R.id.nav_courses:
                            CoursesFragment coursesFragment = new CoursesFragment();
                            FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                            ft3.replace(R.id.content, coursesFragment,"");
                            ft3.commit();
                            return true;
                        case R.id.nav_profile:
                            ProfileFragment profileFragment = new ProfileFragment();
                            FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
                            ft4.replace(R.id.content, profileFragment,"");
                            ft4.commit();
                            return true;

                    }
                    return false;
                }
            };


    private void checkUserStatus() {
        //check status of user if signed in or not
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (!(user == null)) {
            //user redirected to registration page


        } else {
            //set email of logged in user to welcome
            startActivity(new Intent(UserDashboard.this, MainActivity.class));
            finish();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart(){
        //checks when the app starts
        checkUserStatus();
        super.onStart();
    }



}