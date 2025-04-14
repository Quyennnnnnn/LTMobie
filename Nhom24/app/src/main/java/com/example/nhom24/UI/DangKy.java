package com.example.nhom24.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.nhom24.Database.AppDatabase;
import com.example.nhom24.Model.User;
import com.example.nhom24.R;

public class DangKy extends AppCompatActivity {
    private EditText edtPhone, edtEmail, edtPassword, edtConfirmPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ky);

        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edtPhone.getText().toString().trim();
                String taikhoan = edtEmail.getText().toString().trim();
                String matkhau = edtPassword.getText().toString().trim();
                String confirmMatkhau = edtConfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(taikhoan) ||
                        TextUtils.isEmpty(matkhau) || TextUtils.isEmpty(confirmMatkhau)) {
                    Toast.makeText(DangKy.this, "Vui lòng điền đầy đủ các trường", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!matkhau.equals(confirmMatkhau)) {
                    Toast.makeText(DangKy.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                User existingUser = AppDatabase.getInstance(DangKy.this)
                        .userDAO().findByUsername(taikhoan);
                if (existingUser != null) {
                    Toast.makeText(DangKy.this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }

                User newUser = new User(phone, taikhoan, matkhau);
                AppDatabase.getInstance(DangKy.this).userDAO().insert(newUser);

                SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("phone", phone);
                editor.putString("email", taikhoan);
                editor.putString("password", matkhau);
                editor.apply();


                Toast.makeText(DangKy.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}