package com.example.uploading_from_firebase.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.uploading_from_firebase.Authentication.otp_verification;
import com.example.uploading_from_firebase.R;
import com.hbb20.CountryCodePicker;

public class User_registration2 extends AppCompatActivity {

    ImageView back;
    LinearLayout ll;
    Button next_button, login;
    EditText mobileno;
    CountryCodePicker countryCodePicker;
    String _fullname,_email,_password,_confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user_registration2);

            ll = (LinearLayout)findViewById(R.id.linearLayout);
            next_button = (Button) findViewById(R.id.next_button);
            login = (Button) findViewById(R.id.login_btn);
            mobileno = (EditText) findViewById(R.id.phone_no);
            countryCodePicker = (CountryCodePicker) findViewById(R.id.country_code);
            countryCodePicker.detectSIMCountry(true);
            countryCodePicker.detectNetworkCountry(true);

            next_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String phoneno = mobileno.getText().toString();
                    String country_code = countryCodePicker.getSelectedCountryCodeWithPlus();

                    _fullname = getIntent().getStringExtra("fullName");
                    _email = getIntent().getStringExtra("emailid");
                    _password = getIntent().getStringExtra("password");
                    _confirm_password = getIntent().getStringExtra("confirmpassword");

                    Intent intent = new Intent(User_registration2.this, otp_verification.class);
                    intent.putExtra("phoneno", phoneno);
                    intent.putExtra("country_code", country_code);

                    intent.putExtra("full_name",_fullname);
                    intent.putExtra("email_id", _email);
                    intent.putExtra("password_", _password);
                    intent.putExtra("confirm_password_", _confirm_password);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}