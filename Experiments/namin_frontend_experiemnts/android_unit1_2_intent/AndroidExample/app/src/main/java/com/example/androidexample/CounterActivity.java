package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CounterActivity extends AppCompatActivity {

    private TextView numberTxt; // define number textview variable
    private Button increaseBtn; // define increase button variable
    private Button decreaseBtn; // define decrease button variable
    private Button backBtn;     // define back button variable

    // Text alert field
    private TextView alertText;

    // Keep track of alerts seen
    private int[] alertsSeen = {0, 0, 0, 0, 0, 0, 0};


    private int counter = 0;    // counter variable


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        // init the alert text and wipe alerts seen
        alertText = findViewById(R.id.text_alerts);

        /* initialize UI elements */
        numberTxt = findViewById(R.id.number);
        increaseBtn = findViewById(R.id.counter_increase_btn);
        decreaseBtn = findViewById(R.id.counter_decrease_btn);
        backBtn = findViewById(R.id.counter_back_btn);

        // The alert message needs to reflect the inital 0
        onCounterChanged(counter);

        /* when increase btn is pressed, counter++, reset number textview */
        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberTxt.setText(String.valueOf(++counter));
                onCounterChanged(counter);
            }
        });

        /* when decrease btn is pressed, counter--, reset number textview */
        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberTxt.setText(String.valueOf(--counter));
                onCounterChanged(counter);
            }
        });

        /* when back btn is pressed, switch back to MainActivity */
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CounterActivity.this, MainActivity.class);

                intent.putExtra("NUM", String.valueOf(counter));  // key-value to pass to the MainActivity

                // Calculate the number of alerts seen and pass that as well
                int alertCounter = 0;
                for (int i : alertsSeen){
                    alertCounter += i;
                }
                intent.putExtra("alertCounter", String.valueOf(alertCounter));

                startActivity(intent);
            }
        });

    }

    private void onCounterChanged(int currentCounter) {

        if (currentCounter < 0) {
            alertText.setText("NOOOO! your going the wrong way!");
            alertsSeen[0] = 1;
        }

        switch (currentCounter) {
            case 0:
                alertText.setText("Better get to clicking!");
                alertsSeen[1] = 1;
                break;

            case 1:
                alertText.setText("There you go!");
                alertsSeen[2] = 1;
                break;

            case 2:
                alertText.setText("Keep clicking!!");
                alertsSeen[3] = 1;
                break;

            case 4:
                alertText.setText("Keep clicking!!");
                alertsSeen[3] = 1;
                break;

            case 5:
                alertText.setText("Click Click Click!");
                alertsSeen[4] = 1;
                break;

            case 9:
                alertText.setText("Click Click Click!");
                alertsSeen[4] = 1;
                break;

            case 10:
                alertText.setText("10 clicks! way to go!");
                alertsSeen[5] = 1;
                break;

            case 11:
                alertText.setText("Alright, i've run out of cool dialouge, but you should keep clicking");
                alertsSeen[6] = 1;
                break;
        }
    }
}