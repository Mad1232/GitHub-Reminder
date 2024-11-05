package com.example.cydrop_frontend;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;


public class PetCardFragment extends Fragment {

    private static final String ARG_PETNAME = "petName";
    private static final String ARG_PETBREED = "petBreed";
    private static final String ARG_ID = "petId";


    private String petName;
    private String petBreed;
    private String petId;

    // The dialog interface for confirming pet deletion
    DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                DeletePet();
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                //No button clicked, no response needed
                break;
        }
    };


    public PetCardFragment() {
        // Required empty public constructor
    }


    public static PetCardFragment newInstance(String petId, String petName, String petBreed) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pet_card, container, false);

        TextView petNameText = view.findViewById(R.id.card_pet_name);
        petNameText.setText(petName);

        TextView petBreedText = view.findViewById(R.id.card_pet_type);
        SpannableString s = new SpannableString(petBreed);
        s.setSpan(new StyleSpan(Typeface.ITALIC), 0, s.length(), 0);
        petBreedText.setText(s);

        Button deleteButton = view.findViewById(R.id.card_pet_delete_button);
        deleteButton.setOnClickListener(view2 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Are you sure you would like to delete " + petName +"?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        });


        // Inflate the layout for this fragment
        return view;
    }



    void DeletePet(){
        JsonArrayRequest petDeleteRequest = new JsonArrayRequest(
                Request.Method.DELETE,
                "http://coms-3090-038.class.las.iastate.edu:8080/pet/" + petId,
                null, // Pass null as the request body since it's a GET request
                response -> {
                    // No response
                },
                error -> {

                }
        );
        VolleySingleton.getInstance(getContext().getApplicationContext()).addToRequestQueue(petDeleteRequest);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction trans = fragmentManager.beginTransaction();
        trans.remove(this).commit();
    }
}