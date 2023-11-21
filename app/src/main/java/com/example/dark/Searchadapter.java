package com.example.dark;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blank_learn.dark.R;
import com.example.chat.GroupChat;
import com.example.loginandsignup.Users;
import com.example.notification.NotificationModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
//
//public class Searchadapter extends RecyclerView.Adapter<Searchadapter.viewHolder> {
//    Context context;
//    ArrayList<Users> list;
//    public Searchadapter(Context context, ArrayList<Users> list) {
//        this.context = context;
//        this.list = list;
//    }
//    @NonNull
//    @Override
//    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//View view= LayoutInflater.from(context).inflate(R.layout.class_sample,parent,false);
//  return  new viewHolder(view);
//    }
//    @Override
//    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
//      Users users=list.get(position);
//     // Picasso.get().load(users.getProfilepic()).placeholder(R.drawable.profileuser).into(holder.binding.profilepic);
//       // holder.binding.usernme.setText(users.getName());
//        FirebaseDatabase.getInstance().getReference()
//                .child("Users")
//                .child(users.getUserID())
//                .child("followers")
//                .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        {
//                            if (snapshot.exists()) {
//                                holder.binding.buttonfollw.setBackgroundColor(context.getResources().getColor(R.color.purple_700));
//                                holder.binding.buttonfollw.setTextColor(context.getResources().getColor(R.color.black));
//                                holder.binding.buttonfollw.setText("Following");
//                                holder.binding.buttonfollw.setEnabled(false);
//                            } else{
//                                holder.binding.buttonfollw.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        Followmodel followmodel= new Followmodel();
//                                        followmodel.setFollowedBy(FirebaseAuth.getInstance().getUid());
//                                        followmodel.setFollowedAt(new Date().getTime());
//                                        FirebaseDatabase.getInstance().getReference()
//                                                .child("Users")
//                                                .child(users.getUserID())
//                                                .child("followers")
//                                                .child(FirebaseAuth.getInstance().getUid())
//                                                .setValue(followmodel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                    @Override
//                                                    public void onSuccess(Void unused) {
//                                                        FirebaseDatabase.getInstance().getReference()
//                                                                .child("Users")
//                                                                .child(users.getUserID())
//                                                                .child("followercount")
//                                                                .setValue(users.getFollowercount()+1).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                    @Override
//                                                                     public void onSuccess(Void unused) {
//                                                                        // holder.binding.buttonfollw.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.glogo));
//                                                                        holder.binding.buttonfollw.setBackgroundColor(context.getResources().getColor(R.color.white));
//                                                                        holder.binding.buttonfollw.setTextColor(context.getResources().getColor(R.color.black));
//                                                                        holder.binding.buttonfollw.setText("Following");
//                                                                        holder.binding.buttonfollw.setEnabled(false);
//                                                                        Toast.makeText(context, "you followed" + users.getName(), Toast.LENGTH_SHORT).show();
//
//                                                                        NotificationModel notificationModel = new NotificationModel();
//                                                                        notificationModel.setNotificationBy(FirebaseAuth.getInstance().getUid());
//                                                                        notificationModel.setNotificationAt(new Date().getTime());
//                                                                        notificationModel.setType("follow");
//                                                                        FirebaseDatabase.getInstance().getReference()
//                                                                                .child("notification")
//                                                                                .child(users.getUserID())
//                                                                                .push()
//                                                                                .setValue(notificationModel);
//                                                                    }
//
//
//                                                                });
//                                                    }
//                                                });
//                                    }
//                                });
//
//                            }
//                        }
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                    }
//                });
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(context, GroupChat.class);
//                intent.putExtra("name",users.getName());
//                intent.putExtra("ReceiversImage", users.getProfilepic());
//                intent.putExtra("uid",users.getUserID());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public class viewHolder extends RecyclerView.ViewHolder
//    {
//        UserSampleBinding binding;
//
//        public viewHolder(@NonNull View itemView) {
//            super(itemView);
//            binding=UserSampleBinding.bind(itemView);
//        }
//    }
//}
