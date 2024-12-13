package com.example.cydrop_frontend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a fragment that can be displayed in the AdminNavbarMainActivity
 * Holds the users pages, where a admin can manage all other users of the application
 * @author Niraj
 */
public class AdminUsersFragment extends Fragment {

    static final String URL_JSON_ARRAY = "http://coms-3090-038.class.las.iastate.edu:8080/admin/users";

    private TextView dataText;

    /**
     * Empty constructer
     */
    public AdminUsersFragment() {
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
        View view = inflater.inflate(R.layout.fragment_admin_users, container, false);
        dataText = view.findViewById(R.id.data_text);

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

        Button manage = view.findViewById(R.id.manage_btn);
        manage.setOnClickListener(view1 -> {

            Intent intent = new Intent(getActivity(), AdminManageUsersActivity.class);
            startActivity(intent);
        });

        GetJSONData();
        return view;
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

                            String newLine = "ID: " + obj.getString("id") + "\n"
                                    + "Email: " + obj.getString("email") + "\n";


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

}