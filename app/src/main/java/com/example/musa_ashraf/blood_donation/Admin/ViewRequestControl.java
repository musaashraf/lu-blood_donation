package com.example.musa_ashraf.blood_donation.Admin;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musa_ashraf.blood_donation.Adapter.VRAdapter;
import com.example.musa_ashraf.blood_donation.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ViewRequestControl extends AppCompatActivity {
    private RecyclerView mBlogList;
    private DatabaseReference mDatabase, mApprovedDatabase;
    FirebaseRecyclerOptions<VRAdapter> options;
    FirebaseRecyclerAdapter<VRAdapter, ViewRequestControl.ItemViewHolders> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request_control);

        Toolbar mToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Approval List");
        getSupportActionBar().getThemedContext();
        mToolbar.setTitleTextColor(0xFFFFFFFF);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mBlogList = findViewById(R.id.recycler);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("PostRequest");
        mApprovedDatabase = FirebaseDatabase.getInstance().getReference().child("ApprovedPostRequest");
        options = new FirebaseRecyclerOptions.Builder<VRAdapter>().setQuery(mDatabase, VRAdapter.class).build();



        adapter = new FirebaseRecyclerAdapter<VRAdapter, ItemViewHolders>(options) {
            @Override
            protected void onBindViewHolder(final ItemViewHolders holder, int position, final VRAdapter model) {
                holder.t1.setText(model.getStatus());
                holder.t2.setText("Blood Group:   "+model.getBlood());
                holder.t3.setText("Hospital Name: "+model.getHospital());
                holder.t4.setText("Location:      "+model.getLocation());
                holder.t5.setText("Details:       "+model.getDetails());
                holder.t6.setText("Time/Date:     "+model.getTime());
                holder.t7.setText("Phone Number:  "+model.getPhone());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference roomRef2 = mApprovedDatabase.child(model.getUid());

                        Map<String, String> strings = new HashMap<>();
                        strings.put("status", "I need emergency blood");
                        strings.put("blood", model.getBlood());
                        strings.put("hospital", model.getHospital());
                        strings.put("location",model.getLocation());
                        strings.put("details", model.getDetails());
                        strings.put("time", model.getTime());
                        strings.put("phone",model.getPhone());
                        strings.put("stime", model.getStime());
                        strings.put("uid",model.getUid());
                        roomRef2.setValue(strings);
                        Toast.makeText(ViewRequestControl.this, "Data Insert Successfully", Toast.LENGTH_SHORT).show();


                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mDatabase.child(model.getUid()).removeValue();


                        return false;
                    }

                });

            }

            @Override
            public ItemViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_request_sample2, parent, false);
                ItemViewHolders holders =new ItemViewHolders(view);

                holders.t7.setTag(holders);

                return holders;

            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
       linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mBlogList.setLayoutManager(linearLayoutManager);
        adapter.startListening();
        mBlogList.setAdapter(adapter);

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
    public static class ItemViewHolders extends RecyclerView.ViewHolder {

        TextView t1, t2, t3, t4, t5, t6, t7;

        public ItemViewHolders(View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.s_status);
            t2 = itemView.findViewById(R.id.s_bloodType);
            t3 = itemView.findViewById(R.id.s_hospitalname);
            t4 = itemView.findViewById(R.id.s_location);
            t5 = itemView.findViewById(R.id.s_details);
            t6 = itemView.findViewById(R.id.s_time);
            t7 = itemView.findViewById(R.id.s_phone);

        }
    }
}
