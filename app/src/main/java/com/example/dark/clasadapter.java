package com.example.dark;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blank_learn.dark.R;
import com.blank_learn.dark.databinding.ClassSampleBinding;
import com.example.chat.GroupChat;
import com.example.chat.Member;
import com.example.payment.postmodel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class clasadapter extends RecyclerView.Adapter<clasadapter.viewholder> {
ArrayList<clasmodel> list;
Context context;
    public clasadapter(ArrayList<clasmodel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.class_sample,parent,false);
        return  new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        clasmodel clasmodel= list.get(position) ;

      Picasso.get().load(clasmodel.getPostpic())
             // .placeholder(R.drawable.profileuser)
              .into(holder.binding.postpic);

        holder.binding.posttitle.setText(clasmodel.getPosttitle());

     String type = clasmodel.getType();
       FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(clasmodel.getClasid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        postmodel postmodel = snapshot.getValue(postmodel.class);
                        if(type.equals("buyy"))  {
                            holder.binding.usernmed.setText( "start learning You are in the class");
                        }
                        if(type.equals("buy"))  {
                            holder.binding.usernmed.setText( "start learning You are in the class");
                        }
                             if(type.equals("post"))  {
                            holder.binding.usernmed.setText( " you course is now live");
                        }
                        else {
                         //   holder.binding.usernmed.setText( "live");

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        holder.binding.postpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, GroupChat.class);
                intent.putExtra("idd",clasmodel.getLink());
                intent.putExtra("name",clasmodel.getType());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Member.class);
                intent.putExtra("idd",clasmodel.getLink());
                intent.putExtra("name",clasmodel.getType());
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
    ClassSampleBinding binding;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding = ClassSampleBinding.bind(itemView);
        }
    }

}
