package com.example.chat;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

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
    String idd;
    final String senderId = auth.getUid();

    private static final int REQUEST_IMAGE_PICK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding= ActivityGroupChatBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
        list = new ArrayList<>();
       database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        intent = getIntent();
        name= intent.getStringExtra("name");
       // Toast.makeText(this, name+"show", Toast.LENGTH_SHORT).show();

        idd= intent.getStringExtra("idd");
       // Toast.makeText(this, idd+"show", Toast.LENGTH_SHORT).show();
//        final String senderId = auth.getUid();
        chatAdapter chatAdapter = new chatAdapter(list, getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.messageAdapter.setLayoutManager(layoutManager);
        binding.messageAdapter.setAdapter(chatAdapter);

        database.getReference().child("Group").child(idd)
                .child("mess")
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
        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
                {
                String message = binding.edtMessage.getText().toString();
                Date date = new Date();
                chatmodel messages = new chatmodel(senderId, message, date.getTime());
                binding.edtMessage.setText("");
                if(message!= null)
                {
                database.getReference().child("Group").child(idd)
                        .child("mess").push()
                        .setValue(messages).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                            }
                                        });}
                 else {
                    Toast.makeText(GroupChat.this, "type", Toast.LENGTH_SHORT).show();                }
            }
        });


        binding.sendimgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,11);
            }
        });

// ...

        


                binding.backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(GroupChat.this, MainActivity.class));

                    }
                });

            }

//    private void openImagePicker() {
//        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, REQUEST_IMAGE_PICK);
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
//            // Get the selected image's URI
//            Uri imageUri = data.getData();
//
//            // Upload the image to Firebase Storage and get the download URL
//            uploadImageToStorage(imageUri);
//        }
//    }
//
//    private void uploadImageToStorage(Uri imageUri) {
//        StorageReference storageRef = storage.getReference().child("images");
//        StorageReference imageRef = storageRef.child("image_" + System.currentTimeMillis());
//
//        // Upload the image to Firebase Storage
//        imageRef.putFile(imageUri)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        // Get the download URL of the uploaded image
//                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                // Now, you have the download URL of the uploaded image.
//                                // You can associate this URL with a chat message and send it to the database.
//                                String imageUrl = uri.toString();
//
//                                // Create a chat message with the image URL
//                                Date date = new Date();
//                                chatmodel message = new chatmodel(senderId, imageUrl, date.getTime());
//
//                                // Save the message to the database
//                                database.getReference().child("Group").child(idd)
//                                        .child("mess").push()
//                                        .setValue(message)
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void unused) {
//                                                // Image message sent successfully
//                                            }
//                                        });
//                            }
//                        });
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Handle the failure to upload the image
//                        Toast.makeText(GroupChat.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

}