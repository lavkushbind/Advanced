package com.example.dark;
public class clasmodel {
    private  String clasid;
    private  String link;
    private  String type;
    private long clasat;
    private  boolean checkopen;
    private  String posttitle;
    private String postpic;
    public clasmodel() {
    }
    public clasmodel(String link) {
        this.link = link;
    }
    public clasmodel(String clasid, String type, long clasat, boolean checkopen) {
        this.clasid = clasid;
        this.type = type;
        this.clasat = clasat;
        this.checkopen = checkopen;
    }
    public String getPosttitle() {
        return posttitle;
    }
    public void setPosttitle(String posttitle) {
        this.posttitle = posttitle;
    }
    public String getPostpic() {
        return postpic;
    }
    public void setPostpic(String postpic) {
        this.postpic = postpic;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getClasid() {
        return clasid;
    }
    public void setClasid(String clasid) {
        this.clasid = clasid;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public long getClasat() {
        return clasat;
    }
    public void setClasat(long clasat) {
        this.clasat = clasat;
    }
    public boolean isCheckopen() {
        return checkopen;
    }
    public void setCheckopen(boolean checkopen) {
        this.checkopen = checkopen;
    }
}
