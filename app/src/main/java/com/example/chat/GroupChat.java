//package com.example.chat;
//import android.content.ClipboardManager;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.ContextMenu;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import com.blank_learn.dark.databinding.ActivityGroupChatBinding;
//import com.example.home.MainActivity;
//
//import com.example.payment.postmodel;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//import java.util.Date;
//public class GroupChat extends AppCompatActivity {
//ActivityGroupChatBinding binding;
//    FirebaseAuth auth;
//    ArrayList<chatmodel> list;
//    FirebaseDatabase database;
//    FirebaseStorage storage;
//    String name;
//    Intent intent;
//    String idd;
//private static final int REQUEST_IMAGE_PICK = 1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    binding= ActivityGroupChatBinding.inflate(getLayoutInflater());
//    setContentView(binding.getRoot());
//        list = new ArrayList<>();
//       database = FirebaseDatabase.getInstance();
//        storage = FirebaseStorage.getInstance();
//        auth = FirebaseAuth.getInstance();
//        intent = getIntent();
//        name= intent.getStringExtra("name");
//        idd= intent.getStringExtra("idd");
//        chatAdapter chatAdapter = new chatAdapter(list, getApplicationContext());
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        binding.messageAdapter.setLayoutManager(layoutManager);
//        binding.messageAdapter.setAdapter(chatAdapter);
//
//        database.getReference().child("Group").child(idd)
//                .child("mess")
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        list.clear();
//                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                            chatmodel model = snapshot1.getValue(chatmodel.class);
//                            list.add(model);
//                        }
//                        chatAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//
//                });
//        binding.backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(GroupChat.this, MainActivity.class));
//
//            }
//        });
//        binding.sendimgBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent= new Intent();
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(intent,11);
//            }
//        });
//        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                String message = binding.edtMessage.getText().toString();
//                Date date = new Date();
//                String senderId = auth.getUid();
//                chatmodel messages = new chatmodel(senderId, message, date.getTime());
//                binding.edtMessage.setText("");
//                if(message!= null)
//                {
//                    database.getReference().child("Group").child(idd)
//                            .child("mess").push()
//                            .setValue(messages).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                }
//                            });}
//                else {
//                    Toast.makeText(GroupChat.this, "type", Toast.LENGTH_SHORT).show();                }
//            }
//        });
//            }
//
//    private void openImagePicker() {
//        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, REQUEST_IMAGE_PICK);
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
//            Uri imageUri = data.getData();
//            uploadImageToStorage(imageUri);
//        }
//    }
//
//    private void uploadImageToStorage(Uri imageUri) {
//        StorageReference storageRef = storage.getReference().child("images");
//        StorageReference imageRef = storageRef.child("image_" + System.currentTimeMillis());
//        imageRef.putFile(imageUri)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                String imageUrl = uri.toString();
//                                final String senderId = auth.getUid();
//                                Date date = new Date();
//                                chatmodel message = new chatmodel(senderId, imageUrl, date.getTime());
//                                database.getReference().child("Group").child(idd)
//                                        .child("mess").push()
//                                        .setValue(message)
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void unused) {
//                                            }
//                                        });
//                            }
//                        });
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(GroupChat.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//}
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

import com.blank_learn.dark.databinding.ActivityGroupChatBinding;
import com.example.home.MainActivity;
import com.example.payment.postmodel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;

public class GroupChat extends AppCompatActivity {
    ActivityGroupChatBinding binding;
    FirebaseAuth auth;
    ArrayList<chatmodel> list;
    FirebaseDatabase database;
    FirebaseStorage storage;
    String name;
    Intent intent;
    String Postid;
    private chatAdapter chatAdapter;

    private static final int REQUEST_IMAGE_PICK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        intent = getIntent();
        name = intent.getStringExtra("name");
        Postid = intent.getStringExtra("Postid");
        chatAdapter = new chatAdapter(list, getApplicationContext());


//        chatAdapter chatAdapter = new chatAdapter(list, getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.messageAdapter.setLayoutManager(layoutManager);
        binding.messageAdapter.setAdapter(chatAdapter);

        // Load group messages from Firebase
        loadGroupMessages();

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GroupChat.this, MainActivity.class));
            }
        });

        binding.sendimgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });

        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }


    private void scrollToBottom() {
        if (binding.messageAdapter.getAdapter() != null) {
            int itemCount = binding.messageAdapter.getAdapter().getItemCount();
            if (itemCount > 0) {
                binding.messageAdapter.smoothScrollToPosition(itemCount - 1);
            }
        }
    }


    private void loadGroupMessages() {
        database.getReference().child("Group").child(Postid)
                .child("mess")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            chatmodel model = snapshot1.getValue(chatmodel.class);
                            list.add(model);
                        }
                        updateChatAdapter();
                        scrollToBottom();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(GroupChat.this, "Failed to load messages", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateChatAdapter() {
           runOnUiThread(new Runnable() {
        @Override
        public void run() {
            chatAdapter.notifyDataSetChanged();
        }
    });
}

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    private void sendMessage() {
        String message = binding.edtMessage.getText().toString().trim();
        Date date = new Date();
        String senderId = auth.getUid();

        if (!message.isEmpty()) {
            chatmodel messages = new chatmodel(senderId, message, date.getTime());
            database.getReference().child("Group").child(Postid)
                    .child("mess").push()
                    .setValue(messages)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            // Message sent successfully
                        }
                    });
        } else {
            Toast.makeText(GroupChat.this, "Type a message", Toast.LENGTH_SHORT).show();
        }

        // Clear the message input field
        binding.edtMessage.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            uploadImageToStorage(imageUri);
        }
    }

    private void uploadImageToStorage(Uri imageUri) {
        StorageReference storageRef = storage.getReference().child("images");
        StorageReference imageRef = storageRef.child("image_" + System.currentTimeMillis());
        imageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();
                                final String senderId = auth.getUid();
                                Date date = new Date();
                                chatmodel message = new chatmodel(senderId, imageUrl, date.getTime());
                                database.getReference().child("Group").child(Postid)
                                        .child("mess").push()
                                        .setValue(message)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                // Image uploaded and URL added to messages
                                            }
                                        });
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(GroupChat.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
