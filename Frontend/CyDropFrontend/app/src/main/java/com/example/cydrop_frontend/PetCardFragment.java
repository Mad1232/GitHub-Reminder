package com.example.cydrop_frontend;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PetCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PetCardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PETNAME = "petName";
    private static final String ARG_PETBREED = "petBreed";
    //private static final String ARG_ = "petName";


    // TODO: Rename and change types of parameters
    private String petName;
    private String petBreed;

    public PetCardFragment() {
        // Required empty public constructor
    }


    public static PetCardFragment newInstance(String petName, String petBreed) {
        PetCardFragment fragment = new PetCardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PETNAME, petName);
        args.putString(ARG_PETBREED, petBreed);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            petBreed = getArguments().getString(ARG_PETBREED);
            petName = getArguments().getString(ARG_PETNAME);
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