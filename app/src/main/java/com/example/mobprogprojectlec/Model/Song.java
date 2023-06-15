package com.example.mobprogprojectlec.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Song implements Parcelable {

    private int id;
    private String title;
    private int artistId;
    private int albumId;
    private String genre;
    private String preview;
    private String spotify;

    public Song(int id, String title, int artistId, int albumId, String genre, String preview, String spotify) {
        this.id = id;
        this.title = title;
        this.artistId = artistId;
        this.albumId = albumId;
        this.genre = genre;
        this.preview = preview;
        this.spotify = spotify;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getSpotify() {
        return spotify;
    }

    public void setSpotify(String spotify) {
        this.spotify = spotify;
    }

    protected Song(Parcel in) {
        id = in.readInt();
        title = in.readString();
        artistId = in.readInt();
        albumId = in.readInt();
        genre = in.readString();
        preview = in.readString();
        spotify = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(artistId);
        dest.writeInt(albumId);
        dest.writeString(genre);
        dest.writeString(preview);
        dest.writeString(spotify);
    }
}
