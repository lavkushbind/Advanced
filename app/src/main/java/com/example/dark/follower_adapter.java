package com.example.dark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blank_learn.dark.R;

import java.util.ArrayList;

public class follower_adapter extends  RecyclerView.Adapter<follower_adapter.viewholder> {


    ArrayList<Followmodel> list;
    Context contextl;

    public follower_adapter(ArrayList<Followmodel> list, Context contextl) {
        this.list = list;
        this.contextl = contextl;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(contextl).inflate(R.layout.fragment_profile,parent,false);
   return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Followmodel model=list.get(position);
       // holder.storyprofileimg.setImageResource(model.getStoryprofileimg());
        //holder.Storyimg.setImageResource(model.getStoryimg());
        //holder.storyidnamee.setText(model.getStoryidnamee());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public  class viewholder extends RecyclerView.ViewHolder{

      //  ImageView Storyimg,storyprofileimg;
        //TextView storyidnamee;
        public viewholder(@NonNull View itemView) {
            super(itemView);

           // Storyimg =itemView.findViewById(R.id.Profile_img);
            //storyidnamee=itemView.findViewById(R.id.storyidname);
            //storyprofileimg=itemView.findViewById(R.id.storyprofileimg);
        }
    }
}