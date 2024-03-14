package com.example.musa_ashraf.blood_donation;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.musa_ashraf.blood_donation.Adapter.ChatAdapter;
import com.example.musa_ashraf.blood_donation.Adapter.FriendsAdapter;
import com.example.musa_ashraf.blood_donation.Adapter.MessegeAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    private CircleImageView profile_image;
    private TextView userName;
    private EditText text_send;
    private ImageButton btn_send;

    MessegeAdapter messegeAdapter;
    List<ChatAdapter> mChat;
    RecyclerView recyclerView;

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        profile_image = findViewById(R.id.message_profile_image);
        userName = findViewById(R.id.text_message_id);
        text_send = findViewById(R.id.text_send);
        btn_send = findViewById(R.id.btn_send);

        final Toolbar toolbar = findViewById(R.id.toolbar_meseging);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.chatRecycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        final String user_id = getIntent().getStringExtra("User_id");
        reference = FirebaseDatabase.getInstance().getReference().child("Account").child(user_id);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();



        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                if (!msg.equals("")){
                    sendMessage(firebaseUser.getUid(),user_id,msg);
                }else {
                    Toast.makeText(ChatActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                FriendsAdapter userAdapter = dataSnapshot.getValue(FriendsAdapter.class);

                userName.setText(userAdapter.getName());

                if (userAdapter.getImage().equals("default")){
                    profile_image.setImageResource(R.drawable.ic_avarter_launcher);

                }
                else {
                    Glide.with(ChatActivity.this).load(userAdapter.getImage()).into(profile_image);
                }

                readMessage(firebaseUser.getUid(),user_id, userAdapter.getImage());//
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void sendMessage(String sender,String receiver,String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String ,Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message",message);

        reference.child("Chats").push().setValue(hashMap);
    }
    private void  readMessage(final String myid, final String userid, final String imageurl){//
        mChat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    ChatAdapter chatAdapter = snapshot.getValue(ChatAdapter.class);
                    if (chatAdapter.getReceiver().equals(myid) && chatAdapter.getSender().equals(userid)
                            || chatAdapter.getReceiver().equals(userid) && chatAdapter.getSender().equals(myid)){

                        mChat.add(chatAdapter);

                    }
                    messegeAdapter = new MessegeAdapter(ChatActivity.this, mChat,imageurl);//
                    recyclerView.setAdapter(messegeAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
