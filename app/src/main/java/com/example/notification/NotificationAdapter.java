package com.example.notification;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blank_learn.dark.R;
import com.blank_learn.dark.databinding.Notification2sampleBinding;
import com.example.chat.GroupChat;
import com.example.loginandsignup.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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

                            if (type.equals("edit")) {
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("notify").child("massg");

                                // Adding a ValueEventListener to fetch data from Firebase Realtime Database
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            String notificationText = dataSnapshot.getValue(String.class);
                                            holder.binding.notification.setText(notificationText);
                                        } else {
                                            holder.binding.notification.setText("Data not available");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        holder.binding.notification.setText("Error fetching data");
                                    }
                                });
                            }


                            if (type.equals("buy")) {
                                holder.binding.notification.setText("Congratulation you are join to a course");
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
//              holder.binding.notification.setTextColor(Color.parseColor("#100F0F"));
//             holder.binding.openNotification.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else {
        }

    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    private void showNotificationPopup(Context context, NotificationModel notificationModel) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.notification_sample, null);
        dialogBuilder.setView(dialogView);

        // Initialize views from the custom layout
        ImageView popupImageView = dialogView.findViewById(R.id.popupImageView);
        TextView popupNotificationText = dialogView.findViewById(R.id.popupNotificationText);

        // Set values based on the notification model
        Picasso.get().load(notificationModel.getPaypic()).into(popupImageView);

        // Set notification text based on the notification type
        String type = notificationModel.getType();
        if (type.equals("follow")) {
            popupNotificationText.setText("Starting following");
        } else if (type.equals("post")) {
            popupNotificationText.setText("After reviewing, your course will be live");
        } else if (type.equals("edit")) {
            // Set notification text based on the "edit" type
            // You can modify this based on your requirements
            popupNotificationText.setText("Notification text for edit type");
        }
        // Add more conditions for other types

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }


    public  class viewholder extends RecyclerView.ViewHolder{
        Notification2sampleBinding binding;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding =Notification2sampleBinding.bind(itemView);
        }
    }
}
//import android.app.AlertDialog;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.blank_learn.dark.R;
//import com.blank_learn.dark.databinding.Notification2sampleBinding;
//import com.example.loginandsignup.Users;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//
//public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.viewholder> {
//    ArrayList<NotificationModel> list;
//    Context contextl;
//
//    public NotificationAdapter(ArrayList<NotificationModel> list, Context contextl) {
//        this.list = list;
//        this.contextl = contextl;
//    }
//
//    @NonNull
//    @Override
//    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(contextl).inflate(R.layout.notification2sample, parent, false);
//        return new viewholder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull viewholder holder, int position) {
//        NotificationModel model = list.get(position);
//        Picasso.get().load(model.getPaypic()).into(holder.binding.imageView9);
//
//        String type = model.getType();
//        FirebaseDatabase.getInstance().getReference()
//                .child("Users")
//                .child(model.getNotificationBy())
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        Users users = snapshot.getValue(Users.class);
//                        Picasso.get().load(users.getProfilepic())
//                                .placeholder(R.drawable.lavkushbind);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showNotificationPopup(contextl, model);
//            }
//        });
//
//        holder.binding.openNotification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseDatabase.getInstance().getReference()
//                        .child("notification")
//                        .child(model.getNotificationBy())
//                        .child(model.getNotificationId())
//                        .child("checkopen")
//                        .setValue(true);
//            }
//        });
//
//        Boolean checkOpen = model.isCheckOpen();
//        if (checkOpen == true) {
//            // Do something when checkOpen is true
//        } else {
//            // Do something when checkOpen is false
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    private void showNotificationPopup(Context context, NotificationModel notificationModel) {
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View dialogView = inflater.inflate(R.layout.notification_sample, null);
//        dialogBuilder.setView(dialogView);
//
//        ImageView popupImageView = dialogView.findViewById(R.id.popupImageView);
//        TextView popupNotificationText = dialogView.findViewById(R.id.popupNotificationText);
//
//        Picasso.get().load(notificationModel.getPaypic()).into(popupImageView);
//
//        String type = notificationModel.getType();
//        if (type.equals("follow")) {
//            popupNotificationText.setText("Starting following");
//        } else if (type.equals("post")) {
//            popupNotificationText.setText("After reviewing, your course will be live");
//        } else if (type.equals("edit")) {
//            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("notify").child("massg");
//            databaseReference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
//                        String notificationText = dataSnapshot.getValue(String.class);
//                        popupNotificationText.setText(notificationText);
//                    } else {
//                        popupNotificationText.setText("Data not available");
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    popupNotificationText.setText("Error fetching data");
//                }
//            });
//        } else if (type.equals("buy")) {
//            popupNotificationText.setText("Congratulation you are join to a course");
//        } else if (type.equals("Payment received")) {
//            popupNotificationText.setText("Your payment has been sent to your phone number");
//        } else if (type.equals("blanklearn")) {
//            popupNotificationText.setText("Blanklearn inform to you");
//        } else {
//            // Handle other cases or set a default message
//        }
//
//        AlertDialog alertDialog = dialogBuilder.create();
//        alertDialog.show();
//    }
//
//    public class viewholder extends RecyclerView.ViewHolder {
//        Notification2sampleBinding binding;
//
//        public viewholder(@NonNull View itemView) {
//            super(itemView);
//            binding = Notification2sampleBinding.bind(itemView);
//        }
//    }
//}
//
