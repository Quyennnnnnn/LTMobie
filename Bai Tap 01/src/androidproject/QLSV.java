/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package androidproject;
import java.sql.*;
/**
 *
 * @author Vuong Dao
 */
public class QLSV implements ISinhvien{
    private Connection cn;
    @Override
    public void getCon(){
        try {
            cn = DriverManager.getConnection("jdbc:sqlserver://VUONGDAO\\SQLEXPRESS;database=QuanLyLopHoc;user=sa;password=12345;trustServerCertificate=true;");
            //System.out.println("passed connect.");
        } catch (Exception e) {
            System.out.println("failed connect.");
        }
    }

    @Override
    public void showLop() {
        Statement statement = null;
        ResultSet result = null;

        try {
            // Thiết lập kết nối
            getCon();
            
            // Kiểm tra kết nối có thành công không
            if (cn != null) {
                // Tạo đối tượng Statement
                statement = cn.createStatement();
                result = statement.executeQuery("SELECT class_name FROM Classrooms");
                while (result.next()) {
                    System.out.println("- " + result.getString("class_name"));
                }
            } else {
                System.out.println("No connection to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void showSinhvien(String className){
        Statement statement = null;
        ResultSet result = null;
        
        try {
            getCon();
            if (cn != null) {
                statement = cn.createStatement();
                String query = "SELECT first_name, last_name, oop, pm, co, db, mobile FROM Students " +
                         "JOIN Classrooms ON Students.class_id = Classrooms.class_id " +
                         "WHERE class_name = '" + className + "'";
                result = statement.executeQuery(query);
                System.out.println("Danh sach sinh vien lop: " + className +":  ");
                while (result.next()) {
                    double oop = result.getDouble("oop");
                    double pm = result.getDouble("pm");
                    double mc = result.getDouble("co");
                    double db = result.getDouble("db");
                    double mobile = result.getDouble("mobile");
                    
                    double avg = (oop + pm + mc + db + mobile)/5.0;
                    String rank;
                    rank = (avg >= 8.5) ? "A" : (avg >=7.0) ? "B" : (avg >=5.5) ? "C" : (avg < 4.0) ? "D" : "F";
                    System.out.println("- " + result.getString("first_name") + " " + result.getString("last_name") +
                                   " | Huong doi tuong: " + oop +
                                   " | Quan ly du an: " + pm +
                                   " | Hoc may: " + mc +
                                   " | Co so du lieu: " + db +
                                   " | Lap trinh mobile: " + mobile +
                                   " | Rank: " + rank
                   
                    );
                }
            }
            else{
                 System.out.println("No connection to the database.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
       public void thongKeXepLoai(String className) {
        Statement statement = null;
        ResultSet result = null;

        try {
            getCon();
            if (cn != null) {
                statement = cn.createStatement();
                String query = "SELECT " +
                        "COUNT(CASE WHEN (oop + pm + co + db + mobile) / 5.0 >= 8.5 THEN 1 END) AS A, " +
                        "COUNT(CASE WHEN (oop + pm + co + db + mobile) / 5.0 >= 7.0 AND (oop + pm + co + db + mobile) / 5.0 < 8.5 THEN 1 END) AS B, " +
                        "COUNT(CASE WHEN (oop + pm + co + db + mobile) / 5.0 >= 5.5 AND (oop + pm + co + db + mobile) / 5.0 < 7.0 THEN 1 END) AS C, " +
                        "COUNT(CASE WHEN (oop + pm + co + db + mobile) / 5.0 < 5.5 THEN 1 END) AS D, " +
                        "COUNT(CASE WHEN (oop + pm + co + db + mobile) / 5.0 < 4.0 THEN 1 END) AS F " +
                        "FROM Students " +
                        "JOIN Classrooms ON Students.class_id = Classrooms.class_id " +
                        "WHERE class_name = '" + className + "'";
                
                result = statement.executeQuery(query);

                if (result.next()) {
                    System.out.println("Thong ke xep loai lop: " + className + ":");
                    System.out.println("Loai A: " + result.getInt("A"));
                    System.out.println("Loai B: " + result.getInt("B"));
                    System.out.println("Loai C: " + result.getInt("C"));
                    System.out.println("Loai D: " + result.getInt("D"));
                    System.out.println("Loai D: " + result.getInt("F"));
                } else {
                    System.out.println("Khong co du lieu thong ke.");
                }
            } else {
                System.out.println("Khong ket noi duoc voi database.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
