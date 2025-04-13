package com.example.nhom24.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nhom24.Model.ThietBi;

import java.util.List;

@Dao
public interface ThietBiDAO {
    @Query("SELECT * FROM thietbi")
    List<ThietBi> getAll();

    @Insert
    void insert(ThietBi thietBi);

    @Update
    void update(ThietBi thietBi);

    @Delete
    void delete(ThietBi thietBi);

    @Query("SELECT * FROM thietbi WHERE maThietBi LIKE :query OR tenThietBi LIKE :query")
    List<ThietBi> search(String query);

}