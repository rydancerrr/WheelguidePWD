package com.rysum.pwdfriend.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.rysum.pwdfriend.Models.Data;
import com.rysum.pwdfriend.R;
import com.rysum.pwdfriend.Activities.ShowData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DataAdapter extends FirebaseRecyclerAdapter<Data, DataAdapter.PastViewHolder> {


    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;
    private DatabaseReference mUsers;

    public DataAdapter(@NonNull FirebaseRecyclerOptions<Data> options, Context context) {
        super(options);
        mContext = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull PastViewHolder holder, int i, @NonNull final Data data) {
        holder.title.setText(data.getEditName());
        holder.description.setText(data.getEditAddress());
        Picasso.get().load(data.getImageUrl()).into(holder.imgurl);
        if (data.isSetBox1() != true){
            holder.set1.setVisibility(View.GONE);
        }if (data.isSetBox2() != true){
            holder.set2.setVisibility(View.GONE);
        }if (data.isSetBox3() != true){
            holder.set3.setVisibility(View.GONE);
        }if (data.isSetBox4() != true){
            holder.set4.setVisibility(View.GONE);
        }
        if (data.isSetBox5() != true){
            holder.set5.setVisibility(View.GONE);
        }
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            //Start new list activity
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowData.class);
               intent.putExtra("get_id", data.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public PastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.info_window, parent, false);
        return new PastViewHolder(view);
    }

    class PastViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout parentLayout;
        TextView title,description;
        ImageView imgurl;
       ImageView set1,set2,set3,set4, set5;

        public PastViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titlei);
            description = itemView.findViewById(R.id.detail);
            imgurl = itemView.findViewById(R.id.imageShow);
            set5 = itemView.findViewById(R.id.acc_room);
            set2 = itemView.findViewById(R.id.acc_park);
            set1 = itemView.findViewById(R.id.acc_sign);
            set4 = itemView.findViewById(R.id.acc_wide);
            set3 = itemView.findViewById(R.id.acc_wheel);
            parentLayout = itemView.findViewById(R.id.parentLayout);




        }
        ;
    }
}