package com.example.mobprogprojectlec.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.mobprogprojectlec.Model.User;

import java.util.Vector;

public class UserHelper {

    private static String table = "User";
    private Context ctx;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public UserHelper(Context ctx) {
        this.ctx = ctx;
    }

    public void open() throws SQLException {
        dbHelper = new DatabaseHelper(ctx);
        db = dbHelper.getWritableDatabase();
    }

    public void close() throws SQLException{
        dbHelper.close();
    }

    public Vector<User> vUser(){
        Vector<User> userVector = new Vector<>();

        String view = "Select * from User";

        Cursor cursor = db.rawQuery(view,null);

        cursor.moveToFirst();

        User u;
        String tempUsername,tempEmail,tempPassword;
        Integer tempID;

        if(cursor.getCount() > 0){
            do{
                tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                tempEmail = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                tempUsername = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                tempPassword = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                u= new User(tempID,tempUsername,tempEmail,tempPassword);
                userVector.add(u);
                cursor.moveToNext();
            }while(!cursor.isAfterLast());
        }
        cursor.close();
        return userVector;
    }

    public void insertUser(String username, String email, String password){
        String insert = "Select id from User";
        Cursor cursor = db.rawQuery(insert,null);

        int tempID = 0;

        if (cursor!=null && cursor.moveToLast()) {
            tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            int newID = tempID + 1;
            insert = "Insert into User values (" + newID + ",'" + username + "','" + email + "','" + password + "')";
            db.execSQL(insert);
        }else {
            insert = "Insert into User values ('1','" + username + "','" + email + "','" + password + "')";
            db.execSQL(insert);
        }

        cursor.close();

    }

    public Boolean validateUsername(String username){
        String search = "Select * from user where username= ?";

        Cursor cursor = db.rawQuery(search, new String[]{username});
        if(cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean validateUser(String username,String password){
        String search = "Select * from user where username = ? and password = ?";

        Cursor cursor = db.rawQuery(search,new String[]{username,password});
        if(cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public User getUser(String username){
        String view = "Select * from User where username= ? limit 1";

        Cursor cursor = db.rawQuery(view,new String[]{username});

        cursor.moveToFirst();

        if(cursor.getCount()<=0){
            return null;
        }

        User u;
        String tempUsername,tempEmail,tempPassword;
        Integer tempID;

        tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        tempUsername = cursor.getString(cursor.getColumnIndexOrThrow("username"));
        tempEmail = cursor.getString(cursor.getColumnIndexOrThrow("email"));
        tempPassword = cursor.getString(cursor.getColumnIndexOrThrow("password"));
        u = new User(tempID, tempUsername, tempEmail, tempPassword);

        return u;
    }

    public void updateUser(int id,String username, String email, String password){
        String update = "Update User set username = '"+username+"',email = '"+email+"', password = '"+password+"' where id = '"+id+"'";
        db.execSQL(update);
    }



}
