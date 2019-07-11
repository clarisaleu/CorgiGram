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

public class SignUpActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private EditText email;
    private EditText name;
    private Button signupBtn;
    private final String TAG = "SignUpActivity";  // For logcat

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Set up views
        username = (EditText) findViewById(R.id.username_et);
        password = (EditText) findViewById(R.id.password_et);
        email = (EditText) findViewById(R.id.email_et);
        name = (EditText) findViewById(R.id.name_et);
        signupBtn = (Button) findViewById(R.id.signup_btn);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the ParseUser
                User user = new User();

                // Set core properties
                String handle = username.getText().toString();
                user.setUsername(handle);

                String pw = password.getText().toString();
                user.setPassword(pw);

                String emailAddress = email.getText().toString();
                user.setEmail(emailAddress);

                String namePerson = name.getText().toString();
                user.setName(namePerson);


                // Invoke signUpInBackground
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        // Login Unsuccessful
                        if(e != null){
                            Log.d(TAG, "SignUp Failure");
                            e.printStackTrace();
                            return;
                        }
                        // Sign-up successful - pass intent to home page
                        Log.d(TAG,"Login Successful");
                        final Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }



}
