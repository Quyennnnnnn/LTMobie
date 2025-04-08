package com.example.testlogin;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText edtPhone, edtPassword;
    Button btnLogin;

    TextView txtDangKy, txtQuenMK;
    DatabaseHelper dbHelper;

    private boolean isPasswordVisible = false; // Biến trạng thái hiển thị mật khẩu
    private EditText edtMatKhau;
    private ImageView btnEye;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final boolean[] isPasswordVisible = {false}; // Gán giá trị mặc định là false


        edtPhone = findViewById(R.id.edtTaiKhoan);
        edtPassword = findViewById(R.id.edtMatKhau);
        btnLogin = findViewById(R.id.btnLogin);
        txtDangKy = findViewById(R.id.txtDangKy);
        txtQuenMK = findViewById(R.id.txtQuenMK);
        dbHelper = new DatabaseHelper(this);

        // Gạch chân văn bản
        txtDangKy.setPaintFlags(txtDangKy.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtQuenMK.setPaintFlags(txtQuenMK.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnLogin.setOnClickListener(v -> {
            String phone = edtPhone.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (dbHelper.checkUser(phone, password)) { // Không mã hóa mật khẩu
                Toast.makeText(MainActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Home.class));
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
            }
        });


        txtDangKy.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DangKy.class);
            startActivity(intent);
        });

        txtQuenMK.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuenMk.class);
            startActivity(intent);
        });
        DatabaseHelper db = new DatabaseHelper(this);
        db.printAllUsers();
        //DatabaseHelper dbHelper = new DatabaseHelper(this);
//        dbHelper.deleteAllUsersAndResetID();
//        DatabaseHelper dbHelper = new DatabaseHelper(this);
//        dbHelper.deleteAllUsersAndResetID();

        // Biến toàn cục để lưu trạng thái của mật khẩu
        edtMatKhau = findViewById(R.id.edtMatKhau);
        btnEye = findViewById(R.id.btnEye);

        btnEye.setOnClickListener(v -> {
            if (isPasswordVisible[0]) {
                // Ẩn mật khẩu
                edtMatKhau.setTransformationMethod(PasswordTransformationMethod.getInstance());
                btnEye.setImageResource(R.drawable.ic_eye); // Icon mắt đóng
            } else {
                // Hiển thị mật khẩu
                edtMatKhau.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                btnEye.setImageResource(R.drawable.ic_lock); // Icon mắt mở
            }
            isPasswordVisible[0] = !isPasswordVisible[0]; // Đảo trạng thái
            edtMatKhau.setSelection(edtMatKhau.getText().length()); // Giữ con trỏ ở cuối
        });



    }
}
