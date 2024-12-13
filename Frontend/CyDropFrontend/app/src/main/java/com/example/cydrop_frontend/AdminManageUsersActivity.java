package com.example.cydrop_frontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.example.cydrop_frontend.AdminUsersFragment;

public class AdminManageUsersActivity extends AppCompatActivity {
    private Button btnReturn, btnDeleteUser, btnSearch;
    private EditText etUserId;
    private TextView msgResponse;
    private ScrollView scrollViewUserInfo;

    private static final String URL = "http://coms-3090-038.class.las.iastate.edu:8080/admin/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_users); // Make sure your XML file is named activity_admin_user.xml

        // Link Java variables to XML views using findViewById
        btnReturn = findViewById(R.id.btnReturn);
        btnSearch = findViewById(R.id.btnSearch);
        btnDeleteUser = findViewById(R.id.btnDeleteUser);
        etUserId = findViewById(R.id.etUserId);
        msgResponse = findViewById(R.id.tvUserInfo);
        scrollViewUserInfo = findViewById(R.id.scrollViewUserInfo);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = etUserId.getText().toString().trim();
                getJSONData(userId);
                etUserId.setText("");
            }
        });

        // Return button logic (Navigates to the previous screen)
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminManageUsersActivity.this, AdminNavbarMainActivity.class);
                startActivity(intent);
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frame_layout, new AdminUsersFragment());
//                fragmentTransaction.commit();
            }
        });

        // Delete User button logic
        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = etUserId.getText().toString().trim();
                delRequest(userId);
                etUserId.setText("");
            }
        });
    }

    /**
     * Retrieves and displays the medication list from the server as a JSON array.
     * Parses each entry to display the medication details in a clear way.
     */
    private void getJSONData(String id) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,
                URL + "/" + id,
                null, // Pass null as the request body since it's a GET request
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        msgResponse.setText(""); // reset the msg text
                        try{
//                            JSONArray jsonArr = response;
//                            for (int i = 0; i < jsonArr.length(); i++) {
//                                JSONObject jObj = jsonArr.getJSONObject(i);
//                                String newLine = "ID: " + jObj.getString("id") + "\n" +
//                                        "Name: " + jObj.getString("name") + "\n" +
//                                        "Stock: " + jObj.getString("stock") + "\n"
//                                        + "Pet: " + jObj.getString("pet") + "\n";


                                // Parse the main response object
                                JSONObject userObj = response;

                                // Extract user details
                                String userInfo = "User ID: " + userObj.getInt("id") + "\n" +
                                        "Email: " + userObj.getString("email") + "\n" +
                                        "Password: " + userObj.getString("password") + "\n" +
                                        "Phone Number: " + userObj.getString("phoneNumber") + "\n" +
                                        "Address: " + userObj.getString("address") + "\n";

                            msgResponse.append(userInfo + "\n");
                                // Extract pets array
                                JSONArray petsArray = userObj.getJSONArray("pets");

                                for (int i = 0; i < petsArray.length(); i++) {
                                    JSONObject petObj = petsArray.getJSONObject(i); // Individual pet object

                                    // Extract pet details
                                    String petInfo = "Pet ID: " + petObj.getInt("pet_id") + "\n" +
                                            "Pet Name: " + petObj.getString("pet_name") + "\n" +
                                            "Pet Type: " + petObj.getString("pet_type") + "\n" +
                                            "Pet Breed: " + petObj.getString("pet_breed") + "\n" +
                                            "Pet Diagnosis: " + petObj.getString("pet_diagnosis") + "\n" +
                                            "Pet Age: " + petObj.getInt("pet_age") + "\n" +
                                            "Pet Gender: " + petObj.getString("pet_gender") + "\n";

                                    msgResponse.append(petInfo + "\n");

                                    // Extract 'medication' details for the pet
                                    if (petObj.has("medication")) {
                                        JSONObject medicationObj = petObj.getJSONObject("medication");

                                        String medicationInfo = "Medication ID: " + medicationObj.getInt("id") + "\n" +
                                                "Medication Name: " + medicationObj.getString("name") + "\n" +
                                                "Stock: " + medicationObj.getInt("stock") + "\n" +
                                                "Pet (from medication): " + medicationObj.getString("pet") + "\n";

                                        msgResponse.append(medicationInfo + "\n");
                                    }
                                    // Extract 'veterinarians' array for each pet
                                    if (petObj.has("veterinarians") && !petObj.isNull("veterinarians")) {
                                        JSONArray vetsArray = petObj.getJSONArray("veterinarians");

                                        for (int j = 0; j < vetsArray.length(); j++) {
                                            JSONObject vetObj = vetsArray.getJSONObject(j);

                                            String vetInfo = "Veterinarian ID: " + vetObj.getInt("vet_id") + "\n" +
                                                    "Veterinarian Name: " + vetObj.getString("vet_name") + "\n" +
                                                    "Specialization: " + vetObj.getString("specialization") + "\n" +
                                                    "Email: " + vetObj.getString("vetEmail") + "\n" +
                                                    "License #: " + vetObj.getString("licenseNum") + "\n" +
                                                    "Clinic Address: " + vetObj.getString("clinicAddress") + "\n" +
                                                    "Phone: " + vetObj.getString("phone") + "\n";

                                            msgResponse.append(vetInfo + "\n");

                                            // Extract pets related to this vet (if available)
                                            if (vetObj.has("pets") && !vetObj.isNull("pets")) {
                                                JSONArray vetPetsArray = vetObj.getJSONArray("pets");
                                                msgResponse.append("Pets Under This Vet: ");
                                                for (int k = 0; k < vetPetsArray.length(); k++) {
                                                    msgResponse.append(vetPetsArray.getString(k) + (k < vetPetsArray.length() - 1 ? ", " : ""));
                                                }
                                            }

                                            msgResponse.append("\n");

                                            // Extract customers related to this vet
                                            if (vetObj.has("customers") && !vetObj.isNull("customers")) {
                                                JSONArray customersArray = vetObj.getJSONArray("customers");
                                                msgResponse.append("Customers: ");
                                                for (int k = 0; k < customersArray.length(); k++) {
                                                    msgResponse.append(customersArray.getString(k) + (k < customersArray.length() - 1 ? ", " : ""));
                                                }
                                            }

                                            msgResponse.append("\n\n");
                                        }
                                    }
                                }

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
//                        Log.d("Volley Response", response.toString());
//                        msgResponse.setText(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

    /**
     * Sends a DELETE request to remove a medication from the inventory.
     *
     * @param id ID of the user to be deleted.
     */
    private void delRequest(String id){

        StringRequest request = new StringRequest(Request.Method.DELETE, URL + "/" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if ("Ok".equals(response)) {
                            Toast.makeText(getApplicationContext(), "User Deleted", Toast.LENGTH_LONG).show();
                          //  getJSONData(id);
                        } else {
                            Toast.makeText(getApplicationContext(), "Unexpected response: " + response, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

}
