package com.example.mobprogprojectlec.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Album implements Parcelable {

    private int id;
    private String name;
    private int artistId;
    private int year;
    private String description;
    private String image;

    public Album(int id, String name, int artistId, int year, String description, String image) {
        this.id = id;
        this.name = name;
        this.artistId = artistId;
        this.year = year;
        this.description = description;
        this.image = image;
    }

    protected Album(Parcel in) {
        id = in.readInt();
        name = in.readString();
        artistId = in.readInt();
        year = in.readInt();
        description = in.readString();
        image = in.readString();
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

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

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(artistId);
        dest.writeInt(year);
        dest.writeString(description);
        dest.writeString(image);
    }
}
