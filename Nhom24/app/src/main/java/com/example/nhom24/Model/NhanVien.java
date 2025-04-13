package com.example.nhom24.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "nhanvien")
public class NhanVien {
    @PrimaryKey
    private String email;

    public NhanVien(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
