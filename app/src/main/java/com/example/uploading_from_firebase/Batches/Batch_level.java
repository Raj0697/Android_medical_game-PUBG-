package com.example.uploading_from_firebase.Batches;

    import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.uploading_from_firebase.R;
import com.example.uploading_from_firebase.databases.Batch1;
import com.example.uploading_from_firebase.helperclasses.Images;
import com.example.uploading_from_firebase.helperclasses.RecycleAdapter2;
import com.example.uploading_from_firebase.helperclasses.RecyclerAdapter;
import com.example.uploading_from_firebase.helperclasses.batches_helper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

public class Batch_level extends AppCompatActivity {

    public RecycleAdapter2 recycleAdapter;
    private Context context;
    private RecyclerView Recyclerview;
    private DatabaseReference databaseReference;
    SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<batches_helper> batchesarraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_level);

        Recyclerview = (RecyclerView) findViewById(R.id.recycle);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);

        batchesarraylist = new ArrayList<>();

        LinearLayoutManager lm = new LinearLayoutManager(this);
        Recyclerview.setLayoutManager(lm);
        Recyclerview.setHasFixedSize(true);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        getdatafromfirebase();
        clearall();
        // swipe refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recycleAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getdatafromfirebase(){
        Query query = databaseReference.child("batch_levels");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearall();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    batches_helper batches_helperobj = new batches_helper();
                    batches_helperobj.setBatchname(snapshot.child("batchname").getValue().toString());
                    batchesarraylist.add(batches_helperobj);
                }
                recycleAdapter = new RecycleAdapter2(getApplicationContext(),batchesarraylist );
                Recyclerview.setAdapter(recycleAdapter);
                recycleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void clearall(){
        if(batchesarraylist != null){
            batchesarraylist.clear();

            if(recycleAdapter != null){
                recycleAdapter.notifyDataSetChanged();
            }
        }
        batchesarraylist = new ArrayList<>();
    }
}