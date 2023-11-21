package com.example.dark;

public class Followmodel {
    private String followedBy;
    private long followedAt;
        private  long followAt;

    public long getFollowAt() {
        return followAt;
    }

    public void setFollowAt(long followAt) {
        this.followAt = followAt;
    }

    public Followmodel() {
    }

    public String getFollowedBy() {
        return followedBy;
    }

    public void setFollowedBy(String followedBy) {
        this.followedBy = followedBy;
    }

    public long getFollowedAt() {
        return followedAt;
    }

    public void setFollowedAt(long followedAt) {
        this.followedAt = followedAt;
    }
}
