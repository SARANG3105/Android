package com.example.ubuntu.messageme;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ubuntu on 4/25/18.
 */

public class Message implements Serializable,Comparable<Message> {
    private String msgId;
    private String sender;
    private String reciever;
    private String message;
    private Boolean isRead;
    private Date createdAt;
    private String senderEmail;
    private String senderID;

    public Message(){
        createdAt= new Date();
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getMsgId() {
        return msgId;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", reciever='" + reciever + '\'' +
                ", message='" + message + '\'' +
                ", isRead=" + isRead +
                ", createdAt=" + createdAt +
                '}';
    }
    @Override
    public int compareTo(Message o) {
        return o.getCreatedAt().compareTo(getCreatedAt());
    }
}
