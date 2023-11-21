package com.example.home;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.blank_learn.dark.R;
import com.blank_learn.dark.databinding.FragmentHomeBinding;
import com.example.dark.Search_course_adapter;
import com.example.loginandsignup.Users;
import com.example.payment.postmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
public class HomFragment extends Fragment {
    FragmentHomeBinding binding;
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;
    ArrayList<postmodel> list;
     ArrayList<postmodel> allPosts;
     ArrayList<appmodel> app_list;
     ArrayList<Teacher_model> Teacher_list;
    private int[] imageArray = {R.drawable.img, R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img, R.drawable.img};
    private int currentPage = 0;
    private final long AUTO_SCROLL_DELAY = 3000;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        auth = FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();
        list= new ArrayList<>();
        app_list= new ArrayList<>();
        Teacher_list= new ArrayList<>();
        allPosts = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        String greeting = getGreeting();
//        binding.greetingTextView.setText(greeting);


        ImageSliderAdapter adapter = new ImageSliderAdapter(requireActivity(), imageArray);
        binding.viewPager.setAdapter(adapter);

        addDotsIndicator(0);

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == imageArray.length) {
                    currentPage = 0;
                }
                binding.viewPager.setCurrentItem(currentPage++);
            }
        };
        handler.postDelayed(update, AUTO_SCROLL_DELAY);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        addDotsIndicator(position);
        currentPage = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
});


    Search_course_adapter search_course_adapter = new Search_course_adapter(allPosts);
        binding.postRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.postRV.setAdapter(search_course_adapter);
        database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allPosts.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    postmodel post = postSnapshot.getValue(postmodel.class);
                    allPosts.add(post);
                }
                search_course_adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
//        Search_course_adapter search_course_adapter = new Search_course_adapter(allPosts);
//        binding.PostR.setLayoutManager(new LinearLayoutManager(getActivity()));
//        binding.PostR.setAdapter(search_course_adapter);

//        AppAdapter appAdapter = new AppAdapter(app_list);
//        binding.PostR.setLayoutManager(new LinearLayoutManager(getActivity()));
//         binding.PostR.setAdapter(appAdapter);
//        database.getReference().child("AAproject").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                app_list.clear();
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    appmodel appmodel = postSnapshot.getValue(appmodel.class);
//                    app_list.add(appmodel);
//                }
//                search_course_adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });


//        LinearLayoutManager linearLayoutManage = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
//        Top_teacher_adapter topTeacherAdapter = new Top_teacher_adapter(Teacher_list, getContext());
//        binding.recyclerView2.setLayoutManager(linearLayoutManage);
//        binding.recyclerView2.setAdapter(topTeacherAdapter);
//        linearLayoutManage.setStackFromEnd(true);

        Top_teacher_adapter topTeacherAdapter= new Top_teacher_adapter(Teacher_list,getContext());
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        binding.TopTeacherRv.setLayoutManager(layoutManager2);
        binding.TopTeacherRv.setAdapter(topTeacherAdapter);
        binding.TopTeacherRv.scrollToPosition(topTeacherAdapter.getItemCount() - 1);
        layoutManager2.setStackFromEnd(true);

        database.getReference().child("Teacher").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Teacher_list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Teacher_model teacher_model = dataSnapshot.getValue(Teacher_model.class);
                    teacher_model.setTeacherid(dataSnapshot.getKey());
                    Teacher_list.add(teacher_model);
                }
                topTeacherAdapter.notifyDataSetChanged();
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
                            .placeholder(R.drawable.img)
                            .into(binding.profilepic2);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        homeadapter homeadapter = new homeadapter(list, getContext());
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
                    postmodel postmodel = dataSnapshot.getValue(postmodel.class);
                    postmodel.setPostid(dataSnapshot.getKey());

                    list.add(postmodel);
                }
                homeadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return binding.getRoot();

    }

    private void addDotsIndicator(int position) {
        ImageView[] dots = new ImageView[imageArray.length];
       binding. indicatorLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this.getActivity());
            if (i == position) {
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.active_dot));
            } else {
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.inactive_dot));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            binding.indicatorLayout.addView(dots[i], params);
        }
    }
}





