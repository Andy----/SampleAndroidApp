package com.andrew.sampleandroidapp.helper;

/**
 * Created by Andrew on 07/04/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.andrew.sampleandroidapp.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "sample_app_db";



    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE user(user_id LONG PRIMARY KEY, user_name VARCHAR, password VARCHAR);";
        db.execSQL(CREATE_USER_TABLE);
        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS user");
//        db.execSQL("DROP TABLE IF EXISTS jobapplication");
        db.execSQL("DROP TABLE IF EXISTS questiontable");

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(int userId, String userName, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("user_name", userName);
        values.put("password", password);

        // Inserting Row
        db.insert("user", null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + userName);
    }

    /**
     * Check if user exists in SQLite DB
     */
    public boolean authenticateUser(String userName, String password) {
        HashMap<String, String> temp = getUserDetails(userName);

        if(!temp.isEmpty()) {
            if(temp.get("password").equals(password)) {
                return true;
            }else { return false; }
        } else {
            return false;
        }
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails(String userName) {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM user where user_name = '" + userName + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("user_id", cursor.getString(0));
            user.put("user_name",cursor.getString(1));
            user.put("password" , cursor.getString(2));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Delete all tables
     * */
    public void deleteTable(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(tableName, null, null);
        db.close();

        Log.d(TAG, "Deleted table from sqlite");
    }

}