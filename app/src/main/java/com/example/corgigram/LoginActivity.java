package com.example.corgigram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.corgigram.home.HomeActivity;
import com.example.corgigram.model.User;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author      Clarisa Leu-Rodriguez <clarisaleu@gmail.com>
 * Description: Login Activity for CorgiGram
 */
public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.username_et) EditText usernameInput;
    @BindView(R.id.password_et) EditText passwordInput;
    @BindView(R.id.login_btn) Button loginBtn;
    @BindView(R.id.new_user) TextView newUser;
    private final String TAG = "LoginActivity";  // For logcat

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // If current user present, persist login
        if(ParseUser.getCurrentUser()!=null){
            Intent it = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(it);
        }

        // New User Button Listener
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Sign up activity
                Intent it = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(it);
            }
        });

        // Login Button Listener
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                login(username, password);
            }
        });
    }

    private void login(String username, String password){
        User.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                // Login Unsuccessful
                if(e != null){
                    Log.d(TAG, "Login Failure");
                    e.printStackTrace();
                    return;
                }

                // Login successful - pass intent to home page
                Log.d(TAG,"Login Successful");
                final Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
