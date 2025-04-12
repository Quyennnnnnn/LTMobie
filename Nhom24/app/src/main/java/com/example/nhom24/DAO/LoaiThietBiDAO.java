package com.example.nhom24.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nhom24.Model.LoaiThietBi;

import java.util.List;

@Dao
public interface LoaiThietBiDAO {
    @Query("SELECT * FROM loaithietbi")
    List<LoaiThietBi> getAll();

    @Insert
    void insert(LoaiThietBi loaiThietBi);

    @Update
    void update(LoaiThietBi loaiThietBi);

    @Delete
    void delete(LoaiThietBi loaiThietBi);

    @Query("SELECT * FROM loaithietbi WHERE mathietbi LIKE :query OR tenthietbi LIKE :query")
    List<LoaiThietBi> search(String query);
}