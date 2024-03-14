package com.example.musa_ashraf.blood_donation.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {
    EditText jEmail, jPassword, jName, jBloodgroup, jPhone;
    Button  jSignup;
    TextView jSignin;
    DatabaseReference root;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    private ProgressDialog progressDialog;


    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final AccountFragment accountFragment = new AccountFragment();
        firebaseAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_registration, container, false);

        //root = FirebaseDatabase.getInstance().getReference().child("Account");
        firebaseAuth = FirebaseAuth.getInstance();

        jEmail = v.findViewById(R.id.edit_rEmail);
        jPassword = v.findViewById(R.id.edit_rPassword);
        jName = v.findViewById(R.id.edit_rName);
        jBloodgroup = v.findViewById(R.id.edit_rBloodgroup);
        jPhone = v.findViewById(R.id.edit_rPhone);

        jSignup = v.findViewById(R.id.btn_rSignup);
        jSignin = v.findViewById(R.id.btn_rSignin);

        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user= firebaseAuth.getCurrentUser();

            }
        };

        jSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, accountFragment);
                fragmentTransaction.commit();
            }
        });

        jSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog =new ProgressDialog(getActivity());
                progressDialog.setMessage("SignUp Progress...");
                progressDialog.show();

                final  String tEmail= jEmail.getText().toString().trim();
                final  String tPassword= jPassword.getText().toString().trim();
                final  String tBloodgroup= jBloodgroup.getText().toString().trim();
                final  String tPhone= jPhone.getText().toString().trim();
                final  String tName= jName.getText().toString().trim();

                if (TextUtils.isEmpty(tName))
                {
                    progressDialog.hide();
                    Toast.makeText(getContext(),"Enter Your name",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(tEmail))
                {
                    progressDialog.hide();
                    Toast.makeText(getContext(),"Enter your valid Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(tPassword))
                {
                    progressDialog.hide();
                    Toast.makeText(getContext(),"Enter your Password ",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(tBloodgroup))
                {
                    progressDialog.hide();
                    Toast.makeText(getContext(),"Enter Blood Group",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(tPhone))
                {
                    progressDialog.hide();
                    Toast.makeText(getContext(),"Enter Phone Number",Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (tPassword.length() <8)
                {
                    progressDialog.hide();
                    Toast.makeText(getContext(),"Password Length 8",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (tPhone.length() <11)
                {
                    progressDialog.hide();
                    Toast.makeText(getContext(),"Enter Valid Phone Number",Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(tEmail,tPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            String userID = firebaseAuth.getCurrentUser().getUid();

                            root = FirebaseDatabase.getInstance().getReference().child("Account").child(userID);
                            Intent intent=new Intent(getActivity(), MainActivity.class);


                           HashMap<String,String> info = new HashMap<>();
                            info.put("Name",tName);
                            info.put("BloodGroup",tBloodgroup);
                            info.put("Phone",tPhone);
                            info.put("Password",tPassword);
                            info.put("Email",tEmail);
                            info.put("Last Donation","null");
                            info.put("image","null");
                            info.put("Address","null");
                            info.put("Userid",userID);

                            root.setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                    }
                                }
                            });
                            startActivity(intent);


                        }

                        else if (!task.isSuccessful())
                        {
                            Log.d("Error:","onComplete: Failed="+task.getException().getMessage());
                            if (task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(getActivity(),"User already exists",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getActivity(),"Invalid email",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

        return v;
    }
    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener!=null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }

    }


}
