package com.example.home;

import static android.os.Build.VERSION_CODES.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.blank_learn.dark.R;
import com.blank_learn.dark.databinding.TopteaBinding;
import com.example.chat.ChatAA;
import com.example.loginandsignup.Users;
import com.example.profile.ProActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class useradapter extends RecyclerView.Adapter<useradapter.viewholder> {
    ArrayList<Users> list;
    Context context;
    public useradapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(com.blank_learn.dark.R.layout.toptea,parent,false);
        return  new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Users users= list.get(position) ;
        holder.binding.tname.setText(users.getName());
        Picasso.get()
                .load(users.getProfilepic())
                .into(holder.binding.profilepic);
        holder.binding.textView76.setText(String.valueOf(users.getProfesion()));
        holder.binding.price.setText(String.valueOf(users.getCharge()));
        if (users.isVerify()) {
            holder.binding.imageView25.setVisibility(View.VISIBLE);
        } else {
            holder.binding.imageView25.setVisibility(View.GONE);
        }
        holder.binding.textView77.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=  new Intent(context, ChatAA.class);

                intent.putExtra("name", users.getUserID());


                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


        holder.binding.imageView28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Bitmap screenshot = takeScreenshot(holder.binding.getRoot());
                String filename = "screenshot_" + System.currentTimeMillis();
                File screenshotFile = saveScreenshot(context, screenshot, filename);
                if (screenshotFile != null) {
                    shareScreenshot(context, screenshotFile, "https://play.google.com/store/apps/details?id=com.blank_learn.dark&hl=en_IN&gl=US&pli=1");
                }
            }

            private Bitmap takeScreenshot(View rootView) {
                rootView.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(rootView.getDrawingCache());
                rootView.setDrawingCacheEnabled(false);
                return bitmap;
            }

            private File saveScreenshot(Context context, Bitmap screenshot, String filename) {
                File imagePath = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename + ".png");
                try {
                    FileOutputStream fos = new FileOutputStream(imagePath);
                    screenshot.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                    return imagePath;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            private void shareScreenshot(Context context, File screenshotFile, String link) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/png");
                String about= users.getBio();
                Uri imageUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", screenshotFile);
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

                // Add link to the text of the sharing intent
                String shareText =  "Unlock your potential with personalized 1-on-1 live classes that propel you to the next level in your learning journey!" + link;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

                context.startActivity(Intent.createChooser(shareIntent, "Share Screenshot"));
            }
        });


        holder.binding.profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=  new Intent(context, ProActivity.class);

                intent.putExtra("name", users.getUserID());


                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public  class viewholder extends RecyclerView.ViewHolder{
       TopteaBinding  binding;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding = TopteaBinding.bind(itemView);
        }
    }

}
