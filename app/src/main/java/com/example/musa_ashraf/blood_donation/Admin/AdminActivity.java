package com.example.musa_ashraf.blood_donation.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.musa_ashraf.blood_donation.R;
import com.example.musa_ashraf.blood_donation.StartActivity;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminActivity extends AppCompatActivity {
    private AlertDialog.Builder alertDialog;
    private Toolbar mToolbar;
    private Button registerControl, requestControl, donorControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        alertDialog = new AlertDialog.Builder(this);
        registerControl = findViewById(R.id.admin_register_control);
        requestControl = findViewById(R.id.admin_post_request_control);
        donorControl = findViewById(R.id.admin_donor_control);

        registerControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, RegisterAccountControl.class));
            }
        });
        requestControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, ViewRequestControl.class));
            }
        });
        donorControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AdminDonorControl.class));
            }
        });

        mToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Admin Pannel");
        getSupportActionBar().getThemedContext();
        mToolbar.setTitleTextColor(0xFFFFFFFF);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareSubText = "Blood Donation - The Great App";
            String shareBodyText = "https://play.google.com/store/apps/details?id=com.whatsapp&hl=en";
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubText);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(shareIntent, "Share Via"));
            return true;
        }
        if (item.getItemId() == R.id.menu_About) {
            Toast.makeText(getApplicationContext(), "About is Selected", Toast.LENGTH_SHORT).show();
            return true;
        }


        if (item.getItemId() == R.id.menu_exit) {

            alertDialog.setTitle(R.string.title_text);
            alertDialog.setIcon(R.drawable.ic_exit_to_app_black_24dp);
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog1 = alertDialog.create();
            alertDialog1.show();

        }

        return super.onOptionsItemSelected(item);
    }
}
