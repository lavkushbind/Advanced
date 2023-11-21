package com.example.dark;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

import com.blank_learn.dark.R;
import com.example.home.post2Activity;
import com.example.payment.postmodel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Search_course_adapter extends RecyclerView.Adapter<Search_course_adapter.ViewHolder> {

    private List<postmodel> postList;
    Context context;


    public Search_course_adapter(List<postmodel> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_sample, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        postmodel postmodel = postList.get(position);
        holder.contentTextView.setText(postmodel.getAbout());
        holder.name.setText(postmodel.getPostid());
        Picasso.get()
                .load(postmodel.getPostImage())
                .into(holder.title_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=  new Intent(context, post2Activity.class);
                intent.putExtra("price",postmodel.getPrice());
                intent.putExtra("Link",postmodel.getPaylink());
                intent.putExtra("title",postmodel.getPostdescription());

                intent.putExtra("postid",postmodel .getPostid());
                intent.putExtra("postedBy",postmodel.getPostedBy());
                intent.putExtra("video",postmodel.getPostVideo());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView title_image;
        TextView contentTextView;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title_image = itemView.findViewById(R.id.exoplayerimage);
            contentTextView = itemView.findViewById(R.id.vtitle);
            name= itemView.findViewById(R.id.nameID);
        }
    }
}
