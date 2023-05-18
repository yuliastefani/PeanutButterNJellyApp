package com.example.mobprogprojectlec.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper dbInstance;

    public static synchronized DatabaseHelper getInstance(Context ctx){
        if(dbInstance == null){
            dbInstance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return dbInstance;
    }

    public DatabaseHelper(@Nullable Context context) {
        super(context,"PeaButNJelly.db",null,1);
    }

    public void onConfigure(SQLiteDatabase db){
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String artist = "CREATE TABLE IF NOT EXISTS Artist(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name varchar(100), description varchar(255), image varchar(255))";
        db.execSQL(artist);
        String album = "CREATE TABLE IF NOT EXISTS Album(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name varchar(100), artist_id int, year int(4), description varchar(255), image varchar(255), FOREIGN KEY(artist_id) REFERENCES Artist(id))";
        db.execSQL(album);
        String song = "CREATE TABLE IF NOT EXISTS Song(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, title varchar(100), artist_id int, album_id int, genre varchar(100), FOREIGN KEY(artist_id) REFERENCES Artist(id), FOREIGN KEY(album_id) REFERENCES Album(id))";
        db.execSQL(song);
        String user = "CREATE TABLE IF NOT EXISTS User(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, username varchar(100), email varchar(100),password varchar(100))";
        db.execSQL(user);
        String review = "CREATE TABLE IF NOT EXISTS Review(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, comment varchar(255), user_id int, song_id int, rating int, FOREIGN KEY(user_id) REFERENCES User(id), FOREIGN KEY(song_id) REFERENCES Song(id))";
        db.execSQL(review);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Artist");
        db.execSQL("drop table if exists Album");
        db.execSQL("drop table if exists Song");
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists Review");
        onCreate(db);
        db.close();
    }

}
