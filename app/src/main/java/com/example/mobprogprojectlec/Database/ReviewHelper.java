package com.example.mobprogprojectlec.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.mobprogprojectlec.Model.Review;

import java.util.Vector;

public class ReviewHelper {

    private static String TABLE_NAME = "review";
    private Context ctx;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    public ReviewHelper(Context ctx) {
        this.ctx = ctx;
    }

    public void open() throws SQLException {
        databaseHelper = new DatabaseHelper(ctx);
        db = databaseHelper.getWritableDatabase();
    }

    public void close() throws SQLException {
        databaseHelper.close();
    }

    public Vector<Review> vReview(String userId) {
        Vector<Review> reviewVector = new Vector<>();

        String view = "Select * from " + TABLE_NAME + " where user_id= ?";

        Cursor cursor = db.rawQuery(view, new String[]{userId});

        cursor.moveToFirst();

        Review r;
        String tempComment, songName, albumName;
        Integer tempID, tempUser, tempSong, tempRating;

        if (cursor.getCount() > 0) {
            do {
                tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                tempComment = cursor.getString(cursor.getColumnIndexOrThrow("comment"));
                tempUser = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                tempSong = cursor.getInt(cursor.getColumnIndexOrThrow("song_id"));
                tempRating = cursor.getInt(cursor.getColumnIndexOrThrow("rating"));

                r = new Review(tempID, tempComment, tempUser, tempSong, tempRating);

                reviewVector.add(r);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return reviewVector;
    }

    public void insertReview(String comment, int userId, int songId, float rating) {
        String insert = "Select id from Review";

        Cursor cursor = db.rawQuery(insert, null);

        if (cursor != null && cursor.moveToLast()) {
            int tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            tempID++;
            insert = "Insert into Review values (" + tempID + ", '" + comment + "', " + userId + ", " + songId + ", " + rating + ")";
            db.execSQL(insert);
        } else {
            insert = "Insert into Review values (1, '" + comment + "', " + userId + ", " + songId + ", " + rating + ")";
            db.execSQL(insert);
        }
        cursor.close();
    }

    public void updateReview(int id, String comment, int userId, int songId, float rating) {
        String update = "Update Review set comment = '" + comment + "', user_id = " + userId + ", song_id = " + songId + ", rating = " + rating + " where id = " + id;
        db.execSQL(update);
    }

    public void deleteReview(int id) {
        String delete = "Delete from Review where id = " + id;
        db.execSQL(delete);
    }

}
