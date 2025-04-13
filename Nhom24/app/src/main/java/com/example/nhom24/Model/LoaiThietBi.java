package com.example.nhom24.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "loaithietbi")
public class LoaiThietBi {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String mathietbi;
    private String tenthietbi;

    private String emailNhanVien;
    public LoaiThietBi(String mathietbi, String tenthietbi) {
        this.mathietbi = mathietbi;
        this.tenthietbi = tenthietbi;
    }

//    public LoaiThietBi(String mathietbi, String tenthietbi, String emailNhanVien) {
//
//        this.mathietbi = mathietbi;
//        this.tenthietbi = tenthietbi;
//        this.emailNhanVien = emailNhanVien;
//    }

    public String getEmailNhanVien() {
        return emailNhanVien;
    }

    public void setEmailNhanVien(String emailNhanVien) {
        this.emailNhanVien = emailNhanVien;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMathietbi() {
        return mathietbi;
    }

    public void setMathietbi(String mathietbi) {
        this.mathietbi = mathietbi;
    }

    public String getTenthietbi() {
        return tenthietbi;
    }

    public void setTenthietbi(String tenthietbi) {
        this.tenthietbi = tenthietbi;
    }
}
