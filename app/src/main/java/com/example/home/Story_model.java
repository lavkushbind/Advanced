package com.example.home;

public class Story_model {
    private String userid;
    private String username;
    private String video;
    private  String storyid;

    public String getStoryid() {
        return storyid;
    }


    public Story_model(String userid, String username, String video, String storyid) {
        this.userid = userid;
        this.username = username;
        this.video = video;
        this.storyid = storyid;
    }

    public void setStoryid(String storyid) {
        this.storyid = storyid;
    }

    public Story_model() {
    }





    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
