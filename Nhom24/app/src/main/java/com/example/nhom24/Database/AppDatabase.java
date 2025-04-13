package com.example.nhom24.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.nhom24.DAO.ChiTietSuDungDAO;
import com.example.nhom24.DAO.LoaiThietBiDAO;
import com.example.nhom24.DAO.ThietBiDAO;
import com.example.nhom24.DAO.PhongHocDAO;
import com.example.nhom24.DAO.UserDAO;
import com.example.nhom24.Model.ChiTietSuDung;
import com.example.nhom24.Model.LoaiThietBi;
import com.example.nhom24.Model.PhongHoc;
import com.example.nhom24.Model.ThietBi;
import com.example.nhom24.Model.User;

@Database(entities = {User.class, LoaiThietBi.class, ThietBi.class, PhongHoc.class, ChiTietSuDung.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "app9.db";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context)
    {
        if(instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        }
        return instance;
    }



    public abstract UserDAO userDAO();
    public abstract LoaiThietBiDAO loaiThietBiDAO();
    public abstract ThietBiDAO thietBiDAO();
    public abstract PhongHocDAO phongHocDAO();
    public abstract ChiTietSuDungDAO chiTietSuDungDAO();
}