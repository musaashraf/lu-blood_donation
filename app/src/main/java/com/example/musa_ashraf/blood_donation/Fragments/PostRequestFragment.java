package com.example.musa_ashraf.blood_donation.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.musa_ashraf.blood_donation.MainActivity;
import com.example.musa_ashraf.blood_donation.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostRequestFragment extends Fragment {
    private boolean isFirstItem = true;
    private String bloodGroup;


    public PostRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final EditText edit_HospitalName, edit_Location,edit_Details, edit_Time,edit_Phone;

        final Button btn_Submit;
        final ProgressDialog mProgress;
        final DatabaseReference mDatabase;


        View v = inflater.inflate(R.layout.fragment_post_request, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("PostRequest");

        mProgress = new ProgressDialog(getActivity());



        final Spinner spinner = v.findViewById(R.id.editBloodType);
        edit_HospitalName = v.findViewById(R.id.editHospitalName);
        edit_Location = v.findViewById(R.id.editLocation);
        edit_Details = v.findViewById(R.id.editDetails);
        edit_Time = v.findViewById(R.id.editTime);
        edit_Phone = v.findViewById(R.id.editPhone);
        btn_Submit = v.findViewById(R.id.buttonSubmit);

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


        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress.setMessage("Posting to Request Data ....");
                final String hospital = edit_HospitalName.getText().toString().trim();
                final String phone = edit_Phone.getText().toString().trim();
                final String location = edit_Location.getText().toString().trim();
                final String details = edit_Details.getText().toString().trim();
                final String time = edit_Time.getText().toString().trim();


                if (TextUtils.isEmpty(hospital))
                {
                    mProgress.hide();
                    Toast.makeText(getContext(),"Enter Hospital name",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(phone))
                {
                    mProgress.hide();
                    Toast.makeText(getContext(),"Enter your Phone number",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(bloodGroup))
                {
                    mProgress.hide();
                    Toast.makeText(getContext(),"Missing Blood Group",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(location))
                {
                    mProgress.hide();
                    Toast.makeText(getContext(),"Enter your hospital location ",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(details))
                {
                    mProgress.hide();
                    Toast.makeText(getContext(),"Enter Reason",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(time))
                {
                    mProgress.hide();
                    Toast.makeText(getContext(),"Enter Date and Time",Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (phone.length() <11)
                {
                    mProgress.hide();
                    Toast.makeText(getContext(),"Enter Valid Phone Number",Toast.LENGTH_SHORT).show();
                    return;
                }




                if (!TextUtils.isEmpty(bloodGroup) && !TextUtils.isEmpty(hospital) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(location) && !TextUtils.isEmpty(details)
                        && !TextUtils.isEmpty(time)){

                    mProgress.show();

                    DatabaseReference roomRef2 = mDatabase.child(String.valueOf(System.currentTimeMillis()));
                    String uid =  String.valueOf(System.currentTimeMillis());
                    String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                    Map<String, String> strings = new HashMap<>();

                    strings.put("status", "I need emergency blood");
                    strings.put("blood", bloodGroup);
                    strings.put("hospital", hospital);
                    strings.put("location",location);
                    strings.put("details", details);
                    strings.put("time", time);
                    strings.put("phone",phone);
                    strings.put("uid", uid);
                    strings.put("stime",mydate);
                    //strings.put("endtime",);

                    roomRef2.setValue(strings);

                    Intent i = new Intent(getContext(), ViewRequestFragment.class);
                    i.putExtra("NEED", uid);

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);

                    mProgress.dismiss();

                }
                else{
                    Toast.makeText(getContext(), "Please Fill up all Required", Toast.LENGTH_SHORT).show();
                }




            }
        });

        return v;
    }


}
