package com.example.home;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.blank_learn.dark.R;
public class ShimmerFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shimmer, container, false);

//        View shimmerCourseCard = view.findViewById(R.id.shimmer_course_card);
//        View shimmerTopTeacher = view.findViewById(R.id.shimmer_top_teacher);
//        View shimmerTrendingCourse = view.findViewById(R.id.shimmer_trending_course);
//
//        Animation animation = AnimationUtils.loadAnimation(requireContext(), R.anim.shimmer_animation);
//        shimmerCourseCard.startAnimation(animation);
//        shimmerTopTeacher.startAnimation(animation);
//        shimmerTrendingCourse.startAnimation(animation);


        return view;
    }
}
