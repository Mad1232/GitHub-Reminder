package com.example.cydrop_frontend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

/**
 * MedicationActivity manages interactions with the medication inventory,
 * allowing the user to view, add, edit, and delete medications.
 */
public class MedicationActivity extends AppCompatActivity {

    /**
     * Textview displaying the medication list.
     */
    private TextView msgResponse;

    /**
     * Base URL for accessing medication inventory endpoint.
     */
    private static final String URL = "http://coms-3090-038.class.las.iastate.edu:8080/inventory";

    /**
     * EditText for user input when adding or deleting medication.
     */
    private EditText med_input;

    /**
     * Initializes the activity, sets up listeners for add, edit, delete, and return buttons.
     * Add button captures user input and sends a POST request to add a medication.
     * Delete button captures user input and sends a DELETE request to delete a medication.
     * Edit button navigates to the EditMedsActivity.
     * Return button navigates to the VetNavbarMainActivity.
     *
     * @param savedInstanceState Bundle containing the saved state of the activity.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meds);

        msgResponse = findViewById(R.id.tv_medication_list);

        getJSONData();

        findViewById(R.id.btn_add_medication).setOnClickListener(view1 -> {
            med_input = findViewById(R.id.et_medication_input);
            String medicationInput = med_input.getText().toString();
            postRequest(URL, medicationInput);
            getJSONData();
        });

        findViewById(R.id.btn_edit_medication).setOnClickListener(view1 -> {
            Intent intent = new Intent(MedicationActivity.this, EditMedsActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_delete_medication).setOnClickListener(view1 -> {
            med_input = findViewById(R.id.et_medication_input);
            String medicationInput = med_input.getText().toString();
            delRequest(URL, medicationInput);
            getJSONData();
        });

        findViewById(R.id.btn_return).setOnClickListener(view1 -> {
            Intent intent = new Intent(MedicationActivity.this, VetNavbarMainActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Retrieves and displays the medication list from the server as a JSON array.
     * Parses each entry to display the medication details in a clear way.
     */
    private void getJSONData() {
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null, // Pass null as the request body since it's a GET request
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        msgResponse.setText(""); // reset the msg text
                        try{
                            JSONArray jsonArr = response;
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jObj = jsonArr.getJSONObject(i);
                                String newLine = "ID: " + jObj.getString("id") + "\n" +
                                        "Name: " + jObj.getString("name") + "\n" +
                                        "Stock: " + jObj.getString("stock") + "\n"
                                        + "Pet: " + jObj.getString("pet") + "\n";
                                msgResponse.setText(msgResponse.getText() + newLine);
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
     * Sends a POST request to add a medication to the inventory.
     *
     * @param url Endpoint for adding a medication.
     * @param medication Name of the medication to be added.
     */
    private void postRequest(String url, String medication) {

        // Create JSON object for POST request
        JSONObject json = new JSONObject();
        try {
            json.put("name", medication);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Medication Added", Toast.LENGTH_LONG).show();
                        getJSONData();
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

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    /**
     * Sends a DELETE request to remove a medication from the inventory.
     *
     * @param URL Endpoint for deleting a medication
     * @param id ID of the medication to be deleted.
     */
    private void delRequest(String URL, String id){

        StringRequest request = new StringRequest(Request.Method.DELETE, URL + "/" + id,
                new Response.Listener<String>() {
                    @Override
            public void onResponse(String response) {
                        if ("Ok".equals(response)) {
                            Toast.makeText(getApplicationContext(), "Medication Deleted", Toast.LENGTH_LONG).show();
                            getJSONData();
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
