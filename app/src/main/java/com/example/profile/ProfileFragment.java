package com.example.profile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blank_learn.dark.R;
import com.blank_learn.dark.databinding.FragmentProfileBinding;
import com.example.home.Story_model;
import com.example.payment.PostFragment;
import com.example.payment.postmodel;
import com.example.loginandsignup.Users;
import com.example.loginandsignup.login;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    FirebaseAuth auth;
    FirebaseStorage storage;
    String instagramUrl;
    String facebookUrl;
    Intent intent;
    String linkedinUrl;
    String tweeterUrl;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    ArrayList<postmodel> list;
    Users users;
    private static final int PICK_VIDEO_REQUEST = 1;

    private StorageReference storageReference;
    public ProfileFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        auth = FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        storageReference = FirebaseStorage.getInstance().getReference().child("story");



        binding.Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap screenshot = takeScreenshot(binding.getRoot()); // Use the root view from binding
                String filename = "screenshot_" + System.currentTimeMillis();
                saveScreenshot(screenshot, filename);
                File screenshotFile = new File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename + ".png");
                shareScreenshot(screenshotFile, "https://play.google.com/store/apps/details?id=com.blank_learn.dark&hl=en_IN&gl=US&pli=1"); // Add your link here
            }

            private Bitmap takeScreenshot(View rootView) {
                rootView.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(rootView.getDrawingCache());
                rootView.setDrawingCacheEnabled(false);
                return bitmap;
            }

            private void shareScreenshot(File screenshotFile, String link) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/png");
                Uri imageUri = FileProvider.getUriForFile(requireContext(), requireContext().getPackageName() + ".provider", screenshotFile); // Use screenshotFile
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

                // Add link to the text of the sharing intent
                String shareText = "Experience the future of live learning by downloading the Blanklearn App! Explore a revolutionary approach that redefines the way you learn in real-time: " + link;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

                startActivity(Intent.createChooser(shareIntent, "Share Screenshot"));
            }

            private void saveScreenshot(Bitmap screenshot, String filename) {
                File imagePath = new File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename + ".png");

                try {
                    FileOutputStream fos = new FileOutputStream(imagePath);
                    screenshot.compress(Bitmap.CompressFormat.PNG, 100, fos); // Use screenshot instead of bitmap
                    fos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        list = new ArrayList<>();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = currentUser != null ? currentUser.getUid() : "";

        profile_post_adapter homeadapter = new profile_post_adapter(list, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        binding.profilePost.setLayoutManager(layoutManager);
        binding.profilePost.setAdapter(homeadapter);
        binding.profilePost.scrollToPosition(homeadapter.getItemCount() - 1);
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

                    if (postmodel.getPostedBy().equals(currentUserId)) {
                        list.add(postmodel);
                    }
                }
                homeadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled
            }
        });
binding.profilepic.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        startActivityForResult(intent,52);
    }
});


binding.uploadvid.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), uploadvideo.class);
        startActivity(intent);
    }
});

binding.uploadbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new PostFragment());

        transaction.addToBackStack(null); // Optional: Adds the transaction to the back stack
        transaction.commit();

    }
});


