package com.example.home;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.blank_learn.dark.R;
import com.blank_learn.dark.databinding.VideoBinding;
import com.example.chat.ChatAA;
import com.example.chat.GroupChat;
import com.example.loginandsignup.Users;
import com.example.payment.postmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class homeadapter extends RecyclerView.Adapter<homeadapter.viewHolder>  {
    ArrayList<postmodel> list;
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;
    Context context;
   public homeadapter(ArrayList<postmodel> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.video,parent,false);
        auth = FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();

        return new viewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        {
            postmodel postmodel= list.get(position);
            Picasso.get()
                    .load(postmodel.getPostImage())
                    .into(holder.binding.exoplayerimage);
            holder.binding.Seats.setText(String.valueOf(postmodel.getSeats()));

//            holder.binding.price2.setText(String.valueOf(postmodel.getPrice2()));
//            holder.binding.priceFirst.setText(postmodel.getPrice());

            holder.binding.price2.setText(formatPriceAccordingToLocale(postmodel.getPrice2()));
            holder.binding.priceFirst.setText(formatPriceAccordingToLocale(Double.parseDouble(postmodel.getPrice())));



            holder.binding.textView57.setText(postmodel.getAbout());
            holder.binding.vtitle.setText(postmodel.getPostdescription());

            holder.binding.vtitle.setText(postmodel.getPostdescription());

            FirebaseDatabase.getInstance()
                    .getReference().child("Users")
                    .child(postmodel.getPostedBy())
                    .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Users user = snapshot.getValue(Users.class);

                    if (user != null)     {
                        holder.binding.nameID.setText(user.getName());
                        if (user.isVerify()) {
                            holder.binding.imageView26.setVisibility(View.VISIBLE);
                        } else {
                            holder.binding.imageView26.setVisibility(View.GONE);
                        }
                    }else {

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            holder.binding.imageView29.setOnClickListener(new View.OnClickListener() {
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
                    String about= postmodel.getAbout();
                    shareIntent.setType("image/png");
                    Uri imageUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", screenshotFile);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

                    // Add link to the text of the sharing intent
                    String shareText = link+  "   Unlock your potential with personalized live classes in small groups, propelling you to the next level in your learning journey!                          "   + about   ;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

                    context.startActivity(Intent.createChooser(shareIntent, "Share Screenshot"));
                }
            });



            holder.binding.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=  new Intent(context,post2Activity.class);
                    intent.putExtra("price",postmodel.getPrice());

                    intent.putExtra("price2",postmodel.getPrice2());
                    intent.putExtra("seats",postmodel.getSeats());
                    intent.putExtra("title",postmodel.getPostdescription());
                    intent.putExtra("postid",postmodel .getPostid());
                    intent.putExtra("postPic",postmodel.getPostImage());
                    intent.putExtra("postedBy",postmodel.getPostedBy());
                    intent.putExtra("video",postmodel.getPostVideo());

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=  new Intent(context,post2Activity.class);
                    intent.putExtra("price",postmodel.getPrice());
//                    intent.putExtra("price2",postmodel.getPrice2());
//                    intent.putExtra("seats",postmodel.getSeats());

                    intent.putExtra("title",postmodel.getPostdescription());
                    intent.putExtra("postid",postmodel .getPostid());
                    intent.putExtra("postPic",postmodel.getPostImage());
                    intent.putExtra("postedBy",postmodel.getPostedBy());
                    intent.putExtra("video",postmodel.getPostVideo());

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewHolder extends RecyclerView.ViewHolder {
        VideoBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = VideoBinding.bind(itemView);
        }
    }

    private String formatPriceAccordingToLocale(double price) {
        Locale indianLocale = new Locale("en", "IN");
        NumberFormat format = NumberFormat.getCurrencyInstance(indianLocale);
        return format.format(price);
    }
    }













