package com.example.notification;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blank_learn.dark.R;
import com.blank_learn.dark.databinding.Notification2sampleBinding;
import com.example.chat.GroupChat;
import com.example.loginandsignup.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class NotificationAdapter extends  RecyclerView.Adapter<NotificationAdapter.viewholder> {
    ArrayList<NotificationModel> list;
    Context contextl;
    public NotificationAdapter(ArrayList<NotificationModel> list, Context contextl)
    {
        this.list = list;
        this.contextl = contextl;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(contextl).inflate(R.layout.notification2sample,parent,false);
        return new viewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {


            NotificationModel model = list.get(position);
            Picasso.get().load(model.getPaypic())
                    .into(holder.binding.imageView9);

            String type = model.getType();
            FirebaseDatabase.getInstance().getReference()
                    .child("Users")
                    .child(model.getNotificationBy())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Users users = snapshot.getValue(Users.class);
                            Picasso.get().load(users.getProfilepic())
                                    .placeholder(R.drawable.lavkushbind);
                            // .into(holder.binding.profileImg);
//
//
//                            if (type.equals("signup")) {
//                                holder.binding.notification.setText("welcome to blanklearn the ......");
//                            }
                            if (type.equals("follow")) {
                                holder.binding.notification.setText("Starting following");
                            }
                            if (type.equals("post")) {
                                holder.binding.notification.setText("After reviewing, your course will be live");
                            }

                            if (type.equals("payment send")) {
                                holder.binding.notification.setText("We are currently reviewing your payment, and once that is complete, you will be able to join the class.    Don't worry, it's safe");
                            }
                            if (type.equals("Payment received")) {
                                holder.binding.notification.setText("Your payment has been sent to your phone number" );
                            }

                            if (type.equals("blanklearn")) {
                                holder.binding.notification.setText("Blanklearn inform to you" );
                            }
                            else {
                                //  holder.binding.notification.setText( "..");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotificationPopup(contextl, model);
            }

            private void showNotificationPopup(Context context, NotificationModel notificationModel) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.notification2sample, null);
                dialogBuilder.setView(dialogView);

                // Customize the content of the custom view/dialog using dialogView.findViewById(R.id.view_id)
                // Display the notification details in the popup layout

                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
            }        });

            holder.binding.openNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FirebaseDatabase.getInstance().getReference()
                            .child("notification")
                            .child(model.getNotificationBy())
                            .child(model.getNotificationId())
                            .child("checkopen")
                            .setValue(true);

                    //   holder.binding.notification.setTextColor(Color.parseColor("#100F0F"));

                    //  holder.binding.openNotification.setBackgroundColor(contextl.getResources().getColor(R.color.white));

                }
            });
            Boolean checkOpen = model.isCheckOpen();


        if (checkOpen == true)
        {
            //  holder.binding.notification.setTextColor(Color.parseColor("#100F0F"));
            // holder.binding.openNotification.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else {
        }

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class viewholder extends RecyclerView.ViewHolder{
        Notification2sampleBinding binding;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding =Notification2sampleBinding.bind(itemView);
        }
    }
}
