package com.example.cydrop_frontend;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.FrameMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AdminInventoryFragment extends Fragment {
    private static final String URL_JSON_ARRAY = "http://coms-3090-038.class.las.iastate.edu:8080/inventory";

    TextView dataText;

    public AdminInventoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_inventory, container, false);
        dataText = view.findViewById(R.id.data_text);

        Button addInventory = view.findViewById(R.id.adminInventoryAddButton);
        addInventory.setOnClickListener(view1 -> {

        });

        Button logout = view.findViewById(R.id.adminLogoutButton);
        logout.setOnClickListener(view1 -> {
            Intent intent = new Intent(MainActivity.class.toString());
            startActivity(intent);
        });


        GetJSONData();
        return view;
    }

    private void GetJSONData() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(
                Request.Method.GET,
                URL_JSON_ARRAY,
                null, // Pass null as the request body since it's a GET request
                response -> {
                    dataText.setText("");
                    try {
                        JSONArray jsonArr = response;
                        for (int i = 0; i < jsonArr.length(); i++){
                            JSONObject obj = jsonArr.getJSONObject(i);



                            String newLine = "Medication name: " + obj.getString("???") + "\n"
                                    + "Medication quantity: " + obj.getString("???") + "\n";


                            dataText.setText(dataText.getText() + newLine + "\n\n");
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> dataText.setText("Volley error: " + error.toString())) {
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

}