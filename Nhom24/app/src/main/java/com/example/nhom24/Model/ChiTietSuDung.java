package com.example.nhom24.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "chi_tiet_su_dung")
public class ChiTietSuDung {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int phongHocId;
    private int thietBiId;
    private String ngaySuDung;
    private String trangThai; // Thêm trường trạng thái

    public ChiTietSuDung(int phongHocId, int thietBiId, String ngaySuDung, String trangThai) {
        this.phongHocId = phongHocId;
        this.thietBiId = thietBiId;
        this.ngaySuDung = ngaySuDung;
        this.trangThai = trangThai;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhongHocId() {
        return phongHocId;
    }

    public void setPhongHocId(int phongHocId) {
        this.phongHocId = phongHocId;
    }

    public int getThietBiId() {
        return thietBiId;
    }

    public void setThietBiId(int thietBiId) {
        this.thietBiId = thietBiId;
    }

    public String getNgaySuDung() {
        return ngaySuDung;
    }

    public void setNgaySuDung(String ngaySuDung) {
        this.ngaySuDung = ngaySuDung;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}