package com.rysum.pwdfriend.Activities;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;

import com.rysum.pwdfriend.Models.Data;
import com.rysum.pwdfriend.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class ShareActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener{
    private static final int REQUEST_CODE = 101;
    private static final int PICK_IMAGE_REQUEST = 1;
    Location currentLocation;
    final Marker marker_final = null;
    FusedLocationProviderClient fusedLocationProviderClient;
    private Button btnChoose;
    private Button btnAdd;
    RadioGroup radioGroup;
    private ImageView imageView;
    private EditText editName;
    private EditText editAddress;
    private EditText editDetails;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private CheckBox checkBox5;
    public boolean getBox1;
    public boolean getBox2;
    public boolean getBox3;
    public boolean getBox4;
    public boolean getBox5;
    private double editLatitude;
    private double editLonghitude;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String editImageUrl;
    Toolbar mActionBarToolbar;
    private Spinner spinner1;
    String getAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        mActionBarToolbar = findViewById(R.id.toolbar);
        TextView mTitle =  mActionBarToolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(mActionBarToolbar);
        mTitle.setText("Add a Place");

        addListenerOnSpinnerItemSelection();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        databaseReference = FirebaseDatabase.getInstance().getReference("data");
        storageReference = FirebaseStorage.getInstance().getReference("imageFolder");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();


        editName = findViewById(R.id.editName);
        editDetails = findViewById(R.id.editDetails);
        btnAdd = findViewById(R.id.btnAdd);
      imageView = findViewById(R.id.imageView);
      checkBox1 = findViewById(R.id.acc_sign);
        checkBox2 = findViewById(R.id.acc_park);
        checkBox3 = findViewById(R.id.acc_wheel);
        checkBox4 = findViewById(R.id.acc_wide);
        checkBox5 = findViewById(R.id.acc_room);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);

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
                        case R.id.nav_categories:
                            startActivity(new Intent(getApplicationContext(), Categories.class));
                            finish();
                            break;
                    }


                    return false;
                }
            };


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            Toast.makeText(ShareActivity.this, "Choose Image Completed!", Toast.LENGTH_SHORT).show();
            Uri mImageUri = data.getData();
//
            Picasso.get().load(mImageUri).into(imageView);

            final StorageReference Imagename = storageReference.child("image" + mImageUri.getLastPathSegment());

            Imagename.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            editImageUrl = String.valueOf(uri);

                        }
                    });
                }
            });
        }
    }



    public void addData() {

        if(checkBox1.isChecked()){
            getBox1= true;
        } else {
           getBox1= false;
        }

        if(checkBox2.isChecked()){
            getBox2= true;
        }else {
            getBox2= false;
        }

        if(checkBox3.isChecked()){
            getBox3= true;
        }
        else {
             getBox3= false;
        }
        if(checkBox4.isChecked()){
            getBox4= true;
        }else {
            getBox4= false;
        }
        if(checkBox5.isChecked()){
            getBox5= true;
        }else {
            getBox5= false;
        }




        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());


        try {
            addresses = geocoder.getFromLocation(editLatitude, editLonghitude, 1);

            if (addresses != null && addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String country = addresses.get(0).getCountryName();

                getAddress = address;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String getName = editName.getText().toString();
        String getDetails = editDetails.getText().toString();
        double getLatitude = editLatitude;
        double getLonghitude = editLonghitude;
        String getCategory = String.valueOf(spinner1.getSelectedItem());
        String getImageUrl = editImageUrl;


        if (!TextUtils.isEmpty(getName) && !TextUtils.isEmpty(getDetails)) {
            String id = databaseReference.push().getKey();
            Data data = new Data(id,getName,getDetails,getCategory, getAddress, getLonghitude,getLatitude,getImageUrl, getBox1, getBox2, getBox3, getBox4, getBox5,0);
            databaseReference.child(id).setValue(data);

            Toast toast = Toast.makeText(getApplicationContext(),"Successfully Added!",Toast.LENGTH_SHORT);
            toast.show();

        } else {
            Toast.makeText(getApplicationContext(), "Please Fill All the Details!", Toast.LENGTH_LONG).show();
        }


    }


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
                    currentLocation = location;
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
                    assert supportMapFragment != null;
                    View mapView = supportMapFragment.getView();
                    if (mapView != null &&
                            mapView.findViewById(1) != null) {
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
                    supportMapFragment.getMapAsync(ShareActivity.this);

                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        final LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
//        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
//        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19));
        MapStyleOptions mapStyleOptions = MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json);
        googleMap.setMapStyle(mapStyleOptions);

        googleMap.addMarker(new MarkerOptions().title("Is this the Right Location?").snippet("Press a few seconds on this marker to Drag").position(latLng).draggable(true)).showInfoWindow();
        googleMap.setOnMarkerDragListener(this);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        editLatitude = currentLocation.getLatitude();
        editLonghitude = currentLocation.getLongitude();


    }




    @Override
    public void onMarkerDragStart(Marker marker) {

        NestedScrollView mScrollview = findViewById(R.id.scroll);
mScrollview.requestDisallowInterceptTouchEvent(true);
        LatLng position0 = marker.getPosition();
        marker.hideInfoWindow();
        Log.d(getClass().getSimpleName(), String.format("Drag from %f:%f",
                position0.latitude,
                position0.longitude));
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        LatLng position0 = marker.getPosition();

        NestedScrollView mScrollview = findViewById(R.id.scroll);
        mScrollview.requestDisallowInterceptTouchEvent(true);

        Log.d(getClass().getSimpleName(),
                String.format("Dragging to %f:%f", position0.latitude,
                        position0.longitude));
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng position = marker.getPosition();

        Log.d(getClass().getSimpleName(), String.format("Dragged to %f:%f",
                position.latitude,
                position.longitude));

        editLatitude = position.latitude;
        editLonghitude = position.longitude;
    }



}