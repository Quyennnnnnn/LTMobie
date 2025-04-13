package com.example.nhom24.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nhom24.Model.ChiTietSuDung;

import java.util.List;

@Dao
public interface ChiTietSuDungDAO {
    @Insert
    void insert(ChiTietSuDung chiTietSuDung);

    @Update
    void update(ChiTietSuDung chiTietSuDung);

    @Delete
    void delete(ChiTietSuDung chiTietSuDung);

    @Query("SELECT * FROM chi_tiet_su_dung")
    List<ChiTietSuDung> getAllChiTietSuDung();
}