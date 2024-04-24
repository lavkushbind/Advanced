package com.example.home;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blank_learn.dark.databinding.FragmentHomeBinding;
import com.example.chat.Chat_list_Activity;
import com.example.dark.Search_course_adapter;
import com.example.loginandsignup.Users;
import com.example.loginandsignup.login;
import com.example.loginandsignup.slideActivity;
import com.example.payment.PostFragment;
import com.example.payment.SliderAdapter;
import com.example.payment.SliderData;
import com.example.payment.postmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
public class HomFragment extends Fragment {
    FragmentHomeBinding binding;
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;
    ArrayList<postmodel> list;
     ArrayList<postmodel> allPosts;
     ArrayList<appmodel> app_list;
    ArrayList<Story_model> story_list;

    ArrayList<Teacher_model> Teacher_list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        auth = FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();
        list= new ArrayList<>();
        story_list= new ArrayList<>();
        app_list= new ArrayList<>();
        Teacher_list= new ArrayList<>();
        allPosts = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        database.getReference().child("Poster").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String imageUrl = snapshot.getValue(String.class);

                    Picasso.get().load(imageUrl).into(binding.poster);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        database.getReference().child("Poster").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String imageUrl = snapshot.getValue(String.class);

                    Picasso.get().load(imageUrl).into(binding.studentPost);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        homeadapter homeadapter3 = new homeadapter(list, getContext());
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        binding.postRV.setLayoutManager(layoutManager2);
        binding.postRV.setAdapter(homeadapter3);
       binding.postRV.scrollToPosition(homeadapter3.getItemCount() - 1);
         layoutManager2.setStackFromEnd(true);
         binding.poster.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getActivity(), student_slider.class);
                 startActivity(intent);
             }
         });
        binding.studentPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), student_slider.class);
//                startActivity(intent);
            }
        });
        database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    postmodel postmodel = dataSnapshot.getValue(postmodel.class);
                    postmodel.setPostid(dataSnapshot.getKey());
                    if (postmodel.getPostVideo() != null) {
                        list.add(postmodel);
                    }

//                    list.add(postmodel);
                }
                homeadapter3.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        homeadapter homeadapter2 = new homeadapter(list, getContext());
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        binding.TopTeacherRv.setLayoutManager(layoutManager4);
       binding.TopTeacherRv .setAdapter(homeadapter2);
        binding.TopTeacherRv.scrollToPosition(homeadapter2.getItemCount() - 1);
        layoutManager4.setStackFromEnd(true);

        database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    postmodel postmodel = dataSnapshot.getValue(postmodel.class);
                    postmodel.setPostid(dataSnapshot.getKey());
                    list.add(postmodel);
                }
                Collections.shuffle(list);

                homeadapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("App").child("top courses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String value = snapshot.getValue(String.class);

//                    binding.textView11.setText(value);
                }
            }

            @Override
            public void onCancelled(@NonNull     DatabaseError error) {

            }
        });
        database.getReference().child("App").child("new upload").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String value = snapshot.getValue(String.class);

//                    binding.textView18.setText(value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Users user = snapshot.getValue(Users.class);
                    binding.textView25.setText(user.getName());
                    Picasso.get().load(user.getProfilepic())
                            .into(binding.profilepic2);
//                    binding.profilepic2.setVisibility(View.VISIBLE);

                }
                else {
                    binding.profilepic2.setVisibility(View.GONE);
                    Log.d("ProfilePic", "Snapshot does not exist");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        binding.imageView15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Chat_list_Activity.class);
                startActivity(intent);

            }
        });


        Search_course_adapter homeadapter = new Search_course_adapter(list, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        binding.postnow.setLayoutManager(layoutManager);
        binding.postnow.setAdapter(homeadapter);
        binding.postnow.scrollToPosition(homeadapter.getItemCount() - 1);
        layoutManager.setStackFromEnd(true);

        database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("postVideo").getValue() == null) {
                        continue;
                    }
                    postmodel postmodel = dataSnapshot.getValue(postmodel.class);
                    postmodel.setPostid(dataSnapshot.getKey());
                        list.add(postmodel);
                    }
                Collections.shuffle(list);

                homeadapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        StoryAdapter storyAdapter = new StoryAdapter(story_list, getContext());
        LinearLayoutManager layoutManagers = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        binding.StoryRV.setLayoutManager(layoutManagers);
        binding.StoryRV.setAdapter(storyAdapter);
        binding.StoryRV.scrollToPosition(storyAdapter.getItemCount() - 1);
        layoutManagers.setStackFromEnd(true);
        database.getReference().child("stories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                story_list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Story_model storyModel = dataSnapshot.getValue(Story_model.class);
                    storyModel.setStoryid(dataSnapshot.getKey());
                    story_list.add(storyModel);
                }
                Collections.shuffle(list);

                storyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatabaseReference sliderReference = database.getReference().child("Slider");

        sliderReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getActivity() != null && isAdded()) {  // Check if fragment is still attached

                    if (snapshot.exists()) {
                        String url1 = snapshot.child("url1").getValue(String.class);
                        String url2 = snapshot.child("url2").getValue(String.class);
                        String url3 = snapshot.child("url3").getValue(String.class);

                        if (url1 != null && url2 != null && url3 != null) {
                            ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
                            sliderDataArrayList.add(new SliderData(url1));
                            sliderDataArrayList.add(new SliderData(url2));
                            sliderDataArrayList.add(new SliderData(url3));

                            SliderAdapter adapter = new SliderAdapter(requireContext(), sliderDataArrayList);
                            binding.slider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
                            binding.slider.setSliderAdapter(adapter);
                            binding.slider.setScrollTimeInSec(3);
                            binding.slider.setAutoCycle(true);
                            binding.slider.startAutoCycle();
                        } else {
                        }
                    } else {
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}





