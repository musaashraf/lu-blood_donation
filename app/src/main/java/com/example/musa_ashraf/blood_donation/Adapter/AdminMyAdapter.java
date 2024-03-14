package com.example.musa_ashraf.blood_donation.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.musa_ashraf.blood_donation.R;

import java.util.ArrayList;

public class AdminMyAdapter extends RecyclerView.Adapter<AdminMyAdapter.MyAdapterViewHolder> {

    public Context c;
    public ArrayList<VDAdapter> arrayList;

    public AdminMyAdapter(Context c, ArrayList<VDAdapter> arrayList) {
        this.c = c;
        this.arrayList = arrayList;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_donor_sample2,viewGroup,false);

        MyAdapterViewHolder holder = new MyAdapterViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int i) {
        VDAdapter userAdapter = arrayList.get(i);
        holder.t1.setText(userAdapter.getBlood());
        holder.t2.setText(userAdapter.getAddress());
        holder.t3.setText(userAdapter.getUniversity());
        holder.t4.setText(userAdapter.getPhone());
        holder.t5.setText(userAdapter.getUserid());
        holder.t6.setText(userAdapter.getPhone());

    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView t1, t2, t3, t4, t5, t6, t7;

        public MyAdapterViewHolder(View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.donor_bloodType);
            t2 = itemView.findViewById(R.id.donor_address);
            t3 = itemView.findViewById(R.id.donor_university);
            t4 = itemView.findViewById(R.id.donor_LastDonationdate);
            t5 = itemView.findViewById(R.id.donor_userid);
            t6 = itemView.findViewById(R.id.donor_phone);
        }

    }

}

