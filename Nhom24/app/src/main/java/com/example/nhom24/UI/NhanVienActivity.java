package com.example.nhom24.UI;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import com.example.nhom24.Adapter.NhanVienAdapter;
import com.example.nhom24.Database.AppDatabase;
import com.example.nhom24.Model.User;
import com.example.nhom24.R;

import java.util.ArrayList;
import java.util.List;

public class NhanVienActivity extends AppCompatActivity {
    private RecyclerView rvNhanVien;
    private NhanVienAdapter adapter;
    private ImageView btnBack;
    private EditText etSearch;
    private AppDatabase db;
    private List<User> allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);

        rvNhanVien = findViewById(R.id.rvNhanVien);
        etSearch = findViewById(R.id.etSearch);
        btnBack = findViewById(R.id.btnBack);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app11.db")
                .allowMainThreadQueries()
                .build();

        allUsers = db.userDAO().getAllUsers();
        adapter = new NhanVienAdapter(this, allUsers);
        rvNhanVien.setLayoutManager(new LinearLayoutManager(this));
        rvNhanVien.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private void filterList(String query) {
        List<User> filteredList = new ArrayList<>();
        for (User user : allUsers) {
            if (user.getEmail().toLowerCase().contains(query.toLowerCase()) ||
                    user.getPhone().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(user);
            }
        }
        adapter.setFilter(filteredList);
    }
}