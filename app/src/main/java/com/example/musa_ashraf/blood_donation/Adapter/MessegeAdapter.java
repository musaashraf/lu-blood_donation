package com.example.musa_ashraf.blood_donation.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.musa_ashraf.blood_donation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessegeAdapter extends RecyclerView.Adapter<MessegeAdapter.ViewHolder> {
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context mContext;
    private List<ChatAdapter> mChat;
    private String imageurl;

    FirebaseUser fUser;

    public MessegeAdapter(Context mContext, List<ChatAdapter> mChat, String imageurl) {//
        this.mChat = mChat;
        this.mContext = mContext;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right,parent,false);
            return new MessegeAdapter.ViewHolder(view);
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left,parent,false);
            return new MessegeAdapter.ViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

       ChatAdapter chat = mChat.get(position);
       holder.show_message.setText(chat.getMessage());
       if (imageurl.equals("null")){
           holder.profileimage.setImageResource(R.mipmap.ic_launcher);

       }
       else {
           Glide.with(mContext).load(imageurl).into(holder.profileimage);
       }


    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView show_message;
        ImageView profileimage;

        public ViewHolder(View itemView){
            super(itemView);
            show_message = itemView.findViewById(R.id.show_massages);
            profileimage = itemView.findViewById(R.id.profile_image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(fUser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else {
            return MSG_TYPE_LEFT;
        }

    }
}
