package com.example.musa_ashraf.blood_donation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.musa_ashraf.blood_donation.Admin.AdminActivity;
import com.example.musa_ashraf.blood_donation.Fragments.FactFragment;
import com.example.musa_ashraf.blood_donation.Fragments.RegistrationFragment;
import com.example.musa_ashraf.blood_donation.Fragments.UserInformationFragment;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class StartLoginActivity extends AppCompatActivity {
    private Button jForgotAccout;
    private SignInButton mGoogleBtn;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "GOOGLE_AUTH";

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private Button jSignin;
    private Button jSignUp;
    private EditText jEmail;
    private EditText jPassword;
    private FactFragment factsFragment = new FactFragment();
    private RegistrationFragment registrationFragment = new RegistrationFragment();
    private UserInformationFragment informationFragment = new UserInformationFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_login);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        jSignin = findViewById(R.id.btn_aSignin);
        jSignUp = findViewById(R.id.txt_aCreateAccount);
        jEmail = findViewById(R.id.edit_aEmail);
        jPassword = findViewById(R.id.edit_aPassword);
        jForgotAccout = findViewById(R.id.txt_aForgotAccount);


        jForgotAccout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartLoginActivity.this, ResetPasswordActivity.class));
            }
        });


        if (firebaseAuth.getCurrentUser() != null) {

            startActivity(new Intent(StartLoginActivity.this, MainActivity.class));

        }

        jSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email_string = jEmail.getText().toString().trim();
                final String password_string = jPassword.getText().toString().trim();

                if (email_string.equals("admin") && password_string.equals("1234")) {
                    startActivity(new Intent(StartLoginActivity.this, AdminActivity.class));

                }else {
                    if (TextUtils.isEmpty(email_string)) {
                        Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(password_string)) {
                        Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    progressDialog.setMessage("Wait for SignIn....");
                    progressDialog.show();

                    firebaseAuth.signInWithEmailAndPassword(email_string, password_string)
                            .addOnCompleteListener(StartLoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();

                                    if (task.isSuccessful()) {
                                        if (email_string.equals("musaibnashraf@gmail.com") && password_string.equals("1234567890")) {
                                            startActivity(new Intent(StartLoginActivity.this, AdminActivity.class));

                                        } else {
                                            startActivity(new Intent(StartLoginActivity.this, MainActivity.class));
                                        }


                                    }
                                }
                            });

                }




            }

        });
        jSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(StartLoginActivity.this, StartRegisterActivity.class));
            }
        });

    }
}
