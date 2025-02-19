package baitap;

public class MonHoc {
    private String tenMonHoc;
    private float diem;

    public MonHoc(String tenMonHoc, float diem) {
        this.tenMonHoc = tenMonHoc;
        this.diem = diem;
    }

    public String getTenMonHoc() {
        return tenMonHoc;
    }

    public float getDiem() {
        return diem;
    }

    public void hienThiThongTin() {
        System.out.println("  - " + tenMonHoc + ": " + diem);
    }
}
