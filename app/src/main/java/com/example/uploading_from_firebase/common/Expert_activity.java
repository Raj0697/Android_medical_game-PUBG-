package com.example.uploading_from_firebase.common;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.uploading_from_firebase.Authentication.Profile;
import com.example.uploading_from_firebase.R;
import com.example.uploading_from_firebase.helperclasses.userdetails;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Expert_activity extends AppCompatActivity {
    PhotoView photoView;
    RadioGroup radioGroup;
    RadioButton correct,wrong1,wrong2;
    int id,total_images;
    String imgid="";
    String Emailid_entered_by_user="";
    String Emailid_takenby_system="";
    String Not_null_emailid="";
    String em="",em2="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_activity);

        photoView = (PhotoView) findViewById(R.id.photo_view);
        final String s = getIntent().getStringExtra("images");

        final String correct_ans = getIntent().getStringExtra("valid");
        final String wrong_ans = getIntent().getStringExtra("invalid");
        final String wrong_ans2 = getIntent().getStringExtra("invalid2");
        imgid += getIntent().getStringExtra("Image_id");
        id = Integer.parseInt(imgid);
        //Toast.makeText(this,"Image id :"+id,Toast.LENGTH_LONG).show();

        Glide.with(this).asBitmap().load(s).into(photoView);

        radioGroup = findViewById(R.id.rg);
        correct = findViewById(R.id.radioButton);
        wrong1 = findViewById(R.id.radioButton2);
        wrong2 = findViewById(R.id.radioButton3);

        // radio buttons
        correct.setText(correct_ans);
        wrong1.setText(wrong_ans);
        wrong2.setText(wrong_ans2);

        em += Profile.getActivityInstance().getData();
        em2 += userdetails.emailaddress;
        //Toast.makeText(getApplicationContext(),""+Profile.getActivityInstance().getData(),Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(),""+em2,Toast.LENGTH_LONG).show();

        Emailid_takenby_system += em2.replace(".",",");
        Emailid_entered_by_user += em.replace(".",",");

        //FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        //Toast.makeText(getApplicationContext(),""+currentuser.getUid(),Toast.LENGTH_LONG).show();
        
        if(Emailid_entered_by_user == null){
            Not_null_emailid += Emailid_takenby_system;
        }
        else{
            Not_null_emailid += Emailid_entered_by_user;
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //int id = radioGroup.getCheckedRadioButtonId();
                switch (checkedId)
                {
                    case R.id.radioButton:
                        storing_useranswers(correct_ans,Not_null_emailid);
                        break;
                    case R.id.radioButton2:
                        storing_useranswers(wrong_ans,Not_null_emailid);
                        break;
                    case R.id.radioButton3:
                        storing_useranswers(wrong_ans2,Not_null_emailid);
                        break;
                }
            }
        });

        total_images += userdetails.total_images;
       // Toast.makeText(getApplicationContext(),""+total_images,Toast.LENGTH_LONG).show();
      //  Toast.makeText(getApplicationContext(),""+Emailid_takenby_system,Toast.LENGTH_LONG).show();
      //  Toast.makeText(getApplicationContext(),""+Emailid_entered_by_user,Toast.LENGTH_LONG).show();
    }

    private void storing_useranswers(String answer, String emailaddress)
    {
        for(int i=1; i<=total_images; i++){
            if(id == i)
            {
                FirebaseDatabase rootnode = FirebaseDatabase.getInstance();
                DatabaseReference reference = rootnode.getReference("Batch1_user_answers");
                //reference.child(emailaddress).child(imgid).setValue(answer);
                reference.child("Imageid :"+id).child(emailaddress).setValue(answer);
            }
        }
    }
}