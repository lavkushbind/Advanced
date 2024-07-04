package com.example.profile;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blank_learn.dark.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.UUID;

public class uploadvideo extends AppCompatActivity {

    private static final int PICK_VIDEO_REQUEST = 1;

    private Button uploadButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadvideo);

        uploadButton = findViewById(R.id.upload_btn);
        progressBar = findViewById(R.id.progress_bar);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri videoUri = data.getData();
            uploadVideoToFirebase(videoUri);
        }
    }

    private void uploadVideoToFirebase(Uri videoUri) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        String videoName = UUID.randomUUID().toString();
        StorageReference videoRef = storageRef.child("videos/" + videoName);

        progressBar.setVisibility(View.VISIBLE);

        videoRef.putFile(videoUri)
                .addOnSuccessListener(taskSnapshot -> videoRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String videoUrl = uri.toString();
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("storyid");

//                    String videoId = databaseRef.push().getKey();
                    databaseRef.setValue(videoUrl);
                    Toast.makeText(this, "Video Uploaded", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    // Handle success UI updates
                }))
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    // Handle failure UI updates
                })
                .addOnProgressListener(taskSnapshot -> {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressBar.setProgress((int) progress);
                });
    }
}
