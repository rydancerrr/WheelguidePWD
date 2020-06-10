package com.rysum.pwdfriend.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rysum.pwdfriend.Adapters.RatingAdapter;
import com.rysum.pwdfriend.Models.Data;
import com.rysum.pwdfriend.R;


public class ShowData extends AppCompatActivity
{

    FirebaseFirestore fStore;
    FirebaseDatabase fBase;
    DatabaseReference mUsers;
    DatabaseReference ratingUser;
    RatingBar mRatingBar;
    TextView mRatingScale;
    private EditText mFeedback;
    Button mSendFeedback;
    String temp;
    private RatingAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        fStore = FirebaseFirestore.getInstance();
        mRatingBar = findViewById(R.id.simpleRatingBarGet);
        mRatingScale =  findViewById(R.id.tvRatingScale);
        mFeedback =  findViewById(R.id.etFeedback);
        mSendFeedback = findViewById(R.id.btnSubmit);
        mUsers = FirebaseDatabase.getInstance().getReference("data");
        ratingUser = FirebaseDatabase.getInstance().getReference("rating");
        temp = getIntent().getStringExtra("get_id");

        mUsers = fBase.getInstance().getReference().child("data");
        Query data = mUsers.orderByChild("id").equalTo(temp);
        data.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    Data data = s.getValue(Data.class);
                    TextView title =  findViewById(R.id.editName);
                    title.setText(data.editName);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("ShowData", "loadPost:onCancelled", databaseError.toException());
            }
        });

        ratingUser = fBase.getInstance().getReference("rating");
        Query rating = ratingUser.orderByChild("id2").equalTo(temp);
        rating.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                float total = 0;
                float count = 0;
                float average = 0;
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    Rating rating = s.getValue(Rating.class);
                    float rating1 = rating.reviewerRating;
                    total = total + rating1;
                    count = count + 1;
                    average = total / count;
                    RatingBar ratingShow = findViewById(R.id.simpleRatingBarShow);
                    ratingShow.setRating(average);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("ShowData", "loadPost:onCancelled", databaseError.toException());
            }
        });


        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Rating> options =
                new FirebaseRecyclerOptions.Builder<Rating>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("rating").orderByChild("id2").equalTo(temp), Rating.class)
                        .build();
        adapter = new RatingAdapter(options, this);
        recyclerView.setAdapter(adapter);

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mRatingScale.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 0:
                        mRatingScale.setText("Very poor");
                        break;
                    case 1:
                        mRatingScale.setText("Very bad");
                        break;
                    case 2:
                        mRatingScale.setText("Need some improvement");
                        break;
                    case 3:
                        mRatingScale.setText("Good");
                        break;
                    case 4:
                        mRatingScale.setText("Great");
                        break;
                    case 5:
                        mRatingScale.setText("Awesome. I love it");
                        break;
                    default:
                        mRatingScale.setText("");
                }
            }
        });


        mSendFeedback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (false) {
                    Toast.makeText(ShowData.this, "Please fill in feedback text box", Toast.LENGTH_LONG).show();
                } else {
                    addData();
                }
            }
        });

    }

    public void addData() {

        String getReviewerName = "Sample";
        float getReviewerRating = mRatingBar.getRating();
        String getReviewerFeedback =  mFeedback.getText().toString();
        String getReviewerImage = "Sample";
        String id = ratingUser.push().getKey();
        Rating rating = new Rating(id,temp,getReviewerName,getReviewerRating,getReviewerFeedback,getReviewerImage);
        ratingUser.child(id).setValue(rating);
        Toast.makeText(ShowData.this, "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();

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




    public void Back(View view) {
        onBackPressed();
    }

}