package com.example.mobprogprojectlec.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.mobprogprojectlec.Model.Album;
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
        long tempDate;

        if (cursor.getCount() > 0) {
            do {
                tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                tempComment = cursor.getString(cursor.getColumnIndexOrThrow("comment"));
                tempUser = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                tempSong = cursor.getInt(cursor.getColumnIndexOrThrow("song_id"));
                tempRating = cursor.getInt(cursor.getColumnIndexOrThrow("rating"));
                tempDate = cursor.getLong(cursor.getColumnIndexOrThrow("date"));

                r = new Review(tempID, tempComment, tempUser, tempSong, tempRating, tempDate);

                reviewVector.add(r);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return reviewVector;
    }

    public Review fetchReview(int reviewId) {
        String view = "Select * from " + TABLE_NAME + " where id= ?";

        Cursor cursor = db.rawQuery(view, new String[]{String.valueOf(reviewId)});

        cursor.moveToFirst();

        Review r;
        String tempComment;
        Integer tempID, tempUser, tempSong;
        Float tempRating;
        long tempDate;

        if (cursor.getCount() > 0) {
            tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            tempComment = cursor.getString(cursor.getColumnIndexOrThrow("comment"));
            tempUser = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
            tempSong = cursor.getInt(cursor.getColumnIndexOrThrow("song_id"));
            tempRating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"));
            tempDate = cursor.getLong(cursor.getColumnIndexOrThrow("timestamp"));

            r = new Review(tempID, tempComment, tempUser, tempSong, tempRating, tempDate);
        } else {
            r = null;
        }
        cursor.close();
        return r;
    }

    public Vector<Review> viewReviewbyUserID(int userID) {
        Vector<Review> reviewVector = new Vector<>();
        String view = "Select * from " + TABLE_NAME + " where user_id = ?";
        Cursor cursor = db.rawQuery(view, new String[]{String.valueOf(userID)});

        cursor.moveToFirst();

        Review r;
        String tempComment;
        Integer tempID, tempUser, tempSong;
        Float tempRating;
        Long tempDate;

        if (cursor.getCount() > 0) {
            do {
                tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                tempComment = cursor.getString(cursor.getColumnIndexOrThrow("comment"));
                tempUser = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                tempSong = cursor.getInt(cursor.getColumnIndexOrThrow("song_id"));
                tempRating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"));
                tempDate = cursor.getLong(cursor.getColumnIndexOrThrow("timestamp"));

                r = new Review(tempID, tempComment, tempUser, tempSong, tempRating, tempDate);
                reviewVector.add(r);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return reviewVector;
    }

    public Vector<Review> viewReview() {
        Vector<Review> reviewVector = new Vector<>();
        String view = "Select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(view, null);
        cursor.moveToFirst();

        Review r;
        String tempComment;
        Integer tempID, tempUser, tempSong;
        Float tempRating;
        Long tempDate;

        if (cursor.getCount() > 0) {
            do {
                tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                tempComment = cursor.getString(cursor.getColumnIndexOrThrow("comment"));
                tempUser = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                tempSong = cursor.getInt(cursor.getColumnIndexOrThrow("song_id"));
                tempRating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"));
                tempDate = cursor.getLong(cursor.getColumnIndexOrThrow("timestamp"));

                r = new Review(tempID, tempComment, tempUser, tempSong, tempRating, tempDate);
                reviewVector.add(r);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return reviewVector;
    }

    public void insertReview(String comment, int userId, int songId, float rating, long date) {
        String insert = "Select id from Review";

        Cursor cursor = db.rawQuery(insert, null);

        if (cursor != null && cursor.moveToLast()) {
            int tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            tempID++;
            insert = "Insert into Review values (" + tempID + ", '" + comment + "', " + userId + ", " + songId + ", " + rating + ", " + date + ")";
            db.execSQL(insert);
        } else {
            insert = "Insert into Review values (1, '" + comment + "', " + userId + ", " + songId + ", " + rating + ", " + date + ")";
            db.execSQL(insert);
        }
        cursor.close();
    }

    public void updateReview(int id, String comment, float rating, long date) {
        String update = "Update Review set comment = '" + comment + "', rating = " + rating + ", timestamp = " + date + " where id = " + id;
        db.execSQL(update);
    }

    public void deleteReview(int id) {
        String delete = "Delete from Review where id = " + id;
        db.execSQL(delete);
    }

}
