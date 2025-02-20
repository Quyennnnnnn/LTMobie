/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package androidproject;

import java.util.Scanner;

/**
 *
 * @author Vuong Dao
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QLSV sv = new QLSV();
        while (true) {            
            System.out.println("\n=====================");
            sv.showLop();
            System.out.println("\n=====================");
            System.out.print("\nNhap ten lop muon xem: ");
            String className = scanner.nextLine();
            if (className.equalsIgnoreCase("exit")) {
                System.out.println("Thoat chuong trinh.");
                break;
            }
            sv.showSinhvien(className);
            sv.thongKeXepLoai(className);
        }
    }
}
