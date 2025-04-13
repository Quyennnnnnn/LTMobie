package com.example.nhom24.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nhom24.Model.NhanVien;
import com.example.nhom24.Model.ThietBi;

import java.util.List;

@Dao
public interface NhanVienDAO {
    @Query("SELECT * FROM nhanvien")
    List<NhanVien> getAll();

    @Insert
    void insert(NhanVien nhanVien);

    @Delete
    void delete(NhanVien nhanVien);

}
