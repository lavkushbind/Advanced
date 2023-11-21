package com.example.notification;

public class NotificationModel {


  private String NotificationBy;
  private String postID;
  private String sell;
  private String purchase;
  private long notificationAt;
  private long paymentRC;
  private String type;
private  String NotificationId;
private  String paypic;
private  boolean checkOpen;

  public String getPaypic() {
    return paypic;
  }

  public void setPaypic(String paypic) {
    this.paypic = paypic;
  }

  public String getNotificationBy() {
    return NotificationBy;
  }

  public boolean isCheckOpen() {
    return checkOpen;
  }

  public void setCheckOpen(boolean checkOpen) {
    this.checkOpen = checkOpen;
  }

  public String getNotificationId() {
    return NotificationId;
  }

  public void setNotificationId(String notificationId) {
    NotificationId = notificationId;
  }

  public void setNotificationBy(String notificationBy) {
    NotificationBy = notificationBy;
  }

  public String getPostID() {
    return postID;
  }

  public void setPostID(String postID) {
    this.postID = postID;
  }

  public String getSell() {
    return sell;
  }

  public void setSell(String sell) {
    this.sell = sell;
  }

  public String getPurchase() {
    return purchase;
  }

  public void setPurchase(String purchase) {
    this.purchase = purchase;
  }

  public long getNotificationAt() {
    return notificationAt;
  }

  public void setNotificationAt(long notificationAt) {
    this.notificationAt = notificationAt;
  }

  public long getPaymentRC() {
    return paymentRC;
  }

  public void setPaymentRC(long paymentRC) {
    this.paymentRC = paymentRC;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
