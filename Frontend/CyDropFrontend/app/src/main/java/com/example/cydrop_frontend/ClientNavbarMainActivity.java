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

import org.java_websocket.handshake.ServerHandshake;


public class ClientNavbarMainActivity extends AppCompatActivity implements WebSocketListener {

    ActivityClientNavbarMainBinding binding;
    ClientQuestionsFragment currentFrag;

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
        replaceFragment(new ClientHomeFragment());


        // Switch fragments when an icon is selected
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.reminders){
                replaceFragment(new ClientRemindersFragment());
            } else if (itemId == R.id.customers) {
                replaceFragment(new ClientHomeFragment());
            } else { // itemId == questions
                currentFrag = (ClientQuestionsFragment) replaceFragment(new ClientQuestionsFragment());
                connectToWebsocket();
            }

            return true;
        });
    }

    private Fragment replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
        return fragment;
    }

    private void connectToWebsocket(){
        WebSocketManager.getInstance().connectWebSocket(VolleySingleton.backendURL);
        WebSocketManager.getInstance().setWebSocketListener(ClientNavbarMainActivity.this);
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onWebSocketMessage(String message) {

    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onWebSocketError(Exception ex) {

    }
}

