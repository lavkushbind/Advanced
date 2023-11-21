package com.example.chat;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blank_learn.dark.databinding.FragmentMemberBinding;
import com.example.loginandsignup.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class Member extends AppCompatActivity {
    FragmentMemberBinding binding;
    FirebaseAuth auth;
    String name ;
    ArrayList<Users> list;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentMemberBinding.inflate(getLayoutInflater());
        list = new ArrayList<>();
        MemberAdapter memberAdapter= new MemberAdapter(list, getApplicationContext());
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,true);
               binding.RvM.setLayoutManager(linearLayoutManager);
               binding.RvM.setAdapter(memberAdapter);
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
      intent = getIntent();
        name= intent.getStringExtra("idd");

        //Toast.makeText(this, name+"show", Toast.LENGTH_SHORT).show();
    database.getReference().child("Group")
            .child(name)
            .child("member")
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Users users = snapshot1.getValue(Users.class);
                        users.setUserID(snapshot1.getKey());
                       // if(!snapshot1.getKey().equals(FirebaseAuth.getInstance().getUid())) {
                            list.add(users);
                      //  }
                    }
                    memberAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

    });
    setContentView(binding.getRoot());
    }
}
