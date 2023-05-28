package com.example.mobprogprojectlec.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.mobprogprojectlec.Model.Artist;

import java.util.Vector;

public class ArtistHelper {

    private static String table = "Artist";
    private Context ctx;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private ArtistHelper artistHelper;

    public ArtistHelper(Context ctx) {
        this.ctx = ctx;
    }

    public void open() throws SQLException {
        databaseHelper = new DatabaseHelper(ctx);
        db = databaseHelper.getWritableDatabase();
    }

    public void close() throws SQLException {
        databaseHelper.close();
    }

    public Vector<Artist> vArtist() {
        Vector<Artist> artistVector = new Vector<>();
        String view = "Select * from " + table;
        Cursor cursor = db.rawQuery(view, null);
        cursor.moveToFirst();
        Artist a;
        String tempArtistName, tempArtistDescription, tempArtistImage;
        Integer tempID;

        if (cursor.getCount() > 0) {
            do {
                tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                tempArtistName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                tempArtistDescription = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                tempArtistImage = cursor.getString(cursor.getColumnIndexOrThrow("image"));

                a = new Artist(tempID, tempArtistName, tempArtistDescription, tempArtistImage);
                artistVector.add(a);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        cursor.close();
        return artistVector;

    }

    public int insertArtist(String artistName, String artistDescription, String artistImage) {
        String insert = "SELECT id FROM Artist";
        Cursor cursor = db.rawQuery(insert, null);

        ContentValues cv = new ContentValues();

        int artistID;
        if (cursor != null && cursor.moveToLast()){
            int tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            tempID++;
            artistID = tempID;
            cv.put("id", artistID);
            cv.put("name", artistName);
            cv.put("description", artistDescription);
            cv.put("image", artistImage);

            db.insert(table, null, cv);
        }
        else {
            artistID = 1;
            cv.put("id", artistID);
            cv.put("name", artistName);
            cv.put("description", artistDescription);
            cv.put("image", artistImage);

            db.insert(table, null, cv);
        }

        cursor.close();
        return artistID;
    }


    public Artist getArtist(String artistName) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Artist artist = null;

        try {
            db = databaseHelper.getReadableDatabase();
            String selection = "name=?";
            String[] selectionArgs = {artistName};
            cursor = db.query(table, null, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                artist = new Artist(id, name, description, image);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return artist;
    }




    public Boolean validateArtist(String name){
        String search = "Select * from " + table + " where name=?";

        Cursor cursor = db.rawQuery(search, new String[]{name});
        if (cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }

}
