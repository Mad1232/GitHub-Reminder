package com.example.cydrop_frontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MedicationActivity extends AppCompatActivity {

    private TextView msgResponse;
    private Spinner petSpinner, medicationTypeSpinner;
    private EditText med_input;
    private static final String PETS_URL = "http://coms-3090-038.class.las.iastate.edu:8080/pets";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meds);

        // Initialize views
        petSpinner = findViewById(R.id.spinner_pets);
        medicationTypeSpinner = findViewById(R.id.spinner_medication_type);
        msgResponse = findViewById(R.id.msgResponse);

        // Load pets and medication types
        loadPets();
        loadMedicationTypes();

        findViewById(R.id.btn_return).setOnClickListener(view -> {
            Intent intent = new Intent(MedicationActivity.this, VetNavbarMainActivity.class);
            startActivity(intent);
        });

        // Button to assign medication
        findViewById(R.id.btn_assign_medication).setOnClickListener(view -> {
            String selectedPet = petSpinner.getSelectedItem().toString();
            String selectedMedicationType = medicationTypeSpinner.getSelectedItem().toString();
          //  updatePetMedication(selectedPet, selectedMedicationType);
        });
    }

    private void getJSONData() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,
                "http://coms-3090-038.class.las.iastate.edu:8080/vets/" + VolleySingleton.vetIdTEMP,
                null, // Pass null as the request body since it's a GET request
                response -> {
                    msgResponse.setText(""); // Reset the message text
                    try {
                        // Check if 'pets' exists and is not null
                        if (response.has("pets") && !response.isNull("pets")) {
                            // Extract the pets array from the response JSONObject
                            JSONArray petsArray = response.getJSONArray("pets");

                            // Loop through the pets array
                            for (int i = 0; i < petsArray.length(); i++) {
                                JSONObject petObj = petsArray.getJSONObject(i);

                                // Extract pet name
                                String petName = petObj.getString("pet_name");

                                // Extract medication details (check if 'medication' is present)
                                if (petObj.has("medication") && !petObj.isNull("medication")) {
                                    JSONObject medicationObj = petObj.getJSONObject("medication");

                                    String medicationId = medicationObj.optString("id", "N/A");
                                    String medicationName = medicationObj.optString("name", "N/A");
                                    String medicationStock = medicationObj.optString("stock", "N/A");
                                    String medicationPet = medicationObj.optString("pet", "N/A");

                                    // Format the display message
                                    String newLine = "Pet Name: " + petName + "\n" +
                                            "Medication ID: " + medicationId + "\n" +
                                            "Medication Name: " + medicationName + "\n" +
                                            "Stock: " + medicationStock + "\n" +
                                            "Pet Associated: " + medicationPet + "\n\n";

                                    // Append the information to msgResponse
                                    msgResponse.append(newLine);
                                } else {
                                    msgResponse.append("Pet Name: " + petName + "\nNo medication info available\n\n");
                                }
                            }
                        } else {
                            msgResponse.append("No pets available.");
                        }
                    } catch (JSONException e) {
                        Log.e("JSON Parsing Error", e.toString());
                        throw new RuntimeException(e);
                    }
                },
                error -> Log.e("Volley Error", error.toString())
        );

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

    private void updatePetMedication(int vetId, int petId, String medName) {
        // Create the medication object
        JSONObject medicationData = new JSONObject();
        try {
            medicationData.put("name", medName);
        } catch (JSONException e) {
            Log.e("JSON Error", e.toString());
        }

        // Create the pet object that contains the medication
        JSONObject petData = new JSONObject();
        try {
            petData.put("medication", medicationData);
        } catch (JSONException e) {
            Log.e("JSON Error", e.toString());
        }

        // URL to update a specific pet's medication
        String url = "http://coms-3090-038.class.las.iastate.edu:8080/vets/" + vetId + "/pets/" + petId;

        // Send PUT request to update the pet's medication
        JsonObjectRequest updateRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                petData,
                response -> {
                    Toast.makeText(getApplicationContext(), "Medication Assigned", Toast.LENGTH_LONG).show();                },
                error -> {
                    Log.e("Volley Error", error.toString());
                }
        );

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(updateRequest);
    }

    private void loadPets() {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                "http://coms-3090-038.class.las.iastate.edu:8080/user-pet/" + VolleySingleton.userId,
                null,
                response -> {
                    try {
                        JSONArray pets = response;
                        // Populate the spinner with pet names
                        String[] petNames = new String[pets.length()];
                        for (int i = 0; i < pets.length(); i++) {
                            JSONObject pet = pets.getJSONObject(i);
                            petNames[i] = pet.getString("name");
                        }

                        // Populate the Spinner for pets
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, petNames);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        petSpinner.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Volley Error", error.toString())
        );

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void loadMedicationTypes() {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                "http://coms-3090-038.class.las.iastate.edu:8080/inventory",
                null,
                response -> {
                    try {
                        JSONArray types = response;
                        // Populate the spinner with medication types
                        String[] medicationTypes = new String[types.length()];
                        for (int i = 0; i < types.length(); i++) {
                            JSONObject type = types.getJSONObject(i);
                            medicationTypes[i] = type.getString("name");
                        }

                        // Populate the Spinner for medication types
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, medicationTypes);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        medicationTypeSpinner.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Volley Error", error.toString())
        );

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}