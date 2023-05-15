package com.example.mobprogprojectlec.Model;

public class Album {

    private int id;
    private String name;
    private String artistId;
    private int year;
    private String image;

    public Album(int id, String name, String artistId, int year, String image) {
        this.id = id;
        this.name = name;
        this.artistId = artistId;
        this.year = year;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
