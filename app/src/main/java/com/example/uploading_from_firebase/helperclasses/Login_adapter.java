package com.example.uploading_from_firebase.helperclasses;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.uploading_from_firebase.Authentication.Logintab_fragment;
import com.example.uploading_from_firebase.common.Signuptab_fragment;

public class Login_adapter extends FragmentPagerAdapter {

    private Context context;
    int total_tabs;

    public Login_adapter(@NonNull FragmentManager fm, Context context, int total_tabs) {
        super(fm);
        this.context = context;
        this.total_tabs = total_tabs;
    }

    @Override
    public int getCount() {
        return total_tabs;
    }

    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Signuptab_fragment signuptab_fragment = new Signuptab_fragment();
                return signuptab_fragment;
            case 1:
                Logintab_fragment logintab_fragment = new Logintab_fragment();
                return logintab_fragment;
            default:
                return null;
        }
    }

}
