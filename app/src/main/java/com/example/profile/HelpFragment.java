package com.example.profile;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.blank_learn.dark.R;
import com.blank_learn.dark.databinding.FragmentHelpBinding;
import com.example.loginandsignup.login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HelpFragment extends Fragment {
FragmentHelpBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    public HelpFragment() {
    }

  @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      auth= FirebaseAuth.getInstance();
      database= FirebaseDatabase.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHelpBinding.inflate(inflater, container, false);
        binding.textView35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                AuthCredential credential = EmailAuthProvider.getCredential("user@example.com", "password1234");
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setMessage("Do you want to delete permanently your account ?");
                builder.setTitle("Alert ! ");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // When the user click yes button then app will close
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    user.delete()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Intent intent= new Intent(getActivity(), login.class);
                                                        startActivity(intent);
                                                        Log.d(TAG, "User account deleted.");
                                                    }
                                                }
                                            });

                                }
                            });
                });
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // If user click no then dialog box is canceled.
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                // Show the Alert Dialog box
                alertDialog.show();

                // Get auth credentials from the user for re-authentication. The example below shows
                // email and password credentials but there are multiple possible providers,
                // such as GoogleAuthProvider or FacebookAuthProvider.

                // Prompt the user to re-provide their sign-in credentials

            }
        });
        binding.imageView18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();

            }
        });
        binding.imageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= new ProfileFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.replace(R.id.container, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });

        binding.sendhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String update= binding.helpid.getText().toString();
                binding.helpid.setText("");
                database.getReference().child("Help").child(auth.getUid()).child("name").setValue(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
                });
            }
        });


            return binding.getRoot();

}

    private void sendEmail() {
        // Specify the recipient email address
        String recipientEmail = "blanklearndark@gmail.com";

        // Create an intent with the ACTION_SENDTO action
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

        // Set the data for the intent (mailto: recipientEmail)
        emailIntent.setData(Uri.parse("mailto:" + recipientEmail));

        // Set the subject for the email
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Question from the App User");

        // Set the body of the email (You can pre-fill additional information here)
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear Support Team,\n\n");

        // Check if there is an email client available to handle the intent
        if (emailIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Start the email client
            startActivity(emailIntent);
        }
}}