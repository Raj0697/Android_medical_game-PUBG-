package com.example.uploading_from_firebase.Authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

import com.example.uploading_from_firebase.Batches.Batch_level;
import com.example.uploading_from_firebase.R;
import com.example.uploading_from_firebase.common.Welcome_screen;
import com.example.uploading_from_firebase.helperclasses.userdetails;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    public static Profile INSTANCE;
    public static String Emailid = "";

    private TextView name, email;
    private Button logout;
    private ImageView imageView;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        INSTANCE = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = (TextView) findViewById(R.id.username);
        email = (TextView) findViewById(R.id.email);
        logout = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.gif);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraint);

        name.setText(getIntent().getStringExtra("fn"));
        email.setText(getIntent().getStringExtra("mailid"));

        String em = getIntent().getStringExtra("mailid");
        Emailid += em;
        //userdetails.emailaddress = getIntent().getStringExtra("mailid");

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleSignInAccount != null) {
            name.setText(googleSignInAccount.getDisplayName());
            email.setText(googleSignInAccount.getEmail());
            userdetails.emailaddress = googleSignInAccount.getEmail();
        }

        Glide.with(this).load(R.drawable.pathologist_resize).into(imageView);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Welcome_screen.class);
                startActivity(intent);
            }
        });

        // dynamic button
        final Button button = new Button(this);
        button.setLayoutParams(new ConstraintLayout.LayoutParams(500, 150));
        button.setText("PLAY");
        button.setGravity(Gravity.CENTER);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = getIntent().getStringExtra("mob");
                String fn = getIntent().getStringExtra("fn");

                Intent intent = new Intent(getApplicationContext(), Batch_level.class);
                intent.putExtra("mobile_no", mobile);
                intent.putExtra("fullname", fn);
                startActivity(intent);
            }
        });
        constraintLayout.addView(button);
    }

    public static Profile getActivityInstance() {
        return INSTANCE;
    }

    public String getData() {
        return this.Emailid;
    }
}