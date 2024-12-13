package com.example.cydrop_frontend;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

public class ReminderCardFragment extends Fragment {

    private static final String ARG_DAYS = "daysParam";
    private static final String ARG_TIME = "timeParam";
    private static final String ARG_PET = "petParam";
    private static final String ARG_PET_ID = "petIdParam";
    private static final String ARG_DESCRIPTION = "descriptionParam";
    private static final String ARG_MEDICATION = "medicationParam";

    private int[] days;
    private String time;
    private String petId;
    private String description;
    private String pet;
    private String medication;

    private View defaultView;
    private View expandedView;
    private View editView;

    public ReminderCardFragment() {
        // Required empty public constructor
    }

    private enum cardState {
        DEFAULT,
        EXPANDED,
        EDIT
    };

    // The dialog interface for confirming pet deletion
    DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                DeleteReminder();
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                //No button clicked, no response needed
                break;
        }
    };

    void DeleteReminder(){
        JsonArrayRequest petDeleteRequest = new JsonArrayRequest(
                Request.Method.DELETE,
                " http://coms-3090-038.class.las.iastate.edu:8080/events/pets/" + petId,
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


    public static ReminderCardFragment newInstance(String pet, String petId, String medication, int[] days, String time, String description) {
        ReminderCardFragment fragment = new ReminderCardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PET, pet);
        args.putString(ARG_MEDICATION, medication);
        args.putIntArray(ARG_DAYS, days);
        args.putString(ARG_TIME, time);
        args.putString(ARG_PET_ID, petId);
        args.putString(ARG_DESCRIPTION, description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            medication = getArguments().getString(ARG_MEDICATION);
            pet = getArguments().getString(ARG_PET);
            days = getArguments().getIntArray(ARG_DAYS);
            time = getArguments().getString(ARG_TIME);
            description = getArguments().getString(ARG_DESCRIPTION);
            petId = getArguments().getString(ARG_PET_ID);

            // Fix the time string
            String[] split = time.split("T")[1].split(":");
            time = split[0] +":"+  split[1];
        }
    }

    private void switchState(cardState state){
        switch (state){
            case EDIT:
                editView.setVisibility(View.VISIBLE);
                expandedView.setVisibility(View.GONE);
                defaultView.setVisibility(View.GONE);
                break;
            case EXPANDED:
                editView.setVisibility(View.GONE);
                expandedView.setVisibility(View.VISIBLE);
                defaultView.setVisibility(View.GONE);
                break;

            case DEFAULT:
                editView.setVisibility(View.GONE);
                expandedView.setVisibility(View.GONE);
                defaultView.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reminder_card, container, false);

        defaultView = view.findViewById(R.id.card_reminder_collapsed_layout);
        expandedView = view.findViewById(R.id.card_reminder_expanded_layout);
        editView = view.findViewById(R.id.card_reminder_edit_layout);


        Button deleteButton = view.findViewById(R.id.card_reminder_delete_button);
        deleteButton.setOnClickListener(view2 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Are you sure you would like to delete reminder for " + pet +"?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        });

        deleteButton = view.findViewById(R.id.card_reminder_delete_button_expanded);
        deleteButton.setOnClickListener(view2 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Are you sure you would like to delete reminder for " + pet +"?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        });


        view.findViewById(R.id.card_reminder_less_button_expanded).setOnClickListener(view23 -> {
            switchState(cardState.DEFAULT);
        });

        view.findViewById(R.id.card_reminder_expand_button).setOnClickListener(view23 -> {
            switchState(cardState.EXPANDED);
        });


        for (int i = 0; i < days.length; i ++){
            if (days[i] == 0){
                TextView iconToDisable = null;
                TextView icon2 = null;
                switch (i){
                    case 0:
                        iconToDisable = view.findViewById(R.id.card_reminder_m_icon);
                        icon2 = view.findViewById(R.id.card_reminder_m_icon_expanded);
                        break;
                    case 1:
                        iconToDisable = view.findViewById(R.id.card_reminder_t_icon);
                        icon2 = view.findViewById(R.id.card_reminder_t_icon_expanded);
                        break;
                    case 2:
                        iconToDisable = view.findViewById(R.id.card_reminder_w_icon);
                        icon2 = view.findViewById(R.id.card_reminder_w_icon_expanded);
                        break;
                    case 3:
                        iconToDisable = view.findViewById(R.id.card_reminder_r_icon);
                        icon2 = view.findViewById(R.id.card_reminder_r_icon_expanded);
                        break;
                    case 4:
                        iconToDisable = view.findViewById(R.id.card_reminder_f_icon);
                        icon2 = view.findViewById(R.id.card_reminder_f_icon_expanded);
                        break;
                    case 5:
                        iconToDisable = view.findViewById(R.id.card_reminder_s_icon);
                        icon2 = view.findViewById(R.id.card_reminder_s_icon_expanded);
                        break;
                    case 6:
                        iconToDisable = view.findViewById(R.id.card_reminder_u_icon);
                        icon2 = view.findViewById(R.id.card_reminder_u_icon_expanded);
                        break;
                    default:
                        // uhhhh, shouldnt be here
                        iconToDisable = view.findViewById(R.id.card_reminder_u_icon);
                        icon2 = view.findViewById(R.id.card_reminder_u_icon_expanded);
                }
                iconToDisable.setBackground(null);
                icon2.setBackground(null);
            }
        }

        TextView petnameView = view.findViewById(R.id.card_reminder_pet_name);
        petnameView.setText(pet);
        TextView reminderTimeView = view.findViewById(R.id.card_reminder_time);
        reminderTimeView.setText(time);

        reminderTimeView = view.findViewById(R.id.card_reminder_time_expanded);
        reminderTimeView.setText(time);

        TextView t = view.findViewById(R.id.card_reminder_description_expanded);
        t.setText(description);
        t = view.findViewById(R.id.card_reminder_medication_expanded);
        t.setText(medication);

        TextView petNameViewExpanded = view.findViewById(R.id.card_reminder_pet_name_expanded);
        petNameViewExpanded.setText(pet);



        switchState(cardState.DEFAULT);

        return view;
    }
}