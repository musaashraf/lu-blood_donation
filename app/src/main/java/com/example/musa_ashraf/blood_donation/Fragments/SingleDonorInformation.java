package com.example.musa_ashraf.blood_donation.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.musa_ashraf.blood_donation.Adapter.VDAdapter;
import com.example.musa_ashraf.blood_donation.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class SingleDonorInformation extends AppCompatActivity {
    private TextView mProfileBloodgroup, mProfilePhone, mProfileAddress, mProfileLastDonationDate, mProfileEmail;
    private FloatingActionButton mPhone, mMessage;

    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private FirebaseUser currentUser;
    private ProgressDialog mProgress;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_donor_information);

        Toolbar mToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProfileBloodgroup = findViewById(R.id.profile_blood_group);
        mProfileAddress = findViewById(R.id.profile_areaAddress);
        mProfilePhone = findViewById(R.id.profile_phone_number);
        mProfileEmail = findViewById(R.id.profile_email);
        mProfileLastDonationDate = findViewById(R.id.profile_last_donation_date);
        mPhone = findViewById(R.id.phonecall);
        mMessage = findViewById(R.id.messagenumber);

        final String user_id = getIntent().getStringExtra("User_id");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Donor_List").child(user_id);
        mUserDatabase.keepSynced(true);

        if (mAuth.getCurrentUser() != null) {

            mUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String bloodgroup = dataSnapshot.child("blood").getValue().toString();
                    phone = dataSnapshot.child("phone").getValue().toString();
                    String address = dataSnapshot.child("address").getValue().toString();
                    String location = dataSnapshot.child("location").getValue().toString();
                    String university = dataSnapshot.child("university").getValue().toString();

                    mProfileBloodgroup.setText(bloodgroup);
                    mProfileAddress.setText(address+" "+ location);
                    //mProfileLastDonationDate.setText(lastdonation);
                    mProfilePhone.setText(phone);
                    mProfileEmail.setText(user_id);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            mPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phone));
                    startActivity(intent);
                }
            });
            mMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phone));
                    startActivity(intent);
                }
            });
        }

    }

}

