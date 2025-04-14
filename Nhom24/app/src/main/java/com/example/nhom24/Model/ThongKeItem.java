package com.example.nhom24.Model;

public class ThongKeItem {
    private String ngaySuDung;
    private String tenThietBi;
    private String maPhong;
    private int soLuongDaMuon; // Số lượng đã mượn

    public ThongKeItem(String ngaySuDung, String tenThietBi, String maPhong, int soLuongDaMuon) {
        this.ngaySuDung = ngaySuDung;
        this.tenThietBi = tenThietBi;
        this.maPhong = maPhong;
        this.soLuongDaMuon = soLuongDaMuon;
    }

    public String getNgaySuDung() {
        return ngaySuDung;
    }

    public void setNgaySuDung(String ngaySuDung) {
        this.ngaySuDung = ngaySuDung;
    }

    public String getTenThietBi() {
        return tenThietBi;
    }

    public void setTenThietBi(String tenThietBi) {
        this.tenThietBi = tenThietBi;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public int getSoLuongDaMuon() {
        return soLuongDaMuon;
    }

    public void setSoLuongDaMuon(int soLuongDaMuon) {
        this.soLuongDaMuon = soLuongDaMuon;
    }
}