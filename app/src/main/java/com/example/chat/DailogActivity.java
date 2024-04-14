package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blank_learn.dark.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DailogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailog);


        TextView dialogTitle = findViewById(R.id.deleteDialogTitle);
        TextView dialogMessage = findViewById(R.id.deleteDialogMessage);
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnCancel = findViewById(R.id.btnCancel);

        // Customize dialog content
        dialogTitle.setText("Delete Message");
        dialogMessage.setText("Are you sure you want to delete this message?");

        // Set click listeners for buttons
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMessageFromFirebase();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the dialog activity
            }
        });
    }

    private void deleteMessageFromFirebase() {
        // Implement your Firebase deletion logic here
        // Use the appropriate reference to remove the message from the database
        // For example, assuming you have a reference to your database
        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("messages");
        // Use the message content as a unique identifier or adapt this based on your structure
        messagesRef.child("h").removeValue();

        finish(); // Close the dialog activity after deletion
    }
    }
