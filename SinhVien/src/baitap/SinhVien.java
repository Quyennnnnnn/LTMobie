package baitap;

import java.util.ArrayList;
import java.util.List;

public class SinhVien {
    private static int idCounter = 1;
    private int id;
    private String firstName;
    private String lastName;
    private String dob;
    private String address;
    private List<MonHoc> danhSachMonHoc;

    public SinhVien(String firstName, String lastName, String dob, String address) {
        this.id = idCounter++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.address = address;
        this.danhSachMonHoc = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public List<MonHoc> getDanhSachMonHoc() {
        return danhSachMonHoc;
    }

    public void themMonHoc(MonHoc monHoc) {
        danhSachMonHoc.add(monHoc);
    }

    public void hienThiThongTin() {
        System.out.println("Mã SV: " + id + " - " + getFullName() + " - Ngày sinh: " + dob + " - Địa chỉ: " + address);
        System.out.println("Danh sách môn học:");
        for (MonHoc mh : danhSachMonHoc) {
            mh.hienThiThongTin();
        }
        System.out.println("  -> Điểm trung bình: " + tinhDiemTrungBinh());
    }

    // Tính điểm trung bình của sinh viên
    public float tinhDiemTrungBinh() {
        if (danhSachMonHoc.isEmpty()) return 0;
        float sum = 0;
        for (MonHoc mh : danhSachMonHoc) {
            sum += mh.getDiem();
        }
        return sum / danhSachMonHoc.size();
    }
}
