package com.example.cydrop_frontend;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.FrameMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


public class AdminInventoryFragment extends Fragment {
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

        for (String s : arr){
//            TextView text = new TextView(getContext());
//            text.setText(s);
//            layout.addView(text);
            dataText.setText(dataText.getText() + "\n" + s);
        }

        return view;
    }


}