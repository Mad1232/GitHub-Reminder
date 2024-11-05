package com.example.cydrop_frontend;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PetCardFragment extends Fragment {

    private static final String ARG_PETNAME = "petName";
    private static final String ARG_PETBREED = "petBreed";
    private static final String ARG_ID = "petId";


    private String petName;
    private String petBreed;
    private String petId;

    public PetCardFragment() {
        // Required empty public constructor
    }


    public static PetCardFragment newInstance(String petName, String petBreed, String petId) {
        PetCardFragment fragment = new PetCardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PETNAME, petName);
        args.putString(ARG_PETBREED, petBreed);
        args.putString(ARG_ID, petId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            petBreed = getArguments().getString(ARG_PETBREED);
            petName = getArguments().getString(ARG_PETNAME);
            petId = getArguments().getString(ARG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pet_card, container, false);
//
//        TextView petNameText = view.findViewById(R.id.petName);
//        petNameText.setText("Pet name: " + petName);
//
//        TextView petBreedText = view.findViewById(R.id.petBreed);
//        petNameText.setText("Pet breed: " + petBreed);

        // Inflate the layout for this fragment
        return view;
    }
}