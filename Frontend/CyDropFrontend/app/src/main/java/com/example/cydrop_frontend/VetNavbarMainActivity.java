package com.example.cydrop_frontend;

import android.content.Intent;
import android.os.Bundle;

import com.example.cydrop_frontend.databinding.ActivityAdminNavbarMainBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cydrop_frontend.databinding.ActivityVetNavbarMainBinding;

public class VetNavbarMainActivity extends AppCompatActivity {

    @NonNull
    ActivityVetNavbarMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVetNavbarMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        replaceFragment(new VetCustomersFragment());

        // Switch fragments when an icon is selected
        binding.bottomNavigationViewVet.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.customers){
                replaceFragment(new VetCustomersFragment());
            } else if (itemId == R.id.reminders) {
                replaceFragment(new VetRemindersFragment());
            } else { // itemId == questions
                Intent intent = new Intent(VetNavbarMainActivity.this, VetChatActivity.class);
                startActivity(intent);
                //replaceFragment(new VetQuestionsFragment());
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