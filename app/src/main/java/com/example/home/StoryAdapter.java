package com.example.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blank_learn.dark.R;
import com.blank_learn.dark.databinding.StorySampleBinding;
import com.blank_learn.dark.databinding.VideoBinding;
import com.example.loginandsignup.Users;
import com.example.payment.postmodel;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.viewHolder>  {
    ArrayList<Story_model> story_list;
    Context context;
    public StoryAdapter(ArrayList<Story_model> list, Context context) {
        this.story_list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public StoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.story_sample,parent,false);

        return new StoryAdapter.viewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull StoryAdapter.viewHolder holder, int position) {
        Story_model storyModel = story_list.get(position);
        FirebaseDatabase.getInstance()
                .getReference().child("Users")
                .child(storyModel.getUserid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users user = snapshot.getValue(Users.class);

                        Picasso.get()
                                .load(user.getProfilepic()).placeholder(R.drawable.userprofile)
                                .into(holder.binding.profilepic2);
                        holder.binding.textView61.setText(user.getName());

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=  new Intent(context,StoryActivity.class);
                intent.putExtra("Link",storyModel.getStoryid());
                intent.putExtra("userId",storyModel.getUserid());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return story_list.size();
    }
    public static class viewHolder extends RecyclerView.ViewHolder {
        StorySampleBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = StorySampleBinding.bind(itemView);
        }


    }
}
