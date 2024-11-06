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
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;


public class PetCardFragment extends Fragment {

    private static final String ARG_PETNAME = "petName";
    private static final String ARG_PETTYPE = "petType";
    private static final String ARG_ID = "petId";
    private static final String ARG_PETBREED = "petBreed";
    private static final String ARG_PETAGE = "petAge";
    private static final String ARG_PETGENDER = "petGender";
    private static final String ARG_PETDIAGNOSIS = "petDiagnosis";


    private String petName;
    private String petType;
    private String petBreed;
    private String petAge;
    private String petGender;
    private String petDiagnosis;
    private String petId;

    private View editingLayout;
    private View defaultLayout;

    // Info on collapsed view
    private TextView petNameText;
    private TextView petBreedText;
    private TextView editingTextTitle;

    // Info on editing view
    private EditText petNameEditText;
    private EditText petTypeEditText;
    private EditText petBreedEditText;
    private EditText petAgeEditText;
    private EditText petGenderEditText;
    private EditText petdiagnosisEditText;




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


    public static PetCardFragment newInstance(String petId, String petName, String petType, String petBreed, String petAge, String petGender, String petDiagnosis) {
        PetCardFragment fragment = new PetCardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PETNAME, petName);
        args.putString(ARG_PETTYPE, petType);
        args.putString(ARG_ID, petId);
        args.putString(ARG_PETBREED, petBreed);
        args.putString(ARG_PETAGE, petAge);
        args.putString(ARG_PETGENDER, petGender);
        args.putString(ARG_PETDIAGNOSIS, petDiagnosis);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            petType = getArguments().getString(ARG_PETTYPE);
            petName = getArguments().getString(ARG_PETNAME);
            petId = getArguments().getString(ARG_ID);
            petBreed = getArguments().getString(ARG_PETBREED);
            petAge = getArguments().getString(ARG_PETAGE);
            petGender = getArguments().getString(ARG_PETGENDER);
            petDiagnosis = getArguments().getString(ARG_PETDIAGNOSIS);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pet_card, container, false);

        Button deleteButton = view.findViewById(R.id.card_pet_delete_button);
        deleteButton.setOnClickListener(view2 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Are you sure you would like to delete " + petName +"?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        });

        Button editButton = view.findViewById(R.id.card_pet_edit_button);
        editButton.setOnClickListener(view3 -> {
            ToggleEditMode(true);
        });

        Button cancelEdit = view.findViewById(R.id.card_pet_edit_cancel_button);
        cancelEdit.setOnClickListener( x -> {
            ToggleEditMode(false);
        });

        petNameText = view.findViewById(R.id.card_pet_name);
        petBreedText = view.findViewById(R.id.card_pet_type);
        editingTextTitle = view.findViewById(R.id.card_edit_text);

        editingLayout = view.findViewById(R.id.card_pet_expanded_layout);
        defaultLayout = view.findViewById(R.id.card_pet_collapsed_layout);

        petNameEditText = view.findViewById(R.id.card_pet_edit_name_input);
        petTypeEditText = view.findViewById(R.id.card_pet_edit_type_input);
        petBreedEditText = view.findViewById(R.id.card_edit_pet_breed);
        petAgeEditText = view.findViewById(R.id.card_pet_edit_age_input);
        petGenderEditText = view.findViewById(R.id.card_pet_edit_gender_input);
        petdiagnosisEditText = view.findViewById(R.id.card_pet_edit_diagnosis_input);

        PopulateForms();

        // Inflate the layout for this fragment
        return view;
    }

    void PopulateForms(){
        petNameText.setText(petName);

        SpannableString s = new SpannableString(petType);
        s.setSpan(new StyleSpan(Typeface.ITALIC), 0, s.length(), 0);
        petBreedText.setText(s);

        s = new SpannableString("Editing " + petName);
        s.setSpan(new UnderlineSpan(), 0, s.length(), 0);
        editingTextTitle.setText(s);

        petNameEditText.setText(petName);
        petTypeEditText.setText(petType);
        petBreedEditText.setText(petBreed);
        petAgeEditText.setText(petAge);
        petGenderEditText.setText(petGender);
        petdiagnosisEditText.setText(petDiagnosis);
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


    void ToggleEditMode(boolean isEditing){
        if (isEditing){
            defaultLayout.setVisibility(View.GONE);
            editingLayout.setVisibility(View.VISIBLE);
        } else {
            editingLayout.setVisibility(View.GONE);
            defaultLayout.setVisibility(View.VISIBLE);
        }
    }
}