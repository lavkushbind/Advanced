package com.example.chat;
import android.app.AlertDialog;
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


//        chatmodel message = list.get(position);
//        if (message.getMessageType() == chatmodel.MessageType.PDF) {
//            return PDF_TYPE;
//        } else if (message.getMuid().equals(FirebaseAuth.getInstance().getUid())) {
//            return SENDER_TYPE;
//        } else {
//            return RECEIVER_TYPE;
//        }

        if (list.get(position).getMuid().equals(FirebaseAuth.getInstance().getUid())) {
            return SENDER_TYPE;
        } else {
            return RECEIVER_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        chatmodel chatmodel = list.get(position);
//        if (holder instanceof ReceiverViewHolder) {
//            if (chatmodel.getMessageType() == com.example.chat.chatmodel.MessageType.PDF) {
//                // Display PDF icon or attachment icon, and set a click listener to open the PDF
//                ((ReceiverViewHolder) holder).receivedImage.setImageResource(R.drawable.img);
//                ((ReceiverViewHolder) holder).receivedImage.setOnClickListener(v -> {
//                    String pdfUrl = chatmodel.getMasseg(); // Assuming chatmodel.getMasseg() contains the PDF URL
//                    openPdf(pdfUrl);
//                });
//            } else //        if (holder instanceof SenderViewHolder)
//        {
//            ((SenderViewHolder) holder).senderMsg.setText(chatmodel.getMasseg());
//            linkifyText(((SenderViewHolder) holder).senderMsg, chatmodel.getMasseg());
//            setLongPressListener(((SenderViewHolder) holder).senderMsg, chatmodel.getMasseg());
//            ((ReceiverViewHolder) holder).receiverMsg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // Handle opening the image here
//                    String imageUrl = chatmodel.getMasseg(); // Assuming chatmodel.getMasseg() contains the image URL
//                    if (imageUrl != null && !imageUrl.isEmpty()) {
//                        // Open the image, e.g., in an image viewer or a custom dialog
//                        openImage(imageUrl);
//                    }
//                }
//
//
//
//            });
//        }  if (holder instanceof ReceiverViewHolder) {
//            ((ReceiverViewHolder) holder).receiverMsg.setText(chatmodel.getMasseg());
//            linkifyText(((ReceiverViewHolder) holder).receiverMsg, chatmodel.getMasseg());
//
//            setLongPressListener(((ReceiverViewHolder) holder).receiverMsg, chatmodel.getMasseg());
//
//            ((ReceiverViewHolder) holder).receivedImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // Handle opening the image here
//                    String imageUrl = chatmodel.getMasseg(); // Assuming chatmodel.getMasseg() contains the image URL
//                    if (imageUrl != null && !imageUrl.isEmpty()) {
//                        // Open the image, e.g., in an image viewer or a custom dialog
//                        openImage(imageUrl);
//                    }
//                }
//            });
//        }
//        }
        if (holder instanceof SenderViewHolder)
        {
            ((SenderViewHolder) holder).senderMsg.setText(chatmodel.getMasseg());
            linkifyText(((SenderViewHolder) holder).senderMsg, chatmodel.getMasseg());
            setLongPressListener(((SenderViewHolder) holder).senderMsg, chatmodel.getMasseg());
//            ((ReceiverViewHolder) holder).receiverMsg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // Handle opening the image here
//                    String imageUrl = chatmodel.getMasseg(); // Assuming chatmodel.getMasseg() contains the image URL
//                    if (imageUrl != null && !imageUrl.isEmpty()) {
//                        // Open the image, e.g., in an image viewer or a custom dialog
////                        openImage(imageUrl);
//                    }
//                }
//
//            });
        } else if (holder instanceof ReceiverViewHolder) {
            ((ReceiverViewHolder) holder).receiverMsg.setText(chatmodel.getMasseg());
            linkifyText(((ReceiverViewHolder) holder).receiverMsg, chatmodel.getMasseg());

            setLongPressListener(((ReceiverViewHolder) holder).receiverMsg, chatmodel.getMasseg());

            ((ReceiverViewHolder) holder).receivedImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle opening the image here
                    String imageUrl = chatmodel.getMasseg(); // Assuming chatmodel.getMasseg() contains the image URL
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        // Open the image, e.g., in an image viewer or a custom dialog
//                        openImage(imageUrl);
                    }
                }
            });
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String websiteUrl;
                if (holder.itemView instanceof TextView) {
                    websiteUrl = ((TextView) holder.itemView).getText().toString();

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
                    Toast.makeText(v.getContext(), "Not a TextView", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openPdf(String pdfUrl) {
        // Open and display the PDF using an external PDF viewer or library
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(pdfUrl), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Handle the case where no PDF viewer app is available
            Toast.makeText(context, "No app available to handle PDF", Toast.LENGTH_SHORT).show();
        }
    }

//    private void openImage(String imageUrl) {
//        ImageView imageView = new ImageView(context);
//        Picasso.get().load(imageUrl).into(imageView);
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setView(imageView);
//        builder.show();
//    }



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
                        if (intent.resolveActivity(context.getPackageManager()) != null) {
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, "No app available to handle the URL", Toast.LENGTH_SHORT).show();
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

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsg = itemView.findViewById(R.id.senmsg);
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
