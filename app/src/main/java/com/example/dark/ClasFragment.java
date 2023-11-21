package com.example.dark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blank_learn.dark.databinding.FragmentClasBinding;
import com.blank_learn.dark.databinding.NoCourseBinding;
import com.example.home.HomFragment;
import com.example.profile.EditFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClasFragment extends Fragment {
    FirebaseDatabase database;
    ArrayList<clasmodel> list;
    FragmentClasBinding binding;

    public ClasFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        database = FirebaseDatabase.getInstance();

        binding = FragmentClasBinding.inflate(inflater, container, false);
            // Inflate the layout for the case when there are downloads


            list = new ArrayList<>();
            clasadapter adapter = new clasadapter(list, getContext());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
            binding.clasRV.setLayoutManager(layoutManager);
            binding.clasRV.setAdapter(adapter);
            database.getReference().child("AAclass")
                    .child(FirebaseAuth.getInstance().getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                clasmodel clasmodel = dataSnapshot.getValue(clasmodel.class);
                                clasmodel.setClasid(dataSnapshot.getKey());
                                list.add(clasmodel);
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
            return binding.getRoot();
        }

    }