package com.example.mobprogprojectlec.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.mobprogprojectlec.Model.Album;

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

    public Vector<Album> vAlbum() {
        Vector<Album> albumVector = new Vector<>();
        String view = "Select * from " + table;
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

    public void insertAlbum(String albumName, Integer artistID, Integer albumYear, String albumDescription, String albumImage) {
        String insert = "Select * from " + table;
        Cursor cursor = db.rawQuery(insert, null);
        ContentValues cv = new ContentValues();

        if (cursor != null && cursor.moveToLast()){
            int tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            tempID++;
            cv.put("id", tempID + 1);
            cv.put("name", albumName);
            cv.put("artist_id", artistID);
            cv.put("year", albumYear);
            cv.put("description", albumDescription);
            cv.put("image", albumImage);

            db.insert(table, null, cv);

        }
        else {
            cv.put("id", 1);
            cv.put("name", albumName);
            cv.put("artist_id", artistID);
            cv.put("year", albumYear);
            cv.put("description", albumDescription);
            cv.put("image", albumImage);

            db.insert(table, null, cv);
        }

        cursor.close();

    }

    public Album getAlbum(String albumId){
        String view = "Select * from " + table + " where id = " + albumId;
        Cursor cursor = db.rawQuery(view, null);
        cursor.moveToFirst();

        if (cursor.getCount() <= 0) {
            return null;
        }

        Album a;
        String tempAlbumName, tempAlbumDescription, tempAlbumImage;
        Integer tempID, tempArtistID, tempAlbumYear;

        tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        tempAlbumName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        tempArtistID = cursor.getInt(cursor.getColumnIndexOrThrow("artist_id"));
        tempAlbumDescription = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        tempAlbumImage = cursor.getString(cursor.getColumnIndexOrThrow("image"));
        tempAlbumYear = cursor.getInt(cursor.getColumnIndexOrThrow("year"));

        a = new Album(tempID, tempAlbumName, tempArtistID, tempAlbumYear, tempAlbumDescription, tempAlbumImage);
        return a;

    }

    public Boolean validateAlbum(String albumName){
        String view = "Select * from " + table + " where albumName= ?";
        Cursor cursor = db.rawQuery(view, new String[]{albumName});
        
        if (cursor.getCount() <= 0) {
            return false;
        }

        return true;
    }

}


