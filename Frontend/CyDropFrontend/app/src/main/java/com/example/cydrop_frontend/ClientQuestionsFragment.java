package com.example.cydrop_frontend;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
public class ClientQuestionsFragment extends Fragment {

    public ClientQuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_questions, container, false);

        Button vet_chat = view.findViewById(R.id.btn_vet_chat);
        vet_chat.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), VetChatActivity.class);
            startActivity(intent);
        });

        return view;
    }
}