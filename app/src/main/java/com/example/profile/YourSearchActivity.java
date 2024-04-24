package com.example.profile;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dark.Search_course_adapter;
import com.example.payment.postmodel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import com.blank_learn.dark.R;
import com.blank_learn.dark.databinding.SearchFragmentBinding;

public class YourSearchActivity extends Fragment {
    private SearchFragmentBinding binding;
    private Search_course_adapter search_course_adapter;
    private List<postmodel> allPosts;
    private List<postmodel> filteredPosts;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SearchFragmentBinding.inflate(inflater, container, false);

        setHasOptionsMenu(true);

        initializeRecyclerView();
        initializeFirebase();

        return binding.getRoot();
    }

    private void initializeRecyclerView() {
        allPosts = new ArrayList<>();
        filteredPosts = new ArrayList<>();
        search_course_adapter = new Search_course_adapter((ArrayList<postmodel>) filteredPosts, getContext());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(search_course_adapter);
    }

    private void initializeFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("posts");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allPosts.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    postmodel post = postSnapshot.getValue(postmodel.class);
                    if (post != null) {
                        post.setPostid(postSnapshot.getKey());
                        allPosts.add(post);
                    }
                }
                filterAndDisplayPosts("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });

        binding.searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterAndDisplayPosts(newText);
                return true;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.message_options_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_edit_text);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search posts");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterAndDisplayPosts(newText);
                return true;
            }
        });
    }

    private void filterAndDisplayPosts(String query) {
        filteredPosts.clear();
        if (TextUtils.isEmpty(query)) {
            filteredPosts.addAll(allPosts);
        } else {
            for (postmodel post : allPosts) {
                if (postContainsQuery(post, query)) {
                    filteredPosts.add(post);
                }
            }
        }
        search_course_adapter.notifyDataSetChanged();
    }
    private boolean postContainsQuery(postmodel post, String query) {
        return (post.getAbout() != null && post.getAbout().toLowerCase().contains(query.toLowerCase())) ||
                (post.getPostdescription() != null && post.getPostdescription().toLowerCase().contains(query.toLowerCase())) ||
                (post.getStandred() != null && post.getStandred().toLowerCase().contains(query.toLowerCase())) ||
                (post.getLanguage() != null && post.getLanguage().toLowerCase().contains(query.toLowerCase())) ||
                (post.getPostedBy() != null && post.getPostedBy().toLowerCase().contains(query.toLowerCase())) ||
                (post.getTime() != null && post.getTime().toLowerCase().contains(query.toLowerCase())) ||
                (post.getPrice() != null && post.getPrice().toLowerCase().contains(query.toLowerCase())) ||
                (post.getDuration() != null && post.getDuration().toLowerCase().contains(query.toLowerCase())) ||
                (post.getPhone() != null && post.getPhone().toLowerCase().contains(query.toLowerCase())) ||
                (post.getPosttype() != null && post.getPosttype().toLowerCase().contains(query.toLowerCase())) ||
                (post.getPaylink() != null && post.getPaylink().toLowerCase().contains(query.toLowerCase()));
    }



}

