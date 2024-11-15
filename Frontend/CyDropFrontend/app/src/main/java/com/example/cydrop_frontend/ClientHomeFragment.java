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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a fragment that can be displayed in the ClientNavbarMainActivity
 * Holds the home page, where a user can see their current pets, and edit their details
 * @author Niraj
 */
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

    private ArrayList<Fragment> fragList = new ArrayList<>();

    private LinearLayout linearLayout;
    private TextView petsViewTextView;

    /**
     * Required empty constructor
     */
    public ClientHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Generic onCreate
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * GET json data for the pets associated with current userId. Populate those into the view on callback.
     * Add onCLick listeners for all buttons
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return the created view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_home, container, false);

        regularView = view.findViewById(R.id.regularView);
        overlayView = view.findViewById(R.id.addPetOverlay);

        linearLayout = view.findViewById(R.id.petListLinearLayout);

        GetJSONData();

        Button addButton = view.findViewById(R.id.meds_btn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToggleAddPetOverlay(true);
            }
        });

        Button backButton = view.findViewById(R.id.client_home_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToggleAddPetOverlay(false);
            }
        });


        Button submit = view.findViewById(R.id.client_home_submit_button);
        submit.setOnClickListener(view2 -> {
            PostNewPet();
        });

        Button logout = view.findViewById(R.id.logout_button);
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

        ToggleAddPetOverlay(false);

        // Inflate the layout for this fragment
        return view;
    }


    /**
     *  Create a new pet card and add it to the linearLayout
     * @param petId database petId
     * @param petName name of pet
     * @param petType type of pet (dog | cat | )
     * @param petBreed breed of pet (if applicable)
     * @param petAge age of pet
     * @param petGender gender of pet (M | F)
     * @param petDiagnosis medical diagnosis for pet
     */
    public void addNewPetCard(String petId, String petName, String petType, String petBreed,
                              String petAge, String petGender, String petDiagnosis){
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();
        Fragment f = PetCardFragment.newInstance(petId, petName, petType,
                petBreed, petAge, petGender, petDiagnosis );
        fragList.add(f);
        fragTransaction.add(linearLayout.getId(), f, "frag");
        fragTransaction.commit();
    }

    /**
     * Remove all pet fragments. This function should be used if pets are refreshed or reloaded
     */
    public void RemoveAllPetFrags(){
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();
        for (Fragment f : (Fragment[]) fragList.toArray()){
            fragTransaction.remove(f);
        }
        fragTransaction.commit();
    }


    /**
     * Toggle the add pet view.
     * @param addOverlay if true toggle to add pet view. If false, toggle to default view
     */
    private void ToggleAddPetOverlay(boolean addOverlay){
        if (addOverlay){
            regularView.setVisibility(View.INVISIBLE);
            overlayView.setVisibility(View.VISIBLE);
        } else {
            regularView.setVisibility(View.VISIBLE);
            overlayView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * GET the json data for all pets this user has. create pet cards for each of these on callback
     */
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
                            addNewPetCard(json.getString("pet_id"),
                                    json.getString("pet_name"),
                                    json.getString("pet_type"),
                                    json.getString("pet_breed"),
                                    json.getString("pet_age"),
                                    json.getString("pet_gender"),
                                    json.getString("pet_diagnosis")

                            );
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

    /**
     * POST a new pet. Uses the information from the EditText fields contained on the add pet view
     */
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
                        RemoveAllPetFrags();
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


}