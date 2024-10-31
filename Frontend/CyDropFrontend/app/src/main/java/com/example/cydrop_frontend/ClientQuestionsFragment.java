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

public class ClientQuestionsFragment extends Fragment{
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


        // Button to direct chat
        view.findViewById(R.id.client_questions_direct_chat_button).setOnClickListener(view2 -> {
            Intent intent = new Intent(getActivity(), CustomerChatActivity.class);
            startActivity(intent);
        });

        return view;
    }

}