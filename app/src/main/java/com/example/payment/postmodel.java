package com.example.payment;
public class postmodel {
    private String Postid;
    private String paylink;
    public String getPaylink() {
        return paylink;
    }
    public void setPaylink(String paylink) {
        this.paylink = paylink;
    }
    public String getPosttype() {
        return posttype;
    }
    public void setPosttype(String posttype) {
        this.posttype = posttype;
    }
    private String posttype;
    private String PostVideo;
    public String getPostVideo() {
        return PostVideo;
    }
    public void setPostVideo(String postVideo) {
        PostVideo = postVideo;
    }
    private String PostImage;
    private  String exoplyer;
    private String time;
    private String about;
    private  String language;
    private String postdescription;
    private String PostedBy;
    private String postedAt;
    private String Standred;
    private String phone;
    private  String price;
private long price2;
private long seats;

    public long getPrice2() {
        return price2;
    }

    public void setPrice2(long price2) {
        this.price2 = price2;
    }

    public long getSeats() {
        return seats;
    }

    public void setSeats(long seats) {
        this.seats = seats;
    }

    private String duration;
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public postmodel(String postid, String postImage, String exoplyer, String time, String about, String language,
                     String postdescription, String postedBy, String postedAt, String standred, String price, String duration, String s) {
        Postid = postid;
        PostImage = postImage;
        this.exoplyer = exoplyer;
        this.time = time;
        this.about = about;
        this.language = language;
        this.postdescription = postdescription;
        PostedBy = postedBy;
        this.postedAt = postedAt;
        Standred = standred;
        this.price = price;
        this.duration = duration;}
    public postmodel() {}
    public String getPostid() {
        return Postid;
    }
    public void setPostid(String postid) {
        Postid = postid;
    }
    public String getPostImage() {
        return PostImage;
    }
    public void setPostImage(String postImage) {
        PostImage = postImage;
    }
    public String getExoplyer() {
        return exoplyer;
    }
    public void setExoplyer(String exoplyer) {
        this.exoplyer = exoplyer;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPostdescription() {
        return postdescription;
    }

    public void setPostdescription(String postdescription) {
        this.postdescription = postdescription;
    }

    public String getPostedBy() {
        return PostedBy;
    }

    public void setPostedBy(String postedBy) {
        PostedBy = postedBy;
    }

    public String getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }

    public String getStandred() {
        return Standred;
    }

    public void setStandred(String standred) {
        Standred = standred;
    }

    public  String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}