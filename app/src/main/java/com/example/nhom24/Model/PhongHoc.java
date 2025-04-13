package com.example.nhom24.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "phong_hoc")
public class PhongHoc {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String maPhongHoc;
    private String tenPhongHoc;

    public PhongHoc(String maPhongHoc, String tenPhongHoc) {
        this.maPhongHoc = maPhongHoc;
        this.tenPhongHoc = tenPhongHoc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaPhongHoc() {
        return maPhongHoc;
    }

    public void setMaPhongHoc(String maPhongHoc) {
        this.maPhongHoc = maPhongHoc;
    }

    public String getTenPhongHoc() {
        return tenPhongHoc;
    }

    public void setTenPhongHoc(String tenPhongHoc) {
        this.tenPhongHoc = tenPhongHoc;
    }
}