package com.example.musa_ashraf.blood_donation.Admin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musa_ashraf.blood_donation.Adapter.FriendsAdapter;

import com.example.musa_ashraf.blood_donation.ChatActivity;
import com.example.musa_ashraf.blood_donation.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class RegisterAccountControl extends AppCompatActivity {
    private RecyclerView mUsersList;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerOptions<FriendsAdapter> options;
    private FirebaseRecyclerAdapter<FriendsAdapter, RegisterAccountControl.ItemViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account_control);

        Toolbar mToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Register Account");
        getSupportActionBar().getThemedContext();
        mToolbar.setTitleTextColor(0xFFFFFFFF);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mUsersList = findViewById(R.id.friend_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Account");
        options = new FirebaseRecyclerOptions.Builder<FriendsAdapter>().setQuery(mDatabase, FriendsAdapter.class).build();

            adapter = new FirebaseRecyclerAdapter<FriendsAdapter, ItemViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull final FriendsAdapter model) {
                    Picasso.get().load(model.getImage()).placeholder(R.drawable.ic_avarter_launcher).into(holder.i1, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
                    holder.t1.setText("Name: "+ model.getName());
                    holder.t2.setText("Blood Group: "+model.getBloodGroup());
                    final String user_id = getRef(position).getKey();
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(RegisterAccountControl.this, ChatActivity.class);
                            intent.putExtra("User_id", user_id);
                            startActivity(intent);

                        }
                    });
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            mDatabase.child(model.getUserid()).removeValue();
                            return false;
                        }
                    });

                }

                @Override
                public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_single_layout, parent, false);
                    return new ItemViewHolder(view);
                }
            };
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            mUsersList.setLayoutManager(linearLayoutManager);
            adapter.startListening();
            mUsersList.setAdapter(adapter);
        }


    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        if (adapter != null) {
            adapter.stopListening();
        }
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView t1, t2;
        ImageView i1;

        public ItemViewHolder(View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.user_single_name);
            t2 = itemView.findViewById(R.id.users_default_bloodgroup);
            i1 = itemView.findViewById(R.id.users_single_image);
        }
    }
}
