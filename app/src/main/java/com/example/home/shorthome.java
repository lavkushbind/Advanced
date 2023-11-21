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
import com.example.loginandsignup.Users;
import com.example.payment.postmodel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class shorthome extends RecyclerView.Adapter<shorthome.viewHolder>  {
    ArrayList<postmodel> listt;
    Context context;
    public shorthome(ArrayList<postmodel> list, Context context) {
        this.listt = list;
        this.context = context;
    }
    @NonNull
    @Override
    public shorthome.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.shortvideo,parent,false);
return  new viewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull shorthome.viewHolder holder, int position) {
        postmodel postmodel= listt.get(position);
        Picasso.get()
                .load(postmodel.getPostImage())
                .into(holder.binding.exoplayerimage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=  new Intent(context,post2Activity.class);
                intent.putExtra("price",postmodel.getPrice());
                intent.putExtra("stand",postmodel.getStandred());
                intent.putExtra("postid",postmodel .getPostid());
                intent.putExtra("postedBy",postmodel.getPostedBy());
                intent.putExtra("video",postmodel.getPostVideo());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });
    }
    @Override
    public int getItemCount() {
        return listt.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder {
        ShortvideoBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ShortvideoBinding.bind(itemView);
        }
    }
}
