package com.example.cydrop_frontend;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a fragment that can be displayed in the ClientNavbarMainActivity
 * Holds the inventory pages, where an admin can manage medication
 * @author Niraj
 */
public class ClientRemindersFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String CHANNEL_ID = "mychannelid";
    private String mParam1;
    private String mParam2;
    private ArrayList<Fragment> fragList = new ArrayList<>();
    private LinearLayout linearLayout;

    private View defaultView;
    private View addView;

    private TextView reminderTextField;



    private EditText addDescriptionEdit;
    private EditText addTimeEdit;

    private Spinner addpetSpinner;
    private Spinner addMedicationSpinner;


    private int[] addDays = new int[] { 0,0,0,0,0,0,0};



    /**
     * Required empty constructor
     */
    public ClientRemindersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RemindersFragment.
     */
    public static ClientRemindersFragment newInstance(String param1, String param2) {
        ClientRemindersFragment fragment = new ClientRemindersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Generic onCreate
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View view = inflater.inflate(R.layout.fragment_client_reminders, container, false);
       linearLayout = view.findViewById(R.id.client_reminders_linear_layout);

    reminderTextField = view.findViewById(R.id.textView_reminders_text);

       GetJSONData();
       defaultView = view.findViewById(R.id.client_reminders_default_layout);
       addView = view.findViewById(R.id.client_reminders_add_layout);
       switchCurrentView(false);

       view.findViewById(R.id.client_reminders_add_back_button).setOnClickListener(view3 -> {
           switchCurrentView(false);
       });

       view.findViewById(R.id.client_reminder_add_button).setOnClickListener(view3 -> {
           switchCurrentView(true);
       });

       // Clicking days
        view.findViewById(R.id.client_reminders_add_m_button).setOnClickListener(view2 -> {
            int dayId = 0;
            if (addDays[dayId] == 0) {
                addDays[dayId] = 1;
                view.findViewById(R.id.client_reminders_add_m_button).setBackgroundColor(getResources().getColor(R.color.green));
            } else {
                addDays[dayId] = 0;
                view.findViewById(R.id.client_reminders_add_m_button).setBackgroundColor(getResources().getColor(R.color.grey));
            }
        });

        view.findViewById(R.id.client_reminders_add_t_button).setOnClickListener(view2 -> {
            int dayId = 1;
            if (addDays[dayId] == 0) {
                addDays[dayId] = 1;
                view.findViewById(R.id.client_reminders_add_t_button).setBackgroundColor(getResources().getColor(R.color.green));
            } else {
                addDays[dayId] = 0;
                view.findViewById(R.id.client_reminders_add_t_button).setBackgroundColor(getResources().getColor(R.color.grey));
            }
        });

        view.findViewById(R.id.client_reminders_add_w_button).setOnClickListener(view2 -> {
            int dayId = 2;
            if (addDays[dayId] == 0) {
                addDays[dayId] = 1;
                view.findViewById(R.id.client_reminders_add_w_button).setBackgroundColor(getResources().getColor(R.color.green));
            } else {
                addDays[dayId] = 0;
                view.findViewById(R.id.client_reminders_add_w_button).setBackgroundColor(getResources().getColor(R.color.grey));
            }
        });

        view.findViewById(R.id.client_reminders_add_r_button).setOnClickListener(view2 -> {
            int dayId = 3;
            if (addDays[dayId] == 0) {
                addDays[dayId] = 1;
                view.findViewById(R.id.client_reminders_add_r_button).setBackgroundColor(getResources().getColor(R.color.green));
            } else {
                addDays[dayId] = 0;
                view.findViewById(R.id.client_reminders_add_r_button).setBackgroundColor(getResources().getColor(R.color.grey));
            }
        });

        view.findViewById(R.id.client_reminders_add_f_button).setOnClickListener(view2 -> {
            int dayId = 4;
            if (addDays[dayId] == 0) {
                addDays[dayId] = 1;
                view.findViewById(R.id.client_reminders_add_f_button).setBackgroundColor(getResources().getColor(R.color.green));
            } else {
                addDays[dayId] = 0;
                view.findViewById(R.id.client_reminders_add_f_button).setBackgroundColor(getResources().getColor(R.color.grey));
            }
        });

        view.findViewById(R.id.client_reminders_add_s_button).setOnClickListener(view2 -> {
            int dayId = 5;
            if (addDays[dayId] == 0) {
                addDays[dayId] = 1;
                view.findViewById(R.id.client_reminders_add_s_button).setBackgroundColor(getResources().getColor(R.color.green));
            } else {
                addDays[dayId] = 0;
                view.findViewById(R.id.client_reminders_add_s_button).setBackgroundColor(getResources().getColor(R.color.grey));
            }
        });

        view.findViewById(R.id.client_reminders_add_u_button).setOnClickListener(view2 -> {
            int dayId = 6;
            if (addDays[dayId] == 0) {
                addDays[dayId] = 1;
                view.findViewById(R.id.client_reminders_add_u_button).setBackgroundColor(getResources().getColor(R.color.green));
            } else {
                addDays[dayId] = 0;
                view.findViewById(R.id.client_reminders_add_u_button).setBackgroundColor(getResources().getColor(R.color.grey));
            }
        });


        Spinner petSpinner = view.findViewById(R.id.client_reminders_add_pet_spinner);

        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("select pet");
        for (String s : VolleySingleton.userPetsToId.keySet()){
            spinnerItems.add(s);
        }

        // Create an ArrayAdapter with the context from the fragment
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the spinner
        petSpinner.setAdapter(adapter);



        Spinner medicationSpinner = view.findViewById(R.id.client_reminders_add_medication_spinner);

        List<String> medList = new ArrayList<>();
        medList.add("select medication");
        for (String s : VolleySingleton.medicationToId.keySet()){
            medList.add(s);
        }

        // Create an ArrayAdapter with the context from the fragment
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, medList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the spinner
        medicationSpinner.setAdapter(adapter2);

        addMedicationSpinner = medicationSpinner;
        addpetSpinner = petSpinner;
        addDescriptionEdit = view.findViewById(R.id.client_reminders_add_description_input);
        addTimeEdit = view.findViewById(R.id.client_reminders_add_time_input);
        view.findViewById(R.id.client_reminders_add_save_button).setOnClickListener( view3 -> {
            postNewReminder();
        });

        // Inflate the layout for this fragment
        return view;
    }


    private void postNewReminder() {
        JSONObject reminder = new JSONObject();

        String addPetId = VolleySingleton.userPetsToId.get(addpetSpinner.getSelectedItem().toString()) + "";
        String addMedId = VolleySingleton.medicationToId.get(addMedicationSpinner.getSelectedItem().toString()) + "";;

        try{
            JSONObject petInfo = new JSONObject();

            petInfo.put("pet_id", addPetId);
            reminder.put("pet", petInfo);

            JSONObject medicationInfo = new JSONObject();
            medicationInfo.put("id", addMedId);
            reminder.put("medication", medicationInfo);
            String timeInput = addTimeEdit.getText().toString();
            if (timeInput.length() < 5){ timeInput = "0" + timeInput;}
            reminder.put("reminderTime", "2024-11-01T" + timeInput + ":00");
            reminder.put("reminderText", addDescriptionEdit.getText().toString());

            String daysString = "";
            for (int i : addDays){
                daysString += "" + i + "";
            }
            reminder.put("days", daysString);
        } catch (JSONException e){
            // Unable to create json object
            Toast.makeText(getActivity(), "Error creating JSON", Toast.LENGTH_LONG).show();
            return;
        }

        JsonObjectRequest postReq = new JsonObjectRequest(
                Request.Method.POST,
                "http://coms-3090-038.class.las.iastate.edu:8080/events",
                reminder,
                response -> {
                    // Victory! i think
                    Toast.makeText(getActivity(), "Reminder added!", Toast.LENGTH_LONG).show();

                    Calendar calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_WEEK);

                    switch (day) {
                        case Calendar.MONDAY:
                            if (addDays[0] == 1){
                                // Setup reminder
                                createNotification(
                                        addTimeEdit.getText().toString(),
                                        addpetSpinner.getSelectedItem().toString(),
                                        addDescriptionEdit.getText().toString());
                                RemoveAllReminderFrags();
                            }
                            break;
                        case Calendar.TUESDAY:
                            if (addDays[1] == 1){
                                // Setup reminder
                                createNotification(
                                        addTimeEdit.getText().toString(),
                                        addpetSpinner.getSelectedItem().toString(),
                                        addDescriptionEdit.getText().toString());
                                RemoveAllReminderFrags();
                            }
                            break;
                        case Calendar.WEDNESDAY:
                            if (addDays[2] == 1){
                                // Setup reminder
                                createNotification(
                                        addTimeEdit.getText().toString(),
                                        addpetSpinner.getSelectedItem().toString(),
                                        addDescriptionEdit.getText().toString());
                                RemoveAllReminderFrags();
                            }
                            break;
                        case Calendar.THURSDAY:
                            if (addDays[3] == 1){
                                // Setup reminder
                                createNotification(
                                        addTimeEdit.getText().toString(),
                                        addpetSpinner.getSelectedItem().toString(),
                                        addDescriptionEdit.getText().toString());
                                RemoveAllReminderFrags();
                            }
                            break;
                        case Calendar.FRIDAY:
                            if (addDays[4] == 1){
                                // Setup reminder
                                createNotification(
                                        addTimeEdit.getText().toString(),
                                        addpetSpinner.getSelectedItem().toString(),
                                        addDescriptionEdit.getText().toString());
                                RemoveAllReminderFrags();
                            }
                            break;
                        case Calendar.SATURDAY:
                            if (addDays[5] == 1){
                                // Setup reminder
                                createNotification(
                                        addTimeEdit.getText().toString(),
                                        addpetSpinner.getSelectedItem().toString(),
                                        addDescriptionEdit.getText().toString());
                                RemoveAllReminderFrags();
                            }
                            break;
                        case Calendar.SUNDAY:
                            if (addDays[6] == 1){
                                // Setup reminder
                                createNotification(
                                        addTimeEdit.getText().toString(),
                                        addpetSpinner.getSelectedItem().toString(),
                                        addDescriptionEdit.getText().toString());
                                RemoveAllReminderFrags();
                            }
                            break;
                    }


                    GetJSONData();

                    addDescriptionEdit.setText("");
                    addTimeEdit.setText("");
                    switchCurrentView(false);
                },
                error -> {
                    // Oopsie
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                }
        );

        VolleySingleton.getInstance(getContext().getApplicationContext()).addToRequestQueue(postReq);
    }



    public void addNewReminderCard(String pet, String petId, String medication, int[] days, String time, String description){
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();
        Fragment f = ReminderCardFragment.newInstance(pet, petId, medication, days, time, description);
        fragList.add(f);
        fragTransaction.add(linearLayout.getId(), f, "frag");
        fragTransaction.commit();
    }


    public void RemoveAllReminderFrags(){
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();
        for (Fragment f : (Fragment[]) fragList.toArray()){
            fragTransaction.remove(f);
        }
        fragTransaction.commit();
    }

    private void switchCurrentView(boolean isAdding){
        if (isAdding) {
            defaultView.setVisibility(View.INVISIBLE);
            addView.setVisibility(View.VISIBLE);
        } else {
            defaultView.setVisibility(View.VISIBLE);
            addView.setVisibility(View.INVISIBLE);
        }
    }
    final Intent emptyIntent = new Intent();
    private void createNotification(String time, String petname, String description) {
        long timeMillis = getNextTimeInMillis(time);

        NotificationScheduler.scheduleNotificationAtTime(requireContext(), petname, description, timeMillis);
    }

    public static long getNextTimeInMillis(String timeStr) {
        try {
            // Parse the input time string to get the hour and minute
            String[] parts = timeStr.split(":");
            int targetHour = Integer.parseInt(parts[0]);
            int targetMinute = Integer.parseInt(parts[1]);

            // Get the current time
            Calendar currentTime = Calendar.getInstance();
            Calendar targetTime = Calendar.getInstance();

            // Set the target time to the specified hour and minute
            targetTime.set(Calendar.HOUR_OF_DAY, targetHour);
            targetTime.set(Calendar.MINUTE, targetMinute);
            targetTime.set(Calendar.SECOND, 0);
            targetTime.set(Calendar.MILLISECOND, 0);

            // If the target time is before the current time, schedule it for the next day
            if (targetTime.before(currentTime)) {
                targetTime.add(Calendar.DAY_OF_MONTH, 1);
            }

            // Return the target time in milliseconds
            return targetTime.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Return -1 to indicate an error
        }
    }


    private void GetJSONData() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(
                Request.Method.GET,
                "http://coms-3090-038.class.las.iastate.edu:8080/events/users/" + VolleySingleton.userId,
                null, // Pass null as the request body since it's a GET request
                response -> {
                    try {
                        JSONArray jsonArr = response;
                        for (int i = 0; i < jsonArr.length(); i++){
                            JSONObject json = jsonArr.getJSONObject(i);
                            JSONObject petObj = json.getJSONObject("pet");
                            JSONObject medicationObj = json.getJSONObject("medication");
                            String[] sArr = json.getString("days").split("");
                            int[] iArr = new int[7];
                            for (int j = 0; j < 7; j++){
                                String s = sArr[j];
                                iArr[j] = Integer.parseInt(s);
                            }
                            addNewReminderCard(
                                    petObj.getString("pet_name"),
                                    petObj.getString("pet_id"),
                                    medicationObj.getString("name"),
                                    iArr,
                                    json.getString("reminderTime"),
                                    json.getString("reminderText")
                            );
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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