package com.example.uploading_from_firebase.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.uploading_from_firebase.R;

public class Signuptab_fragment extends Fragment {

    EditText e1,e2,e3,e4;
    Button b;
    float v=0;
    String fullname,Email,password,confirm_password;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.signuptab_fragment,container,false);

        e1 = viewGroup.findViewById(R.id.fullname);
        e2 = viewGroup.findViewById(R.id.email_id);
        e3 = viewGroup.findViewById(R.id.pass);
        e4 = viewGroup.findViewById(R.id.confirm_pass);
        b = viewGroup.findViewById(R.id.signup);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fullname = e1.getText().toString().trim();
                Email = e2.getText().toString().trim();
                password = e3.getText().toString().trim();
                confirm_password = e4.getText().toString().trim();

                Intent intent = new Intent(getActivity(), User_registration2.class);
                intent.putExtra("fullName",fullname);
                intent.putExtra("emailid",Email);
                intent.putExtra("password",password);
                intent.putExtra("confirmpassword",confirm_password);
                startActivity(intent);
            }
        });

        e1.setTranslationX(800);
        e2.setTranslationX(800);
        e3.setTranslationX(800);
        e4.setTranslationX(800);
        b.setTranslationX(800);

        e1.setAlpha(v);
        e2.setAlpha(v);
        e3.setAlpha(v);
        e4.setAlpha(v);
        b.setAlpha(v);

        e1.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        e2.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        e3.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        e4.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        b.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();

        return viewGroup;
    }

}
