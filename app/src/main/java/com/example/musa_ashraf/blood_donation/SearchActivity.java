package com.example.musa_ashraf.blood_donation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musa_ashraf.blood_donation.Adapter.MyAdapter;
import com.example.musa_ashraf.blood_donation.Adapter.VDAdapter;
import com.example.musa_ashraf.blood_donation.Fragments.SingleDonorInformation;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private EditText mSearchField;
    private RecyclerView mRecyclerList;
    private DatabaseReference mDatabase;
    FirebaseRecyclerOptions<VDAdapter> options;
    FirebaseRecyclerAdapter<VDAdapter, SearchViewHolder> adapter;
    ArrayList<VDAdapter> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar mToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSearchField = findViewById(R.id.donorSearch);

        arrayList = new ArrayList<>();
        mRecyclerList = findViewById(R.id.recyclerDonor);
        mRecyclerList.setHasFixedSize(true);
        mRecyclerList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Donor_List");
        options = new FirebaseRecyclerOptions.Builder<VDAdapter>().setQuery(mDatabase, VDAdapter.class).build();


        mSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //searchUser(charSequence.toString())

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()){
                    search(s.toString());
                }
                else {
                    search("");
                }

            }
        });

        adapter = new FirebaseRecyclerAdapter<VDAdapter, SearchViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SearchViewHolder holder, int position, @NonNull VDAdapter model) {
                holder.t1.setText(model.getBlood());
                holder.t2.setText(model.getAddress());
                holder.t3.setText(model.getLocation());
                holder.t4.setText(model.getUniversity());
                //holder.t5.setText("User Id"+model.getUserid());
                //holder.t6.setText("Phone Number:"+model.getPhone());
                final String user_id = getRef(position).getKey();
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchActivity.this, SingleDonorInformation.class);
                        intent.putExtra("User_id",user_id);
                        startActivity(intent);

                    }
                });
            }

            @NonNull
            @Override
            public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_donor_sample, viewGroup, false);
                return new SearchViewHolder(view);

            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        mRecyclerList.setLayoutManager(linearLayoutManager);
        adapter.startListening();
        mRecyclerList.setAdapter(adapter);



    }

    private void search(String s) {
        Query firebaseSearchQuery = mDatabase.orderByChild("blood").startAt(s).endAt(s +"\uf8ff");
        firebaseSearchQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()){
                    arrayList.clear();
                    for (DataSnapshot dss: dataSnapshot.getChildren()){
                        final  VDAdapter userAdapter = dss.getValue(VDAdapter.class);
                        arrayList.add(userAdapter);

                    }
                    MyAdapter myAdapter = new MyAdapter(getApplicationContext(), arrayList);
                    mRecyclerList.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SearchActivity.this, "Enter Valid  BloodGroup", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView t1, t2, t3, t4, t5, t6, t7;

        public SearchViewHolder(View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.donor_bloodType);
            t2 = itemView.findViewById(R.id.donor_address);
            t3 = itemView.findViewById(R.id.donor_university);
            t4 = itemView.findViewById(R.id.donor_LastDonationdate);
            //t5 = itemView.findViewById(R.id.donor_Userid);
            //t6 = itemView.findViewById(R.id.donor_phone);
        }
    }
}






