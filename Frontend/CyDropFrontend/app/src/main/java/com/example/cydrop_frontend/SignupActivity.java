package com.example.cydrop_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SignupActivity extends AppCompatActivity{
    private EditText usernameEditText;  // define username edittext variable
    private EditText passwordEditText;  // define password edittext variable
    //  private EditText typeEditText;   // define type edittext variable
    private Button loginButton;         // define login button variable
    private Button signupButton;        // define signup button variable
    ExecutorService executorService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        /* initialize UI elements */
        usernameEditText = findViewById(R.id.signup_username_edt);  // link to username edtext in the Signup activity XML
        passwordEditText = findViewById(R.id.signup_password_edt);  // link to password edtext in the Signup activity XML
        //  typeEditText = findViewById(R.id.signup_confirm_edt);    // link to confirm edtext in the Signup activity XML
        loginButton = findViewById(R.id.signup_login_btn);    // link to login button in the Signup activity XML
        signupButton = findViewById(R.id.signup_signup_btn);  // link to signup button in the Signup activity XML

        // Initialize the ExecutorService with a single thread pool
        executorService = Executors.newSingleThreadExecutor();

        /* click listener on login button pressed */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when login button is pressed, use intent to switch to Login Activity */
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);  // go to LoginActivity
            }
        });

        /* click listener on signup button pressed */
        // Button to send POST request
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                // String type = typeEditText.getText().toString();

                // Create JSON object for POST request
                JSONObject json = new JSONObject();
                try {
                    json.put("username", username);
                    json.put("password", password);
                    //  json.put("type", type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
//                        sendPostRequest("http://coms-3090-000.class.las.iastate.edu:8080/students", json.toString());
                        sendPostRequest(VolleySingleton.backendURL + "/user", json.toString());
                    }
                });
            }
        });
    }


    // Method to send POST Request
    private void sendPostRequest(String urlString, String jsonData) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            // Write JSON data to the output stream
            OutputStream os = conn.getOutputStream();
            os.write(jsonData.getBytes("UTF-8"));
            os.flush();
            os.close();

            // Get the response
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            conn.disconnect();
            String result = sb.toString();

            // Optionally handle the response from the POST request
            Log.d("POST RESPONSE", result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

