package com.example.cydrop_frontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class VetInfoActivity extends AppCompatActivity {

    /**
     * Textview displaying the vet info list.
     */
    private TextView msgResponse;

    /**
     * Base URL for accessing medication inventory endpoint.
     */
    private static final String URL = "http://coms-3090-038.class.las.iastate.edu:8080/vet/" + VolleySingleton.vetIdTEMP;


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
        setContentView(R.layout.activity_vet_info);

        msgResponse = findViewById(R.id.data_text);

        getJSONData();

        findViewById(R.id.returnButton).setOnClickListener(view1 -> {
            Intent intent = new Intent(VetInfoActivity.this, ClientNavbarMainActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.chat_btn).setOnClickListener(view1 -> {
            Intent intent = new Intent(VetInfoActivity.this, CustomerChatActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Retrieves and displays the medication list from the server as a JSON array.
     * Parses each entry to display the medication details in a clear way.
     */
    private void getJSONData() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null, // Pass null as the request body since it's a GET request
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        msgResponse.setText(""); // reset the msg text
                        try{
                           // JSONArray jsonArr = response.getJSONArray();
                           // for (int i = 0; i < jsonArr.length(); i++) {
                               // JSONObject jObj = jsonArr.getJSONObject(i);
                                String newLine = "ID: " + response.getString("vet_id") + "\n" +
                                        "Name: " + response.getString("vet_name") + "\n" +
                                        "Specialization: " + response.getString("specialization") + "\n"
                                        + "Email: " + response.getString("vetEmail") + "\n"
                                        + "License #: " + response.getString("licenseNum") + "\n"
                                        + "Clinic Address: " + response.getString("clinicAddress") + "\n"
                                        + "Phone #: " + response.getString("phone") + "\n";
                                msgResponse.setText(msgResponse.getText() + newLine);
                          //  }
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
}
