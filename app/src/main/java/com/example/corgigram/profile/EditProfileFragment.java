package com.example.corgigram.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.corgigram.R;
import com.example.corgigram.model.User;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.w3c.dom.Text;

public class EditProfileFragment extends Fragment  {
    private static final String TAG = "EditProfileFragment";
    private static Context mContext;

    private static EditText username;
    private static EditText name;
    private static EditText email;
    private static EditText website;
    private static EditText phoneNumber;

    private static final ParseUser user = ParseUser.getCurrentUser();

    ImageView mProfileImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        mProfileImg = (ImageView) view.findViewById(R.id.profile_photo);
        mContext = (Context) view.getContext();
        username = (EditText) view.findViewById(R.id.username);
        name = (EditText) view.findViewById(R.id.display_name);
        email = (EditText)view.findViewById(R.id.email);
        website = (EditText) view.findViewById(R.id.website);
        phoneNumber = (EditText) view.findViewById(R.id.phoneNumber);

        // Get Image
        ParseUser user = ParseUser.getCurrentUser();
        ParseFile img = user.getParseFile("profileImg");

        if(img != null) {
            String imageUrl = img.getUrl();
            Glide.with(mContext).load(imageUrl).into(mProfileImg);
        }

        String un = user.getUsername();
        username.setText(un);

        String mail = user.getEmail();
        email.setText(mail);

        String nameFull = user.getString("handle");
        name.setText(nameFull);

        return  view;
    }


}
