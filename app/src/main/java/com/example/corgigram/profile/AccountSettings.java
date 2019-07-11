package com.example.corgigram.profile;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.corgigram.R;
import com.example.corgigram.util.SectionsStatePagerAdapter;

import java.util.ArrayList;

public class AccountSettings extends AppCompatActivity {
    private final static String TAG = "AccountSettings";
    private Context mContext;
    private SectionsStatePagerAdapter pagerAdapter;
    private ViewPager mViewPager;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        Log.d(TAG, "onCreate started");

        mContext = AccountSettings.this;
        mViewPager = (ViewPager) findViewById(R.id.container);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relLayout1);

        setUpSettingsList();
        setUpFragments();

        // Set up back arrow click listener to go back to profile activity
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: navigating back to profile page");
               finish();
            }
        });

    }

    private void setUpFragments(){
        pagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new EditProfileFragment(), getString(R.string.edit_profile));  // fragment 0
        pagerAdapter.addFragment(new SignOutFragment(), getString(R.string.sign_out));  // fragment 1
    }

    private void setUpViewPager(int fragNumber){
        mRelativeLayout.setVisibility(View.GONE);
        Log.d(TAG, "setUpViewPager: navigating to fragment number: "+fragNumber);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(fragNumber) ;

    }

    private void setUpSettingsList(){
        Log.d(TAG, "setUpSettingsList: initializing account settings list.");
        ListView listView = (ListView) findViewById(R.id.lvAccountSettings);

        ArrayList<String>  options = new ArrayList<>();
        options.add(getString(R.string.edit_profile));
        options.add(getString(R.string.sign_out));

        ArrayAdapter adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, options);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: Navigating to item fragment: "+position);
                setUpViewPager(position);
            }
        });
    }
}
