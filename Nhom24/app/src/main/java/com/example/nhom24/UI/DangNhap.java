package com.example.nhom24.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.nhom24.Database.AppDatabase;
import com.example.nhom24.Model.User;
import com.example.nhom24.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class DangNhap extends AppCompatActivity {

    private EditText edtTaiKhoan, edtMatKhau;
    private Button btnLogin;
    private TextView txtDangKy, txtQuenMK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);

        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        btnLogin = findViewById(R.id.btnLogin);
        txtDangKy = findViewById(R.id.txtDangKy);
        txtQuenMK = findViewById(R.id.txtQuenMK);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taikhoan = edtTaiKhoan.getText().toString().trim();
                String matkhau = edtMatKhau.getText().toString().trim();

                if (TextUtils.isEmpty(taikhoan) || TextUtils.isEmpty(matkhau)) {
                    Toast.makeText(DangNhap.this, "Vui lòng điền đầy đủ các trường", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = AppDatabase.getInstance(DangNhap.this)
                        .userDAO().login(taikhoan, matkhau);
                if (user != null) {
                    Toast.makeText(DangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent;
                    intent = new Intent(DangNhap.this, Home.class);
                    intent.putExtra("username", taikhoan);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(DangNhap.this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangNhap.this, DangKy.class));
            }
        });

        txtQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DangNhap.this, "Chức năng quên mật khẩu chưa được triển khai", Toast.LENGTH_SHORT).show();
            }
        });
    }
}