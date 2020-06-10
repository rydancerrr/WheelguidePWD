package com.rysum.pwdfriend.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rysum.pwdfriend.Adapters.DataAdapter;
import com.rysum.pwdfriend.Models.Data;
import com.rysum.pwdfriend.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MapsActivity extends AppCompatActivity implements
        OnMapReadyCallback{

    Marker marker;
    private DatabaseReference mUsers;
    private GoogleMap mMap;
    public Location lastLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    public Double latitude, longtitude;
    private static final int REQUEST_CODE = 101;
    private RecyclerView recyclerView;
    private DataAdapter adapter;
    Toolbar mActionBarToolbar;
    String temp;

    public static final String VALUE_VALUE = "value";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();

        mActionBarToolbar = findViewById(R.id.toolbar);
        TextView mTitle = mActionBarToolbar.findViewById(R.id.toolbar_title);
        TextView nTitle = findViewById(R.id.navs_1);
        temp = getIntent().getStringExtra(PostCategory.VALUE_VALUE);
        mTitle.setText(temp);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,  LinearLayoutManager.HORIZONTAL, false));

        FirebaseRecyclerOptions<Data> options =
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("data").orderByChild("editCategory").equalTo(temp), Data.class)
                        .build();
        adapter = new DataAdapter(options, this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.nav_places:
                            startActivity(new Intent(getApplicationContext(), ShareActivity.class));
                            finish();

                            break;
                        case R.id.nav_account:
                            startActivity(new Intent(getApplicationContext(), Account.class));
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

    public void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @SuppressLint("ResourceType")
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    lastLocation = location;
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    assert supportMapFragment != null;
                    View mapView = supportMapFragment.getView();
                    if (mapView != null && mapView.findViewById(1) != null) {
                        // Get the button view
                        View locationButton = ((View) mapView.findViewById(1).getParent()).findViewById(2);
                        // and next place it, on bottom right (as Google Maps app)
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                                locationButton.getLayoutParams();
                        // position on right bottom
                        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                        layoutParams.setMargins(0, 0, 30, 30);
                    }
                    supportMapFragment.getMapAsync(MapsActivity.this);

                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMyLocationEnabled(true);
        MapStyleOptions mapStyleOptions = MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json);
        googleMap.setMapStyle(mapStyleOptions);
        final LatLng latLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        mUsers = FirebaseDatabase.getInstance().getReference("data");
        mUsers.push().setValue(marker);
        mUsers.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    final Data data = s.getValue(Data.class);
                    LatLng lol = new LatLng(data.setLatitude, data.setLongitude);

                    mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                        @Override
                        public View getInfoWindow(Marker marker) {


                            return null;
                        }

                        @Override
                        public View getInfoContents(Marker marker) {
                            View v = getLayoutInflater().inflate(R.layout.info_window, null);
                            Data user = (Data) marker.getTag();
                            TextView title =  v.findViewById(R.id.title);
                            TextView detail = v.findViewById(R.id.detail);
                            ImageView image = v.findViewById(R.id.imageShow);
                            Picasso.get().load(user.imageUrl).into(image);
                            title.setText(marker.getTitle());
                            detail.setText(user.getEditDetails());
                            return v;

                        }
                    });

                    MarkerOptions markerOptions = new MarkerOptions().position(lol).title(data.editName);
                    Marker mMarker = mMap.addMarker(markerOptions);
                    mMarker.setTag(data);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("MapsActivity", "loadPost:onCancelled", databaseError.toException());
            }
        });

    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }


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

            case R.id.navs_1:
                onBackPressed();
                break;

        }

    }

}


