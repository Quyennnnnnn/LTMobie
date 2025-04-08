package com.example.nhom24.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "thietbi",
        foreignKeys = @ForeignKey(entity = LoaiThietBi.class,
                parentColumns = "id",
                childColumns = "loaiThietBiId",
                onDelete = ForeignKey.CASCADE))
public class ThietBi {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String maThietBi;
    private String tenThietBi;
    private String xuatXu;
    private int soLuong;
    private int loaiThietBiId; // Khóa ngoại tham chiếu đến LoaiThietBi

    public ThietBi(String maThietBi, String tenThietBi, String xuatXu, int soLuong, int loaiThietBiId) {
        this.maThietBi = maThietBi;
        this.tenThietBi = tenThietBi;
        this.xuatXu = xuatXu;
        this.soLuong = soLuong;
        this.loaiThietBiId = loaiThietBiId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaThietBi() {
        return maThietBi;
    }

    public void setMaThietBi(String maThietBi) {
        this.maThietBi = maThietBi;
    }

    public String getTenThietBi() {
        return tenThietBi;
    }

    public void setTenThietBi(String tenThietBi) {
        this.tenThietBi = tenThietBi;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getLoaiThietBiId() {
        return loaiThietBiId;
    }

    public void setLoaiThietBiId(int loaiThietBiId) {
        this.loaiThietBiId = loaiThietBiId;
    }
}