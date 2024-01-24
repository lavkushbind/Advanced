package com.example.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blank_learn.dark.R;
import com.blank_learn.dark.databinding.TopteaBinding;
import com.example.profile.ProActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Top_teacher_adapter extends RecyclerView.Adapter<Top_teacher_adapter.viewholder> {
    ArrayList<Teacher_model> Teacher_list;
    Context context;
    public Top_teacher_adapter( ArrayList<Teacher_model> Teacher_list,Context context) {
        this.Teacher_list = Teacher_list;
        this.context = context;
    }
    @NonNull
    @Override
    public Top_teacher_adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.toptea,parent,false);
        return new viewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull Top_teacher_adapter.viewholder holder, int position) {
        Teacher_model teacher_model = Teacher_list.get(position);
        Picasso.get().load(teacher_model.getPic())
//                .placeholder(R.drawable.profileuser)
                .into(holder.binding.profilepic);
        holder.binding.tname.setText(teacher_model.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = teacher_model.getId();
                Intent intent=  new Intent(context, ProActivity.class);
                intent.putExtra("USER_ID", userId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return Teacher_list.size();
    }

    public  class viewholder extends RecyclerView.ViewHolder{
        @NonNull
  TopteaBinding binding;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding= TopteaBinding.bind(itemView);
        }
    }


}