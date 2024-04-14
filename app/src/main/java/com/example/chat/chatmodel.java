package com.example.chat;

import com.google.android.exoplayer2.Renderer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class chatmodel {
    String Muid,masseg;
    Long timestamp;
    public boolean isImageUrl() {
        return masseg != null && masseg.contains("firebasestorage.googleapis.com") && masseg.contains("alt=media");

    }
    String urip;
    private MessageType messageType;

    public MessageType getMessageType() {
        return messageType;
    }

    public chatmodel(String muid, String masseg, Long timestamp, String urip, MessageType messageType) {
        Muid = muid;
        this.masseg = masseg;
        this.timestamp = timestamp;
        this.urip = urip;
        this.messageType = messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public enum MessageType {
        TEXT, // Indicates a text message
        PDF,  // Indicates a PDF message
        // Add more types as needed
    }// Add this field

    public String getUrip() {
        return urip;
    }


    public void setUrip(String urip) {
        this.urip = urip;
    }

    public chatmodel() {
    }

    public chatmodel(String senderId, String message) {
    }

    public String getMuid() {
        return Muid;
    }

    public void setMuid(String muid) {
        Muid = muid;
    }

    public String getMasseg() {
        return masseg;
    }

    public void setMasseg(String masseg) {
        this.masseg = masseg;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public chatmodel(String muid, String masseg, Long timestamp) {
        Muid = muid;
        this.masseg = masseg;
        this.timestamp = timestamp;
    }
}