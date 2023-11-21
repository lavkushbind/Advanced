package com.example.home;
import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blank_learn.dark.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity2 extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 456;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize Firebase Storage and Database references
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Request permission to read external storage
        requestStoragePermission();

        // Button to trigger gallery upload
        Button uploadButton = findViewById(R.id.button4);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadGalleryToFirebase();
            }
        });
    }

    private void requestStoragePermission() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(this, permissions[0]) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }
    }

    private void uploadGalleryToFirebase() {
        // Get all images from the gallery
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                Uri fileUri = Uri.parse("file://" + filePath);
                String fileName = fileUri.getLastPathSegment();

                StorageReference fileRef = storageReference.child("uploads/" + fileName);

                // Get the actual file path
                String realPath = getRealPathFromURI(fileUri);

                // Create a file object
                File file = new File(realPath);

                // Upload the file
                UploadTask uploadTask = fileRef.putFile(Uri.fromFile(file));
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw Objects.requireNonNull(task.getException());
                        }

                        // Continue with the task to get the download URL
                        return fileRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        if (downloadUri != null) {
                            String downloadUrl = downloadUri.toString();

                            // File uploaded successfully, store metadata in Firebase Database
                            Map<String, Object> fileData = new HashMap<>();
                            fileData.put("fileName", fileName);
                            fileData.put("downloadUrl", downloadUrl);

                            databaseReference.child("uploadedFiles").push().setValue(fileData)
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d("UploadFile", "File metadata stored in database.");
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("UploadFile", "Failed to store file metadata: " + e.getMessage());
                                    });
                        }
                    } else {
                        Log.e("UploadFile", "File upload failed: " + task.getException().getMessage());
                    }
                });
            }
            cursor.close();
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String result = cursor.getString(column_index);
            cursor.close();
            return result;
        }
    }
}
