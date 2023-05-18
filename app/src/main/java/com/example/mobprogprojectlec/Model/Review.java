package com.example.mobprogprojectlec.Model;

public class Review {

    private int id;
    private String comment;
    private int userId;
    private int songId;
    private int rating;

    public Review(int id, String comment, int userId, int songId, int rating) {
        this.id = id;
        this.comment = comment;
        this.userId = userId;
        this.songId = songId;
        this.rating = rating;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
