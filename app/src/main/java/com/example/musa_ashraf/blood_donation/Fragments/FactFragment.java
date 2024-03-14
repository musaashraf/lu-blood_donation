package com.example.musa_ashraf.blood_donation.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.musa_ashraf.blood_donation.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FactFragment extends Fragment {
    TextView ts;


    public FactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fact, container, false);
        //ts = v.findViewById(R.id.font);
        //ts.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "kalpurush.ttf"));

        return v;
    }

}
