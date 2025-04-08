package com.example.nhom24.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.nhom24.R;
import com.google.android.material.button.MaterialButton;

public class Home extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView btnMenu, imgProfile;
    private MaterialButton btnLoaiThietBi, btnThietBi, btnChiTietSuDung, btnPhongHoc, btnNhanVien, btnThongKe;
    private MaterialButton btnSettings, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Ánh xạ các view từ layout
        toolbar = findViewById(R.id.toolbar);
        btnMenu = findViewById(R.id.btnMenu);
        imgProfile = findViewById(R.id.imgProfile);
        btnLoaiThietBi = findViewById(R.id.btnLoaiThietBi);
        btnThietBi = findViewById(R.id.btnThietBi);
        btnChiTietSuDung = findViewById(R.id.btnChiTietSuDung);
        btnPhongHoc = findViewById(R.id.btnPhongHoc);
        btnNhanVien = findViewById(R.id.btnNhanVien);
        btnThongKe = findViewById(R.id.btnThongKe);
        btnSettings = findViewById(R.id.btnSettings);
        btnLogout = findViewById(R.id.btnLogout);

        // Thiết lập Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false); // Ẩn tiêu đề mặc định của Toolbar
        }

        // Xử lý sự kiện cho nút Menu (có thể mở Navigation Drawer)
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Home.this, "Mở menu (chưa triển khai)", Toast.LENGTH_SHORT).show();
                // Nếu dùng Navigation Drawer, thêm code mở Drawer ở đây
            }
        });

        // Xử lý sự kiện cho Avatar
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Home.this, "Mở hồ sơ (chưa triển khai)", Toast.LENGTH_SHORT).show();
                // Có thể mở ProfileActivity ở đây
                // startActivity(new Intent(Home.this, ProfileActivity.class));
            }
        });

        // Xử lý sự kiện cho các nút chức năng
        btnLoaiThietBi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, LoaiThietBiActivity.class));
            }
        });

        btnThietBi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, ThietBiActivity.class));
            }
        });

        btnChiTietSuDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, ChiTietSuDungActivity.class));
            }
        });

        btnPhongHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, PhongHocActivity.class));
            }
        });

        btnNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, NhanVienActivity.class));
            }
        });

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, ThongKeActivity.class));
            }
        });

        // Xử lý sự kiện cho Footer
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, SettingsActivity.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại màn hình đăng nhập và xóa trạng thái đăng nhập nếu cần
                startActivity(new Intent(Home.this, DangNhap.class));
                finish();
            }
        });

        // Xử lý padding cho system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.toolbar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}