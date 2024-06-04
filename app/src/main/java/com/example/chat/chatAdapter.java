package com.example.chat;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.blank_learn.dark.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class chatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int SENDER_TYPE = 1;
    private static final int RECEIVER_TYPE = 2;
    private static final int PDF_TYPE = 3;

    private ArrayList<chatmodel> list;
    private Context context;

    public chatAdapter(ArrayList<chatmodel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == SENDER_TYPE) {
            view = LayoutInflater.from(context).inflate(R.layout.sender_layout_item, parent, false);
            return new SenderViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.receiver_layout_item, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {



        if (list.get(position).getMuid().equals(FirebaseAuth.getInstance().getUid())) {
            return SENDER_TYPE;
        } else {
            return RECEIVER_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        chatmodel chatmodel = list.get(position);{
            if (holder instanceof SenderViewHolder)
            {
                ((SenderViewHolder) holder).senderMsg.setText(chatmodel.getMasseg());
                linkifyText(((SenderViewHolder) holder).senderMsg, chatmodel.getMasseg());
                setLongPressListener(((SenderViewHolder) holder).senderMsg, chatmodel.getMasseg());
                if (chatmodel.isImageUrl()) {
                    loadImageWithPicasso(chatmodel.getMasseg(), ((SenderViewHolder) holder).senderImage, R.drawable.gallery);

                    ((SenderViewHolder) holder).senderImage.setVisibility(View.VISIBLE);
                    ((SenderViewHolder) holder).imageView2.setVisibility(View.VISIBLE);
                    ((SenderViewHolder) holder).senderMsg.setVisibility(View.GONE);
                }
                else {
                    ((SenderViewHolder) holder).senderImage.setVisibility(View.GONE);
                    ((SenderViewHolder) holder).imageView2.setVisibility(View.GONE);

                    ((SenderViewHolder) holder).senderMsg.setVisibility(View.VISIBLE);
                }
                if (holder instanceof SenderViewHolder) {



                ((SenderViewHolder) holder).senderImage.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (chatmodel.isImageUrl()) {
                            downloadImage(chatmodel.getMasseg());
                            Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
                        }
                        return true;

                    }
                });}

            }}
             if (holder instanceof ReceiverViewHolder)
             {
                ((ReceiverViewHolder) holder).receiverMsg.setText(chatmodel.getMasseg());
                linkifyText(((ReceiverViewHolder) holder).receiverMsg, chatmodel.getMasseg());
                setLongPressListener(((ReceiverViewHolder) holder).receiverMsg, chatmodel.getMasseg());

                if (chatmodel.isImageUrl()) {
                    loadImageWithPicasso(chatmodel.getMasseg(), ((ReceiverViewHolder) holder).receivedImage, R.drawable.gallery);
                    ((ReceiverViewHolder) holder).receivedImage.setVisibility(View.VISIBLE);
                    ((ReceiverViewHolder) holder).imageView20.setVisibility(View.VISIBLE);

                    ((ReceiverViewHolder) holder).receiverMsg.setVisibility(View.GONE);
                } else {
                    ((ReceiverViewHolder) holder).receivedImage.setVisibility(View.GONE);
                    ((ReceiverViewHolder) holder).imageView20.setVisibility(View.GONE);

                    ((ReceiverViewHolder) holder).receiverMsg.setVisibility(View.VISIBLE);
                }

//                ((ReceiverViewHolder)holder).imageView21r.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        showDeleteConfirmationDialog(chatmodel.getMasseg());
//                    }
//                });


                ((ReceiverViewHolder) holder).receivedImage.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (chatmodel.isImageUrl()) {
                            downloadImage(chatmodel.getMasseg());
                            Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
                        }
                        return true;

                    }
                });}
            }












//        chatmodel chatModel = list.get(position);

