package com.example.mobprogprojectlec.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.mobprogprojectlec.Model.Song;

import java.util.Vector;

public class SongHelper {

    private static String table = "song";
    private Context ctx;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    public SongHelper(Context ctx) {
        this.ctx = ctx;
    }

    public void open() throws SQLException {
        databaseHelper = new DatabaseHelper(ctx);
        db = databaseHelper.getWritableDatabase();
    }

    public void close() throws SQLException {
        databaseHelper.close();
    }

    public Song getSong(String songId) {
        String view = "Select * from Song where id= ? limit 1";

        Cursor cursor = db.rawQuery(view, new String[]{songId});
        cursor.moveToFirst();
        if (cursor.getCount() <= 0){
            cursor.close();
            return null;
        }

        Song s;
        String tempSongTitle, tempGenre, tempPreview;
        Integer tempID, tempArtistID, tempAlbumID;

        tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        tempSongTitle = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        tempArtistID = cursor.getInt(cursor.getColumnIndexOrThrow("artist_id"));
        tempAlbumID = cursor.getInt(cursor.getColumnIndexOrThrow("album_id"));
        tempGenre = cursor.getString(cursor.getColumnIndexOrThrow("genre"));
        tempPreview = cursor.getString(cursor.getColumnIndexOrThrow("preview"));

        s = new Song(tempID, tempSongTitle, tempArtistID, tempAlbumID, tempGenre, tempPreview);

        return s;
    }

    public void insertSong(String songTitle, Integer artistId, Integer albumId, String genre, String preview) {
        String selectMaxIdQuery = "SELECT MAX(id) FROM Song";
        Cursor cursor = db.rawQuery(selectMaxIdQuery, null);
        int maxId = 0;

        if (cursor != null && cursor.moveToFirst()) {
            maxId = cursor.getInt(0);
            cursor.close();
        }

        int songId = maxId + 1;

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", songId);
        contentValues.put("title", songTitle);
        contentValues.put("artist_id", artistId);
        contentValues.put("album_id", albumId);
        contentValues.put("genre", genre);
        contentValues.put("preview", preview);

        db.insert(table, null, contentValues);
    }


    public boolean validateSong(String title, int artistId, int albumId) {
        String search = "SELECT * FROM Song WHERE title = ? AND artist_id = ? AND album_id = ?";
        Cursor cursor = db.rawQuery(search, new String[]{title, String.valueOf(artistId), String.valueOf(albumId)});

        return cursor.getCount() > 0;
    }

    public Vector<Song> viewSong() {
        Vector<Song> songVector = new Vector<>();
        String viewSong = "Select * from Song ORDER BY title ASC";
        Cursor cursor = db.rawQuery(viewSong, null);

        cursor.moveToFirst();

        Song s;
        String tempSongTitle, tempGenre, tempPreview;
        Integer tempID, tempArtistID, tempAlbumID;

        if (cursor.getCount() > 0) {
            do {
                tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                tempSongTitle = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                tempArtistID = cursor.getInt(cursor.getColumnIndexOrThrow("artist_id"));
                tempAlbumID = cursor.getInt(cursor.getColumnIndexOrThrow("album_id"));
                tempGenre = cursor.getString(cursor.getColumnIndexOrThrow("genre"));
                tempPreview = cursor.getString(cursor.getColumnIndexOrThrow("preview"));

                s = new Song(tempID, tempSongTitle, tempArtistID, tempAlbumID, tempGenre, tempPreview);

                songVector.add(s);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return songVector;
    }

    public Song fetchSong(Integer songId) {
        String view = "Select * from Song where id= ? limit 1";

        Cursor cursor = db.rawQuery(view, new String[]{String.valueOf(songId)});
        cursor.moveToFirst();
        if (cursor.getCount() <= 0){
            cursor.close();
            return null;
        }

        Song s;
        String tempSongTitle, tempGenre, tempPreview;
        Integer tempID, tempArtistID, tempAlbumID;

        tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        tempSongTitle = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        tempArtistID = cursor.getInt(cursor.getColumnIndexOrThrow("artist_id"));
        tempAlbumID = cursor.getInt(cursor.getColumnIndexOrThrow("album_id"));
        tempGenre = cursor.getString(cursor.getColumnIndexOrThrow("genre"));
        tempPreview = cursor.getString(cursor.getColumnIndexOrThrow("preview"));

        s = new Song(tempID, tempSongTitle, tempArtistID, tempAlbumID, tempGenre, tempPreview);

        return s;
    }

    public Vector<Song> viewSongById(Integer albumID) {
        Vector<Song> songVector = new Vector<>();
        String view = "Select * from " + table + " where album_id = ? ORDER BY title ASC";
        Cursor cursor = db.rawQuery(view, new String[]{String.valueOf(albumID)});

        cursor.moveToFirst();

        Song s;
        String tempSongTitle, tempGenre, tempPreview;
        Integer tempID, tempArtistID, tempAlbumID;

        if (cursor.getCount() > 0) {
            do {
                tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                tempSongTitle = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                tempArtistID = cursor.getInt(cursor.getColumnIndexOrThrow("artist_id"));
                tempAlbumID = cursor.getInt(cursor.getColumnIndexOrThrow("album_id"));
                tempGenre = cursor.getString(cursor.getColumnIndexOrThrow("genre"));
                tempPreview = cursor.getString(cursor.getColumnIndexOrThrow("preview"));

                s = new Song(tempID, tempSongTitle, tempArtistID, tempAlbumID, tempGenre, tempPreview);

                songVector.add(s);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }

        cursor.close();
        return songVector;

    }


    public Vector<Song> vSong(String artist_Id, String album_Id) {
        Vector<Song> songVector = new Vector<>();
        String viewSong = "Select * from Song where artist_id = ? and album_id = ?";
        Cursor cursor = db.rawQuery(viewSong, new String[]{artist_Id, album_Id});

        cursor.moveToFirst();

        Song s;
        String tempSongTitle, tempGenre, tempPreview;
        Integer tempID, tempArtistID, tempAlbumID;

        if (cursor.getCount() > 0) {
            do {
                tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                tempSongTitle = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                tempArtistID = cursor.getInt(cursor.getColumnIndexOrThrow("artist_id"));
                tempAlbumID = cursor.getInt(cursor.getColumnIndexOrThrow("album_id"));
                tempGenre = cursor.getString(cursor.getColumnIndexOrThrow("genre"));
                tempPreview = cursor.getString(cursor.getColumnIndexOrThrow("preview"));

                s = new Song(tempID, tempSongTitle, tempArtistID, tempAlbumID, tempGenre, tempPreview);

                songVector.add(s);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }

        cursor.close();
        return songVector;

    }


}
