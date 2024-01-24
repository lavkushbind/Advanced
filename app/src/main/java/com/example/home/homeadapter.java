package com.example.home;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blank_learn.dark.R;
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
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class homeadapter extends RecyclerView.Adapter<homeadapter.viewHolder>  {
    ArrayList<postmodel> list;
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;
    Context context;
   public homeadapter(ArrayList<postmodel> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.video,parent,false);
        auth = FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();

        return new viewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        {
            postmodel postmodel= list.get(position);
            Picasso.get()
                    .load(postmodel.getPostImage())
                    .into(holder.binding.exoplayerimage);
            holder.binding.textView58.setText(postmodel.getPrice());
            holder.binding.textView57.setText(postmodel.getAbout());
            holder.binding.vtitle.setText(postmodel.getPostdescription());

            FirebaseDatabase.getInstance()
                    .getReference().child("Users")
                    .child(postmodel.getPostedBy())
                    .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Users user = snapshot.getValue(Users.class);

                    if (user != null)     {
                        holder.binding.nameID.setText(user.getName());
                    }else {

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            holder.binding.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=  new Intent(context,post2Activity.class);
                    intent.putExtra("price",postmodel.getPrice());
                    intent.putExtra("title",postmodel.getPostdescription());
                    intent.putExtra("postid",postmodel .getPostid());
                    intent.putExtra("postPic",postmodel.getPostImage());
                    intent.putExtra("postedBy",postmodel.getPostedBy());
                    intent.putExtra("video",postmodel.getPostVideo());

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=  new Intent(context,post2Activity.class);
                    intent.putExtra("price",postmodel.getPrice());
                    intent.putExtra("title",postmodel.getPostdescription());
                    intent.putExtra("postid",postmodel .getPostid());
                    intent.putExtra("postPic",postmodel.getPostImage());
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
        return list.size();
    }
    public static class viewHolder extends RecyclerView.ViewHolder {
        VideoBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = VideoBinding.bind(itemView);
        }
    }
    }













