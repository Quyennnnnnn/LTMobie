package com.example.nhom24.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom24.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class DoiMatKhauActivity extends AppCompatActivity {
    EditText edtOldPassword, edtNewPassword, edtConfirmPassword;
    Button btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);

        edtOldPassword = findViewById(R.id.edtOldPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        String currentPassword = preferences.getString("password", "");

        btnChangePassword.setOnClickListener(v -> {
            String oldPass = edtOldPassword.getText().toString();
            String newPass = edtNewPassword.getText().toString();
            String confirmPass = edtConfirmPassword.getText().toString();

            if (!oldPass.equals(currentPassword)) {
                Toast.makeText(this, "Mật khẩu cũ không đúng!", Toast.LENGTH_SHORT).show();
            } else if (!newPass.equals(confirmPass)) {
                Toast.makeText(this, "Xác nhận mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
            } else {
                // Lưu mật khẩu mới
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("password", newPass);
                editor.apply();

                Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}

