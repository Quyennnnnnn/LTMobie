package com.example.nhom24.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nhom24.Model.User;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM user WHERE email = :TaiKhoan AND password = :MatKhau")
    User login(String TaiKhoan, String MatKhau);
    @Query("SELECT * FROM user WHERE email = :TaiKhoan")
    User findByUsername(String TaiKhoan); // Kiểm tra username đã tồn tại chưa
}
