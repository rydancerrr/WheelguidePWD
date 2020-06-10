package com.rysum.pwdfriend.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rysum.pwdfriend.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.nav_account:
                            startActivity(new Intent(getApplicationContext(), Account.class));
                            finish();
                            break;
                        case R.id.nav_places:
                            startActivity(new Intent(getApplicationContext(), ShareActivity.class));
                            finish();
                            break;
                        case R.id.nav_categories:
                            startActivity(new Intent(getApplicationContext(), Categories.class));
                            finish();
                            break;
                    }


                    return false;
                }
            };


    public void onClick(View view){


        switch (view.getId()){

            case R.id.button:
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                break;

        }

    }
}