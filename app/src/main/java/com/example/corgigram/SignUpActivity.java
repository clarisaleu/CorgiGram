package com.example.corgigram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.corgigram.home.HomeActivity;
import com.example.corgigram.model.User;
import com.example.corgigram.share.ShareActivity;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SignUpCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Clarisa Leu-Rodriguez <clarisaleu@gmail.com>
 * Description: Sign Up Activity for CorgiGram Account Settings
 */
public class SignUpActivity extends AppCompatActivity {
    @BindView(R.id.username_et) EditText username;
    @BindView(R.id.password_et) EditText password;
    @BindView(R.id.email_et) EditText email;
    @BindView(R.id.name_et) EditText name;
    @BindView(R.id.signup_btn) Button signupBtn;
    private final String TAG = "SignUpActivity";  // For logcat

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the ParseUser
                User user = new User();
                // Set core properties
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());
                user.setEmail(email.getText().toString());
                user.setName(name.getText().toString());

                // Invoke signUpInBackground
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        // Login Unsuccessful
                        if (e != null) {
                            Log.d(TAG, "SignUp Failure");
                            e.printStackTrace();
                            return;
                        }
                        // Sign-up successful - pass intent to home page
                        Log.d(TAG, "Login Successful");
                        final Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

}
