package com.example.musa_ashraf.blood_donation.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.example.musa_ashraf.blood_donation.MapsActivity;
import com.example.musa_ashraf.blood_donation.R;
import com.example.musa_ashraf.blood_donation.SearchActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private ViewFlipper view_Flipper;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final AddDonorFragment addDonorFragment = new AddDonorFragment();
        final FactFragment factFragment = new FactFragment();
        final BankFragment bankFragment = new BankFragment();
        final ViewRequestFragment viewRequestFragment = new ViewRequestFragment();
        final PostRequestFragment postRequestFragment = new PostRequestFragment();


        View v = inflater.inflate(R.layout.fragment_home, container, false);
        CardView searchId = v.findViewById(R.id.searchId);
        CardView postId = v.findViewById(R.id.postRequestId);
        CardView bloodBankId = v.findViewById(R.id.bloodBankId);
        CardView addDonorId = v.findViewById(R.id.addDonorId);
        CardView factId = v.findViewById(R.id.factId);
        CardView viewRequest = v.findViewById(R.id.viewRequestId);
        CardView postRequestId = v.findViewById(R.id.postRequestId);

        /*view_Flipper = v.findViewById(R.id.view_flipper);
        int images[]={R.drawable.slide1,R.drawable.slide2,R.drawable.slide3,R.drawable.slide4,R.drawable.slide5,R.drawable.slide6};

        for (int image: images){
            //flipperimages(image);
            ImageView imageView= new ImageView(getContext());
            imageView.setBackgroundResource(image);

            view_Flipper.addView(imageView);
            view_Flipper.setFlipInterval(5000);
            view_Flipper.setAutoStart(true);
            //animation
            view_Flipper.setInAnimation(getContext(),android.R.anim.slide_out_right);
            view_Flipper.setOutAnimation(getContext(),android.R.anim.slide_in_left);
        }*/



        addDonorId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame,addDonorFragment);
                fragmentTransaction.commit();
            }
        });
        factId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.main_frame,factFragment);
                fragmentTransaction.commit();
            }
        });
        bloodBankId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame,bankFragment);
                fragmentTransaction.commit();*/
                startActivity(new Intent(getActivity(), MapsActivity.class));
            }
        });
        postRequestId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame,postRequestFragment);
                fragmentTransaction.commit();
            }
        });
        viewRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame,viewRequestFragment);
                fragmentTransaction.commit();
                /*Intent intent = new Intent(getActivity(),ViewActivity.class);
                startActivity(intent);*/
            }
        });
        searchId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

        return v;

    }

}
