package com.example.home;

public class Teacher_model {

    private String id;
    private String pic;
    private String name;
    private String video;
    private  String storyid;

    public Teacher_model() {
    }

    public Teacher_model(String id, String pic, String name) {
        this.id = id;
        this.pic = pic;
        this.name = name;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getStoryid() {
        return storyid;
    }

    public void setStoryid(String storyid) {
        this.storyid = storyid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}