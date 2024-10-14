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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;  // define username edittext variable
    private EditText passwordEditText;  // define password edittext variable
    private Button loginButton;         // define login button variable
    private Button signupButton;        // define signup button variable
    private TextView loginDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);            // link to Login activity XML

        /* initialize UI elements */
        usernameEditText = findViewById(R.id.login_username_edt);
        passwordEditText = findViewById(R.id.login_password_edt);
        loginButton = findViewById(R.id.login_login_btn);    // link to login button in the Login activity XML
        signupButton = findViewById(R.id.login_signup_btn);  // link to signup button in the Login activity XML
        loginDisplay = findViewById(R.id.login_display_text);

        // Button to send GET request
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                executorService.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        sendGetRequest(VolleySingleton.backendURL + "/user");

   //             String username = usernameEditText.getText().toString();
    //            String password = passwordEditText.getText().toString();
     //           verifyLogin(username, password);


//                Intent intent = new Intent(LoginActivity.this, VetDetailsActivity.class);  //only for testing demo2
//                startActivity(intent);  // go to LoginActivity
//

                SendLoginRequest();
                    }
                });


        /* click listener on signup button pressed */
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);  // go to SignupActivity
            }
        });
    }

    private void verifyLogin(String username, String password){
        //String url = VolleySingleton.backendURL + "/user" + "?username=" + username + "&password=" + password;
        //sendGetRequest(url);
    }

    // Method to send GET Request
    private void sendGetRequest(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            urlConnection.disconnect();

            String result = content.toString();
            Log.d("GET RESPONSE", result); // Log the response for debugging

            // Update UI on the main thread
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {

 //                   textGetResponse.setText(result);
                    if (result.equals("success")) {
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, ClientHomeFragment.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (Exception e) {
            Log.e("GET ERROR", e.getMessage(), e); // Log any errors
            e.printStackTrace();
        }
    }

    private void SendLoginRequest(){
        loginDisplay.setText("Sending req");

        JSONObject userLogin = new JSONObject();
        try {
//            userLogin.put("email", usernameEditText.getText().toString());
//            userLogin.put("password", passwordEditText.getText().toString());

            userLogin.put("email", "sophia.williams4@example.com");
            userLogin.put("password", "mynameisSophia4");
        } catch (Exception e) {
            loginDisplay.setText("Error creating JSON object");
            return;
        }

        JsonObjectRequest postReq = new JsonObjectRequest(
                Request.Method.POST,
                "http://coms-3090-038.class.las.iastate.edu:8080/login",
                userLogin,
                response -> {
                    // Victory! i think
                    loginDisplay.setText(response.toString());
                },
                error -> {
                    // Oopsie
                    loginDisplay.setText(error.toString());
                }
        );

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(postReq);
    }


}
