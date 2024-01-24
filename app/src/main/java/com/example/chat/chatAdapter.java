package com.example.chat;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blank_learn.dark.R;
import com.example.home.post2Activity;
import com.google.android.exoplayer2.Renderer;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

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
            if (holder instanceof SenderViewHolder) {
                ((SenderViewHolder) holder).senderMsg.setText(chatmodel.getMasseg());
                linkifyText(((SenderViewHolder) holder).senderMsg, chatmodel.getMasseg());
                setLongPressListener(((SenderViewHolder) holder).senderMsg, chatmodel.getMasseg());

                // Check if it's an image message
                if (chatmodel.isImageUrl()) {
                    // Load and display the image using Picasso
                    loadImageWithPicasso(chatmodel.getMasseg(), ((SenderViewHolder) holder).senderImage);
                    ((SenderViewHolder) holder).senderImage.setVisibility(View.VISIBLE);
                    ((SenderViewHolder) holder).senderMsg.setVisibility(View.GONE);
                } else {
                    ((SenderViewHolder) holder).senderImage.setVisibility(View.GONE);
                    ((SenderViewHolder) holder).senderMsg.setVisibility(View.VISIBLE);
                }
                ((SenderViewHolder) holder).senderImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String imageUrl = chatmodel.getMasseg();
                        if (imageUrl != null && chatmodel.isImageUrl()) {
                            // Show the image in full screen
                            showImageFullScreen(imageUrl);
                        }
                    }
                });

            } else if (holder instanceof ReceiverViewHolder) {
                ((ReceiverViewHolder) holder).receiverMsg.setText(chatmodel.getMasseg());
                linkifyText(((ReceiverViewHolder) holder).receiverMsg, chatmodel.getMasseg());
                setLongPressListener(((ReceiverViewHolder) holder).receiverMsg, chatmodel.getMasseg());

                // Check if it's an image message
                if (chatmodel.isImageUrl()) {
                    // Load and display the image using Picasso
                    loadImageWithPicasso(chatmodel.getMasseg(), ((ReceiverViewHolder) holder).receivedImage);
                    ((ReceiverViewHolder) holder).receivedImage.setVisibility(View.VISIBLE);
                    ((ReceiverViewHolder) holder).receiverMsg.setVisibility(View.GONE);
                } else {
                    ((ReceiverViewHolder) holder).receivedImage.setVisibility(View.GONE);
                    ((ReceiverViewHolder) holder).receiverMsg.setVisibility(View.VISIBLE);
                }

// Inside your adapter's onBindViewHolder method
                ((ReceiverViewHolder) holder).receivedImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String imageUrl = chatmodel.getMasseg();
                        if (imageUrl != null && chatmodel.isImageUrl()) {
                            // Show the image in full screen
                            showImageFullScreen(imageUrl);
                        }
                    }
                });
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (chatmodel.isImageUrl()) {
                        Intent intent = new Intent(context, photoview.class);
                        intent.putExtra(FullScreenImageActivity.EXTRA_IMAGE_URL, chatmodel.getMasseg());

                        if (context instanceof Activity) {
                            ((Activity) context).startActivity(intent);
                        } else {
                            Log.e("Adapter", "Cannot start activity from non-Activity context");
                        }
                    }                    else if (holder.itemView instanceof TextView) {
                        String websiteUrl = ((TextView) holder.itemView).getText().toString();

                        if (!TextUtils.isEmpty(websiteUrl)) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
                            if (intent.resolveActivity(context.getPackageManager()) != null) {
                                context.startActivity(intent);
                            } else {
                                Toast.makeText(context, "No app available to handle the URL", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(v.getContext(), "URL is empty", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Handle other cases if needed
                    }
                }
            });


        }
    }

    private void showImageFullScreen(String imageUrl) {
        Log.d("ShowFullScreen", "Context: " + context);
        if (context instanceof Activity && !((Activity) context).isFinishing()) {
            Log.d("ShowFullScreen", "Showing fullscreen image");
            Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            dialog.setContentView(R.layout.dialog_fullscreen_image);

            ImageView fullScreenImageView = dialog.findViewById(R.id.fullScreenImageView);
            loadImageWithPicasso(imageUrl, fullScreenImageView);

            // Set a click listener on the image to close the dialog when clicked
            fullScreenImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            // Show the dialog
            dialog.show();
        }
    }

    private void loadImageWithPicasso(String imageUrl, ImageView imageView) {
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
        ImageView senderImage;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsg = itemView.findViewById(R.id.senmsg);
            senderImage= itemView.findViewById(R.id.image);
        }
    }

    private static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView receiverMsg;
        ImageView receivedImage;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMsg = itemView.findViewById(R.id.recmsg);
            receivedImage = itemView.findViewById(R.id.image); // Initialize the ImageView

        }
    }
}