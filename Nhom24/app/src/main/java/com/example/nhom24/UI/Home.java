package com.example.nhom24.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.nhom24.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity {

    private ImageView btnMenu, imgProfile;
    private MaterialButton btnLoaiThietBi, btnThietBi, btnChiTietSuDung, btnPhongHoc, btnNhanVien, btnThongKe;
    private MaterialButton btnSettings, btnLogout;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        btnMenu = findViewById(R.id.btnMenu);
        imgProfile = findViewById(R.id.imgProfile);
        btnLoaiThietBi = findViewById(R.id.btnLoaiThietBi);
        btnThietBi = findViewById(R.id.btnThietBi);
        btnChiTietSuDung = findViewById(R.id.btnChiTietSuDung);
        btnPhongHoc = findViewById(R.id.btnPhongHoc);
        btnNhanVien = findViewById(R.id.btnNhanVien);
        btnThongKe = findViewById(R.id.btnThongKe);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);



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


        // Xử lý sự kiện nhấn icon menu
        btnMenu.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        // Xử lý sự kiện khi chọn mục trong Navigation Drawer
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_settings) {
                startActivity(new Intent(Home.this, DoiMatKhauActivity.class));
            } else if (itemId == R.id.nav_logout) {
                Intent intent = new Intent(Home.this, DangNhap.class);
                // Nếu bạn muốn xoá trạng thái đăng nhập, có thể clear SharedPreferences ở đây
                startActivity(intent);
                finish();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });


            // Lấy header view từ navigationView
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nav_username);
        TextView navEmail = headerView.findViewById(R.id.nav_email);

        // Lấy dữ liệu từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        String phone = sharedPreferences.getString("phone", "Chưa có số điện thoại");
        String email = sharedPreferences.getString("email", "Chưa có email");

    // Hiển thị lên header
        navUsername.setText(phone);
        navEmail.setText(email);

    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}

