package com.rysum.pwdfriend.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rysum.pwdfriend.Adapters.DataAdapter;
import com.rysum.pwdfriend.Models.Data;
import com.rysum.pwdfriend.R;

public class PostCategory extends AppCompatActivity implements RecyclerView.OnClickListener {
    Toolbar mActionBarToolbar;
    public static final String VALUE_VALUE = "value";
    private EditText title,description,author;
    private TextView text;
    private Button save,read;
    private DatabaseReference Post;
    private RecyclerView recyclerView;
    private DataAdapter adapter;
    String temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_categories);




        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.nav_categories);
        mActionBarToolbar = findViewById(R.id.toolbar);
        TextView mTitle = mActionBarToolbar.findViewById(R.id.toolbar_title);
        temp = getIntent().getStringExtra(VALUE_VALUE);
        mTitle.setText(temp);


        setSupportActionBar(mActionBarToolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Data> options =
                new FirebaseRecyclerOptions.Builder<Data>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("data").orderByChild("editCategory").equalTo(temp), Data.class)
                        .build();
        adapter = new DataAdapter(options, this);
        recyclerView.setAdapter(adapter);

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
                            startActivity(new Intent(getApplicationContext(),ShareActivity.class));
                            finish();
                            break;
                    }


                    return false;
                }
            };


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void onClick(View view) {
        Intent intent;

        switch (view.getId()) {

            case R.id.navs_2:
                intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra(VALUE_VALUE, temp);
                startActivity(intent);
                break;

        }

    }









}





