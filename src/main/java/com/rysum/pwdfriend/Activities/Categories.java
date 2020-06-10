package com.rysum.pwdfriend.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rysum.pwdfriend.R;


public class Categories extends AppCompatActivity {
    Toolbar mActionBarToolbar;
    public static final String VALUE_VALUE = "value";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.nav_categories);
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) mActionBarToolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(mActionBarToolbar);
        mTitle.setText("Categories");

        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
//                            case R.id.nav_guide:
//                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                            break;
//                        case R.id.nav_home:
//                            startActivity(new Intent(getApplicationContext(),UserActivity.class));
//                            break;


                    }


                    return false;
                }
            };


        public void onClick(View view){
                Intent intent;

            switch (view.getId()){

                case R.id.banks:
                    intent = new Intent(getApplicationContext(), PostCategory.class);
                    intent.putExtra(VALUE_VALUE, "Banks");
                    startActivity(intent);
                    break;
                case R.id.Cinema:
                    intent = new Intent(getApplicationContext(),PostCategory.class);
                    intent.putExtra(VALUE_VALUE, "Cinema");
                    startActivity(intent);
                    break;

                case R.id.Cafe:
                    intent = new Intent(getApplicationContext(),PostCategory.class);
                    intent.putExtra(VALUE_VALUE, "Cafe");
                    startActivity(intent);
                    break;

                case R.id.Hospitals:

                    intent = new Intent(getApplicationContext(),PostCategory.class);
                    intent.putExtra(VALUE_VALUE, "Hospitals");
                    startActivity(intent);
                    break;

                case R.id.Museums:
                    intent = new Intent(getApplicationContext(),PostCategory.class);
                    intent.putExtra(VALUE_VALUE, "Museums");
                    startActivity(intent);

                    break;
                case R.id.Malls:
                    intent = new Intent(getApplicationContext(),PostCategory.class);

                    intent.putExtra(VALUE_VALUE, "Malls");
                    startActivity(intent);

                    break;

                case R.id.Parks:
                    intent = new Intent(getApplicationContext(),PostCategory.class);
                    intent.putExtra(VALUE_VALUE, "Parks");
                    startActivity(intent);
                    break;

                case R.id.Restaurant:
                    intent = new Intent(getApplicationContext(),PostCategory.class);
                    intent.putExtra(VALUE_VALUE, "Restaurant");
                    startActivity(intent);

                    break;




            }

        }

}







