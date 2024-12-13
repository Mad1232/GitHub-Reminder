package com.example.cydrop_frontend;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is a fragment that can be displayed in the ClientNavbarMainActivity
 * Holds the inventory pages, where an admin can manage medication
 * @author Niraj
 */
public class ClientQuestionsFragment extends Fragment implements WebSocketListener{
    EditText messageInputText;
    LinearLayout linearLayout;
    int fragCount = 0;
    private Button client_questions_direct_chat_button;

    /**
     * Empty required constructor
     */
    public ClientQuestionsFragment() {
        // Required empty public constructor
    }

    /**
     * Generic onCreate
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Connect to the websocket and load the chat. Add onClick listeners to buttons
     *
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return the created view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_questions, container, false);

        // Try to connect to websocket
        WebSocketManager.getInstance().connectWebSocket("ws://coms-3090-038.class.las.iastate.edu:8080/chat/" + VolleySingleton.email);
        WebSocketManager.getInstance().setWebSocketListener(ClientQuestionsFragment.this);


        linearLayout = view.findViewById(R.id.global_questions_linear_layout);
        messageInputText = view.findViewById(R.id.client_questions_messagebox);

        view.findViewById(R.id.client_questions_send_button).setOnClickListener(view2 -> {
            WebSocketManager.getInstance().sendMessage(messageInputText.getText().toString());
        });

        // Button to direct chat
        view.findViewById(R.id.client_questions_direct_chat_button).setOnClickListener(view2 -> {

            Intent intent = new Intent(getActivity(), CustomerChatActivity.class);
            startActivity(intent);


        });

        return view;

    }

    /**
     * Create a new MessageFragment and populate the username and content params. Add it to the linearLayout of messages.
     * @param username the username of the message to add
     * @param content the content of the message to add
     */
    public void addMessage(String username, String content){
        fragCount++;
       FragmentManager fragMan = getFragmentManager();
       FragmentTransaction fragTransaction = fragMan.beginTransaction();
       Fragment f = MessageFragment.newInstance(username + ": ",content);
       fragTransaction.add(linearLayout.getId(), f, "frag" + fragCount);
       fragTransaction.commit();
    }

    /**
     * Empty, required by interface
     * @param handshakedata Information about the server handshake.
     */
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    /**
     * Break the message up by user and content. Use string interpolation to cut out email addresses and detect system messages
     * Call addMessage() with the parsed information
     * @param message The received WebSocket message.
     */
    @Override
    public void onWebSocketMessage(String message) {
        String[] messageSplit = message.split(":");
        if (messageSplit.length > 1) {
            String[] usernameSplit = messageSplit[0].split("@");
            String finalUsername = usernameSplit[0].substring(0,1).toUpperCase() +
                    usernameSplit[0].substring(1);
            addMessage(finalUsername, messageSplit[1].trim());
        } else {
            String[] systemMessageSplit = message.split(" ");
            String finalMessage = "";
            for (int i = 0; i < systemMessageSplit.length; i++){
                String[] tempArr = systemMessageSplit[i].split("@");
                if (tempArr.length > 1){
                    String finalUsername = tempArr[0].substring(0,1).toUpperCase() +
                            tempArr[0].substring(1);
                    finalMessage += finalUsername + " ";
                } else {
                    finalMessage += tempArr[0] + " ";
                }
            }
            addMessage("System", finalMessage.trim());
        }
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