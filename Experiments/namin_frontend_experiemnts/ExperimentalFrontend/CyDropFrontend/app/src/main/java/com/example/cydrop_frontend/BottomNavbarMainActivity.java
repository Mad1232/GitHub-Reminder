package com.example.cydrop_frontend;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class BottomNavbarMainActivity extends AppCompatActivity {

    private int userid = -1;
    private EViewFragment currentViewFragment = EViewFragment.Home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bottom_navbar_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // extract data passed into this activity from another activity
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            // This scenario only occurs when the simulate login button is used
            userid = 0;
        } else {
            userid = extras.getInt("USERID");  // this will come from LoginActivity

        }

    }
}

