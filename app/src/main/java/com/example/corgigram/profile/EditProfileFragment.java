package com.example.corgigram.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.corgigram.R;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class EditProfileFragment extends Fragment  {
    private static final String TAG = "EditProfileFragment";
    private static Context mContext;
    ImageView mProfileImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        mProfileImg = (ImageView) view.findViewById(R.id.profile_image);
        mContext = view.getContext();

        // Get Image
        ParseUser user = ParseUser.getCurrentUser();
        ParseFile img = user.getParseFile("profileImg");

        if(img != null) {
            String imageUrl = img.getUrl();
            Glide.with(mContext).load(imageUrl).into(mProfileImg);
        }

        return  view;
    }


}
