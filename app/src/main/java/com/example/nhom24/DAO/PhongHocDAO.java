package com.example.nhom24.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nhom24.Model.PhongHoc;

import java.util.List;

@Dao
public interface PhongHocDAO {
    @Query("SELECT * FROM phong_hoc")
    List<PhongHoc> getAll();
    @Insert
    void insert(PhongHoc phongHoc);

    @Update
    void update(PhongHoc phongHoc);

    @Delete
    void delete(PhongHoc phongHoc);


}
