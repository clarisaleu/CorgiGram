package com.example.corgigram.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.corgigram.R;
import com.example.corgigram.model.Post;
import com.example.corgigram.profile.ProfileActivity;
import com.example.corgigram.util.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author      Clarisa Leu-Rodriguez <clarisaleu@gmail.com>
 * Description: Home/Feed Activity for CorgiGram
 */
public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private static final int ACTIVITY_NUM = 0;
    private final Context mContext = HomeActivity.this;
    @BindView(R.id.rvPostFeed) RecyclerView rvPosts;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private static PostAdapter postAdapter;
    ArrayList<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Log.d(TAG, "Starting feed activity: onCreate()");
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        // Load 20 posts
        posts = new ArrayList<>();
        postAdapter = new PostAdapter(posts, 0);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        rvPosts.setAdapter(postAdapter);

        // Refresh Listener
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPosts();
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // Load Posts
        getPosts();

        // Set up bottom nav bar
        setUpBottomNavigationView();
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
                    posts.clear();
                    postAdapter.notifyDataSetChanged();
                    posts.addAll(objects);
                    postAdapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);
                } else {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_favorite) {
            Intent it = new Intent(HomeActivity.this, ProfileActivity.class);
            Toast.makeText(HomeActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
