package com.example.dark;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.blank_learn.dark.R;
import com.blank_learn.dark.databinding.ActivityOneclassBinding;
import com.blank_learn.dark.databinding.ActivityStoryBinding;
import com.example.chat.ChatAA;
import com.example.home.Story_model;
import com.example.loginandsignup.Users;
import com.example.profile.ProActivity;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.rpc.Help;
import com.squareup.picasso.Picasso;


public class oneclassActivity extends AppCompatActivity {
    ActivityOneclassBinding binding;
    FirebaseAuth auth;
    FirebaseStorage storage;
    private boolean isPlayerInitialized = false;

    FirebaseDatabase database;
    private DatabaseReference databaseReference;

    Intent intent;
    String postId;
    String name;
    private SimpleExoPlayer exoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOneclassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();
        postId = intent.getStringExtra("Link");
        name = intent.getStringExtra("name");
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Users");

        exoPlayer = new SimpleExoPlayer.Builder(this).build();
        fetchVideoUrlFromFirebase();
        binding.exoplayerimage.setPlayer(exoPlayer);


        binding.textView78.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (name != null) {
                    Intent intent = new Intent(com.example.dark.oneclassActivity.this, ChatAA.class);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
            }
        });

        binding.profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (name != null) {
                    Intent intent = new Intent(com.example.dark.oneclassActivity.this, ProActivity.class);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
            }
        });

        binding.imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareVideo();
            }
        });
    }




    private void fetchVideoUrlFromFirebase() {
        databaseReference.child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Users storyModel = dataSnapshot.getValue(Users.class);
                    binding.textView53.setText(storyModel.getName());
                    Picasso.get().load(storyModel.getProfilepic())
                            .placeholder(R.drawable.userprofile)
                            .into(binding.profilepic);
                    if (storyModel != null && storyModel.getStoryid() != null) {
//                        Toast.makeText(oneclassActivity.this, storyModel.getStoryid(), Toast.LENGTH_SHORT).show();
                        MediaItem mediaItem = MediaItem.fromUri(postId);
                        exoPlayer.setMediaItem(mediaItem);
                    } else {
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void shareVideo() {
        MediaItem currentMediaItem = exoPlayer.getCurrentMediaItem();
        if (currentMediaItem != null) {
            Uri videoUri = currentMediaItem.playbackProperties.uri;

            String shareText = "Check out this video: " + videoUri.toString();

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("video/*");  // Set MIME type to video/*
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);  // Add the text with the video link

            // Check if the video file type is supported before attaching
            if (isVideoFileSupported(videoUri)) {
                shareIntent.putExtra(Intent.EXTRA_STREAM, videoUri);  // Attach the video file
            } else {
                // Provide a fallback or show a message that the attachment is unsupported
                shareText += "\n\nDownload the video from: " + videoUri.toString();
            }

            startActivity(Intent.createChooser(shareIntent, "Share Video"));
        }
    }

    private boolean isVideoFileSupported(Uri videoUri) {
        ContentResolver contentResolver = getContentResolver();
        String type = contentResolver.getType(videoUri);

        // Check if the type is video/*
        if (type != null && type.startsWith("video/")) {
            // You can further check the file extension if needed
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(videoUri.toString());
            if (fileExtension != null) {
                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
                // Check if the mimeType is supported
                if (mimeType != null && mimeType.startsWith("video/")) {
                    return true;
                }
            }
        }

        // Video file type is not supported
        return false;
    }




    //    private void initializePlayer(String videoUrl) {
//        if (exoPlayer == null) {
//            exoPlayer = new SimpleExoPlayer.Builder(this).build();
//            isPlayerInitialized = true;
//        }
//
//        MediaItem mediaItem = MediaItem.fromUri(videoUrl);
//        exoPlayer.setMediaItem(mediaItem);
//        exoPlayer.prepare();
//        exoPlayer.setPlayWhenReady(true);
//
//        binding.exoplayerimage.setPlayer(exoPlayer);
//    }
    @Override
    protected void onStart() {
        super.onStart();
        if (isPlayerInitialized) {
            exoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isPlayerInitialized) {
            exoPlayer.setPlayWhenReady(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null) {
            exoPlayer.release();
        }
    }
}