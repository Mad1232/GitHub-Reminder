package com.example.cydrop_frontend;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.FrameMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AdminInventoryFragment extends Fragment {
    private static final String URL_JSON_ARRAY = "https://8671d7ff-5727-46d7-a203-c8589f137865.mock.pstmn.io/inventory";

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

        // Test array
        String[] arr = new String[]{"test1", "test2"};
        TextView dataText = view.findViewById(R.id.data_text);
        //LinearLayout layout = getActivity().getWindow().getDecorView().getRootView().findViewById(R.id.admin_inventory_linearlayout);

        GetJSONData(dataText);

        return view;
    }

    private void GetJSONData(TextView dataText) {
        dataText.setText("goofy");
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(
                Request.Method.GET,
                URL_JSON_ARRAY,
                null, // Pass null as the request body since it's a GET request
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        dataText.setText(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dataText.setText("Volley error");
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

}