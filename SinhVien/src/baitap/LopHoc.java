package baitap;

import java.util.ArrayList;
import java.util.List;

public class LopHoc {
    private String maLop;
    private String tenLop;
    private List<SinhVien> danhSachSinhVien;

    public LopHoc(String maLop, String tenLop) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.danhSachSinhVien = new ArrayList<>();
    }

    public String getMaLop() {
        return maLop;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void themSinhVien(SinhVien sv) {
        danhSachSinhVien.add(sv);
    }

    public void hienThiDanhSachSinhVien() {
        System.out.println("Lớp: " + tenLop + " (" + maLop + ")");
        for (SinhVien sv : danhSachSinhVien) {
            sv.hienThiThongTin();
            System.out.println("-----------------------");
        }
    }

    // Đếm số sinh viên theo rank điểm trung bình
    public void thongKeXepLoai() {
        int countA = 0, countB = 0, countC = 0, countD = 0, countF = 0;

        for (SinhVien sv : danhSachSinhVien) {
            float avg = sv.tinhDiemTrungBinh();

            if (avg >= 8.5) countA++;
            else if (avg >= 7.0) countB++;
            else if (avg >= 5.5) countC++;
            else if (avg >= 4.0) countD++;
            else countF++;
        }

        System.out.println("Thống kê xếp loại trong lớp " + tenLop + " (" + maLop + "):");
        System.out.println("  - A (>= 8.5) : " + countA);
        System.out.println("  - B (7.0 – 8.4) : " + countB);
        System.out.println("  - C (5.5 – 6.9) : " + countC);
        System.out.println("  - D (4.0 – 5.4) : " + countD);
        System.out.println("  - < D (< 4.0) : " + countF);
    }
}
