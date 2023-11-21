package com.example.home;

public class appmodel {
    private String photo;
    private  String topic;
    private  String pay;
    private  String appid;

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getPhotoid() {
        return photoid;
    }

    public void setPhotoid(String photoid) {
        this.photoid = photoid;
    }

    private  String photoid;


    public appmodel() {
    }

    public appmodel(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopics(String topics) {
        this.topic = topic;
    }
}
