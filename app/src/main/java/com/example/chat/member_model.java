package com.example.chat;

public class member_model {
    private  String name;
    private  String prof;
    private  String follow;
    private  String img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public member_model(String name, String prof, String follow, String img) {
        this.name = name;
        this.prof = prof;
        this.follow = follow;
        this.img = img;
    }
}
