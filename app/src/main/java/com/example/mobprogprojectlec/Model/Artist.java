package com.example.mobprogprojectlec.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Artist implements Parcelable{
    private int id;
    private String name;
    private String description;
    private String image;

    public Artist(int id, String name, String description, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    protected Artist(Parcel in) {
        name = in.readString();
        description = in.readString();
        image = in.readString();
        id = in.readInt();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeInt(id);
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int describeContents() {
        return 0;
    }
}
