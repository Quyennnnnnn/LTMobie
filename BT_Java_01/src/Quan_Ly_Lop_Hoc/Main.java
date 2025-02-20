package Quan_Ly_Lop_Hoc;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Map<String, Lop> lop = new HashMap<>();

        lop.put("CNTT1", new Lop("CNTT1"));
        lop.put("CNTT2", new Lop("CNTT2"));

        lop.get("CNTT1").addSinhVien(new SinhVien("Thai", "Son", "2004-01-16", "Quang Ninh", "CNTT1", 8.0, 8.5, 7.5, 9.5, 8.0));
        lop.get("CNTT1").addSinhVien(new SinhVien("Van", "Anh", "2004-10-31", "Ha Noi", "CNTT1", 9.0, 7.5, 7.0, 8.5, 8.0));
        lop.get("CNTT1").addSinhVien(new SinhVien("Yen", "Nhi", "2004-08-26", "Thai Binh", "CNTT1", 8.5, 8.0, 7.6, 8.3, 7.4));
        
        lop.get("CNTT2").addSinhVien(new SinhVien("Huu", "Thanh", "2004-05-10", "TP HCM", "CNTT2", 6.5, 5.0, 7.0, 9.0, 3.5));
        lop.get("CNTT2").addSinhVien(new SinhVien("Thuy", "Linh", "2004-03-15", "Nha Trang", "CNTT2", 7.5, 6.0, 3.0, 2.5, 3.5));
        lop.get("CNTT2").addSinhVien(new SinhVien("Dung", "Pham", "2004-08-25", "Cần Thơ", "CNTT2", 3.0, 4.0, 3.5, 2.5, 3.0));

        System.out.println("Danh sách lớp hiện có:");
        for (String classId : lop.keySet()) {
            System.out.println("- " + classId);
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\nNhập mã lớp để xem danh sách sinh viên (hoặc nhập 'exit' để thoát): ");
            String input = scanner.nextLine();
            
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Chương trình kết thúc.");
                break;
            }
            
            Lop selectedClass = lop.get(input);
            if (selectedClass != null) {
                selectedClass.listSinhVien();;
                selectedClass.printRankSummary();
            } else {
                System.out.println("Lớp không tồn tại, vui lòng nhập lại!");
            }
        }

        scanner.close();
    }
}
