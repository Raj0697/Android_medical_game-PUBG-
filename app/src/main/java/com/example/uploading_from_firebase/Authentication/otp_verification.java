package com.example.uploading_from_firebase.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.uploading_from_firebase.R;
import com.example.uploading_from_firebase.helperclasses.Userdata;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.concurrent.TimeUnit;

public class otp_verification extends AppCompatActivity {
    Button verify;
    ImageView close;
    PinView pinView;
    ProgressBar progressBar;
    String verification_code_by_system;
    private FirebaseAuth firebaseAuth;
    String phone_no,country_code,fullName,email,password,confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_otp_verification);

            pinView = (PinView) findViewById(R.id.otp_pinview);
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);
            verify = (Button) findViewById(R.id.verify_button);
            close = (ImageView) findViewById(R.id.close);

            phone_no = getIntent().getStringExtra("phoneno");
            country_code = getIntent().getStringExtra("country_code");

            fullName = getIntent().getStringExtra("full_name");
            email = getIntent().getStringExtra("email_id");
            password = getIntent().getStringExtra("password_");
            confirm_password = getIntent().getStringExtra("confirm_password_");

            sendverificationcode(phone_no, country_code);

            verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String code = pinView.getText().toString();

                    if (code.isEmpty() || code.length() < 6) {
                        pinView.setError("Wrong OTP");
                        pinView.requestFocus();
                        return;
                    } else {
                        pinView.setText(code);
                        progressBar.setVisibility(View.VISIBLE);
                        verifycode(code);
                    }

                }
            });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void sendverificationcode(String phone, String country) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                country + phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                (Activity) TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verification_code_by_system = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                progressBar.setVisibility(View.VISIBLE);
                verifycode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(otp_verification.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };

    private void verifycode(String codebyuser) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verification_code_by_system, codebyuser);
        signIntheuserbycredentials(credential);

        storeuserdata();
    }

    private void signIntheuserbycredentials(PhoneAuthCredential credential) {

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(otp_verification.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    storeuserdata();

                } else {
                    Toast.makeText(otp_verification.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("Error :",task.getException().getMessage());
                }
            }
        });
    }

    private void storeuserdata() {

       FirebaseDatabase rootnode = FirebaseDatabase.getInstance();
       DatabaseReference reference = rootnode.getReference("Users");

       Userdata userdata = new Userdata(fullName,email,password,confirm_password,phone_no);
       reference.child(phone_no).setValue(userdata);
       TastyToast.makeText(getApplicationContext(),"Data stored successfully",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS).show();

       Intent intent = new Intent(otp_verification.this, Profile.class);
       intent.putExtra("mobileno",phone_no);
       intent.putExtra("fullname",fullName);
       intent.putExtra("email",email);
       startActivity(intent);
    }
}