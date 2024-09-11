package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView messageText;   // define message textview variable
    private TextView messageSubtext;    //define message for subtext
    private TextView messageEndtext;   //define message for endtext

    int grey = Color.parseColor("#C0C0C0"); //define background color for endtext

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);             // link to Main activity XML

        /* initialize UI elements */
        messageText = findViewById(R.id.main_msg_txt);      // link to message textview in the Main activity XML
        messageText.setText("Hello Team");

        messageSubtext = findViewById(R.id.main_msg_subtext);      // subtext in blue, small font size
        messageSubtext.setText("My name is Madison");

        messageEndtext = findViewById(R.id.main_msg_endtext);      // bottom text in orange, medium font size
        messageEndtext.setText("Welcome to COMS309");
        messageEndtext.setBackgroundColor(grey);                    //grey box around text
    }
}