package com.example.cydrop_frontend;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class VetCustomersFragment extends Fragment {


    public VetCustomersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vet_customers, container, false);

        Button addCustomer = view.findViewById(R.id.add_new_customer_button);
        addCustomer.setOnClickListener(view2 -> {
            ToggleAddCustomer(true);
        });



        return view;
    }


    void ToggleAddCustomer(boolean isAdding){
        if (isAdding){

        }
    }
}