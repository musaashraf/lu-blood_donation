package com.example.musa_ashraf.blood_donation;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {
    Button login,register,guestUser;
    FirebaseUser firebaseUser;
    private AlertDialog.Builder alertDialog;

    @Override
    protected void onStart() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        super.onStart();
        if (firebaseUser != null){
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        login =findViewById(R.id.Startlogin);
        register = findViewById(R.id.Startregister);
        guestUser = findViewById(R.id.skipRegester);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, StartLoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, StartRegisterActivity.class));
            }
        });
        guestUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog = new AlertDialog.Builder(StartActivity.this);
                alertDialog.setTitle("Guest User can't be use some features");
                //alertDialog.setIcon(R.drawable.ic_info_black_24dp);
                alertDialog.setPositiveButton("AGREE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

                AlertDialog alertDialog1 = alertDialog.create();
                alertDialog1.show();
            }
        });
    }
}
