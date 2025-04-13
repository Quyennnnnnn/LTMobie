package com.example.nhom24.UI;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom24.Adapter.NhanVienAdapter;
import com.example.nhom24.Database.AppDatabase;
import com.example.nhom24.Model.LoaiThietBi;
import com.example.nhom24.Model.NhanVien;
import com.example.nhom24.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class NhanVienActivity extends AppCompatActivity {

    private RecyclerView rvNhanVien;
    private NhanVienAdapter adapter;
    private List<NhanVien> nhanVienList;
    private MaterialButton btnAddNhanVien;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);

        // Ánh xạ
        rvNhanVien = findViewById(R.id.rvNhanVien);
        btnAddNhanVien = findViewById(R.id.btnAddNhanVien);
        database = AppDatabase.getInstance(this);

        // Khởi tạo danh sách nhân viên
        nhanVienList = new ArrayList<>();
        nhanVienList.addAll(database.nhanVienDAO().getAll());

        // Thiết lập RecyclerView
        rvNhanVien.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NhanVienAdapter(nhanVienList);
        rvNhanVien.setAdapter(adapter);

        // Sự kiện nhấn nút Thêm Nhân Viên
        btnAddNhanVien.setOnClickListener(v -> showAddNhanVienDialog());
    }

    private void showAddNhanVienDialog() {
        // Tạo Dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_nhan_vien);
        dialog.setCancelable(false);

        // Ánh xạ các thành phần trong Dialog
        TextInputEditText edtEmail = dialog.findViewById(R.id.edtEmail);
        MaterialButton btnCancel = dialog.findViewById(R.id.btnCancel);
        MaterialButton btnSave = dialog.findViewById(R.id.btnSave);

        // Sự kiện nhấn nút Hủy
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // Sự kiện nhấn nút Lưu
        btnSave.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            if (email.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                return;
            }

            // Thêm nhân viên mới
            NhanVien nhanVien = new NhanVien(email);
            database.nhanVienDAO().insert(nhanVien);

            // Cập nhật danh sách
            nhanVienList.clear();
            nhanVienList.addAll(database.nhanVienDAO().getAll());
            adapter.notifyDataSetChanged();

            Toast.makeText(this, "Đã thêm nhân viên: " + email, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        // Hiển thị Dialog
        dialog.show();
    }


}