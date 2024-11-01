package com.example.cydrop_frontend;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;


public class ClientQuestionsFragment extends Fragment implements WebSocketListener{
    EditText messageInputText;
    LinearLayout linearLayout;
    int fragCount = 0;


    public ClientQuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

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


        return  view;
    }

    public void addMessage(String username, String content){
       FragmentManager fragMan = getFragmentManager();
       FragmentTransaction fragTransaction = fragMan.beginTransaction();
       Fragment f = MessageFragment.newInstance(username + ": ",content);
       fragTransaction.add(linearLayout.getId(), f, "frag" + fragCount);
       fragTransaction.commit();
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

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

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onWebSocketError(Exception ex) {

    }
}