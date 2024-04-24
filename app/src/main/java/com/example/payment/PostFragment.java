package com.example.payment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.blank_learn.dark.R;
import com.blank_learn.dark.databinding.FragmentPostBinding;
import com.example.dark.clasmodel;
import com.example.notification.NotificationModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.Date;
import java.util.UUID;

public class PostFragment extends Fragment{
FragmentPostBinding binding;
Uri uri;
String randomKey;
String randomKey2;

FirebaseAuth auth;
FirebaseDatabase database;
ProgressDialog dialog;
FirebaseStorage storage;
    public PostFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth= FirebaseAuth.getInstance();
        //postid=auth.getTenantId();
        database= FirebaseDatabase.getInstance();
        storage= FirebaseStorage.getInstance();
        dialog = new ProgressDialog(getContext());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
         binding =FragmentPostBinding.inflate(inflater, container, false);
         dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
         dialog.setMessage("Please wait......");
         dialog.setTitle("Post Uploading");
         dialog.setCancelable(false);
         dialog.setCanceledOnTouchOutside(false);
         binding.postdisc.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
          }
          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              String description = binding.postdisc.getText().toString();
              if (!description.isEmpty()){
                  //  binding.postbtn.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.alarm));
                //  binding.postbtn.setBackgroundColor(getContext().getResources().getColor(R.color.purple_700));

                  //binding.postbtn.setTextColor(getContext().getResources().getColor(R.color.black));
                  //binding.postbtn.setEnabled(true);
              }else{
                //  binding.postbtn.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_home_black_24dp));
                 // binding.postbtn.setTextColor(getContext().getResources().getColor(R.color.black));
                  //binding.postbtn.setEnabled(false);
              }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
         binding.languageP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String language = binding.languageP.getText().toString();
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
         binding.timeP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String time = binding.timeP.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
         binding.aboutP.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

          }

          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              String about = binding.postdisc.getText().toString();

          }

          @Override
          public void afterTextChanged(Editable editable) {

          }
      });
         binding.price.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String price = binding.price.getText().toString();
    }
    @Override
    public void afterTextChanged(Editable editable) {
    }
});
         binding.duration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String duration = binding.duration.getText().toString();
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        database.getReference().child("App").child("toptea").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String value = snapshot.getValue(String.class);
                    binding.teach.setText(value);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.imgp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,10);
            }
        });
         binding.addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                startActivityForResult(intent,22);
            }
        });
        binding.postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uri == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Select image")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }



                randomKey2= UUID.randomUUID().toString();
                randomKey = database.getReference().push().getKey();
                dialog.show();
                final StorageReference reference = storage.getReference().child("posts").child(FirebaseAuth.getInstance().getUid()).child(new Date().getTime() + "");
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                dialog.dismiss();
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        postmodel postmodel = new postmodel();
                                        postmodel.setStandred(randomKey);
//                                postmodel.setPostVideo(uri.toString());
                                        postmodel.setPostImage(uri.toString());
                                        postmodel.setPhone(binding.phone.getText().toString());
                                        postmodel.setAbout(binding.aboutP.getText().toString());
                                        postmodel.setPostedBy(FirebaseAuth.getInstance().getUid());
                                        postmodel.setPrice(binding.price.getText().toString());
                                        postmodel.setTime(binding.timeP.getText().toString());
                                        postmodel.setLanguage(binding.languageP.getText().toString());
                                        postmodel.setDuration(binding.duration.getText().toString());
                                        postmodel.setPostdescription(binding.postdisc.getText().toString());

                                        NotificationModel notificationModel = new NotificationModel();
                                        notificationModel.setNotificationBy(FirebaseAuth.getInstance().getUid());
                                        notificationModel.setNotificationAt(new Date().getTime());
                                        notificationModel.setType("post");
                                        FirebaseDatabase.getInstance().getReference()
                                                .child("notification")
                                                .child(FirebaseAuth.getInstance().getUid())
                                                .push()
                                                .setValue(notificationModel);

                                        clasmodel clasmodel = new clasmodel();
                                        clasmodel.setType("post");
                                        clasmodel.setLink(randomKey);
                                          clasmodel.setPostpic(uri.toString());
                                        clasmodel.setPosttitle(binding.postdisc.getText().toString());
                                        clasmodel.setClasat(new Date().getTime());
                                        FirebaseDatabase.getInstance().getReference()
                                                .child("Classes")
                                                .child(FirebaseAuth.getInstance().getUid())
                                                .push()
                                                .setValue(clasmodel);






                                        database.getReference()
                                                .child("posts")
                                                .child(randomKey)
                                                .setValue(postmodel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        FirebaseDatabase.getInstance().getReference()
                                                                .child("Group")
                                                                .child(randomKey)
                                                                .child("member")
                                                                .child(FirebaseAuth.getInstance().getUid())
                                                                .push()
                                                                .setValue(randomKey)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {
                                                                    }
                                                                });
                                                        Toast.makeText(getContext(), "posted", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                });
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                float per=(100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                                dialog.setMessage("Upload:"+(int)per+"%");
                            }
                        });
            }
        });
        return  binding.getRoot();
            }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 22) {
                if (data.getData() != null) {
                    Uri uri = data.getData();
                    final StorageReference reference = storage.getReference().child("uploadvideo").child(FirebaseAuth.getInstance().getUid());
                    reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    database.getReference().child("Postvideo").push().child(FirebaseAuth.getInstance().getUid())
                                            .child("postvideo").push()
                                            .setValue(uri.toString());
                                }
                            });
                        }
                    })
                    ;
                }
            }


        else {
            if (data.getData() != null) {
                uri = data.getData();


                binding.postbtn.setBackgroundColor(getContext().getResources().getColor(R.color.white));
                  binding.postbtn.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.postbg));
                binding.postbtn.setEnabled(true);
            }
        }
    }
}