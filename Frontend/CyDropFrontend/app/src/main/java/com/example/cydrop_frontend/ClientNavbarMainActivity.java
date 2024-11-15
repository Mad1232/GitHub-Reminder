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


/**
 * @author Niraj
 * The navbar activity for client view. Holds the fragments ClientHomeFragment, ClientQuestionsFragment, ClientRemindersFragment
 */
public class ClientNavbarMainActivity extends AppCompatActivity implements WebSocketListener {

    ActivityClientNavbarMainBinding binding;
    ClientQuestionsFragment currentFrag;

    /**
     * Creates the activity. Sets up the binding to allow navbar to control current fragment. Switches current fragment to ClientHomeFragment
     * @param savedInstanceState
     */
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
            WebSocketManager.getInstance().disconnectWebSocket();
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

    /**
     *  replace the current fragment with provided one
     * @param fragment the fragment to replace current fragment
     * @return the fragment that replaced the old fragment
     */
    private Fragment replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
        return fragment;
    }

    /**
     * Connect to the websocket
     */
    private void connectToWebsocket(){
        WebSocketManager.getInstance().connectWebSocket(VolleySingleton.backendURL);
        WebSocketManager.getInstance().setWebSocketListener(ClientNavbarMainActivity.this);
    }

    /**
     * Empty, required by interface
     * @param handshakedata Information about the server handshake.
     */
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    /**
     * Empty, required by interface
     * @param message The received WebSocket message.
     */
    @Override
    public void onWebSocketMessage(String message) {

    }

    /**
     * Empty, required by interface
     * @param code   The status code indicating the reason for closure.
     * @param reason A human-readable explanation for the closure.
     * @param remote Indicates whether the closure was initiated by the remote endpoint.
     */
    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {

    }

    /**
     * Empty, required by interface
     * @param ex The exception that describes the error.
     */
    @Override
    public void onWebSocketError(Exception ex) {

    }
}

