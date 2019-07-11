package com.example.corgigram.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.example.corgigram.home.HomeActivity;
import com.example.corgigram.likes.LikesActivity;
import com.example.corgigram.profile.ProfileActivity;
import com.example.corgigram.R;
import com.example.corgigram.search.SearchActivity;
import com.example.corgigram.share.ShareActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

// Helper Class for Styling Bottom NavBar
public class BottomNavigationViewHelper {
    static final String TAG = "BottomNavViewHelper";

    public static void setUpBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "setting up bottom navigation view...");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.ic_house:  // ACTIVITY_NUM = 0
                        Intent it1 = new Intent(context, HomeActivity.class);
                        context.startActivity(it1);
                        break;
                    case R.id.ic_search:  // ACTIVITY_NUM = 1
                        Intent it2 = new Intent(context, SearchActivity.class);
                        context.startActivity(it2);
                        break;
                    case R.id.ic_add:  // ACTIVITY_NUM = 2
                        Intent it4 = new Intent(context, ShareActivity.class);
                        context.startActivity(it4);
                        break;
                    case R.id.ic_alert:  // ACTIVITY_NUM = 3
                        Intent it3 = new Intent(context, LikesActivity.class);
                        context.startActivity(it3);
                        break;

                    case R.id.ic_prof:  // ACTIVITY_NUM = 4
                        Intent it5 = new Intent(context, ProfileActivity.class);
                        context.startActivity(it5);
                        break;
                }
                return false;
            }
        });
    }
}

