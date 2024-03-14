package com.example.musa_ashraf.blood_donation.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musa_ashraf.blood_donation.Adapter.VRAdapter;
import com.example.musa_ashraf.blood_donation.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewRequestFragment extends Fragment {

    private RecyclerView mBlogList;
    private DatabaseReference mDatabase, endDatabase;
    FirebaseRecyclerOptions<VRAdapter> options;
    FirebaseRecyclerAdapter<VRAdapter, ItemViewHolder> adapter;
    public String myid;



    public ViewRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_request, container, false);


        mBlogList = v.findViewById(R.id.recycler);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ApprovedPostRequest");
        endDatabase = FirebaseDatabase.getInstance().getReference().child("PostRequest");
        options = new FirebaseRecyclerOptions.Builder<VRAdapter>().setQuery(mDatabase, VRAdapter.class).build();



        adapter = new FirebaseRecyclerAdapter<VRAdapter, ItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(final ItemViewHolder holder, int position, final VRAdapter model) {
                holder.t1.setText(model.getStatus());
                holder.t2.setText("Blood Group:   "+model.getBlood());
                holder.t3.setText("Hospital Name: "+model.getHospital());
                holder.t4.setText("Location:      "+model.getLocation());
                holder.t5.setText("Details:       "+model.getDetails());
                holder.t6.setText("Time/Date:     "+model.getTime());
                holder.t7.setText("Phone Number:  "+model.getPhone());
                holder.t8.setText("Status Time:  "+model.getStime());

                endDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //String req_type = dataSnapshot.child(model.getUid()).child("blood").getValue().toString();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_request_sample, parent, false);
                return new ItemViewHolder(view);
            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mBlogList.setLayoutManager(linearLayoutManager);
        adapter.startListening();
        mBlogList.setAdapter(adapter);



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
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView t1, t2,t3,t4,t5,t6,t7,t8;

        public ItemViewHolder(View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.s_status);
            t2 = itemView.findViewById(R.id.s_bloodType);
            t3 = itemView.findViewById(R.id.s_hospitalname);
            t4 = itemView.findViewById(R.id.s_location);
            t5 = itemView.findViewById(R.id.s_details);
            t6 = itemView.findViewById(R.id.s_time);
            t7 = itemView.findViewById(R.id.s_phone);
            t8 = itemView.findViewById(R.id.s_statustime);

        }

    }
}
