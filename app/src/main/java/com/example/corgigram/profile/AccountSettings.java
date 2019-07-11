package com.example.corgigram.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.corgigram.LoginActivity;
import com.example.corgigram.R;
import com.example.corgigram.util.SectionsStatePagerAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Clarisa Leu-Rodriguez <clarisaleu@gmail.com>
 * Description: AccoutSettings Activity for CorgiGram
 */
public class AccountSettings extends AppCompatActivity {
    private final static String TAG = "AccountSettings";
    private Context mContext;
    private SectionsStatePagerAdapter pagerAdapter;
    @BindView(R.id.container)
    private ViewPager mViewPager;
    @BindView(R.id.relLayout1)
    private RelativeLayout mRelativeLayout;
    @BindView(R.id.backArrow)
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        Log.d(TAG, "onCreate started");
        ButterKnife.bind(this);
        mContext = AccountSettings.this;

        // Set up Settings List
        setUpSettingsList();

        // Set up fragments in list
        setUpFragments();

        // Used for getting incoming intent from profile page to navigate to edit profile fragment
        getIncomingIntent();

        // Set up back arrow click listener to go back to profile activity
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to profile page");
                finish();
            }
        });
    }

    private void getIncomingIntent() {
        Intent it = getIntent();
        if (it.hasExtra("calling_activity")) {
            setUpViewPager(pagerAdapter.getFragmentNumber(getString(R.string.edit_profile)));
        }
    }

    private void setUpFragments() {
        pagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new EditProfileFragment(), getString(R.string.edit_profile));  // fragment 0
        pagerAdapter.addFragment(new SignOutFragment(), getString(R.string.sign_out));  // fragment 1
    }

    private void setUpViewPager(int fragNumber) {
        mRelativeLayout.setVisibility(View.GONE);
        Log.d(TAG, "setUpViewPager: navigating to fragment number: " + fragNumber);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(fragNumber);

    }

    // Setting list with SectionsStatePageAdapter - easier to add more items to settings list in future.
    private void setUpSettingsList() {
        Log.d(TAG, "setUpSettingsList: initializing account settings list.");
        ListView listView = (ListView) findViewById(R.id.lvAccountSettings);

        ArrayList<String> options = new ArrayList<>();
        options.add(getString(R.string.edit_profile));
        options.add(getString(R.string.sign_out));

        ArrayAdapter adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, options);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    // Sign out - go back to login page.
                    // In future - maybe make fragment more detailed.
                    ParseUser.logOut();
                    Intent it = new Intent(AccountSettings.this, LoginActivity.class);
                    startActivity(it);
                    finish();
                }
                Log.d(TAG, "onItemClick: Navigating to item fragment: " + position);
                setUpViewPager(position);
            }
        });
    }
}
