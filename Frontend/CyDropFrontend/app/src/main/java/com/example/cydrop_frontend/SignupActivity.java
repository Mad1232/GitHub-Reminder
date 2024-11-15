package com.example.cydrop_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * SignupActivity provides the functionality for user sign-up, allowing new users to create an account.
 * Users are prompted to enter an email and password, and a POST request is sent to register the new user.
 *
 * @author Madison Vosburg
 */
public class SignupActivity extends AppCompatActivity{

    /**
     * EditText field for entering the email.
     */
    private EditText usernameEditText;

    /**
     * EditText field for entering the password.
     */
    private EditText passwordEditText;

    /**
     * Button for navigating to the login screen.
     */
    private Button loginButton;

    /**
     * Button for submitting a sign-up request.
     */
    private Button signupButton;

    /**
     * Initializes the activity, sets up listeners for login and signup button.
     * Signup button captures user input and sends a POST request to add user.
     * Login button navigates to the LoginActivity.
     *
     * @param savedInstanceState Bundle containing the saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        /* initialize UI elements */
        usernameEditText = findViewById(R.id.signup_username_edt);  // link to username edtext in the Signup activity XML
        passwordEditText = findViewById(R.id.signup_password_edt);  // link to password edtext in the Signup activity XML
        loginButton = findViewById(R.id.signup_login_btn);    // link to login button in the Signup activity XML
        signupButton = findViewById(R.id.signup_signup_btn);  // link to signup button in the Signup activity XML

        /* click listener on login button pressed */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when login button is pressed, use intent to switch to Login Activity */
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
              //  Intent intent = new Intent(SignupActivity.this, VetDetailsActivity.class);  //only for testing demo2
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

                postRequest("http://coms-3090-038.class.las.iastate.edu:8080/signup", username, password);
            }
        });

    }

    /**
     * Sends a POST request to the server to register a new user.
     *
     * @param url The URL of the endpoint for user sign-up.
     * @param username The email provided by the user.
     * @param password The password provided by the user.
     */
    private void postRequest(String url, String username, String password) {

        // Create JSON object for POST request
        JSONObject json = new JSONObject();
        try {
            json.put("email", username);
            json.put("password", password);
            //  json.put("type", type);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Signed Up!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

}

