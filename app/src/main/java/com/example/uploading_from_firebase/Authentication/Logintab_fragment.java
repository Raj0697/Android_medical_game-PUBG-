package com.example.uploading_from_firebase.Authentication;

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

import com.example.uploading_from_firebase.Authentication.Profile;
import com.example.uploading_from_firebase.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

public class Logintab_fragment extends Fragment {

    EditText e1, e2;
    Button b;
    float v = 0;
    String mobile, password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.logintab_fragment, container, false);

        e1 = viewGroup.findViewById(R.id.mobileno);
        e2 = viewGroup.findViewById(R.id.login_password);
        b = viewGroup.findViewById(R.id.login_button);

        e1.setTranslationX(800);
        e2.setTranslationX(800);
        b.setTranslationX(800);

        e1.setAlpha(v);
        e2.setAlpha(v);
        b.setAlpha(v);

        e1.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        e2.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        b.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile = e1.getText().toString().trim();
                password = e2.getText().toString().trim();

                // database
                Query checkuser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phone_no").equalTo(mobile);
                checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            e1.setError(null);

                            String systempassword = snapshot.child(mobile).child("password").getValue(String.class);
                            if (systempassword.equals(password)) {
                                e2.setError(null);

                                String _fullName = snapshot.child(mobile).child("fullName").getValue(String.class);
                                String _Email = snapshot.child(mobile).child("email").getValue(String.class);
                                String _password = snapshot.child(mobile).child("password").getValue(String.class);
                                String _confirmpass = snapshot.child(mobile).child("confirm_password").getValue(String.class);

                                Intent intent = new Intent(getActivity(), Profile.class);
                                intent.putExtra("fn",_fullName);
                                intent.putExtra("pass",_password);
                                intent.putExtra("mailid",_Email);
                                intent.putExtra("confirmpass",_confirmpass);
                                intent.putExtra("mob",mobile);
                                startActivity(intent);

                            } else {
                                TastyToast.makeText(getActivity(), "Password doesn't match!", TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
                            }
                        } else {
                            TastyToast.makeText(getActivity(), "No such User exists !", TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        TastyToast.makeText(getActivity(), error.getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
                    }
                });
            }
        });

        return viewGroup;
    }
}
