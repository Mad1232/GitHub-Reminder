package com.example.cydrop_frontend;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import android.widget.Button;


public class VetCustomersFragment extends Fragment {

    View defaultView;
    View addView;
    TextView customersTitle;

    LinearLayout CustomersLinearLayout;

    ArrayList<Fragment> fragList = new ArrayList<>();





    public VetCustomersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vet_customers, container, false);

        defaultView = view.findViewById(R.id.vet_customers_regular_layout);
        addView = view.findViewById(R.id.vet_customers_adding_layout);
        customersTitle = view.findViewById(R.id.vet_customers_text);

        Button addCustomer = view.findViewById(R.id.add_new_customer_button);
        addCustomer.setOnClickListener(view2 -> {
            ToggleAddCustomer(true);
        });

        view.findViewById(R.id.vet_customers_logout_button).setOnClickListener(view3 -> {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("userId", "-1");
            editor.putString("userType", "none");
            editor.commit();

            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });


        view.findViewById(R.id.vet_customer_add_cancel_button).setOnClickListener(view3 -> {
            ToggleAddCustomer(false);
        });

        EditText addByIdTextView = view.findViewById(R.id.vet_customer_add_id_input);
        EditText addByEmailTextView = view.findViewById(R.id.vet_customer_add_email_input);


        view.findViewById(R.id.vet_customer_add_by_email_submit).setOnClickListener(view3 -> {
            try {
                AddCustomerByEmail(addByEmailTextView.getText().toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        view.findViewById(R.id.vet_customer_add_by_id_submit).setOnClickListener(view3 -> {
            try {
                AddCustomerByID(addByIdTextView.getText().toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });


        LoadCustomers();
        ToggleAddCustomer(false);
        return view;
    }


    void ToggleAddCustomer(boolean isAdding) {
        if (isAdding) {
            addView.setVisibility(View.VISIBLE);
            defaultView.setVisibility(View.GONE);
        } else {
            addView.setVisibility(View.GONE);
            defaultView.setVisibility(View.VISIBLE);
        }
    }

    void LoadCustomers() {
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET,
                        "http://coms-3090-038.class.las.iastate.edu:8080/vets/" + VolleySingleton.userId + "/customers",
                        null,
                        response -> {
                            try {
                                for (int i = 0; i < response.length(); i++){
                                    JSONObject customerData = response.getJSONObject(i);
                                    createCustomerCard(customerData);
                                }

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }


                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    void AddCustomerByID(String CustomerID) throws JSONException {
        JSONObject j = new JSONObject();
        j.put("id", Integer.parseInt(CustomerID));
        AddCustomerGivenJSON(j);
    }

    void AddCustomerByEmail(String Email) throws JSONException {
        JSONObject j = new JSONObject();
        j.put("email", Email.trim());
        AddCustomerGivenJSON(j);
    }

    void AddCustomerGivenJSON(JSONObject j){
        JsonObjectRequest postReq = new JsonObjectRequest(
                Request.Method.POST,
                "http://coms-3090-038.class.las.iastate.edu:8080/vets/" + VolleySingleton.userId + "/customers",
                j,
                response -> {
                    try {
                        createCustomerCard(response);
                        ToggleAddCustomer(false);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // sooo, this is kinda expected to error out? and then i check which error with string parsing ??
                        // Im not gonna fix this
                        if (error.toString().split(":")[0].equals("com.android.volley.ParseError")){
                            Toast.makeText(requireContext(), "Volley parse error", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "Error adding new customer", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
        VolleySingleton.getInstance(getContext()).addToRequestQueue(postReq);
    }

    void ClearCustomers(){
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();
        for (Fragment f : (Fragment[]) fragList.toArray()){
            fragTransaction.remove(f);
        }
        fragTransaction.commit();
    }

    void createCustomerCard(JSONObject object) throws JSONException {
        String customerName = object.getString("email").trim();
        // remove the email addresses from our customer names? IDK if we want this
        // customerName = customerName.split("@")[0].trim();

        String customerId = object.getString("id");


        JSONArray petArray = object.getJSONArray("pets");
        String[] petNames = new String[petArray.length()];
        for (int i = 0; i < petArray.length(); i++){
            petNames[i] = (petArray.getJSONObject(i).getString("pet_name"));
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CustomerFragment frag = CustomerFragment.newInstance(customerName, customerId, petNames);
        fragList.add(frag);
        fragmentTransaction.add(R.id.vet_customers_linear_layout, frag);
        fragmentTransaction.commit();

    }

}