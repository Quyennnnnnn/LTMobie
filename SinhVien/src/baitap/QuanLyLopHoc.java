package baitap;

import java.util.ArrayList;
import java.util.List;

public class QuanLyLopHoc {
    private List<LopHoc> danhSachLopHoc;

    public QuanLyLopHoc() {
        this.danhSachLopHoc = new ArrayList<>();
    }

    public void themLopHoc(LopHoc lop) {
        danhSachLopHoc.add(lop);
    }

    public void hienThiDanhSachLopHoc() {
        for (LopHoc lop : danhSachLopHoc) {
            System.out.println("📌 " + lop.getTenLop() + " (" + lop.getMaLop() + ")");
        }
    }

    public LopHoc timLopHoc(String maLop) {
        for (LopHoc lop : danhSachLopHoc) {
            if (lop.getMaLop().equalsIgnoreCase(maLop)) {
                return lop;
            }
        }
        return null;
    }
}
