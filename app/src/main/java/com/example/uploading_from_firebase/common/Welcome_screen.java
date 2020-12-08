package com.example.uploading_from_firebase.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.uploading_from_firebase.R;
import com.example.uploading_from_firebase.databases.User_details;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Welcome_screen extends AppCompatActivity {
    ProgressBar progressBar;
    @SuppressWarnings("deprecation")
    ProgressDialog progressDialog;
    TextView tv, tv2;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.welcome_screen);
            // action bar
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            tv = (TextView) findViewById(R.id.textview2);
            tv2 = (TextView) findViewById(R.id.pbText);
            tv2.setText("");
            LottieAnimationView lottieAnimationView = (LottieAnimationView) findViewById(R.id.lottie_layer_name);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void started(View view) {
        progressDialog = new ProgressDialog(Welcome_screen.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(true);
/*
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Welcome_screen.this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        sweetAlertDialog.setTitleText("Loading ...");
        sweetAlertDialog.setCancelable(true);
        sweetAlertDialog.show();
*/
        Intent intent = new Intent(Welcome_screen.this, User_details.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Welcome_screen.this, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setTitleText("Are you sure you want to Exit ?");
        sweetAlertDialog.setCancelText("No");
        sweetAlertDialog.setConfirmText("Yes");
        sweetAlertDialog.setCanceledOnTouchOutside(true);
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                Welcome_screen.super.onBackPressed();
            }
        });
        sweetAlertDialog.show();
    }

}