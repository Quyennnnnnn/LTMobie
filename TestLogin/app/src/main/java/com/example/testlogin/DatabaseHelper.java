package com.example.testlogin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";

    // Cột trong bảng
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PHONE + " TEXT UNIQUE, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Thêm tài khoản mới
    public boolean addUser(String phone, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    // Kiểm tra đăng nhập
    public boolean checkUser(String phone, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_PHONE + "=? AND " + COLUMN_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{phone, password});

        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }

    // Lấy danh sách tài khoản (hiển thị đầy đủ)
    // Lấy danh sách tài khoản (hiển thị đầy đủ)
    public void printAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);

        Log.d("DATABASE", "------ Dữ liệu hiện tại trong bảng users ------");

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String phone = cursor.getString(1);
                String email = cursor.getString(2);
                String password = cursor.getString(3);

                Log.d("DATABASE", "ID: " + id + " | SĐT: " + phone + " | Email: " + email + " | Mật khẩu: " + password);
            }
            while (cursor.moveToNext());
            cursor.close();
        }


    }
//    public void deleteAllUsersAndResetID() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DELETE FROM users"); // Xóa toàn bộ dữ liệu trong bảng
//        db.execSQL("DELETE FROM sqlite_sequence WHERE name='users'"); // Reset ID tự động tăng (nếu có)
//        db.close();
//    }

//    public void deleteAllUsersAndResetID() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DELETE FROM " + TABLE_USERS);
//        db.execSQL("DELETE FROM sqlite_sequence WHERE name='" + TABLE_USERS + "'"); // Reset ID
//        db.execSQL("VACUUM");
//        db.close();
//        Log.d("DATABASE", "Đã xóa toàn bộ dữ liệu và reset ID");
//    }

}









