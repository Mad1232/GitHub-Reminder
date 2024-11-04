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

public class VetQuestionsFragment extends Fragment{

    private Button client_questions_direct_chat_button;

    public VetQuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vet_questions, container, false);

        // Button to direct chat
        view.findViewById(R.id.client_questions_direct_chat_button).setOnClickListener(view2 -> {
            Intent intent = new Intent(getActivity(), VetChatActivity.class);
            startActivity(intent);
        });

        return  view;

    }
}