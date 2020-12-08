package com.example.uploading_from_firebase.helperclasses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uploading_from_firebase.common.Expert_activity;
import com.example.uploading_from_firebase.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public static int total_images;
    private static final String Tag = "RecylerView";
    private final Context mcontext;
    private final ArrayList<Images> imagesArrayList;

    public RecyclerAdapter(Context mcontext, ArrayList<Images> imagesArrayList) {
        this.mcontext = mcontext;
        this.imagesArrayList = imagesArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_widgets, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        // ImageView : glide library
        Glide.with(mcontext).load(imagesArrayList.get(position).getImageUrl()).into(holder.img);

        // concatinating the db values to strings
        holder.correct.concat(imagesArrayList.get(position).getCorrect());
        holder.wrongans1.concat(imagesArrayList.get(position).getWrong1());
        holder.wrongans2.concat(imagesArrayList.get(position).getWrong2());
        holder.id.concat(imagesArrayList.get(position).getId());

        final Images temp = imagesArrayList.get(position);

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(Tag, "onclicked: clicked on: " + imagesArrayList.get(position));
                Bundle b = new Bundle();
                b.putString("key", imagesArrayList.get(position).getImageUrl());

                Intent intent = new Intent(mcontext, Expert_activity.class);
                intent.putExtra("images", temp.getImageUrl());
                intent.putExtra("valid",temp.getCorrect());
                intent.putExtra("invalid",temp.getWrong1());
                intent.putExtra("invalid2",temp.getWrong2());
                intent.putExtra("Image_id",temp.getId());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        total_images = imagesArrayList.size();
        // storing the value in global variable so that we can access it in anywhere in the project...
        userdetails.total_images = total_images;
        return imagesArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView img;
        final String correct="";
        final String wrongans1="";
        final String wrongans2="";
        final String id="";
        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img1);
        }

    }
}
