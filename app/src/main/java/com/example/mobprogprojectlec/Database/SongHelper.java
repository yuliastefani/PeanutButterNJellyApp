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

    public Vector<Song> vSong(){
        Vector<Song> songVector = new Vector<>();

        String view = "Select * from " + table;

        Cursor cursor = db.rawQuery(view, null);

        cursor.moveToFirst();

        Song s;
        String tempSongTitle, tempGenre;
        Integer tempID, tempArtistID, tempAlbumID;

        if (cursor.getCount() > 0) {
            do {
                tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                tempSongTitle = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                tempArtistID = cursor.getInt(cursor.getColumnIndexOrThrow("artist_id"));
                tempAlbumID = cursor.getInt(cursor.getColumnIndexOrThrow("album_id"));
                tempGenre = cursor.getString(cursor.getColumnIndexOrThrow("genre"));

                s = new Song(tempID, tempSongTitle, tempArtistID, tempAlbumID, tempGenre);

                songVector.add(s);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return songVector;
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
        String tempSongTitle, tempGenre;
        Integer tempID, tempArtistID, tempAlbumID;

        tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        tempSongTitle = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        tempArtistID = cursor.getInt(cursor.getColumnIndexOrThrow("artist_id"));
        tempAlbumID = cursor.getInt(cursor.getColumnIndexOrThrow("album_id"));
        tempGenre = cursor.getString(cursor.getColumnIndexOrThrow("genre"));

        s = new Song(tempID, tempSongTitle, tempArtistID, tempAlbumID, tempGenre);

        return s;
    }

    public void insertData(String songTitle, String artistId, String albumId, String genre) {
        String insert = "Select id from Song";
        Cursor cursor = db.rawQuery(insert, null);
        ContentValues contentValues = new ContentValues();
        if (cursor != null && cursor.moveToLast()) {
            int tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            tempID++;
            contentValues.put("id", tempID);
            contentValues.put("title", songTitle);
            contentValues.put("artist_id", artistId);
            contentValues.put("album_id", albumId);
            contentValues.put("genre", genre);

            db.insert(table, null, contentValues);

        }else {
            contentValues.put("id", 1);
            contentValues.put("title", songTitle);
            contentValues.put("artist_id", artistId);
            contentValues.put("album_id", albumId);
            contentValues.put("genre", genre);

            db.insert(table, null, contentValues);
        }

        cursor.close();

    }

    public Boolean validateGame(String title) {
        String search = "Select * from Song where title = ?";
        Cursor cursor = db.rawQuery(search, new String[]{title});

        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }


}
