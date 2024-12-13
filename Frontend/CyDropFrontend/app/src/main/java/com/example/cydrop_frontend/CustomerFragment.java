package com.example.cydrop_frontend;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

/**
 * This is a fragment that displays a customers information for vets and admins
 * @author Niraj
 */
public class CustomerFragment extends Fragment {

    private static final String ARG_PETS = "pets";
    private static final String ARG_CUSTOMER_NAME = "customerName";
    private static final String ARG_CUSTOMER_ID = "id";


    private String[] pets;
    private String customerName;
    private String customerId;

    private View defaultView;
    private View expandedView;



    // The dialog interface for confirming pet deletion
    DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                RemoveCustomer();
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                //No button clicked, no response needed
                break;
        }
    };


    public CustomerFragment() {
        // Required empty public constructor
    }


    public static CustomerFragment newInstance(String customerName, String customerId, String[] pets) {
        CustomerFragment fragment = new CustomerFragment();
        Bundle args = new Bundle();
        args.putStringArray (ARG_PETS, pets);
        args.putString(ARG_CUSTOMER_NAME, customerName);
        args.putString(ARG_CUSTOMER_ID, customerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pets = getArguments().getStringArray(ARG_PETS);
            customerName = getArguments().getString(ARG_CUSTOMER_NAME);
            customerId = getArguments().getString(ARG_CUSTOMER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customer, container, false);

        TextView name = view.findViewById(R.id.card_customer_name);
        name.setText(customerName);

        TextView cardEditingName = view.findViewById(R.id.card_customer_editing_title);
        cardEditingName.setText(customerName);

        expandedView = view.findViewById(R.id.card_customer_expanded_layout);
        defaultView = view.findViewById(R.id.card_customer_collapsed_layout);

        view.findViewById(R.id.card_customer_edit_collapse_button).setOnClickListener(view3 -> {
            toggleExpandedView(false);
        });

        view.findViewById(R.id.card_customer_expand_button).setOnClickListener(view3 -> {
            toggleExpandedView(true);
        });

        view.findViewById(R.id.card_customer_edit_remove_customer_button).setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you would like to remove " + customerName +" as a customer?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
        });

        String petListString = "";
        for (String s : pets){
            petListString += s + "\n";
        }
        TextView petListText = view.findViewById(R.id.card_customer_pet_list_text_in_linear);
        petListText.setText(petListString);

        view.findViewById(R.id.card_customer_edit_inspect_button).setOnClickListener(view3 -> {
            Intent intent = new Intent(getActivity(), MedicationActivity.class);
            startActivity(intent);
        });


        toggleExpandedView(false);
        return view;
    }

    void toggleExpandedView(boolean isExpanded) {
        if (isExpanded) {
            expandedView.setVisibility(View.VISIBLE);
            defaultView.setVisibility(View.GONE);
        } else {
            expandedView.setVisibility(View.GONE);
            defaultView.setVisibility(View.VISIBLE);
        }
    }


    void RemoveCustomer(){
        JsonArrayRequest petDeleteRequest = new JsonArrayRequest(
                Request.Method.DELETE,
                "http://coms-3090-038.class.las.iastate.edu:8080/vets/" + VolleySingleton.userId +
                        "/customers/" + customerId.trim(),
                null, // Pass null as the request body since it's a DELETE request
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