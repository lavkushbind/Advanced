package com.example.profile;
import static android.content.ContentValues.TAG;
import static com.google.android.material.color.utilities.MaterialDynamicColors.error;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blank_learn.dark.R;
import com.blank_learn.dark.databinding.FragmentProfileBinding;
import com.example.home.profile_homeadapter;
import com.example.payment.PostFragment;
import com.example.home.homeadapter;
import com.example.payment.postmodel;
import com.example.loginandsignup.Users;
import com.example.loginandsignup.login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
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
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    ArrayList<postmodel> list;
    Users users;

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

            binding.fb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bitmap screenshot = takeScreenshot(binding.getRoot()); // Use the root view from binding
                    String filename = "screenshot_" + System.currentTimeMillis();
                    saveScreenshot(screenshot, filename);
                    File screenshotFile = new File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename + ".png");
                    shareScreenshot(screenshotFile);
                }

                private Bitmap takeScreenshot(View rootView) {
                    rootView.setDrawingCacheEnabled(true); // Use rootView instead of view
                    Bitmap bitmap = Bitmap.createBitmap(rootView.getDrawingCache()); // Use rootView instead of view
                    rootView.setDrawingCacheEnabled(false); // Use rootView instead of view
                    return bitmap;
                }
//            private void shareScreenshot(File screenshotFile) {
//                String linkUrl = "https://play.google.com/store/apps/details?id=com.blank_learn.dark&hl=en_IN&gl=US";
//                String htmlContent = "<a href=\"" + linkUrl + "\">Click here to visit the link</a>";
//
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("text/html");
//                shareIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(htmlContent));
//                shareIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(requireContext(), requireContext().getPackageName() + ".provider", screenshotFile));
//                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                startActivity(Intent.createChooser(shareIntent, "Share Screenshot"));
//            }

                private void shareScreenshot(File screenshotFile) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("image/png");
                    Uri imageUri = FileProvider.getUriForFile(requireContext(), requireContext().getPackageName() + ".provider", screenshotFile); // Use screenshotFile
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                    startActivity(Intent.createChooser(shareIntent, "Share Screenshot"));
                }

                private void saveScreenshot(Bitmap screenshot, String filename) {
                    File imagePath = new File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename + ".png");

                    try {
                        FileOutputStream fos = new FileOutputStream(imagePath);
                        screenshot.compress(Bitmap.CompressFormat.PNG, 100, fos); // Use screenshot instead of bitmap
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            list = new ArrayList<>();
            profile_homeadapter profile_homeadapter = new profile_homeadapter(list, getContext());
            LinearLayoutManager linearLayoutManage = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
            binding.recyclerProfile.setLayoutManager(linearLayoutManage);
            binding.recyclerProfile.setAdapter(profile_homeadapter);
            linearLayoutManage.setStackFromEnd(true);



        database.getReference().child("posts").orderByChild(auth.getUid()).equalTo(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    postmodel postmodel = dataSnapshot.getValue(postmodel.class);
                    postmodel.setPostid(dataSnapshot.getKey());

                    list.add(postmodel);
                }
                profile_homeadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                                        .placeholder(R.drawable.lavkushbind)
                                        .into(binding.coverpic);
                                Picasso.get().load(user.getProfilepic())
                                        .placeholder(R.drawable.profileuser)
                                        .into(binding.profilepic);
                                binding.Username.setText(user.getName());
                                binding.bio.setText(user.getBio());
                                binding.fbtext.setText(user.getFb());
                                binding.linkedintext.setText(user.getLinkedin());
                                binding.instatext.setText(user.getInstagram());
                                binding.twittertext.setText(user.getTwitter());
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
            binding.messageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getActivity(), login.class);
                    startActivity(intent);
                }
            });


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
            binding.instatext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(users.getInstagram()));
                    startActivity(urlIntent);
                }
            });
            binding.linkedintext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(users.getLinkedin()));
                    startActivity(urlIntent);
                }
            });
            binding.fbtext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(users.getFb()));
                    startActivity(urlIntent);
                }
            });
            binding.twittertext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(users.getTwitter()));
                    startActivity(urlIntent);
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
            } else {
                if (data.getData() != null) {
                    Uri uri = data.getData();
                    binding.profilepic.setImageURI(uri);
                    final StorageReference reference = storage.getReference().child("profilepic").child(FirebaseAuth.getInstance().getUid());
                    reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getContext(), "Cover pic saved", Toast.LENGTH_SHORT).show();
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