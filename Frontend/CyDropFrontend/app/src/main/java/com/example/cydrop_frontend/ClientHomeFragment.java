package com.example.cydrop_frontend;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ClientHomeFragment extends Fragment {

    private View overlayView;
    private View regularView;

    private EditText petId;
    private EditText petType;
    private EditText petBreed;
    private EditText petName;
    private EditText petDiagnosis;
    private EditText petAge;
    private EditText petGender;

    LinearLayout linearLayout;
    TextView petsViewTextView;

    public ClientHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_home, container, false);
        regularView = view.findViewById(R.id.regularView);
        overlayView = view.findViewById(R.id.addPetOverlay);

        linearLayout = view.findViewById(R.id.petListLinearLayout);
        petsViewTextView = view.findViewById(R.id.textView2);

        GetJSONData();

        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToggleAddPetOverlay(true);
            }
        });

        Button backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToggleAddPetOverlay(false);
            }
        });

        Button submit = view.findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        petId = view.findViewById(R.id.petId);
        petType = view.findViewById(R.id.petType);
        petBreed = view.findViewById(R.id.petBreed);
        petAge = view.findViewById(R.id.petAge);
        petName = view.findViewById(R.id.petNameInput);
        petDiagnosis = view.findViewById(R.id.petDiagnosis);
        petGender = view.findViewById(R.id.petGender);

        ToggleAddPetOverlay(false);

        // Inflate the layout for this fragment
        return view;
    }


    private void ToggleAddPetOverlay(boolean addOverlay){
        if (addOverlay){
            regularView.setVisibility(View.INVISIBLE);
            overlayView.setVisibility(View.VISIBLE);
        } else {
            regularView.setVisibility(View.VISIBLE);
            overlayView.setVisibility(View.INVISIBLE);
        }
    }

    private void GetJSONData() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(
                Request.Method.GET,
                "http://coms-3090-038.class.las.iastate.edu:8080/pets",
                null, // Pass null as the request body since it's a GET request
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            petsViewTextView.setText("");
                            JSONArray jsonArr = response;
                            for (int i = 0; i < jsonArr.length(); i++){
                                JSONObject json = jsonArr.getJSONObject(i);

                                String newText = "Pet name: " + json.getString("pet_name") +
                                        "\nPet breed: " + json.getString("pet_breed") + "\n\n";
                                petsViewTextView.setText(petsViewTextView.getText() + newText);

//                                FragmentManager fragmentManager = getParentFragmentManager();
//                                FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
//
//                                PetCardFragment frag = new PetCardFragment();
////                                PetCardFragment frag = PetCardFragment.newInstance(
////                                        json.getString("pet_name"),
////                                        json.getString("pet_breed"));
//
//                                fragTransaction.add(layout.getId(), frag , "fragment" + i);
//                                fragTransaction.commit();
//                                layout.addView(frag.getView());
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        VolleySingleton.getInstance(getContext().getApplicationContext()).addToRequestQueue(jsonArrReq);
    }

    private void PostNewPet(){
        JSONObject pet = new JSONObject();

        try{
            // TEMPORARY OWNER ID. ALWAYS SET TO 1 HERE
            JSONObject ownerInfo = new JSONObject();
            ownerInfo.put("id", 1);
            pet.put("owner", ownerInfo);

            pet.put("pet_id", petId.getText().toString());
            pet.put("pet_name", petName.getText().toString());
            pet.put("pet_type", petType.getText().toString());
            pet.put("pet_breed", petBreed.getText().toString());
            pet.put("pet_diagnosis", petDiagnosis.getText().toString());
            pet.put("pet_age", Integer.parseInt(petAge.getText().toString()) );
            pet.put("pet_gender", petGender.getText().toString());
        } catch (JSONException e){
            // Unable to create json object
            return;
        }

        JsonObjectRequest postReq = new JsonObjectRequest(
                Request.Method.POST,
                "http://coms-3090-038.class.las.iastate.edu:8080/pet",
                pet,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Victory! i think
                        GetJSONData();
                        ToggleAddPetOverlay(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Oopsie
                    }
                }
        );

        VolleySingleton.getInstance(getContext().getApplicationContext()).addToRequestQueue(postReq);
    }


}