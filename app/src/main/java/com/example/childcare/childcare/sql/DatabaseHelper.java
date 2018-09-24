package com.example.childcare.childcare.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.childcare.childcare.Constantss;
import com.example.childcare.childcare.model.Child;
import com.example.childcare.childcare.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ChildCare";

    // Table names
    private static final String TABLE_USER = "users";
    private static final String TABLE_ADMIN = "admins";
    private static final String TABLE_CHILDREN = "children";
    private static final String TABLE_ACTIVITIES = "activities";
    private static final String TABLE_PHOTOS = "photos";
    private static final String TABLE_CHILD_ACTIVITIES = "child_activities";

    // Common columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_CREATED_AT = "created_at";
    private static final String COLUMN_CHILD_ID = "child_id";


    // User Table Columns names
    private static final String COLUMN_USER_FNAME = "fname";
    private static final String COLUMN_USER_LNAME = "lname";
    private static final String COLUMN_USER_PHONE = "phone";
    private static final String COLUMN_USER_ADDRESS = "address";
    private static final String COLUMN_USER_DOB = "dob";
    private static final String COLUMN_USER_EMAIL = "email";

    // Admin Table Columns names
    private static final String COLUMN_ADMIN_USERNAME = "username";

    // Children Table Columns names
    private static final String COLUMN_CHILD_NAME = "name";
    private static final String COLUMN_CHILD_AGE = "age";
    private static final String COLUMN_CHILD_SCHEDULE = "schedule";
    private static final String COLUMN_CHILD_ADDRESS = "address";
    private static final String COLUMN_CHILD_DOB = "dob";
    private static final String COLUMN_PARENT_ID = "user_id";

    // Activities Table Columns names
    private static final String COLUMN_ACTIVITY_NAME = "name";

    // Photos Table Columns names
    private static final String COLUMN_PHOTO_DESCRIPTION = "description";
    private static final String COLUMN_PHOTO_IMAGE = "image";

    // Child_Activities Table Columns names
    private static final String COLUMN_ACTIVITY_ID = "activity_id";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_FNAME + " TEXT,"
            + COLUMN_USER_LNAME + " TEXT," + COLUMN_USER_PHONE + " TEXT," + COLUMN_USER_ADDRESS + " TEXT,"
            + COLUMN_USER_DOB + " TEXT," + COLUMN_USER_EMAIL + " TEXT," + COLUMN_PASSWORD + " TEXT" + ")";

    private String CREATE_ADMIN_TABLE = "CREATE TABLE " + TABLE_ADMIN + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ADMIN_USERNAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT" + ")";

    private String CREATE_CHILDREN_TABLE = "CREATE TABLE " + TABLE_CHILDREN + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CHILD_NAME + " TEXT,"
            + COLUMN_CHILD_AGE + " INTEGER," + COLUMN_CHILD_ADDRESS + " TEXT," + COLUMN_CHILD_DOB + " TEXT,"
            + COLUMN_CHILD_SCHEDULE + " TEXT," + COLUMN_PARENT_ID + " INTEGER," + COLUMN_CREATED_AT + " DATETIME,"
            + " FOREIGN KEY (" + COLUMN_PARENT_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID + "));";

    private String CREATE_ACTIVITIES_TABLE = "CREATE TABLE " + TABLE_ACTIVITIES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ACTIVITY_NAME + " TEXT" + ")";

    private String CREATE_PHOTOS_TABLE = "CREATE TABLE " + TABLE_PHOTOS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_PHOTO_DESCRIPTION + " TEXT,"
            + COLUMN_CHILD_ID + " INTEGER," + COLUMN_PHOTO_IMAGE + " BLOB," + COLUMN_CREATED_AT + " DATETIME,"
            + " FOREIGN KEY (" + COLUMN_CHILD_ID + ") REFERENCES " + TABLE_CHILDREN + "(" + COLUMN_ID + "));";

    private String CREATE_CHILD_ACTIVITIES_TABLE = "CREATE TABLE " + TABLE_CHILD_ACTIVITIES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ACTIVITY_ID + " INTEGER,"
            + COLUMN_CHILD_ID + " INTEGER,"
            + " FOREIGN KEY (" + COLUMN_ACTIVITY_ID + ") REFERENCES " + TABLE_ACTIVITIES + "(" + COLUMN_ID + "),"
            + " FOREIGN KEY (" + COLUMN_CHILD_ID + ") REFERENCES " + TABLE_CHILDREN + "(" + COLUMN_ID + "));";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_ADMIN_TABLE = "DROP TABLE IF EXISTS " + TABLE_ADMIN;
    private String DROP_CHILDREN_TABLE = "DROP TABLE IF EXISTS " + TABLE_CHILDREN;
    private String DROP_ACTIVITIES_TABLE = "DROP TABLE IF EXISTS " + TABLE_ACTIVITIES;
    private String DROP_PHOTOS_TABLE = "DROP TABLE IF EXISTS " + TABLE_PHOTOS;
    private String DROP_CHILD_ACTIVITIES_TABLE = "DROP TABLE IF EXISTS " + TABLE_CHILD_ACTIVITIES;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_ADMIN_TABLE);
        db.execSQL(CREATE_CHILDREN_TABLE);
        db.execSQL(CREATE_ACTIVITIES_TABLE);
        db.execSQL(CREATE_PHOTOS_TABLE);
        db.execSQL(CREATE_CHILD_ACTIVITIES_TABLE);

        ContentValues values = new ContentValues();
        values.put(COLUMN_ADMIN_USERNAME, "Administrator");
        values.put(COLUMN_PASSWORD, "12345678");
        db.insert(TABLE_ADMIN, null, values);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop Tables if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_ADMIN_TABLE);
        db.execSQL(DROP_CHILDREN_TABLE);
        db.execSQL(DROP_ACTIVITIES_TABLE);
        db.execSQL(DROP_PHOTOS_TABLE);
        db.execSQL(DROP_CHILD_ACTIVITIES_TABLE);

        // Create tables again
        onCreate(db);
    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_FNAME, user.getFname());
        values.put(COLUMN_USER_LNAME, user.getLname());
        values.put(COLUMN_USER_PHONE, user.getPhone());
        values.put(COLUMN_USER_ADDRESS, user.getAddress());
        values.put(COLUMN_USER_DOB, user.getDob());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    /**
     * This method is to create child record
     *
     * @param child
     */
    public void addChild(Child child) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CHILD_NAME, child.getName());
        values.put(COLUMN_CHILD_AGE, child.getAge());
        values.put(COLUMN_CHILD_ADDRESS, child.getAddress());
        values.put(COLUMN_CHILD_SCHEDULE, child.getSchedule());
        values.put(COLUMN_CHILD_DOB, child.getDob());
        values.put(COLUMN_PARENT_ID, child.getUser_id());

        // Inserting Row
        db.insert(TABLE_CHILDREN, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_FNAME,
                COLUMN_USER_LNAME,
                COLUMN_USER_PHONE,
                COLUMN_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_LNAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
                user.setFname(cursor.getString(cursor.getColumnIndex(COLUMN_USER_FNAME)));
                user.setLname(cursor.getString(cursor.getColumnIndex(COLUMN_USER_LNAME)));
                user.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PHONE)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method is to fetch all children and return the list of user records
     *
     * @return list
     */
    public ArrayList<Child> getAllChildren() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_ID,
                COLUMN_CHILD_NAME,
                COLUMN_CHILD_AGE,
                COLUMN_CHILD_DOB,
                COLUMN_CHILD_ADDRESS,
                COLUMN_PARENT_ID,
                COLUMN_CHILD_SCHEDULE
        };
        // sorting orders
        String sortOrder =
                COLUMN_CHILD_NAME + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_CHILDREN, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order

        Constantss.childList.clear();

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Child child = new Child();
                child.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
                child.setName(cursor.getString(cursor.getColumnIndex(COLUMN_CHILD_NAME)));
                child.setAge(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_CHILD_AGE))));
                child.setDob(cursor.getString(cursor.getColumnIndex(COLUMN_CHILD_DOB)));
                child.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_CHILD_ADDRESS)));
                child.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PARENT_ID))));
                child.setSchedule(cursor.getString(cursor.getColumnIndex(COLUMN_CHILD_SCHEDULE)));
                // Adding user record to list
                Constantss.childList.add(child);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return Constantss.childList;
    }

    /**
     * This method is to fetch my children and return the list of user records
     *
     * @return list
     */
    public ArrayList<Child> getMyChildren(int id) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_ID,
                COLUMN_CHILD_NAME,
                COLUMN_CHILD_AGE,
                COLUMN_CHILD_DOB,
                COLUMN_CHILD_ADDRESS,
                COLUMN_PARENT_ID,
                COLUMN_CHILD_SCHEDULE
        };
        // sorting orders
        String sortOrder =
                COLUMN_CHILD_NAME + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_CHILDREN, //Table to query
                columns,    //columns to return
                COLUMN_PARENT_ID + " = " + id,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order

        Constantss.childList.clear();

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Child child = new Child();
                child.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
                child.setName(cursor.getString(cursor.getColumnIndex(COLUMN_CHILD_NAME)));
                child.setAge(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_CHILD_AGE))));
                child.setDob(cursor.getString(cursor.getColumnIndex(COLUMN_CHILD_DOB)));
                child.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_CHILD_ADDRESS)));
                child.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PARENT_ID))));
                child.setSchedule(cursor.getString(cursor.getColumnIndex(COLUMN_CHILD_SCHEDULE)));
                // Adding user record to list
                Constantss.childList.add(child);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return Constantss.childList;
    }

    /**
     * This method is to fetch a single childs
     *
     * @return list
     */
    public Child getChild(int id) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_ID,
                COLUMN_CHILD_NAME,
                COLUMN_CHILD_AGE,
                COLUMN_CHILD_DOB,
                COLUMN_CHILD_ADDRESS,
                COLUMN_PARENT_ID,
                COLUMN_CHILD_SCHEDULE
        };

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_CHILDREN, //Table to query
                columns,    //columns to return
                COLUMN_ID + " = " + id,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order


        // Traversing through all rows and adding to list
        if (cursor != null)
            cursor.moveToFirst();

        Child child = new Child();
        child.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
        child.setName(cursor.getString(cursor.getColumnIndex(COLUMN_CHILD_NAME)));
        child.setAge(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_CHILD_AGE))));
        child.setDob(cursor.getString(cursor.getColumnIndex(COLUMN_CHILD_DOB)));
        child.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_CHILD_ADDRESS)));
        child.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PARENT_ID))));
        child.setSchedule(cursor.getString(cursor.getColumnIndex(COLUMN_CHILD_SCHEDULE)));

        cursor.close();
        db.close();

        // return user list
        return child;
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_FNAME, user.getFname());
        values.put(COLUMN_USER_LNAME, user.getLname());
        values.put(COLUMN_USER_PHONE, user.getPhone());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to update child record
     *
     * @param child
     */
    public void updateChild(Child child) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CHILD_NAME, child.getName());
        values.put(COLUMN_CHILD_AGE, child.getAge());
        values.put(COLUMN_CHILD_ADDRESS, child.getAddress());
        values.put(COLUMN_CHILD_SCHEDULE, child.getSchedule());
        values.put(COLUMN_CHILD_DOB, child.getDob());
        values.put(COLUMN_PARENT_ID, child.getUser_id());

        // updating row
        db.update(TABLE_CHILDREN, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(child.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param child
     */
    public void deleteChild(Child child) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_CHILDREN, COLUMN_ID + " = ?",
                new String[]{String.valueOf(child.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public int GetUserID(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String where = COLUMN_USER_EMAIL+" LIKE '%"+email+"%'";
        Cursor c = db.query(true, TABLE_USER, null,
                where, null, null, null, null, null);
        if(c.getCount()>0  && c.moveToFirst())
            return Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_ID)));
        else
            return 0;
    }

    /**
     * This method to check user exist or not
     *
     * @param username
     * @return true/false
     */
    public boolean checkAdmin(String username) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_ADMIN_USERNAME + " = ?";

        // selection argument
        String[] selectionArgs = {username};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_ADMIN, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param username
     * @param password
     * @return true/false
     */
    public boolean checkAdmin(String username, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_ADMIN_USERNAME + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {username, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from admin table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT id FROM admin WHERE username = 'jack@androidtutorialshub.com' AND password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_ADMIN, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /**
     * get datetime
     * */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
