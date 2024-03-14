package com.example.musa_ashraf.blood_donation.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.musa_ashraf.blood_donation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddDonorFragment extends Fragment {
    private EditText mDonorAddress,mDonorLocation,mDonorUniversity, mDonorPhone;
    private Button mSaveDonor;
    private ProgressDialog mProgressDialog;
    private DatabaseReference mDonorDatabase;
    private FirebaseAuth firebaseAuth;

    private boolean isFirstItem = true;
    private String bloodGroup;

    public AddDonorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_add_donor, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(getContext());

        final Spinner spinner = v.findViewById(R.id.edit_addDonor_blood_group);
        mDonorAddress = v.findViewById(R.id.edit_addDonor_Address);
        mDonorLocation = v.findViewById(R.id.edit_addDonor_location);
        mDonorPhone = v.findViewById(R.id.edit_addDonor_phone);
        mDonorUniversity = v.findViewById(R.id.edit_addDonor_University);
        mSaveDonor = v.findViewById(R.id.edit_addDonor_SaveInformation);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.blood_option, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirstItem == true){
                    isFirstItem = false;
                }
                else {
                    String blood  = parent.getItemAtPosition(position).toString();
                    bloodGroup = blood;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mSaveDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = mDonorAddress.getText().toString();
                String location = mDonorLocation.getText().toString();
                String phone = mDonorPhone.getText().toString();
                String university = mDonorUniversity.getText().toString();

                mProgressDialog.setMessage("Processing Data...");
                mProgressDialog.show();

                if (TextUtils.isEmpty(address))
                {
                    mProgressDialog.hide();
                    Toast.makeText(getContext(),"Enter Your Address",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(location))
                {
                    mProgressDialog.hide();
                    Toast.makeText(getContext(),"Enter your Location",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(bloodGroup))
                {
                    mProgressDialog.hide();
                    Toast.makeText(getContext(),"Missing Blood Group",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(phone))
                {
                    mProgressDialog.hide();
                    Toast.makeText(getContext(),"Enter Phone Number",Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (phone.length() <11)
                {
                    mProgressDialog.hide();
                    Toast.makeText(getContext(),"Enter Valid Phone Number",Toast.LENGTH_SHORT).show();
                    return;
                }




                if (!TextUtils.isEmpty(bloodGroup) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(location) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(university)) {
                    mProgressDialog.setTitle("Save User data");
                    mProgressDialog.setMessage("Please wait while we Insert the donor data.");
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.show();

                    if (firebaseAuth.getCurrentUser() !=null){
                        mDonorDatabase = FirebaseDatabase.getInstance().getReference().child("Donor_List").child(String.valueOf(System.currentTimeMillis()));
                        HashMap<String, String> userMap = new HashMap<>();
                        userMap.put("blood", bloodGroup);
                        userMap.put("address", address);
                        userMap.put("phone", phone);
                        userMap.put("location", location);
                        userMap.put("university", university);
                        userMap.put("userid", String.valueOf(System.currentTimeMillis()));
                        mDonorDatabase.setValue(userMap);
                        mDonorDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    mProgressDialog.dismiss();
                                }
                            }
                        });
                    }

                    else {
                        mProgressDialog.hide();
                        Toast.makeText(getActivity(), "You Must Signin Account", Toast.LENGTH_SHORT).show();

                    }

                }
                else {
                    Toast.makeText(getContext(), "Please Enter Valid Donor", Toast.LENGTH_SHORT).show();
                }

            }

        });

        return v;
    }

}
