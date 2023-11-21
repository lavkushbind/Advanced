package com.example.chat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blank_learn.dark.R;
import com.blank_learn.dark.databinding.ActivityChatBinding;

import com.example.loginandsignup.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
public class ChatAA extends AppCompatActivity {
    String ReceiversImage;
    String ReceiversUID;
    FirebaseAuth auth;
    ArrayList<chatmodel> list;
    FirebaseDatabase database;
    FirebaseStorage storage;

    ActivityChatBinding binding;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       binding.receiversName.setText(getIntent().getStringExtra("name"));
        list = new ArrayList<>();


        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        final String senderId = auth.getUid();
        intent = getIntent();
        ReceiversUID = intent.getStringExtra("uid");
        chatAdapter chatAdapter = new chatAdapter(list, getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.messageAdapter.setLayoutManager(layoutManager);
        binding.messageAdapter.setAdapter(chatAdapter);
//        layoutManager.scrollToPosition(chatAdapter.getItemCount()-1);
        layoutManager.setReverseLayout(true);
       // layoutManager.setStackFromEnd(true);




        binding.messageAdapter.scrollToPosition(chatAdapter.getItemCount() - 1);

        final String senderRoom = senderId + ReceiversUID;
        final String receiverRoom = ReceiversUID + senderId;
        database.getReference()
                .child("Users")
                .child(ReceiversUID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Users user= snapshot.getValue(Users.class);
                            Picasso.get().load(user.getProfilepic())
                                    .placeholder(R.drawable.profileuser)
                                    .into(binding.profileImage);
                            binding.receiversName.setText(user.getName());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
        database.getReference()
                .child("chats")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            chatmodel model = snapshot1.getValue(chatmodel.class);
                            list.add(model);
                        }
                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });


        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChatAA.this, GroupChat.class));
            }
        });
        binding.attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 25);
            }
        });




        binding.attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("/*");
                startActivityForResult(intent, 25);
            }
        });





        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String message = binding.edtMessage.getText().toString();
                Date date = new Date();

                if (!message.isEmpty()) {
                    chatmodel messages = new chatmodel(senderId, message, date.getTime());
                binding.edtMessage.setText("");
               // String randomKey = database.getReference().push().getKey();


                database.getReference().child(FirebaseAuth.getInstance().getUid()).child("chats").child(senderId).child(ReceiversUID)
                        .child(senderRoom)
                        //   .child("messages")
                        //.child(randomKey)
                        .push()
                        .setValue(messages).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                database.getReference().child("chats").child(ReceiversUID).child(senderId)
                                        .child(receiverRoom)
                                        // .child("messages")
                                        //.child(randomKey)
                                        .push()
                                        .setValue(messages).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });
                            }
                        });
            }

            else {
                    Toast.makeText(ChatAA.this, "", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void sendImageMessage(Uri imageUri) {
        String senderId = auth.getUid();
        Date date = new Date();

        final String senderRoom = senderId + ReceiversUID;
        final String receiverRoom = ReceiversUID + senderId;

        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("chats")
                .child(senderRoom)
                .child(date.getTime() + "");

        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();

//                                chatmodel imageMessage = new chatmodel(senderId, imageUrl, date.getTime(), true);
//
//                                database.getReference().child("Users").child(ReceiversUID).child("chats").child(senderId).child(senderRoom)
//                                        .push().setValue(imageMessage).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void unused) {
//                                                database.getReference().child("Users").child(senderId).child("chats").child(ReceiversUID).child(receiverRoom)
//                                                        .push().setValue(imageMessage).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                            @Override
//                                                            public void onSuccess(Void unused) {
//                                                                Toast.makeText(ChatAA.this, "Image sent", Toast.LENGTH_SHORT).show();
//                                                            }
//                                                        });
//                                            }
//                                        });
                            }
                        });
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 25) {
            if (resultCode == RESULT_OK && data != null) {
                Uri imageUri = data.getData();
                sendImageMessage(imageUri);
            }
        }
    }

}