package com.example.musa_ashraf.blood_donation;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutUs extends AppCompatActivity implements View.OnClickListener {
    CircleImageView musafb, musagmail, musatwitter, shakilfb, shakilgmail, shakiltwitter, foyezfb, foyezgamil, foyeztwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Toolbar mToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().getThemedContext();
        mToolbar.setTitleTextColor(0xFFFFFFFF);
        getSupportActionBar().setTitle("About App");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        musafb = findViewById(R.id.fbmusa);
        musagmail = findViewById(R.id.gmailmusa);
        musatwitter = findViewById(R.id.twittermusa);

        shakilfb = findViewById(R.id.fbshakil);
        shakilgmail = findViewById(R.id.gmailshakil);
        shakiltwitter = findViewById(R.id.twittershakil);

        foyezfb = findViewById(R.id.fbfoyez);
        foyezgamil = findViewById(R.id.gmailfoyez);
        foyeztwitter = findViewById(R.id.twitterfoyez);

        musafb.setOnClickListener(this);
        musagmail.setOnClickListener(this);
        musatwitter.setOnClickListener(this);
        shakilfb.setOnClickListener(this);
        shakilgmail.setOnClickListener(this);
        shakiltwitter.setOnClickListener(this);
        foyezgamil.setOnClickListener(this);
        foyezfb.setOnClickListener(this);
        foyeztwitter.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.fbmusa) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/musa.ashraf.8")));
        } else if (v.getId() == R.id.gmailmusa) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/u/0/+MusaAshraf")));
        } else if (v.getId() == R.id.twittermusa) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/MusaAshraf")));
        }

        else if (v.getId() == R.id.fbshakil) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/ashrafali.shakil")));
        } else if (v.getId() == R.id.gmailshakil) {
            //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gmail.com/ashrafalishakil@gmail.com")));
        } else if (v.getId() == R.id.twittershakil) {
            //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.google.com/mail/u/0/#inbox?compose=new")));
        }

        else if (v.getId() == R.id.fbfoyez) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=100011465829203")));
        } else if (v.getId() == R.id.twitterfoyez) {
           // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.google.com/mail/u/0/#inbox?compose=new")));
        } else if (v.getId() == R.id.gmailfoyez) {
           // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("")));
        }


    }
}
