package com.example.musa_ashraf.blood_donation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.musa_ashraf.blood_donation.Fragments.AccountFragment;
import com.example.musa_ashraf.blood_donation.Fragments.AddDonorFragment;
import com.example.musa_ashraf.blood_donation.Fragments.ChatFragment;
import com.example.musa_ashraf.blood_donation.Fragments.HomeFragment;
import com.example.musa_ashraf.blood_donation.Fragments.NotificationFragment;
import com.example.musa_ashraf.blood_donation.Fragments.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private CircleImageView actionbarImg;
    private FirebaseAuth firebaseAuth;

    private AlertDialog.Builder alertDialog;
    private Toolbar mToolbar;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private HomeFragment homeFragment;
    private NotificationFragment notificationFragment;
    private AccountFragment accountFragment;
    private ChatFragment chatFragment;
    private AddDonorFragment addDonorFragment;
    private ProfileFragment profileFragment;
    private String image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        bottomNavigationView = findViewById(R.id.main_nav);
        frameLayout = findViewById(R.id.main_frame);
        actionbarImg = findViewById(R.id.actionbar_image);

        homeFragment = new HomeFragment();
        notificationFragment = new NotificationFragment();
        accountFragment = new AccountFragment();
        chatFragment = new ChatFragment();
        addDonorFragment = new AddDonorFragment();
        profileFragment = new ProfileFragment();
        setFragment(homeFragment);

        mToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().getThemedContext();
        mToolbar.setTitleTextColor(0xFFFFFFFF);
        getSupportActionBar().setTitle("Blood Donation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setFragment(homeFragment);
                if (firebaseAuth.getCurrentUser() != null){
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }else{
                    startActivity(new Intent(MainActivity.this, StartActivity.class));

                }

            }
        });



        actionbarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseAuth.getCurrentUser() != null) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Account").child(user.getUid());


                    mUserDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            image = dataSnapshot.child("image").getValue().toString();
                            Picasso.get().load(image).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.ic_avarter_launcher).into(actionbarImg);

                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_frame, profileFragment);
                            fragmentTransaction.commit();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {


                        }
                    });

                } else {

                    Toast.makeText(MainActivity.this, "Please Login or Create Account", Toast.LENGTH_SHORT).show();
                }

            }
        });
        if (firebaseAuth.getCurrentUser() != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Account").child(user.getUid());

            mUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    image = dataSnapshot.child("image").getValue().toString();
                    Picasso.get().load(image).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.ic_avarter_launcher).into(actionbarImg);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {


                }
            });

        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        //bottomNavigationView.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(homeFragment);
                        return true;

                    case R.id.nav_notification:
                        //bottomNavigationView.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(notificationFragment);
                        return true;

                    case R.id.nav_account:
                        //bottomNavigationView.setItemBackgroundResource(R.color.design_default_color_primary_dark);
                        setFragment(accountFragment);
                        return true;
                    case R.id.nav_chat:
                        //bottomNavigationView.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(chatFragment);
                        return true;
                    case R.id.nav_addDonor:
                        // bottomNavigationView.setItemBackgroundResource(R.color.design_default_color_primary_dark);
                        setFragment(addDonorFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    @Override //menubar
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
            //Toast.makeText(getApplicationContext(), "About is Selected", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, AboutUs.class));
            return true;
        }

        if (item.getItemId() == R.id.menu_exit) {
            alertDialog = new AlertDialog.Builder(MainActivity.this);
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
    }//end menubar

    private void setFragment(Fragment Fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, Fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you want to Exit?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.super.onBackPressed();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
