package Quan_Ly_Lop_Hoc;


public class SinhVien {
    private String FirstName;
    private String LastName;
    private String BirthDate;
    private String Address;
    private String ClassName;
    private double LTHDT;
    private double QLDA;
    private double HocMay;
    private double CSDL;
    private double LTUD;
    

    public SinhVien(){
        this.FirstName = "";
        this.LastName = "";
        this.BirthDate = "";
        this.Address = "";
        this.ClassName = "";
        this.LTHDT = 0;
        this.QLDA = 0;
        this.HocMay = 0;
        this.CSDL = 0;
        this.LTUD = 0;
    }

    public SinhVien(String firstName, String lastName, String birthDate, String address, 
    String className, double lthdt, double qlda, double hocMay, double csdl, double ltud){
        this.FirstName = firstName;
        this.LastName = lastName;
        this.BirthDate = birthDate;
        this.Address = address;
        this.ClassName = className;
        this.LTHDT = lthdt;
        this.QLDA = qlda;
        this.HocMay = hocMay;
        this.CSDL = csdl;
        this.LTUD = ltud;
    }

    public double tinhDiemTB(){
        return (LTHDT + QLDA + HocMay + CSDL + LTUD) / 5;
    }

    public String xepLoai(){
        double avg = tinhDiemTB();
        if(avg >= 8.5) return "A";
        else if(avg >= 7.0) return "B";
        else if(avg >= 5.5) return "C";
        else if(avg >= 4.0) return "D";
        else return "F";
    }

    public String tenLop(){
        return ClassName;
    }

    public String fullName(){
        return FirstName + " " + LastName;
    }

    public void hienThiThongTin(){
        System.out.printf("%s - Birthdate: %s - Address: %s - %s - Avg: %.2f - Rank: %s%n", 
            fullName(), BirthDate, Address, ClassName, tinhDiemTB(), xepLoai());
    }
}
