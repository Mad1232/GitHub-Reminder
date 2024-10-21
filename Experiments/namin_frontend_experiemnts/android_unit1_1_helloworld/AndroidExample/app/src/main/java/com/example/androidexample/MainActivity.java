package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView messageText;   // define message textview variable
    private TextView messageSubtext; // A new variable to hold subtext
    private TextView messageFooter; // A third variable for the footer
    private BottomNavigationView bottomNav;

    // Colors
    int grey = Color.parseColor("#C0C0C0");
    int white = Color.parseColor("#FFFFFF");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);             // link to Main activity XML

        /* initialize UI elements */
        messageText = findViewById(R.id.main_msg_txt);      // link to message textview in the Main activity XML
        messageText.setText("Hello World");

        messageSubtext = findViewById(R.id.main_msg_subtext);
        messageSubtext.setText("This is some subtext, its grey and small!");

        messageFooter = findViewById(R.id.main_msg_footer);
        messageFooter.setText("This is a footer, with a different bg and anchored to the bottom!");
        messageFooter.setBackgroundColor(grey);

        bottomNav = findViewById(R.id.main_bottom_nav);
        bottomNav.setBackgroundColor(white);
    }
}