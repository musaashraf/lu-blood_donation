package com.example.musa_ashraf.blood_donation.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.musa_ashraf.blood_donation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BankFragment extends Fragment {


    public BankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] name={"Android","IOS","Windows","Linux"};

        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_bank, container, false);

        return v;
    }

}
