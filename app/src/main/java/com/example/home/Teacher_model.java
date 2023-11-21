package com.example.home;

public class Teacher_model {

    private String photo;
    private String userId;

    public Teacher_model() {
    }

    private String Teacher_name;
    private String Teacherid;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTeacher_name() {
        return Teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        Teacher_name = teacher_name;
    }

    public String getTeacherid() {
        return Teacherid;
    }

    public void setTeacherid(String teacherid) {
        Teacherid = teacherid;
    }

    public Teacher_model(String photo, String userId, String teacher_name, String teacherid) {
        this.photo = photo;
        this.userId = userId;
        Teacher_name = teacher_name;
        Teacherid = teacherid;
    }
}
