package com.example.home;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blank_learn.dark.R;
import com.blank_learn.dark.databinding.ShortvideoBinding;
import com.blank_learn.dark.databinding.VideoBinding;
import com.example.chat.ChatAA;
import com.example.chat.GroupChat;
import com.example.loginandsignup.Users;
import com.example.payment.postmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class profile_homeadapter extends RecyclerView.Adapter<profile_homeadapter.viewHolder>  {
    ArrayList<postmodel> list_p;
    Context context;
    public profile_homeadapter(ArrayList<postmodel> list, Context context) {
        this.list_p = list;
        this.context = context;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.shortvideo,parent,false);
        return new viewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        {
            postmodel postmodel= list_p.get(position);
            Picasso.get()
                    .load(postmodel.getPostImage())
                    //   .placeholder(R.drawable.profileuser)
                    .into(holder.binding.exoplayerimage);
            //  holder.binding.nameID.setText(postmodel.getPostedBy());
            holder.binding.priceID.setText(postmodel.getPrice());
            holder.binding.vtitle.setText(postmodel.getPostdescription());
            FirebaseDatabase.getInstance().getReference().child("Users").child(postmodel.getPostedBy()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Users user = snapshot.getValue(Users.class);
                    holder.binding.nameID.setText(user.getName());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=  new Intent(context,post2Activity.class);
                    intent.putExtra("price",postmodel.getPrice());
                    intent.putExtra("Link",postmodel.getPaylink());
                    intent.putExtra("title",postmodel.getPostdescription());

                    intent.putExtra("postid",postmodel .getPostid());
                    intent.putExtra("postedBy",postmodel.getPostedBy());
                    intent.putExtra("video",postmodel.getPostVideo());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return list_p.size();
    }

    public void  searchList(ArrayList<postmodel> searchList){
        list_p= searchList;
        notifyDataSetChanged();
    }
    public static class viewHolder extends RecyclerView.ViewHolder {
        ShortvideoBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ShortvideoBinding.bind(itemView);
        }
    }
}





