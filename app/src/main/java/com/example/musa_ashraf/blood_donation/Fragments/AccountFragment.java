package com.example.musa_ashraf.blood_donation.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.musa_ashraf.blood_donation.Admin.AdminActivity;
import com.example.musa_ashraf.blood_donation.MainActivity;
import com.example.musa_ashraf.blood_donation.R;
import com.example.musa_ashraf.blood_donation.ResetPasswordActivity;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    private Button jSignin;
    private Button jSignUp;
    private EditText jEmail;
    private EditText jPassword;
    private FactFragment factsFragment = new FactFragment();
    private RegistrationFragment registrationFragment = new RegistrationFragment();
    private UserInformationFragment informationFragment = new UserInformationFragment();

    private  Button jForgotAccout;
    private SignInButton mGoogleBtn;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "GOOGLE_AUTH";

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_account, container, false);

        //mGoogleBtn = v.findViewById(R.id.googleSigninBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getActivity());
        jSignin = v.findViewById(R.id.btn_aSignin);
        jSignUp = v.findViewById(R.id.txt_aCreateAccount);
        jEmail = v.findViewById(R.id.edit_aEmail);
        jPassword = v.findViewById(R.id.edit_aPassword);
        jForgotAccout = v.findViewById(R.id.txt_aForgotAccount);

        jForgotAccout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ResetPasswordActivity.class));
            }
        });


//googleauth
        /*mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    //startActivity(new Intent(getActivity(), MainActivity.class));
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_frame, informationFragment);
                    fragmentTransaction.commit();

                }
            }
        };
        GoogleSignInOptions gsp = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        mGoogleApiClient = new GoogleApiClient.Builder(getContext()).enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(getActivity(), "You get error", Toast.LENGTH_SHORT).show();


            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, gsp).build();
        mGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });//*/


        if (firebaseAuth.getCurrentUser() != null) {

            ProfileFragment profileFragment = new ProfileFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, profileFragment);
            fragmentTransaction.commit();

        }

        jSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_string = jEmail.getText().toString().trim();
                String password_string = jPassword.getText().toString().trim();
                if (email_string=="admin" || password_string=="1234"){
                    startActivity(new Intent(getActivity(), AdminActivity.class));

                }
                else {
                    if (TextUtils.isEmpty(email_string)) {
                        Toast.makeText(getContext(), "Please enter email", Toast.LENGTH_SHORT).show();
                        return;

                    }
                    if (TextUtils.isEmpty(password_string)) {
                        Toast.makeText(getContext(), "Please enter password", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    progressDialog.setMessage("Registering user....");
                    progressDialog.show();

                    firebaseAuth.signInWithEmailAndPassword(email_string, password_string)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();

                                    if (task.isSuccessful()) {

                                        startActivity(new Intent(getContext(), MainActivity.class));

                                    }
                                }
                            });

                }


            }

        });
        jSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, registrationFragment);
                fragmentTransaction.commit();
            }
        });


        return v;
    }

    //googleAuth2
    /*@Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {

            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(getContext(), "Authentication field", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }//*/
}
