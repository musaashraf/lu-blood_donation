package com.example.musa_ashraf.blood_donation.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.musa_ashraf.blood_donation.MainActivity;
import com.example.musa_ashraf.blood_donation.R;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserInformationFragment extends Fragment {
    private Button mLogOutBtn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    public UserInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final AccountFragment accountFragment = new AccountFragment();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_information, container, false);


        mAuth = FirebaseAuth.getInstance();
        mLogOutBtn = v.findViewById(R.id.signOutBtn);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null){
                   //startActivity(new Intent(AccountActivity.this,GoogleAuth.class));
                    startActivity(new Intent(getActivity(), MainActivity.class));
                   /* FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_frame, accountFragment);
                    fragmentTransaction.commit();*/



                }
            }
        };

        mLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}






/*
public class FactFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private TextView jUserview;
    private Button jUserLogout;


    public FactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final AccountFragment accountFragment = new AccountFragment();
        View v = inflater.inflate(R.layout.fragment_fact, container, false);

        firebaseAuth = FirebaseAuth.getInstance();


        if (firebaseAuth.getCurrentUser() == null) {

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_frame,accountFragment);
            fragmentTransaction.commit();


        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        jUserview = v.findViewById(R.id.txt_aUser);

        jUserLogout = v.findViewById(R.id.btn_aUserLogout);
        jUserview.setText("Welcome "+user.getEmail());

        jUserLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame,accountFragment);
                fragmentTransaction.commit();
            }
        });

        return v;
    }

}
*/
