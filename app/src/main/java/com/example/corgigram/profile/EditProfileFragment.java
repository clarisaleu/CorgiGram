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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.corgigram.R;
import com.parse.ParseFile;
import com.parse.ParseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Clarisa Leu-Rodriguez <clarisaleu@gmail.com>
 * Description: EditProfile Fragment for Account Settings Activity
 */
public class EditProfileFragment extends Fragment {
    private static final String TAG = "EditProfileFragment";
    private static Context mContext;

    @BindView(R.id.username)
    private static EditText username;
    @BindView(R.id.display_name)
    private static EditText name;
    @BindView(R.id.email)
    private static EditText email;
    @BindView(R.id.website)
    private static EditText website;
    @BindView(R.id.phoneNumber)
    private static EditText phoneNumber;
    @BindView(R.id.description)
    private static EditText descript;
    @BindView(R.id.saveChanges)
    private static ImageView check;
    @BindView(R.id.profile_photo)
    private static ImageView mProfileImg;
    @BindView(R.id.backArrow)
    private ImageView back;
    private static final ParseUser user = ParseUser.getCurrentUser();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        ButterKnife.bind(view);
        mContext = (Context) view.getContext();

        // Pull from data base and display
        website.setText(user.getString("website"));
        phoneNumber.setText(user.getString("phone"));
        descript.setText(user.getString("description"));
        username.setText(user.getString("handle"));
        email.setText(user.getEmail());
        name.setText(user.getUsername());


        // Set up backarrow on click listener
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();  // go back to prof page
            }
        });

        // Get Image and display if it exists
        ParseFile img = user.getParseFile("profileImg");
        if (img != null) {
            String imageUrl = img.getUrl();
            Glide.with(mContext).load(imageUrl).into(mProfileImg);
        }

        // Set on click listener for save changes
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setEmail(email.getText().toString());
                user.setUsername(name.getText().toString());
                user.put("handle", username.getText().toString());
                user.put("phone", phoneNumber.getText().toString());
                user.put("email", email.getText().toString());
                user.put("website", website.getText().toString());
                user.put("description", descript.getText().toString());
                user.saveInBackground();
                Toast.makeText(getContext(), "Updated Information Successfully", Toast.LENGTH_SHORT).show();
                // TODO: FIX BUG - once done, the profile page fields do not reload!
                getActivity().finish();
            }
        });

        return view;
    }
}
