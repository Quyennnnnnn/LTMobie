package com.example.nhom24.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom24.Database.AppDatabase;
import com.example.nhom24.DAO.UserDAO;
import com.example.nhom24.Model.User;
import com.example.nhom24.R;

public class DoiMatKhauActivity extends AppCompatActivity {
    EditText edtOldPassword, edtNewPassword, edtConfirmPassword;
    Button btnChangePassword;
    AppDatabase database;
    UserDAO userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);

        edtOldPassword = findViewById(R.id.edtOldPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        if (edtOldPassword == null || edtNewPassword == null ||
                edtConfirmPassword == null || btnChangePassword == null) {
            Toast.makeText(this, "Lỗi giao diện!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        database = AppDatabase.getInstance(this);
        userDao = database.userDAO();

        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        String userEmail = preferences.getString("email", "");

        if (userEmail.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy thông tin người dùng!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        btnChangePassword.setOnClickListener(v -> {
            String oldPass = edtOldPassword.getText().toString().trim();
            String newPass = edtNewPassword.getText().toString().trim();
            String confirmPass = edtConfirmPassword.getText().toString().trim();

            if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (newPass.length() < 6) {
                Toast.makeText(this, "Mật khẩu mới phải có ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!newPass.equals(confirmPass)) {
                Toast.makeText(this, "Xác nhận mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = userDao.findByUsername(userEmail);
            if (user == null || !user.getPassword().equals(oldPass)) {
                Toast.makeText(this, "Mật khẩu cũ không đúng!", Toast.LENGTH_SHORT).show();
                return;
            }

            user.setPassword(newPass);
            int rowsAffected = userDao.updateUser(user);

            if (rowsAffected > 0) {
                Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Lỗi khi cập nhật mật khẩu!", Toast.LENGTH_LONG).show();
            }
        });
    }
}