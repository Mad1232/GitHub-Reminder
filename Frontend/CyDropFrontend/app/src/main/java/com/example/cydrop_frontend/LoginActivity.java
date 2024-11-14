package com.example.cydrop_frontend;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * LoginActivity handles user login operations, allowing users to log in with their email and password.
 * It sends a POST request to authenticate the user, and redirects them based on their user role (client, admin, or vet).
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * EditText field for inputting the user's email.
     */
    private EditText usernameEditText;

    /**
     * EditText field for inputting the user's password.
     */
    private EditText passwordEditText;

    /**
     * Button to initiate login.
     */
    private Button loginButton;

    /**
     * Button to navigate to the Signup page.
     */
    private Button signupButton;

    /**
     * TextView to display login error messages.
     */
    private TextView loginDisplay;


    /**
     * Initializes the activity, sets up listeners for login and signup buttons.
     * Login button captures user input and sends a PUT request to verify credentials.
     * Signup button navigates to the SignupActivity.
     *
     * @param savedInstanceState Bundle containing the saved state of the activity.
     */
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
                VolleySingleton.email = usernameEditText.getText().toString();
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

//    private void verifyLogin(String username, String password){
//        //String url = VolleySingleton.backendURL + "/user" + "?username=" + username + "&password=" + password;
//        //sendGetRequest(url);
//    }

    /**
     * Sends a login request to authenticate the user. Constructs a JSON object containing the user's email and password,
     * then sends it in a POST request. Upon successful login (verification in the backend), saves user information in SharedPreferences and redirects based on user type.
     */
    private void SendLoginRequest(){
        JSONObject userLogin = new JSONObject();
        try {
            userLogin.put("email", usernameEditText.getText().toString());
            userLogin.put("password", passwordEditText.getText().toString());
        } catch (Exception e) {
            Toast.makeText(LoginActivity.this, "Error creating JSON object", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest testReq = new StringRequest(
                Request.Method.POST,
                "http://coms-3090-038.class.las.iastate.edu:8080/login",
                response -> {
                    if (response.equals("User not found") || response.equals("Username or password is incorrect")) {
                        Toast.makeText(LoginActivity.this, "ERROR: " + response, Toast.LENGTH_LONG).show();
                        return;
                    }

                    // We have a valid user
                    VolleySingleton.userId = response.split(",")[1];
                    String userType = response.split(",")[0];

                    // Save the user info
                    SharedPreferences sharedPref =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("userId", VolleySingleton.userId);
                    editor.putString("userType", userType);
                    editor.putString("userEmail", VolleySingleton.email);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, ClientNavbarMainActivity.class);
                    switch (userType){
                        case "client_view":
                            intent = new Intent(LoginActivity.this, ClientNavbarMainActivity.class);
                            startActivity(intent);  // go to SignupActivity
                            break;
                        case "admin_view":
                             intent = new Intent(LoginActivity.this, AdminNavbarMainActivity.class);
                            startActivity(intent);  // go to SignupActivity
                            break;
                        case "vet_view":
                            intent = new Intent(LoginActivity.this, VetNavbarMainActivity.class);
                            startActivity(intent);  // go to SignupActivity
                            break;
                    }



                },
                error -> {
                    loginDisplay.setText("Error: " + error.toString());
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return userLogin.toString() == null ? null : userLogin.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", userLogin.toString(), "utf-8");
                    return null;
                }
            }
        };


        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(testReq);
    }


}
