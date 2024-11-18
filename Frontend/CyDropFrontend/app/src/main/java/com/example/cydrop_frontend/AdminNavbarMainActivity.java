package com.example.cydrop_frontend;

import android.os.Bundle;
import android.os.Debug;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cydrop_frontend.databinding.ActivityAdminNavbarMainBinding;
import com.example.cydrop_frontend.databinding.ActivityClientNavbarMainBinding;

/**
 * @author Niraj
 * The navbar activity for admin view. Holds the fragments AdminInventoryFragment, AdminQuestionsFragment, AdminUsersFragment
 */
public class AdminNavbarMainActivity extends AppCompatActivity {

    private @NonNull ActivityAdminNavbarMainBinding binding;

    /**
     * Creates the activity. Sets up the binding to allow navbar to control current fragment. Switches current fragment to AdminInventoryFragment
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminNavbarMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        replaceFragment(new AdminInventoryFragment());

        // Switch fragments when an icon is selected
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.inventory){
                replaceFragment(new AdminInventoryFragment());
            } else if (itemId == R.id.users) {
                replaceFragment(new AdminUsersFragment());
            } else { // itemId == questions
                replaceFragment(new AdminQuestionsFragment());
            }

            return true;
        });
    }

    /**
     * A helper function to replace the current fragment
     * @param fragment the fragment that will be replacing the current frag
     */
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }


}

