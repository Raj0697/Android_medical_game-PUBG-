package com.example.uploading_from_firebase.helperclasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uploading_from_firebase.R;

import java.util.ArrayList;

public class RecycleAdapter2 extends RecyclerView.Adapter<RecycleAdapter2.ViewHolder> {

    private final Context context;
    private final ArrayList<batches_helper> batchesArrayList;
    private static final String Tag = "RecylerView";

    public RecycleAdapter2(Context context, ArrayList<batches_helper> batchesArrayList) {
        this.context=context;
        this.batchesArrayList=batchesArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.batchlevel_widgets, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {

        holder.textView.setText(batchesArrayList.get(position).getBatchname());
        final batches_helper temp = batchesArrayList.get(position);

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,""+batchesArrayList.get(position).getBatchname(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return batchesArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.batch);
        }

    }
}