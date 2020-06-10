package com.rysum.pwdfriend.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.rysum.pwdfriend.R;
import com.rysum.pwdfriend.Activities.Rating;

public class RatingAdapter extends FirebaseRecyclerAdapter<Rating, RatingAdapter.PastViewHolder> {

    private Context mContext;
    private DatabaseReference ratingUsers;

    public RatingAdapter(@NonNull FirebaseRecyclerOptions<Rating> options, Context context) {
        super(options);
        mContext = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull PastViewHolder holder, int i, @NonNull final Rating rating) {
        holder.reviewerName.setText(rating.getReviewerName());
        holder.reviewerFeedback.setText(rating.getReviewerFeedback());
        holder.reviewerRating.setRating(rating.getReviewerRating());

    }

    @NonNull
    @Override
    public PastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.info_review, parent, false);
        return new PastViewHolder(view);
    }

    class PastViewHolder extends RecyclerView.ViewHolder{

        TextView reviewerName,reviewerFeedback;
        RatingBar reviewerRating;

        public PastViewHolder(@NonNull View itemView) {
            super(itemView);
             reviewerName = itemView.findViewById(R.id.reviewerName);
            reviewerFeedback = itemView.findViewById(R.id.etFeedback);
            reviewerRating = itemView.findViewById(R.id.simpleRatingBarShow1);




        }
        ;
    }
}