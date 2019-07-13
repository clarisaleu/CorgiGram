package com.example.corgigram.profile;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.corgigram.R;
import com.example.corgigram.home.PostAdapter;
import com.example.corgigram.model.Post;
import com.example.corgigram.util.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Clarisa Leu-Rodriguez <clarisaleu@gmail.com>
 * Description: User Profile Activity for CorgiGram
 */
public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private Context mContext = ProfileActivity.this;
    private final static ParseUser user = ParseUser.getCurrentUser();
    private static final int ACTIVITY_NUM = 3;

    @BindView(R.id.profileProgBar) ProgressBar mProgressBar;
    @BindView(R.id.profile_image) ImageView mProfImg;
    @BindView(R.id.screenNameProf) TextView topNavBarScreenName;
    @BindView(R.id.description) TextView descript;
    @BindView(R.id.display_name) TextView dispName;
    @BindView(R.id.website) TextView website;
    @BindView(R.id.tvFollowers) TextView followers;
    @BindView(R.id.tvFollowing) TextView following;
    @BindView(R.id.tvPosts) TextView posts;
    @BindView(R.id.textEditProfile) TextView editProf;
    @BindView(R.id.gridView) RecyclerView rvPosts;

    private static PostAdapter postAdapter;
    ArrayList<Post> postsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        // Grid View
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        postsList = new ArrayList<>();
        postAdapter = new PostAdapter(postsList, 1);
        rvPosts.setLayoutManager(layoutManager);
        rvPosts.setAdapter(postAdapter);
        getPosts();

        // Set top nav bar screen name
        topNavBarScreenName.setText(user.getUsername());

        // Set on click listener for edit profile text
        editProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ProfileActivity.this, AccountSettings.class);
                it.putExtra("calling_activity", "profile_activity");
                startActivity(it);
            }
        });

        // Pull from database and display
        descript.setText(user.getString("description"));
        dispName.setText(user.getString("handle"));
        website.setText(user.getString("website"));
        followers.setText(user.getString("followers"));
        following.setText(user.getString("following"));
        posts.setText(user.getString("posts"));

        // Hide prog Bar
        mProgressBar.setVisibility(View.GONE);

        // Get Image
        ParseUser user = ParseUser.getCurrentUser();
        ParseFile img = user.getParseFile("profileImg");

        // If image exits, display
        if(img != null) {
            String imageUrl = img.getUrl();
            Glide.with(mContext).load(imageUrl).into(mProfImg);
        }

        setUpBottomNavigationView();
        setUpToolbar();
    }

    private void setUpToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.profileToolBar);
        setSupportActionBar(toolbar);
        ImageView profMenu = (ImageView) findViewById(R.id.profMenu);
        profMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to account settings");
                Intent it = new Intent(mContext, AccountSettings.class);
                startActivity(it);
            }
        });
    }

    // BottomNavBar Setup
    private void setUpBottomNavigationView(){
        Log.d(TAG, "setUpBottomNavigationView(): setting up your bottom nav bar...");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavView);
        BottomNavigationViewHelper.setUpBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }

    private void getPosts() {
        final Post.Query postsQuery = new Post.Query();
        postsQuery
                .getTop()
                .withUser();
        postsQuery.addDescendingOrder("createdAt");

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                    }
                    postsList.clear();
                    postAdapter.notifyDataSetChanged();
                    postsList.addAll(objects);
                    postAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });

    }
}
