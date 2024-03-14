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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musa_ashraf.blood_donation.MainActivity;
import com.example.musa_ashraf.blood_donation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {
    private CircleImageView mProfileImage;
    private EditText mProfileName,mProfileBloodgroup,mAddress,mLastDonationDate, mPhone;
    private Button mEditInformation;
    private ProgressDialog mProgressDialog;

    private DatabaseReference mUsersDatabase;
    private FirebaseAuth firebaseAuth;


    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        mProgressDialog = new ProgressDialog(getContext());


        mProfileImage = v.findViewById(R.id.edit_profile_image);
        mProfileName = v.findViewById(R.id.edit_profile_Username);
        mProfileBloodgroup = v.findViewById(R.id.edit_profile_blood_group);
        mAddress = v.findViewById(R.id.edit_profile_areaAddress);
        mLastDonationDate = v.findViewById(R.id.edit_profile_last_donation_date);
        mPhone = v.findViewById(R.id.edit_profile_phone);
        mEditInformation = v.findViewById(R.id.edit_profile_EditInformation);

        mEditInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                String name = mProfileName.getText().toString();
                String bloodgroup = mProfileBloodgroup.getText().toString();
                String address = mAddress.getText().toString();
                String lastdonation = mLastDonationDate.getText().toString();
                String phone = mPhone.getText().toString();

                if (TextUtils.isEmpty(name))
                {
                    mProgressDialog.hide();
                    Toast.makeText(getContext(),"Enter Your name",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(bloodgroup))
                {
                   mProgressDialog.hide();
                    Toast.makeText(getContext(),"Enter your valid Blood Group",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(address))
                {
                    mProgressDialog.hide();
                    Toast.makeText(getContext(),"Enter your Address ",Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (TextUtils.isEmpty(lastdonation))
                {
                    mProgressDialog.hide();
                    Toast.makeText(getContext(),"Enter your Last Donate Blood",Toast.LENGTH_SHORT).show();
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

                if (firebaseAuth.getCurrentUser() != null){
                    if (!TextUtils.isEmpty(name)|| !TextUtils.isEmpty(bloodgroup) || !TextUtils.isEmpty(address)|| !TextUtils.isEmpty(lastdonation)){
                        mProgressDialog.setTitle("Loading User data");
                        mProgressDialog.setMessage("Please wait while we Insert the user data.");
                        mProgressDialog.setCanceledOnTouchOutside(false);
                        mProgressDialog.show();

                        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("User_Profile").child(firebaseAuth.getUid());
                        HashMap<String,String> userMap = new HashMap<>();
                        userMap.put("Name", name);
                        userMap.put("Phone",phone);
                        userMap.put("BloodGroup",bloodgroup);
                        userMap.put("Address",address);
                        userMap.put("Last Donation",lastdonation);

                        mUsersDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    mProgressDialog.dismiss();
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });

                    }
                    else {
                        mProgressDialog.hide();
                        Toast.makeText(getActivity(), "Please Enter Your Valid Information", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(getContext(), "You should to be login", Toast.LENGTH_SHORT).show();
                }

            }
        });



        return v;
    }

}
