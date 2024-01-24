package com.example.dark;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blank_learn.dark.R;
import com.blank_learn.dark.databinding.SearchSampleBinding;
import com.blank_learn.dark.databinding.VideoBinding;
import com.example.chat.ChatAA;
import com.example.chat.GroupChat;
import com.example.home.post2Activity;
import com.example.loginandsignup.Users;
import com.example.payment.postmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Search_course_adapter extends RecyclerView.Adapter<Search_course_adapter.viewHolder>  {
    ArrayList<postmodel> list;
    Context context;
    public Search_course_adapter(ArrayList<postmodel> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public Search_course_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.search_sample,parent,false);
        return new Search_course_adapter.viewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull Search_course_adapter.viewHolder holder, int position) {
        {
            postmodel postmodel= list.get(position);
            Picasso.get()
                    .load(postmodel.getPostImage())
                    .into(holder.binding.exoplayerimage);
            holder.binding.vtitle.setText(postmodel.getPostdescription());
            holder.binding.textView59.setText(postmodel.getAbout());

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
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=  new Intent(context, post2Activity.class);
                    intent.putExtra("price",postmodel.getPrice());
                    intent.putExtra("Link",postmodel.getPaylink());
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
        SearchSampleBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SearchSampleBinding.bind(itemView);
        }
    }
}





