package com.example.corgigram.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.corgigram.R;
import com.example.corgigram.model.Post;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author      Clarisa Leu-Rodriguez <clarisaleu@gmail.com>
 * Description: Post Details Activity for CorgiGram Post
 */
public class PostDetails extends AppCompatActivity {
    ImageView ivImage;
    TextView tvUserName;
    TextView tvDesc;
    ImageView ivProf;
    TextView date;
    TextView tvUserName2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        tvUserName = (TextView) findViewById(R.id.tvUser);
        tvDesc = (TextView) findViewById(R.id.tvDescriptionPost);
        ivProf = (ImageView) findViewById(R.id.userProfPostImg);
        date = (TextView) findViewById(R.id.posted);
        tvUserName2 = (TextView) findViewById(R.id.userName2);

        String username = getIntent().getExtras().get("username").toString();
        String profImg = getIntent().getExtras().get("profImg").toString();
        String img = getIntent().getExtras().get("img").toString();
        String datePost = getIntent().getExtras().get("date").toString();
        String descr = getIntent().getExtras().get("description").toString();

        tvUserName.setText("@"+ username);
        tvUserName2.setText("@"+username);
        tvDesc.setText("    "+descr);
        String posted = getRelativeTimeAgo(datePost);
        date.setText("Posted " +posted);
        // Get Profile Image and display if it exists
        Glide.with(this).load(profImg).into(ivProf);
        Glide.with(this).load(img).into(ivImage);

    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

}