//        if (holder instanceof SenderViewHolder) {
//            SenderViewHolder senderViewHolder = (SenderViewHolder) holder;
//
//            senderViewHolder.senderMsg.setText(chatModel.getMasseg());
//            linkifyText(senderViewHolder.senderMsg, chatModel.getMasseg());
//            setLongPressListener(senderViewHolder.senderMsg, chatModel.getMasseg());
//
////            if (chatModel.isImageUrl()) {
////                loadImageWithPicasso(chatModel.getMasseg(), senderViewHolder.senderImage);
////                senderViewHolder.senderImage.setVisibility(View.VISIBLE);
////                senderViewHolder.senderMsg.setVisibility(View.GONE);
////                senderViewHolder.imageView2.setVisibility(View.VISIBLE); // Add download button visibility
////            } else {
////                senderViewHolder.senderImage.setVisibility(View.GONE);
////                senderViewHolder.senderMsg.setVisibility(View.VISIBLE);
////                senderViewHolder.imageView2.setVisibility(View.GONE); // Hide download button for non-image messages
////            }
////            if (chatModel.isImageUrl()) {
////                loadImageWithPicasso(chatModel.getMasseg(), senderViewHolder.senderImage);
////                if (senderViewHolder.senderImage != null) {
////                    senderViewHolder.senderImage.setVisibility(View.VISIBLE);
////                }
////                if (senderViewHolder.senderMsg != null) {
////                    senderViewHolder.senderMsg.setVisibility(View.GONE);
////                }
////            } else {
////                if (senderViewHolder.senderImage != null) {
////                    senderViewHolder.senderImage.setVisibility(View.GONE);
////                }
////                if (senderViewHolder.senderMsg != null) {
////                    senderViewHolder.senderMsg.setVisibility(View.VISIBLE);
////                }
////            }
//
//// Similar checks for other places where you set the visibility of ImageView
//            if (chatModel.isImageUrl()) {
//                loadImageWithPicasso(chatModel.getMasseg(), senderViewHolder.senderImage);
//
//                // Check if senderImage is not null before setting OnClickListener
//                if (senderViewHolder.senderImage != null) {
//                    senderViewHolder.senderImage.setVisibility(View.VISIBLE);
////                    senderViewHolder.senderImage.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            String imageUrl = chatModel.getMasseg();
////                            if (imageUrl != null && chatModel.isImageUrl()) {
////                                // Show the image in full screen
////                                showImageFullScreen(imageUrl);
////                            }
////                        }
////                    });
//                }
//
//                if (senderViewHolder.senderMsg != null) {
//                    senderViewHolder.senderMsg.setVisibility(View.GONE);
//                }
//            } else {
//                if (senderViewHolder.senderImage != null) {
//                    senderViewHolder.senderImage.setVisibility(View.GONE);
//                }
//                if (senderViewHolder.senderMsg != null) {
//                    senderViewHolder.senderMsg.setVisibility(View.VISIBLE);
//                }
//            }
//
//
//
////            senderViewHolder.senderImage.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    if (chatModel.isImageUrl()) {
////                        showImageFullScreen(chatModel.getMasseg());
////                    }
////                }
////            });
//
//            senderViewHolder.senderImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (chatModel.isImageUrl()) {
//                        downloadImage(chatModel.getMasseg());
//                    }
//                }
//            });
//
//        } else if (holder instanceof ReceiverViewHolder) {
//            ReceiverViewHolder receiverViewHolder = (ReceiverViewHolder) holder;
//
//            receiverViewHolder.receiverMsg.setText(chatModel.getMasseg());
//            linkifyText(receiverViewHolder.receiverMsg, chatModel.getMasseg());
//            setLongPressListener(receiverViewHolder.receiverMsg, chatModel.getMasseg());
//
//            if (chatModel.isImageUrl()) {
//                loadImageWithPicasso(chatModel.getMasseg(), receiverViewHolder.receivedImage);
//                receiverViewHolder.receivedImage.setVisibility(View.VISIBLE);
//                receiverViewHolder.receiverMsg.setVisibility(View.GONE);
//                receiverViewHolder.imageView20.setVisibility(View.VISIBLE); // Add download button visibility
//            } else {
//                receiverViewHolder.receivedImage.setVisibility(View.GONE);
//                receiverViewHolder.receiverMsg.setVisibility(View.VISIBLE);
//                receiverViewHolder.imageView20.setVisibility(View.GONE); // Hide download button for non-image messages
//            }
//
//            receiverViewHolder.receivedImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (chatModel.isImageUrl()) {
//                        showImageFullScreen(chatModel.getMasseg());
//                    }
//                }
//            });
//
//            receiverViewHolder.imageView20.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (chatModel.isImageUrl()) {
//                        downloadImage(chatModel.getMasseg());
//                    }
//                }
//            });
//        }
//
//// Rest of the code remains unchanged
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (chatModel.isImageUrl()) {
//                    Intent intent = new Intent(context, photoview.class);
//                    intent.putExtra(FullScreenImageActivity.EXTRA_IMAGE_URL, chatModel.getMasseg());
//
//                    if (context instanceof Activity) {
//                        ((Activity) context).startActivity(intent);
//                    } else {
//                        Log.e("Adapter", "Cannot start activity from non-Activity context");
//                    }
//                } else if (holder.itemView instanceof TextView) {
//                    // ... existing code ...
//                } else {
//                    // ... existing code ...
//                }
//            }
//        });
//
//    }




    private void downloadImage(String imageUrl) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(imageUrl));
        request.setTitle("File Download");
        request.setDescription("Downloading...");

        // Set the destination file path
        String fileName = "Blanklearn";
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            // Enqueue the download
            long downloadId = downloadManager.enqueue(request);

            // Monitor the download status
            BroadcastReceiver receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

                    // Create a new Query
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(downloadId);

                    Cursor cursor = manager.query(query);
                    if (cursor != null && cursor.moveToFirst()) {
                        int statusColumnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                        int localUriColumnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);

                        // Check if the column indexes are valid
                        if (statusColumnIndex >= 0 && localUriColumnIndex >= 0) {
                            int status = cursor.getInt(statusColumnIndex);
                            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                                // Download completed successfully
                                String filePath = cursor.getString(localUriColumnIndex);
                                // Show a Toast indicating the file path
                                Toast.makeText(context, "File downloaded: " + filePath, Toast.LENGTH_SHORT).show();
//                                openDownloadedFile(context, filePath);

                            } else {
                                // Download failed or in progress
                                Toast.makeText(context, "Image download failed or in progress", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    // Close the cursor to avoid resource leaks
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }

                    // Unregister the receiver to avoid memory leaks
                    context.unregisterReceiver(this);
                }
            };

            context.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        } else {
            // Handle the case where DownloadManager is not available
            Toast.makeText(context, "DownloadManager not available", Toast.LENGTH_SHORT).show();
        }
    }





    private void loadImageWithPicasso(String imageUrl, ImageView imageView, int gallery) {
        Picasso.get().load(imageUrl).into(imageView);
    }






    private void linkifyText(TextView textView, String text) {
        SpannableString spannableString = new SpannableString(text);
        Pattern pattern = Patterns.WEB_URL;
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String url = matcher.group();
            int start = matcher.start();
            int end = matcher.end();
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    // Handle the URL click event here
                    if (!TextUtils.isEmpty(url)) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                        // Use the correct context to start the activity
                        if (widget.getContext() instanceof Activity) {
                            // If widget.getContext() is an Activity, use it
                            ((Activity) widget.getContext()).startActivity(intent);
                        } else {
                            // If widget.getContext() is not an Activity, add FLAG_ACTIVITY_NEW_TASK flag
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            widget.getContext().startActivity(intent);
                        }
                    } else {
                        Toast.makeText(widget.getContext(), "URL is empty", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(true);
                    ds.setColor(context.getResources().getColor(R.color.card_blue));
                }
            };



            spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT);
    }

    private void setLongPressListener(TextView textView, String textToCopy) {
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                if (clipboard != null) {
                    ClipData clip = ClipData.newPlainText("Copied Text", textToCopy);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(context, "Text copied", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Clipboard not available", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private static class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView senderMsg;

        ImageView senderImage,imageView2,imageView21s,delete_img;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsg = itemView.findViewById(R.id.senmsg);
            senderImage= itemView.findViewById(R.id.image);
            imageView2= itemView.findViewById(R.id.imageView19);
//            imageView21s= itemView.findViewById(R.id.imageView21);
            delete_img= itemView.findViewById(R.id.imageView21);

        }

    }

    private static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView receiverMsg;
        ImageView receivedImage,imageView20,imageView21r;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMsg = itemView.findViewById(R.id.recmsg);
            imageView20= itemView.findViewById(R.id.imageView20);
            imageView21r= itemView.findViewById(R.id.imageView21);


            receivedImage = itemView.findViewById(R.id.image);

        }
    }
}