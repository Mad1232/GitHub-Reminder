package com.example.cydrop_frontend;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientHomeFragment extends Fragment {

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
        LinearLayout linearLayout = view.findViewById(R.id.petListLinearLayout);
        GetJSONData(linearLayout);

        // Inflate the layout for this fragment
        return view;
    }



    private void GetJSONData(LinearLayout layout) {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(
                Request.Method.GET,
                VolleySingleton.backendURL + "/pets",
                null, // Pass null as the request body since it's a GET request
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray jsonArr = response;
                            for (int i = 0; i < jsonArr.length(); i++){
                                JSONObject jObj = jsonArr.getJSONObject(i);
                                PetPanelFragment petFragment = new PetPanelFragment();
//                                String newLine = "ID: " + jObj.getString("id") +
//                                        " | Product name: " + jObj.getString("product-name") +
//                                        " | Quantity: " + jObj.getInt("quantity") + "\n";
//                                dataText.setText(dataText.getText() + newLine);
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
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