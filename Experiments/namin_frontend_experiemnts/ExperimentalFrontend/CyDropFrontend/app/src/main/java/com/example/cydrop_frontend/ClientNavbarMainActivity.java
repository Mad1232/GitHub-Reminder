package com.example.cydrop_frontend;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cydrop_frontend.databinding.ActivityClientNavbarMainBinding;


public class ClientNavbarMainActivity extends AppCompatActivity {

    private int userid = -1;

    ActivityClientNavbarMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        EdgeToEdge.enable(this);
        binding = ActivityClientNavbarMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        replaceFragment(new RemindersFragment());

        // extract data passed into this activity from another activity
        Bundle extras = getIntent().getExtras();
        userid = extras.getInt("USERID");  // this will come from LoginActivity


        // Switch fragments when an icon is selected
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.reminders){
                replaceFragment(new RemindersFragment());
            } else if (itemId == R.id.home) {
                replaceFragment(new HomeFragment());
            } else { // itemId == questions
                replaceFragment(new QuestionsFragment());
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}

