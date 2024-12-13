package com.example.cydrop_frontend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a fragment that can be displayed in the AdminNavbarMainActivity
 * Holds the inventory pages, where an admin can manage medication
 * @author Niraj
 */
public class AdminInventoryFragment extends Fragment {
 static final String URL_JSON_ARRAY = "http://coms-3090-038.class.las.iastate.edu:8080/admin/inventory";

    private TextView dataText;
    private View regularView;
    private View addInventoryView;

    private EditText inventoryName;
    private EditText inventoryQuantity;

    /**
     * Empty constructer
     */
    public AdminInventoryFragment() {
        // Required empty public constructor
    }


    /**
     * Generic onCreate method
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     *
     * When creating the view, setup the regular and inventory views and add them to the fragment.
     * Setup onClick listeners for all buttons
     * Send a GET request to get the current inventory information, populating fields on callback
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return the view created
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_inventory, container, false);
        dataText = view.findViewById(R.id.data_text);

        regularView = view.findViewById(R.id.adminInventoryRegularLayout);
        addInventoryView = view.findViewById(R.id.adminInventoryAddLayout);

        inventoryName = view.findViewById(R.id.addInventoryItemName);
        inventoryQuantity = view.findViewById(R.id.addInventoryItemQuantity);

        Button submit = view.findViewById(R.id.adminInventorySubmitButton);
        submit.setOnClickListener(view1 -> {
            PostNewInventory();
            inventoryName.setText("");
            inventoryQuantity.setText("");
        });

        Button delete = view.findViewById(R.id.adminInventoryDeleteButton);
        delete.setOnClickListener(view2 -> {
            String id = inventoryName.getText().toString();
            delRequest(id);
            GetJSONData();
            inventoryName.setText("");
            inventoryQuantity.setText("");
        });

        Button logout = view.findViewById(R.id.adminLogoutButton);
        logout.setOnClickListener(view1 -> {

            SharedPreferences sharedPref =  PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("userId", "-1");
            editor.putString("userType", "none");
            editor.apply();

            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });

        Button addInventoryButton = view.findViewById(R.id.add_inventory_button);
        addInventoryButton.setOnClickListener(view2 -> {
           // ToggleAddPetOverlay(true);
            Intent intent = new Intent(getActivity(), EditMedsActivity.class);
            startActivity(intent);
        });

        Button closeOverlay = view.findViewById(R.id.adminCloseOverlayButton);
        closeOverlay.setOnClickListener(view3 -> {
            ToggleAddPetOverlay(false);
        });

        ToggleAddPetOverlay(false);

        GetJSONData();
        return view;
    }

    /**
     * Toggle between the add overlay view and the regular view
     * @param addOverlay if true, toggle to the add view. Otherwise, toggle to regular view
     */
    private void ToggleAddPetOverlay(boolean addOverlay){
        if (addOverlay){
            regularView.setVisibility(View.INVISIBLE);
            addInventoryView.setVisibility(View.VISIBLE);
        } else {
            regularView.setVisibility(View.VISIBLE);
            addInventoryView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Make a GET call to the API to retrieve the medication inventory
     */
    private void GetJSONData() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(
                Request.Method.GET,
                URL_JSON_ARRAY,
                null, // Pass null as the request body since it's a GET request
                response -> {
                    dataText.setText(""); // reset text field
                    try {
                        for (int i = 0; i < response.length(); i++){
                            JSONObject obj = response.getJSONObject(i);

                            String newLine = "Medication ID: " + obj.getString("id") + "\n" +
                                    "Name: " + obj.getString("name") + "\n"
                                    + "Stock: " + obj.getString("stock") + "\n";


                            dataText.setText(dataText.getText() + newLine + "\n");
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
                        Toast.makeText(getContext().getApplicationContext(), "Medication Added", Toast.LENGTH_LONG).show();
                        GetJSONData();
                        ToggleAddPetOverlay(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Oopsie
                    }
                }
        );

        VolleySingleton.getInstance(getContext().getApplicationContext()).addToRequestQueue(postReq);
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
                      //  if ("Ok".equals(response)) {
                            Toast.makeText(getContext().getApplicationContext(), "Medication Deleted", Toast.LENGTH_LONG).show();
                            GetJSONData();
                            ToggleAddPetOverlay(false);
                       // }
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

        VolleySingleton.getInstance(getContext().getApplicationContext()).addToRequestQueue(request);
    }


}