database.getReference()
                    .child("Users")
                    .child(auth.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Users user = snapshot.getValue(Users.class);
                                Picasso.get().load(user.getCoverpic())
                                        .placeholder(R.drawable.backprofile)
                                        .into(binding.coverpic);
                                Picasso.get().load(user.getProfilepic())
                                        .placeholder(R.drawable.userprofile)
                                        .into(binding.profilepic);
                                binding.Username.setText(user.getName());
                                binding.bio.setText(user.getBio());
                                facebookUrl=(user.getFb());
                                linkedinUrl=(user.getLinkedin());
                                instagramUrl =(user.getInstagram());
                                if (instagramUrl != null && !instagramUrl.isEmpty()) {
                                    Picasso.get().load(instagramUrl).into(binding.instagram);
                                } else {
                                    binding.instagram.setVisibility(View.GONE);
                                }

                                if (user.isVerify()) {
                                    binding.imageView23.setVisibility(View.VISIBLE);
                                } else {
                                    binding.imageView23.setVisibility(View.GONE);
                                }
                               tweeterUrl=(user.getTwitter());
                                binding.profesiontext.setText(user.getProfesion());
                                binding.emailtext.setText(user.getEmail());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
            binding.messageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Do you want to logout ?");
                    builder.setTitle("Alert ! ");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(), login.class);
                        startActivity(intent);
                    });
                    builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                        // If user click no then dialog box is canceled.
                        dialog.cancel();
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
//            binding.messageBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    FirebaseAuth.getInstance().signOut();
//                    Intent intent = new Intent(getActivity(), login.class);
//                    startActivity(intent);
//                }
//            });


            binding.editpro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new EditFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container, fragment);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }
            });
            binding.helptext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new HelpFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container, fragment);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }
            });
            binding.cha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, 11);
                }
            });
            binding.instagram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openLink(instagramUrl);

                }
            });

            binding.linked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openLink(linkedinUrl);

                }
            });
            binding.face.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openLink(facebookUrl);

                }
            });
            binding.tweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openLink(tweeterUrl);

                }
            });


            binding.changecp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, 22);
                }
            });
            return binding.getRoot();
        }

    private void openVideoPicker() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO_REQUEST);
    }
    private void openLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }


    @Override
        public boolean
        onOptionsItemSelected(@NonNull MenuItem item)
        {
            super.onOptionsItemSelected(item);
            if (item.getItemId() == R.id.menulogout) {
                FirebaseAuth.getInstance().signOut();
                //senttostart();
            }
            if (item.getItemId() == R.id.delete) {
                //startActivity(new Intent(MainActivity.this, DeleteUser.class));
            }
            return true;
        }
        @Override
        public void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_VIDEO_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
//                Uri videoUri = data.getData();
////                uploadVideoToFirebase(videoUri);
            }

            if (requestCode == 22) {
                if (data.getData() != null) {
                    Uri uri = data.getData();
                    binding.coverpic.setImageURI(uri);
                    final StorageReference reference = storage.getReference().child("coverpic").child(FirebaseAuth.getInstance().getUid());
                    reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getContext(), "Cover pic saved", Toast.LENGTH_SHORT).show();
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    database.getReference().child("Users").child(auth.getUid()).child("coverpic").setValue(uri.toString());
                                }
                            });
                        }
                    });
                }
            }
         else if (requestCode == 52) {
                if (data.getData() != null) {
                    Uri uri = data.getData();

                    final StorageReference storageReference = storage.getReference().child("Story_videos").child(FirebaseAuth.getInstance().getUid());

                    // Create a ProgressDialog
                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Uploading...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    storageReference.putFile(uri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    storageReference.getDownloadUrl()
                                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri downloadUri) {
                                                    // Generate a unique storyid
                                                    String storyId = database.getReference().push().getKey();

                                                    // Create a Story_model object
                                                    Story_model storyModel = new Story_model(FirebaseAuth.getInstance().getUid(), "", downloadUri.toString(), storyId);

                                                    // Save Story_model object to the Realtime Database
                                                    DatabaseReference databaseReference = database.getReference().child("stories").child(storyId);
                                                    databaseReference.setValue(storyModel);

                                                    // Dismiss the ProgressDialog on success
                                                    progressDialog.dismiss();
                                                }
                                            });
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                    // Update the ProgressDialog with the upload progress
                                    float percent = (100 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                    progressDialog.setProgress((int) percent);
                                }
                            });
                }
            }





            else {
                if (data.getData() != null)

//                if (intent != null && intent.getData() != null)
                {
                    Uri uri = data.getData();
                    binding.profilepic.setImageURI(uri);
                    final StorageReference reference = storage.getReference().child("profilepic").child(FirebaseAuth.getInstance().getUid());
                    reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    database.getReference().child("Users").child(auth.getUid()).child("profilepic").setValue(uri.toString());
                                }
                            });
                        }
                    });
                }

            }
    }
}