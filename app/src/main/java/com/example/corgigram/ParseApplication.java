package com.example.corgigram;

import android.app.Application;

import com.example.corgigram.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;


public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Register Post Class as subclass
        ParseObject.registerSubclass(Post.class);

        // Use for troubleshooting -- remove this line for production
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);


        // set applicationId, and server server based on the values in the Heroku settings.
        // clientKey is not needed unless explicitly configured
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("clarisaleu") // should correspond to APP_ID env variable
                .clientKey("sloan-kettering")  // set explicitly unless clientKey is explicitly configured on Parse server
                .server("https://clarisaleu-fbu-instagram.herokuapp.com/parse").build());

    }
}