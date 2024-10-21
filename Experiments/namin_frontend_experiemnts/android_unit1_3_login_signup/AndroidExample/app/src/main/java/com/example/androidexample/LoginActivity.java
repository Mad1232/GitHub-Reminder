package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;  // define username edittext variable
    private EditText passwordEditText;  // define password edittext variable
    private Button loginButton;         // define login button variable
    private Button signupButton;        // define signup button variable

    private ArrayList<String> users;
    private ArrayList<String> passwords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);            // link to Login activity XML

        /* initialize UI elements */
        usernameEditText = findViewById(R.id.login_username_edt);
        passwordEditText = findViewById(R.id.login_password_edt);
        loginButton = findViewById(R.id.login_login_btn);    // link to login button in the Login activity XML
        signupButton = findViewById(R.id.login_signup_btn);  // link to signup button in the Login activity XML

        // read users and passwords
        Bundle extras = getIntent().getExtras();
        try {
            users = extras.getStringArrayList("users");
            passwords = extras.getStringArrayList("passwords");
        } catch (Exception e) {
            users = new ArrayList<>();
            passwords = new ArrayList<>();
        }

        /* click listener on login button pressed */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* grab strings from user inputs */
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Verify the username password
                for (int i = 0; i < users.size(); i++){
                    if (users.get(i).equals(username)){
                        if (passwords.get(i).equals(password)){
                            /* when login button is pressed, use intent to switch to Login Activity */
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("users", users);
                            intent.putExtra("passwords", passwords);
                            intent.putExtra("USERNAME", username);  // key-value to pass to the MainActivity
                            intent.putExtra("PASSWORD", password);  // key-value to pass to the MainActivity
                            startActivity(intent);  // go to MainActivity with the key-value data
                            return;
                        }
                    }
                }

                // There were no matching users
                Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_LONG).show();


            }
        });

        /* click listener on signup button pressed */
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                intent.putExtra("users", users);
                intent.putExtra("passwords", passwords);
                startActivity(intent);  // go to SignupActivity
            }
        });
    }
}