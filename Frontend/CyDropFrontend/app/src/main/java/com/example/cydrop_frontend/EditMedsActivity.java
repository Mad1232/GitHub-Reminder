package com.example.cydrop_frontend;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * EditMedsActivity class provides an interface for editing medication details, allowing users to update the name and stock of a specified medication.
 * It sends a PUT request to update the medication information on the server.
 *
 * @author Madison Vosburg
 */
public class EditMedsActivity extends AppCompatActivity {

    /**
     * EditText field for inputting the medication ID.
     */
    private EditText inventoryID;

    /**
     * EditText field for inputting the new medication name.
     */
    private EditText inventoryName;

    /**
     * EditText field for inputting the new stock quantity of the medication.
     */
    private EditText inventoryQuantity;

    private EditText med_input;

    /**
     * Base URL for the medication inventory endpoint.
     */
    private static final String URL = "http://coms-3090-038.class.las.iastate.edu:8080/inventory";

    /**
     * Initializes the activity layout, sets up listeners for save and return buttons.
     * The save button captures user input and sends a PUT request to update medication.
     * The return button navigates to MedicationActivity.
     *
     * @param savedInstanceState Bundle containing the saved state of the activity.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meds);

        inventoryID = findViewById(R.id.et_medication_name_prev);
        inventoryName = findViewById(R.id.et_medication_name_new);
        inventoryQuantity = findViewById(R.id.et_medication_stock);

        findViewById(R.id.btn_edit).setOnClickListener(view1 -> {
            String old_name = inventoryID.getText().toString();
            String new_name = inventoryName.getText().toString();
            String new_stock = inventoryQuantity.getText().toString();
            putRequest(URL, old_name, new_name, new_stock);
            inventoryName.setText("");
            inventoryQuantity.setText("");
            inventoryID.setText("");
        });

        findViewById(R.id.btn_add).setOnClickListener(view1 -> {
            PostNewInventory();
            inventoryName.setText("");
            inventoryQuantity.setText("");
        });

        findViewById(R.id.btn_delete).setOnClickListener(view1 -> {
            inventoryID = findViewById(R.id.et_medication_name_prev);
            String medicationInput = inventoryID.getText().toString();
            delRequest(medicationInput);
            inventoryID.setText("");
        });

        findViewById(R.id.btn_return).setOnClickListener(view1 -> {
            Intent intent = new Intent(EditMedsActivity.this, AdminNavbarMainActivity.class);
            startActivity(intent);
        });

    }

    /**
     * Sends a PUT request to the server to update the medication's name and stock quantity.
     * Constructs a JSON Object with the new data and appends the ID to the URL to identify the correct medication to update.
     *
     * @param url Base URL for the medication inventory endpoint.
     * @param old The current ID of the medication to update.
     * @param name The new name to update the medication to.
     * @param stock The new stock quantity to update the medication to.
     */
    private void putRequest(String url, String old, String name, String stock) {
        String new_url = url + "/" + old;
        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
            json.put("stock", stock);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, new_url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Medication Updated", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                throw new RuntimeException(error);
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

    /**
     *  Send a POST to add new medication to inventory
     */
    private void PostNewInventory(){

        JSONObject inventoryObj = new JSONObject();

        try{
            inventoryObj.put("name", inventoryName.getText().toString());
            inventoryObj.put("stock", Integer.parseInt(inventoryQuantity.getText().toString()));
        } catch (JSONException e){
            // Unable to create json object
            return;
        }

        JsonObjectRequest postReq = new JsonObjectRequest(
                Request.Method.POST,
                "http://coms-3090-038.class.las.iastate.edu:8080/admin/inventory",
                inventoryObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Medication Added", Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Oopsie
                    }
                }
        );

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(postReq);
    }

    /**
     * Sends a DELETE request to remove a medication from the inventory.
     *
     * @param id ID of the medication to be deleted.
     */
    private void delRequest(String id){

        StringRequest request = new StringRequest(Request.Method.DELETE, "http://coms-3090-038.class.las.iastate.edu:8080/admin/inventory"
                + "/" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                              Toast.makeText(getApplicationContext(), "Medication Deleted", Toast.LENGTH_LONG).show();
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
