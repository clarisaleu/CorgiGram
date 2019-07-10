package com.example.corgigram;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.corgigram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private final String TAG = "HomeActivity";  // For logcat messages
    private EditText descriptionInput;
    private Button captureButton;
    private ImageView ivPostImage;
    private Button submitButton;
    private TextView corgiGram;

    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        descriptionInput = (EditText) findViewById(R.id.description_et);
        captureButton = (Button) findViewById(R.id.captureImage_btn);
        submitButton = (Button) findViewById(R.id.submit_btn);
        ivPostImage = (ImageView) findViewById(R.id.ivPostImage);

        // Set custom font
        corgiGram = (TextView) findViewById(R.id.corgigram);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/logo.ttf");
        corgiGram.setTypeface(custom_font);


        captureButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onLaunchCamera(v);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = descriptionInput.getText().toString();
                ParseUser user = ParseUser.getCurrentUser();
                savePost(description, user);
            }
        });
    }

    public void onLaunchCamera(View view) {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(HomeActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }


    // Method to save posts
    private void savePost(final String description, ParseUser user) {
        Post post = new Post();
        post.setDescription(description);
        post.setUser(user);
       // post.setImage();
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null){
                    Log.d(TAG, "Error while saving");
                    e.printStackTrace();
                    return;
                }
                Log.d(TAG, "Success!");
                descriptionInput.setText("");  // Empty out text
            }
        });
    }


    // Used for debugging purposes
    private void queryPosts(){
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.d(TAG,"Error with query");
                    e.printStackTrace();
                    return;
                }
                for(int i = 0 ; i < posts.size(); i++) {
                    Post post = posts.get(i);
                    Log.d(TAG, "Post:" + post.getDescription() + "username = " +post.getUser().getUsername());
                }
            }
        });
    }


}
