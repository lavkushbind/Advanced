package com.example.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blank_learn.dark.R;
import com.blank_learn.dark.databinding.MemberBinding;

import com.example.home.post2Activity;
import com.example.loginandsignup.Users;
import com.example.payment.postmodel;
import com.example.profile.ProActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.viewholder> {
    ArrayList<Users> list;
    Context context;

    public MemberAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.member,parent,false);
        return new viewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Users users = list.get(position);
        Picasso.get().load(users.getProfilepic())
                .placeholder(R.drawable.profileuser)
                        .into(holder.binding.Member);
        holder.binding.textView32.setText(users.getName());
       holder.binding.textView27.setText(users.getBio());
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=  new Intent(context,ProActivity.class);
               intent.putExtra("name",users.getUserID());
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity(intent);
           }
       });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public  class viewholder extends RecyclerView.ViewHolder{
        @NonNull
        MemberBinding binding;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding= MemberBinding.bind(itemView);
        }
    }
}