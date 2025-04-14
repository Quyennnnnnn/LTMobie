package com.example.nhom24.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nhom24.Model.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);
    @Update
    int updateUser(User user);

    @Query("SELECT * FROM user WHERE email = :TaiKhoan AND password = :MatKhau")
    User login(String TaiKhoan, String MatKhau);
    @Query("SELECT * FROM user WHERE email = :TaiKhoan")
    User findByUsername(String TaiKhoan); // Kiểm tra username đã tồn tại chưa
    @Query("SELECT * FROM user")
    List<User> getAllUsers();
}
