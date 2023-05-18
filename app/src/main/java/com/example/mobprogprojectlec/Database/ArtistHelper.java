package com.example.mobprogprojectlec.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.mobprogprojectlec.Model.Artist;

import java.util.Vector;

public class ArtistHelper {

    private static String table = "artist";
    private Context ctx;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

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

    public void insertArtist(String artistName, String artistDescription, String artistImage) {
        String insert = "Select id from Artist";
        Cursor cursor = db.rawQuery(insert, null);
        if (cursor != null && cursor.moveToLast()){
            int tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            tempID++;
            insert = "Insert into Artist values (" + tempID + ", '" + artistName + "', '" + artistDescription + "', '" + artistImage + "')";
            db.execSQL(insert);
        }
        else {
            insert = "Insert into Artist values (1, '" + artistName + "', '" + artistDescription + "', '" + artistImage + "')";
            db.execSQL(insert);
        }
        cursor.close();
    }

    public Artist getArtist(String artistName) {
        String view = "Select * from " + table + " where artistName=? limit 1";

        Cursor cursor = db.rawQuery(view, new String[]{artistName});

        cursor.moveToFirst();
        if (cursor.getCount() <= 0) {
            cursor.close();
            return null;
        }

        Artist a;
        String tempArtistName, tempArtistDescription, tempArtistImage;
        Integer tempID;

        tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        tempArtistName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        tempArtistDescription = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        tempArtistImage = cursor.getString(cursor.getColumnIndexOrThrow("image"));

        a = new Artist(tempID, tempArtistName, tempArtistDescription, tempArtistImage);
        cursor.close();
        return a;
    }

}
