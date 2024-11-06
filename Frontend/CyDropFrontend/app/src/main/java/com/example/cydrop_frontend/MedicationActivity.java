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

public class MedicationActivity extends AppCompatActivity {
    private TextView msgResponse;
    private static final String URL = "http://coms-3090-038.class.las.iastate.edu:8080/inventory";
    private EditText med_input;

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

    //get method
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
//                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
//                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("param1", "value1");
//                params.put("param2", "value2");
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }


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
                //                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
                //                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //                params.put("param1", "value1");
                //                params.put("param2", "value2");
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

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
                    //                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
                    //                headers.put("Content-Type", "application/json");
                    return headers;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    //                params.put("param1", "value1");
                    //                params.put("param2", "value2");
                    return params;
                }

            };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

}
