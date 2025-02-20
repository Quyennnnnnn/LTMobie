package baitap;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QuanLyLopHoc qllh = new QuanLyLopHoc();

        // Tạo các lớp học
        LopHoc it01 = new LopHoc("IT-01", "Lớp Công nghệ thông tin 1");
        LopHoc it02 = new LopHoc("IT-02", "Lớp Công nghệ thông tin 2");

        // Tạo sinh viên và thêm vào lớp
        SinhVien sv1 = new SinhVien("Nguyen", "An", "2002-03-15", "Hà Nội");
        sv1.themMonHoc(new MonHoc("Lập trình hướng đối tượng", 8.5f));
        sv1.themMonHoc(new MonHoc("Quản lý dự án", 7.0f));

        SinhVien sv2 = new SinhVien("Tran", "Binh", "2001-07-20", "TP. HCM");
        sv2.themMonHoc(new MonHoc("Cơ sở dữ liệu", 9.0f));
        sv2.themMonHoc(new MonHoc("Lập trình ứng dụng", 8.0f));

        SinhVien sv3 = new SinhVien("Le", "Hoa", "2002-08-10", "Đà Nẵng");
        sv3.themMonHoc(new MonHoc("Lập trình hướng đối tượng", 5.5f));
        sv3.themMonHoc(new MonHoc("Quản lý dự án", 6.8f));
        sv3.themMonHoc(new MonHoc("Cơ sở dữ liệu", 4.2f));

        SinhVien sv4 = new SinhVien("Pham", "Minh", "2003-05-22", "Hải Phòng");
        sv4.themMonHoc(new MonHoc("Lập trình hướng đối tượng", 3.0f));
        sv4.themMonHoc(new MonHoc("Quản lý dự án", 2.5f));
        
        SinhVien sv5 = new SinhVien("Pham", "Minh", "2003-05-22", "Hải Phòng");
        sv5.themMonHoc(new MonHoc("Lập trình hướng đối tượng", 9.0f));
        sv5.themMonHoc(new MonHoc("Quản lý dự án", 8.5f));

        it01.themSinhVien(sv1);
        it01.themSinhVien(sv2);
        it02.themSinhVien(sv3);
        it02.themSinhVien(sv4);
        it02.themSinhVien(sv5);

        qllh.themLopHoc(it01);
        qllh.themLopHoc(it02);

        // Vòng lặp chính
        while (true) {
            System.out.println("\n📌 Danh sách lớp:");
            qllh.hienThiDanhSachLopHoc();
            System.out.println("Nhập mã lớp để xem danh sách sinh viên (hoặc gõ 'exit' để thoát): ");
            String maLop = scanner.nextLine();

            if (maLop.equalsIgnoreCase("exit")) {
                System.out.println("Thoát chương trình.");
                break;
            }

            LopHoc lopChon = qllh.timLopHoc(maLop);
            if (lopChon != null) {
                lopChon.hienThiDanhSachSinhVien();
                lopChon.thongKeXepLoai();
            } else {
                System.out.println("⚠ Không tìm thấy lớp học!");
            }
        }
        scanner.close();
    }
}
