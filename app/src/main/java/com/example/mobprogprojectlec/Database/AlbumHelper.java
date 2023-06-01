package com.example.mobprogprojectlec.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.mobprogprojectlec.Model.Album;
import com.example.mobprogprojectlec.Model.Artist;

import java.util.Vector;

public class AlbumHelper {

    private static String table = "Album";
    private Context ctx;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    public AlbumHelper(Context ctx) {
        this.ctx = ctx;
    }

    public void open() throws SQLException {
        databaseHelper = new DatabaseHelper(ctx);
        db = databaseHelper.getWritableDatabase();
    }

    public void close() throws SQLException {
        databaseHelper.close();
    }

    public Vector<Album> viewAlbum(String orderByTable, String orderBy) {
        Vector<Album> albumVector = new Vector<>();
        String view = "Select * from " + table + " ORDER BY " + orderByTable + " " + orderBy;
        Cursor cursor = db.rawQuery(view, null);
        cursor.moveToFirst();
        Album a;
        String tempAlbumName, tempAlbumDescription, tempAlbumImage;
        Integer tempID, tempArtistID, tempAlbumYear;

        if (cursor.getCount() > 0) {
            do {
                tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                tempAlbumName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                tempArtistID = cursor.getInt(cursor.getColumnIndexOrThrow("artist_id"));
                tempAlbumDescription = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                tempAlbumImage = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                tempAlbumYear = cursor.getInt(cursor.getColumnIndexOrThrow("year"));

                a = new Album(tempID, tempAlbumName, tempArtistID, tempAlbumYear, tempAlbumDescription, tempAlbumImage);
                albumVector.add(a);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        cursor.close();
        return albumVector;

    }


    public int insertAlbum(String albumName, Integer artistID, Integer albumYear, String albumDescription, String albumImage) {
        String insert = "SELECT * FROM " + table;
        Cursor cursor = db.rawQuery(insert, null);
        ContentValues cv = new ContentValues();

        int albumID = -1; // Initialize with a default value

        if (cursor != null && cursor.moveToLast()) {
            int tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            tempID++;
            albumID = tempID;
        } else {
            albumID = 1;
        }

        cv.put("id", albumID);
        cv.put("name", albumName);
        cv.put("artist_id", artistID);
        cv.put("year", albumYear);
        cv.put("description", albumDescription);
        cv.put("image", albumImage);

        db.insert(table, null, cv);

        if (cursor != null) {
            cursor.close();
        }

        return albumID;
    }

    public Vector<Album> viewAlbumbyID(int artistID) {
        Vector<Album> albumVector = new Vector<>();
        String view = "Select * from " + table + " WHERE artist_id = ? ORDER BY name ASC";
        Cursor cursor = db.rawQuery(view, new String[]{String.valueOf(artistID)});
        cursor.moveToFirst();
        Album a;
        String tempAlbumName, tempAlbumDescription, tempAlbumImage;
        Integer tempID, tempArtistID, tempAlbumYear;

        if (cursor.getCount() > 0) {
            do {
                tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                tempAlbumName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                tempArtistID = cursor.getInt(cursor.getColumnIndexOrThrow("artist_id"));
                tempAlbumDescription = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                tempAlbumImage = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                tempAlbumYear = cursor.getInt(cursor.getColumnIndexOrThrow("year"));

                a = new Album(tempID, tempAlbumName, tempArtistID, tempAlbumYear, tempAlbumDescription, tempAlbumImage);
                albumVector.add(a);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        cursor.close();
        return albumVector;

    }


    public Album getAlbum(String albumName, int artistId) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Album album = null;

        try {
            db = databaseHelper.getReadableDatabase();
            String selection = "name=? AND artist_id=?";
            String[] selectionArgs = {albumName, String.valueOf(artistId)};
            cursor = db.query(table, null, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                int artistID = cursor.getInt(cursor.getColumnIndexOrThrow("artist_id"));
                int year = cursor.getInt(cursor.getColumnIndexOrThrow("year"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                album = new Album(id, name, artistID, year, description, image);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return album;
    }

    public Album fetchAlbum(int albumID) {
        String select = "Select * from Album where id= ? limit 1";
        Cursor cursor = db.rawQuery(select, new String[]{String.valueOf(albumID)});

        cursor.moveToFirst();
        if (cursor.getCount() <= 0) {
            return null;
        }

        Album album;
        String tempAlbumName, tempAlbumDescription, tempAlbumImage;
        Integer tempID, tempArtistID, tempAlbumYear;

        tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        tempAlbumName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        tempArtistID = cursor.getInt(cursor.getColumnIndexOrThrow("artist_id"));
        tempAlbumDescription = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        tempAlbumImage = cursor.getString(cursor.getColumnIndexOrThrow("image"));
        tempAlbumYear = cursor.getInt(cursor.getColumnIndexOrThrow("year"));

        album = new Album(tempID, tempAlbumName, tempArtistID, tempAlbumYear, tempAlbumDescription, tempAlbumImage);
        return album;
    }


    public boolean validateAlbum(String name, int artistId) {
        String search = "SELECT * FROM " + table + " WHERE name = ? AND artist_id = ?";
        Cursor cursor = db.rawQuery(search, new String[]{name, String.valueOf(artistId)});

        return cursor.getCount() > 0;
    }

}


