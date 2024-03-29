package com.example.corgigram.likes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.corgigram.R;
import com.example.corgigram.util.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * @author      Clarisa Leu-Rodriguez <clarisaleu@gmail.com>
 * Description: Likes Activity for CorgiGram
 */
public class LikesActivity extends AppCompatActivity {
    private static final String TAG = "LikesActivity";
    private static final int ACTIVITY_NUM = 2;
    private Context mContext = LikesActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        setUpBottomNavigationView();
    }

    // BottomNavBar Setup
    private void setUpBottomNavigationView() {
        Log.d(TAG, "setUpBottomNavigationView(): setting up your bottom nav bar...");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavView);
        BottomNavigationViewHelper.setUpBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
