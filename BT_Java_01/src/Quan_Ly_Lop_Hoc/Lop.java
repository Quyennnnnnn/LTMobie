package Quan_Ly_Lop_Hoc;
import java.util.*;


public class Lop {
    private String classId;
    private List<SinhVien> students;

    public Lop(String classId){
        this.classId = classId;
        this.students = new ArrayList<>();
    }

    public String getClassID(){
        return classId;
    }

    public void addSinhVien(SinhVien student){
        if(student.tenLop().equals(this.classId)){
            students.add(student);
        }
    }

    public void listSinhVien(){
        System.out.println("\nDanh sách sinh viên lớp " + classId + ":");
        if(students.isEmpty()){
            System.out.println("Không có sinh viên nào");
        } else {
            students.forEach(SinhVien::hienThiThongTin);
        }
    }

    public void printRankSummary(){
        long aCount = 0, bCount = 0, cCount = 0, dCount = 0, underDCount = 0;

        for(SinhVien sv : students){
            String rank = sv.xepLoai();
            if(rank.equals("A")) aCount++;
            else if(rank.equals("B")) bCount++;
            else if(rank.equals("C")) cCount++;
            else if(rank.equals("D")) dCount++;
            else underDCount++;
        }

        System.out.println("\nThống kê xếp loại lớp " + classId + ":");
        System.out.println("A: " + aCount + " | B: " + bCount + " | C: " + cCount + " | D: " + dCount + " | <D: " + underDCount);
    }
}
