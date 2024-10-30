package com.example.cydrop_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class VetChatActivity extends AppCompatActivity {

    private Button returnButton;         // define return button variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_chat);            // link to Login activity XML

        /* initialize UI elements */
        returnButton = findViewById(R.id.btn_return);    // link to return button in the Login activity XML

        // Button to send GET request
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VetChatActivity.this, ClientNavbarMainActivity.class);
                startActivity(intent);
            }
        });
    }

}
