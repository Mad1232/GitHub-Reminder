package com.example.cydrop_frontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Console;


public class MainActivity extends AppCompatActivity {

    private Button simulateAdminLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("TAG", "on click running ");

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button simulateClientLogin = findViewById(R.id.button_simulate_client_login);
        simulateClientLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "on click running ");
                Intent intent = new Intent(MainActivity.this, ClientNavbarMainActivity.class);
                intent.putExtra("USERID",1); // Sample client is userid 0
                startActivity(intent);
            }
        });


        Button simulateAdminLogin = findViewById(R.id.button_simulate_admin_login);
        simulateAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ClientNavbarMainActivity.class);
                intent.putExtra("USERID",0); // Sample admin is userid 0
                startActivity(intent);
            }
        });
    }
}