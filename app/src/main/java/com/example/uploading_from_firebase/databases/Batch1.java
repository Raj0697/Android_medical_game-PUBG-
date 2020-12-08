package com.example.uploading_from_firebase.databases;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.example.uploading_from_firebase.helperclasses.Images;
import com.example.uploading_from_firebase.R;
import com.example.uploading_from_firebase.helperclasses.RecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import com.sdsmdg.tastytoast.TastyToast;

public class Batch1 extends AppCompatActivity {

    private ArrayList<Images> imagelist;
    private RecyclerAdapter recyclerAdapter;
    private Context mcontext;
    private RecyclerView recycleview;
    private DatabaseReference ref;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_level);

        recycleview = (RecyclerView) findViewById(R.id.recycle);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);

        LinearLayoutManager lm = new LinearLayoutManager(this);
        recycleview.setLayoutManager(lm);
        recycleview.setHasFixedSize(true);

        ref = FirebaseDatabase.getInstance().getReference();

        imagelist = new ArrayList<>();

        checkconnection();

        getdatafromfirebase();
        clearall();
        // swipe refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void getdatafromfirebase() {
        Query query = ref.child("Batch1");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clearall();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Images images = new Images();
                    images.setImageUrl(snapshot.child("image").getValue().toString());
                    images.setCorrect(snapshot.child("validanswer").getValue().toString());
                    images.setWrong1(snapshot.child("wronganswer1").getValue().toString());
                    images.setWrong2(snapshot.child("wronganswer2").getValue().toString());
                    images.setId(snapshot.child("id").getValue().toString());
                    imagelist.add(images);
                }
                recyclerAdapter = new RecyclerAdapter(getApplicationContext(), imagelist);
                recycleview.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void clearall() {
        if (imagelist != null) {
            imagelist.clear();

            if (recyclerAdapter != null) {
                recyclerAdapter.notifyDataSetChanged();
            }
        }
        imagelist = new ArrayList<>();

    }

    private void checkconnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (null != networkInfo) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                TastyToast.makeText(getApplicationContext(), "Wifi Enabled !", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                TastyToast.makeText(getApplicationContext(), "Data Network Enabled !", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
            }
        } else {
            TastyToast.makeText(getApplicationContext(), "No Internet Connection !", TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
        }
    }
}