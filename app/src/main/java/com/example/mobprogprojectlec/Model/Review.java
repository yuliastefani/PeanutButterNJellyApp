package com.example.mobprogprojectlec.Model;

public class Review {

    private int id;
    private String comment;
    private int userId;
    private int songId;
    private float rating;
    private long date;

    public Review(int id, String comment, int userId, int songId, float rating, long date) {
        this.id = id;
        this.comment = comment;
        this.userId = userId;
        this.songId = songId;
        this.rating = rating;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
