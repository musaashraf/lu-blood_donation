package com.example.musa_ashraf.blood_donation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musa_ashraf.blood_donation.Fragments.AccountFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class StartRegisterActivity extends AppCompatActivity {
    private EditText jEmail, jPassword, jName, jBloodgroup, jPhone;
    private Button jSignup;
    private TextView jSignin;
    private DatabaseReference root;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_register);
        final AccountFragment accountFragment = new AccountFragment();

        progressDialog =new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();

        jEmail =findViewById(R.id.edit_rEmail);
        jPassword = findViewById(R.id.edit_rPassword);
        jName = findViewById(R.id.edit_rName);
        jBloodgroup = findViewById(R.id.edit_rBloodgroup);
        jPhone = findViewById(R.id.edit_rPhone);

        jSignup =findViewById(R.id.btn_rSignup);
        jSignin =findViewById(R.id.btn_rSignin);

        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user= firebaseAuth.getCurrentUser();

            }
        };

        jSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartRegisterActivity.this, StartLoginActivity.class));
            }
        });

        jSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                    Toast.makeText(StartRegisterActivity.this,"Enter Your name",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(tEmail))
                {
                    progressDialog.hide();
                    Toast.makeText(StartRegisterActivity.this,"Enter your valid Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(tPassword))
                {
                    progressDialog.hide();
                    Toast.makeText(StartRegisterActivity.this,"Enter your Password ",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(tBloodgroup))
                {
                    progressDialog.hide();
                    Toast.makeText(StartRegisterActivity.this,"Enter Blood Group",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(tPhone))
                {
                    progressDialog.hide();
                    Toast.makeText(StartRegisterActivity.this,"Enter Phone Number",Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (tPassword.length() <8)
                {
                    progressDialog.hide();
                    Toast.makeText(StartRegisterActivity.this,"Password Length 8",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (tPhone.length() <11)
                {
                    progressDialog.hide();
                    Toast.makeText(StartRegisterActivity.this,"Enter Valid Phone Number",Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(tEmail,tPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            String userID = firebaseAuth.getCurrentUser().getUid();

                            root = FirebaseDatabase.getInstance().getReference().child("Account").child(userID);
                            Intent intent=new Intent(StartRegisterActivity.this, MainActivity.class);


                            HashMap<String,String> info = new HashMap<>();
                            info.put("Name",tName);
                            info.put("BloodGroup",tBloodgroup);
                            info.put("Phone",tPhone);
                            info.put("Password",tPassword);
                            info.put("Email",tEmail);
                            info.put("Last Donation","null");
                            info.put("image","null");
                            info.put("Address","null");
                            info.put("userid", userID);

                            root.setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(StartRegisterActivity.this, MainActivity.class);
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
                                Toast.makeText(StartRegisterActivity.this,"User already exists",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(StartRegisterActivity.this,"Invalid email",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
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
