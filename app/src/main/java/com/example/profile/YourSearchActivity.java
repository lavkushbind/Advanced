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
import com.blank_learn.dark.R;
import com.blank_learn.dark.databinding.FragmentSearchBinding;
import com.example.dark.Search_course_adapter;
import com.example.payment.postmodel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
public class YourSearchActivity extends Fragment {
    FragmentSearchBinding binding;
    private Search_course_adapter search_course_adapter;
    private List<postmodel> allPosts;
    private List<postmodel> filteredPosts;
    private DatabaseReference databaseReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.search_fragment, container, false);
        binding = FragmentSearchBinding.inflate(inflater, container, false);

//        recyclerView = view.findViewById(R.id.recyclerView);
//        searchView= view.findViewById(R.id.searchView);
        allPosts = new ArrayList<>();
        filteredPosts = new ArrayList<>();
        search_course_adapter = new Search_course_adapter(filteredPosts);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(search_course_adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("posts");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allPosts.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    postmodel post = postSnapshot.getValue(postmodel.class);
                    allPosts.add(post);
                }
                filterAndDisplayPosts("");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
      binding.searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
          @Override
          public boolean onQueryTextSubmit(String query) {
              return false;
          }

          @Override
          public boolean onQueryTextChange(String newText) {
              filterPosts(newText);

              return true;
          }
          private void filterPosts(String query) {
              List<postmodel> filteredPosts = new ArrayList<>();

              for (postmodel post : allPosts) {
                  if (post.getAbout().toLowerCase().contains(query.toLowerCase()) ||
                          post.getPostdescription().toLowerCase().contains(query.toLowerCase()) ||
                          post.getStandred().toLowerCase().contains(query.toLowerCase()) ||
                          post.getLanguage().toLowerCase().contains(query.toLowerCase())

                  ) {
                      filteredPosts.add(post);
                  }
              }
              search_course_adapter = new Search_course_adapter(filteredPosts);
              binding.recyclerView.setAdapter(search_course_adapter);
          }

      });

//        binding.searchView.setOnQueryTextListener(new android.widget.SearchView.SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                filterPosts(newText);
//                return true;
//            }
//            private void filterPosts(String query) {
//                List<postmodel> filteredPosts = new ArrayList<>();
//
//                for (postmodel post : allPosts) {
//                    if (post.getAbout().toLowerCase().contains(query.toLowerCase()) ||
//                            post.getPostdescription().toLowerCase().contains(query.toLowerCase()) ||
//                            post.getStandred().toLowerCase().contains(query.toLowerCase()) ||
//                            post.getLanguage().toLowerCase().contains(query.toLowerCase())
//
//                    ) {
//                        filteredPosts.add(post);
//                    }
//                }
//                search_course_adapter = new Search_course_adapter(filteredPosts);
//                binding.recyclerView.setAdapter(search_course_adapter);
//            }
//        });
        setHasOptionsMenu(true);
        return binding.getRoot();
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

    }private void filterAndDisplayPosts(String query) {
        filteredPosts.clear();
        if (TextUtils.isEmpty(query)) {
            filteredPosts.addAll(allPosts);
        } else {
            for (postmodel post : allPosts) {
                if (post.getAbout().toLowerCase().contains(query.toLowerCase()) ||
                        post.getAbout().toLowerCase().contains(query.toLowerCase())) {
                    filteredPosts.add(post);
                }
            }
        }
        search_course_adapter.notifyDataSetChanged();


    }
}

