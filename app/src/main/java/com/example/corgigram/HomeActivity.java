package com.example.corgigram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.corgigram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private final String TAG = "HomeActivity";  // For logcat messages
    private EditText description;
    private Button createButton;
    private Button refreshButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        description = (EditText) findViewById(R.id.description_et);
        createButton = (Button) findViewById(R.id.home_txt);
        refreshButton = (Button) findViewById(R.id.refresh_btn);

        loadTopPosts();
    }

    private void loadTopPosts(){
        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser();
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e == null){
                    for(int i=0; i < objects.size(); i++) {
                        Log.d(TAG, "Post[" + i + "] = " + objects.get(i).getDescription() + "\nusername = " + objects.get(i).getUser().getUsername()) ;
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
