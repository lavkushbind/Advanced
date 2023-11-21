package com.example.profile;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.blank_learn.dark.R;
import com.blank_learn.dark.databinding.FragmentEditBinding;
import com.example.home.HomFragment;
import com.example.loginandsignup.Users;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class EditFragment extends Fragment {

    FragmentEditBinding binding;
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;

    public EditFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditBinding.inflate(inflater, container, false);
        binding.imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= new ProfileFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.replace(R.id.container, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });

        database.getReference()
                .child("Users")
                .child(auth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Users user = snapshot.getValue(Users.class);
                            binding.nametext.setText(user.getName());
                            binding.profesiontext.setText(user.getProfesion());
                            binding.biotext.setText(user.getBio());
                            binding.emailtex.setText(user.getEmail());


                        }    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

        binding.Updatepro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String update = binding.profesiontext.getText().toString();
                binding.profesiontext.setText("");
  final StorageReference reference= storage.getReference().child("profesion").child(FirebaseAuth.getInstance().getUid());
                database.getReference().child("Users")
                        .child(auth.getUid()).child("profesion").
                        setValue(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            }
                        });
            }
        });
        binding.Updatename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String update = binding.nametext.getText().toString();
                binding.nametext.setText("");
                database.getReference().child("Users")
                        .child(auth.getUid()).child("name").
                        setValue(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override


                            public void onSuccess(Void unused) {
                            }
                        });
            }
        });

        binding.Updateemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String update = binding.emailtex.getText().toString();
                binding.emailtex.setText("");
                database.getReference().child("Users")
                        .child(auth.getUid()).child("email").
                        setValue(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            }
                        });
            }
        });

        binding.Update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String update = binding.biotext.getText().toString();
                        binding.biotext.setText("");
                        database.getReference().child("Users")
                                .child(auth.getUid()).child("bio").
                                setValue(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                    }
                                });
                    }
                });

        binding.updatef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String update = binding.facebookurl.getText().toString();
                binding.facebookurl.setText("");
                database.getReference().child("Users")
                        .child(auth.getUid()).child("fb").
                        setValue(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            }
                        });
            }
        });


        binding.updateL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String update = binding.linkdinurl.getText().toString();
                binding.linkdinurl.setText("");
                database.getReference().child("Users")
                        .child(auth.getUid()).child("linkdin").
                        setValue(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            }
                        });
            }
        });

        binding.updatei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String update = binding.instagramurl.getText().toString();
                binding.instagramurl.setText("");
                database.getReference().child("Users")
                        .child(auth.getUid()).child("instagram").
                        setValue(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            }
                        });
            }
        });

        binding.updatet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String update = binding.tweeterurl.getText().toString();
                binding.tweeterurl.setText("");
                database.getReference().child("Users")
                        .child(auth.getUid()).child("tweeter").
                        setValue(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            }
                        });
            }
        });
                return binding.getRoot();


            }


        }