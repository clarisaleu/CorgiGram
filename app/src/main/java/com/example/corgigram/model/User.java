package com.example.corgigram.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseUser;

/**
 * @author Clarisa Leu-Rodriguez <clarisaleu@gmail.com>
 * Description: User Class for CorgiGram
 */
@ParseClassName("User")
public class User extends ParseUser {
    private static final String KEY_NAME = "handle";
    private static final String KEY_PROFILEIMG = "profileImg";

    public String getName() {
        return getString(KEY_NAME);
    }

    public void setName(String name) {
        put(KEY_NAME, name);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_PROFILEIMG);
    }

    public void setImage(ParseFile image) {
        put(KEY_PROFILEIMG, image);
    }
}
