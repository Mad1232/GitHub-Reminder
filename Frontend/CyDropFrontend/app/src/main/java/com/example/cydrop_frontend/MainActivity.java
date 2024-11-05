package com.example.cydrop_frontend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Console;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private Button simulateAdminLogin;
    private Button simulateClientLogin;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Try to see if login is saved
        try {
            SharedPreferences sharedPref =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            VolleySingleton.userId = sharedPref.getString("userId", "-1");
            VolleySingleton.userType = sharedPref.getString("userType", "none");
            VolleySingleton.email = sharedPref.getString("userEmail", "none");
            if (!Objects.equals(VolleySingleton.userId, "-1") && !VolleySingleton.userType.equals("none")){
                Intent intent = new Intent(MainActivity.this, ClientNavbarMainActivity.class);
                switch (VolleySingleton.userType){
                    case "client_view":
                        intent = new Intent(MainActivity.this, ClientNavbarMainActivity.class);
                        startActivity(intent);  // go to SignupActivity
                        break;
                    case "admin_view":
                        intent = new Intent(this, AdminNavbarMainActivity.class);
                        startActivity(intent);  // go to SignupActivity
                        break;
                    case "vet_view":
                        intent = new Intent(MainActivity.this, VetDetailsActivity.class);
                        startActivity(intent);  // go to SignupActivity
                        break;
                }
            }
        } catch (Exception ignored){}

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        Button loginButton = findViewById(R.id.main_login_button);
        loginButton.setOnClickListener(view2 -> {
            /* when signup button is pressed, use intent to switch to Signup Activity */
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            // Intent intent = new Intent(MainActivity.this, ClientNavbarMainActivity.class);
            startActivity(intent);
        });

        signupButton = findViewById(R.id.main_signup_btn);  // link to signup button in the Main activity XML
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });


    }
}