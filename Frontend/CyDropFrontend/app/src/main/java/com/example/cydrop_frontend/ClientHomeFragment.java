package com.example.cydrop_frontend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private View petDeleteView;
    private View petEditView;

    private EditText petId;
    private EditText petType;
    private EditText petBreed;
    private EditText petName;
    private EditText petDiagnosis;
    private EditText petAge;
    private EditText petGender;

    private EditText petDeleteId;

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

        GetJSONData();

        Button addButton = view.findViewById(R.id.addPetButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToggleAddPetOverlay(true);
            }
        });

        Button backButton = view.findViewById(R.id.adminCloseOverlayButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToggleAddPetOverlay(false);
            }
        });


        Button editPetButton = view.findViewById(R.id.editPetById);
        editPetButton.setOnClickListener(view2 -> {
            TogglePetEdit(true);
        });

        Button submit = view.findViewById(R.id.adminInventorySubmitButton);
        submit.setOnClickListener(view2 -> {
            PostNewPet();
        });

        Button deleteSubmit = view.findViewById(R.id.deletePetSubmitButton);
        deleteSubmit.setOnClickListener(view2 -> {
            DeletePet();
            TogglePetDelete(false);
            GetJSONData();
        });

        Button logout = view.findViewById(R.id.adminLogoutButton);
        logout.setOnClickListener(view1 -> {

            SharedPreferences sharedPref =  PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("userId", "-1");
            editor.putString("userType", "none");
            editor.commit();

            Intent intent = new Intent(MainActivity.class.toString());
            startActivity(intent);
        });

        petType = view.findViewById(R.id.petType);
        petBreed = view.findViewById(R.id.card_pet_type);
        petAge = view.findViewById(R.id.petAge);
        petName = view.findViewById(R.id.petNameInput);
        petDiagnosis = view.findViewById(R.id.petDiagnosis);
        petGender = view.findViewById(R.id.petGender);
        petDeleteId = view.findViewById(R.id.petIdToDelete);

        ToggleAddPetOverlay(false);

        // Inflate the layout for this fragment
        return view;
    }


    public void addNewPetCard(String petName, String petType, String petId){
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();
        Fragment f = PetCardFragment.newInstance(petName, petType, petId);
        fragTransaction.add(linearLayout.getId(), f, "frag");
        fragTransaction.commit();
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

    private void TogglePetEdit(boolean addOverlay){
        if (addOverlay){
            regularView.setVisibility(View.INVISIBLE);
            petEditView.setVisibility(View.VISIBLE);
        } else {
            regularView.setVisibility(View.VISIBLE);
            petEditView.setVisibility(View.INVISIBLE);
        }
    }

    private void TogglePetDelete(boolean addOverlay){
        if (addOverlay){
            regularView.setVisibility(View.INVISIBLE);
            petDeleteView.setVisibility(View.VISIBLE);
        } else {
            regularView.setVisibility(View.VISIBLE);
            petDeleteView.setVisibility(View.INVISIBLE);
        }
    }

    private void GetJSONData() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(
                Request.Method.GET,
                "http://coms-3090-038.class.las.iastate.edu:8080/user-pet/" + VolleySingleton.userId,
                null, // Pass null as the request body since it's a GET request
                response -> {
                    try {
                        JSONArray jsonArr = response;
                        for (int i = 0; i < jsonArr.length(); i++){
                            JSONObject json = jsonArr.getJSONObject(i);
                            addNewPetCard(json.getString("pet_id"), json.getString("pet_name"), json.getString("pet_type"));
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
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
            JSONObject ownerInfo = new JSONObject();
            ownerInfo.put("id", VolleySingleton.userId);
            pet.put("owner", ownerInfo);

//            JSONObject medicationInfo = new JSONObject();
//            pet.put("medication", medicationInfo);

            pet.put("pet_name", petName.getText().toString());
            pet.put("pet_type", petType.getText().toString());
            pet.put("pet_breed", petBreed.getText().toString());
            pet.put("pet_diagnosis", petDiagnosis.getText().toString());
            pet.put("pet_age", Integer.parseInt(petAge.getText().toString()) );
            pet.put("pet_gender", petGender.getText().toString());
        } catch (JSONException e){
            // Unable to create json object
            Toast.makeText(getActivity(), "Error creating JSON", Toast.LENGTH_LONG);
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
                        ToggleAddPetOverlay(false);
                        GetJSONData();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Oopsie
                        Toast.makeText(getActivity(), "Error POSTing pet", Toast.LENGTH_LONG);
                    }
                }
        );

        VolleySingleton.getInstance(getContext().getApplicationContext()).addToRequestQueue(postReq);
    }

    void DeletePet(){
        JsonArrayRequest petDeleteRequest = new JsonArrayRequest(
                Request.Method.DELETE,
                "http://coms-3090-038.class.las.iastate.edu:8080/pet/" + petDeleteId.getText().toString().trim(),
                null, // Pass null as the request body since it's a GET request
                response -> {
                    TogglePetDelete(false);
                    Toast.makeText(getActivity(), "Pet delete works!", Toast.LENGTH_LONG);
                },
                error -> {

                }
        );

        VolleySingleton.getInstance(getContext().getApplicationContext()).addToRequestQueue(petDeleteRequest);
    }


}