package com.example.musa_ashraf.blood_donation.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
    private RecyclerView mUsersList;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerOptions<FriendsAdapter> options;
    private FirebaseRecyclerAdapter<FriendsAdapter, BlogViewHolder> adapter;



    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chat, container, false);

        mUsersList = v.findViewById(R.id.friend_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Account");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        options = new FirebaseRecyclerOptions.Builder<FriendsAdapter>().setQuery(mDatabase, FriendsAdapter.class).build();


        if (firebaseAuth.getCurrentUser() != null){
            adapter = new FirebaseRecyclerAdapter<FriendsAdapter, BlogViewHolder>(options) {
                @Override
                protected void onBindViewHolder(final BlogViewHolder holder, int position, FriendsAdapter model) {

                    Picasso.get().load(model.getImage()).placeholder(R.drawable.ic_avarter_launcher).into(holder.i1, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            //Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                    holder.t1.setText("Name: "+model.getName());
                    holder.t2.setText("Blood Group: "+model.getBloodGroup());
                    final String user_id = getRef(position).getKey();
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), ChatActivity.class);
                            intent.putExtra("User_id", user_id);
                            startActivity(intent);

                        }
                    });
                }

                @Override
                public BlogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_single_layout, parent, false);
                    return new BlogViewHolder(view);
                }
            };
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
            mUsersList.setLayoutManager(gridLayoutManager);
            adapter.startListening();
            mUsersList.setAdapter(adapter);
        }
        else {
            AccountFragment accountFragment = new AccountFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_frame,accountFragment);
            fragmentTransaction.commit();
        }



        return v;
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

    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        TextView t1, t2;
        ImageView i1;

        public BlogViewHolder(View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.user_single_name);
            t2 = itemView.findViewById(R.id.users_default_bloodgroup);
            i1 = itemView.findViewById(R.id.users_single_image);
        }
    }

}
