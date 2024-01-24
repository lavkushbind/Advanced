package com.example.chat;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blank_learn.dark.databinding.ActivityChatListBinding;
import com.example.loginandsignup.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.blank_learn.dark.R;
import com.blank_learn.dark.databinding.ActivityChatBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Chat_list_Activity extends AppCompatActivity {
    ActivityChatListBinding binding;
    FirebaseAuth auth;
    ArrayList<Users> list;
    FirebaseDatabase database;
    String chatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatListBinding.inflate(getLayoutInflater());
        list = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
//        chatId = createChatId();  // Assume you have a method to create the chatId

        MemberAdapter memberAdapter = new MemberAdapter(list, getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        binding.RvM.setLayoutManager(linearLayoutManager);
        binding.RvM.setAdapter(memberAdapter);

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Users user = snapshot.getValue(Users.class);
                    binding.textView34.setText(user.getName());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        String currentUserUid = FirebaseAuth.getInstance().getUid();

        if (currentUserUid != null) {
            database.getReference().child("Personal_chat")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            list.clear();

                            for (DataSnapshot chatSnapshot : snapshot.getChildren()) {
                                String chatId = chatSnapshot.getKey();

                                // Check if the currently authenticated user's ID is present in the chat ID
                                if (chatId.contains(currentUserUid)) {
                                    String[] userIds = chatId.split("_");
                                    String memberId = (userIds[0].equals(currentUserUid)) ? userIds[1] : userIds[0];

                                    database.getReference().child("Users").child(memberId)
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                                                    if (userSnapshot.exists()) {
                                                        Users user = userSnapshot.getValue(Users.class);
                                                        user.setUserID(memberId);
                                                        list.add(user);
                                                        memberAdapter.notifyDataSetChanged();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                    // Handle error
                                                }
                                            });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle error
                        }
                    });
        } else {
            // Handle the case where the current user is not authenticated
//            Toast.makeText(ChatAA.this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }



//        database.getReference().child("Personal_chat")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        list.clear();
//
//                        for (DataSnapshot chatSnapshot : snapshot.getChildren()) {
//                            String chatId = chatSnapshot.getKey();
//
//                            String[] userIds = chatId.split("_");
//                            String memberId = userIds[1];
//                            database.getReference().child("Users").child(memberId)
//                                    .addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
//                                            if (userSnapshot.exists()) {
//                                                Users user = userSnapshot.getValue(Users.class);
//                                                user.setUserID(memberId);
//                                                list.add(user);
//                                                memberAdapter.notifyDataSetChanged();
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError error) {
//                                            // Handle error
//                                        }
//                                    });
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        // Handle error
//                    }
//                });


        setContentView(binding.getRoot());

        // Retrieve and display the list of users associated with the personal chat
    }




}
