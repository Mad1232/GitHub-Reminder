package com.example.cydrop_frontend;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

public class ClientQuestionsFragment extends Fragment implements WebSocketListener{
    TextView messageBody;
    EditText messageInputText;
    private Button client_questions_direct_chat_button;

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

        messageBody = view.findViewById(R.id.websockt_testview);
        messageBody.setText("ws://coms-3090-038.class.las.iastate.edu:8080/chat/" + VolleySingleton.email);

        messageInputText = view.findViewById(R.id.client_questions_messagebox);

        view.findViewById(R.id.client_questions_send_button).setOnClickListener(view2 -> {
            WebSocketManager.getInstance().sendMessage(messageInputText.getText().toString());
        });

        // Button to direct chat
        view.findViewById(R.id.client_questions_direct_chat_button).setOnClickListener(view2 -> {
            Intent intent = new Intent(getActivity(), CustomerChatActivity.class);
            startActivity(intent);
        });

        return  view;

    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onWebSocketMessage(String message) {
        messageBody.setText(message);
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onWebSocketError(Exception ex) {

    }
}