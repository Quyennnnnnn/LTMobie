package com.example.nhom24.UI;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom24.Adapter.LoaiThietBiAdapter;
import com.example.nhom24.Database.AppDatabase;
import com.example.nhom24.Model.LoaiThietBi;
import com.example.nhom24.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class LoaiThietBiActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView btnBack;
    private EditText edtSearch;
    private RecyclerView recyclerViewLoaiThietBi;
    private FloatingActionButton fabAdd;
    private LoaiThietBiAdapter adapter;
    private List<LoaiThietBi> loaiThietBiList;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_thiet_bi);

        toolbar = findViewById(R.id.toolbar);
        btnBack = findViewById(R.id.btnBack);
        edtSearch = findViewById(R.id.edtSearch);
        recyclerViewLoaiThietBi = findViewById(R.id.recyclerViewLoaiThietBi);
        fabAdd = findViewById(R.id.fabAdd);

        database = AppDatabase.getInstance(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        btnBack.setOnClickListener(v -> finish());

        recyclerViewLoaiThietBi.setLayoutManager(new LinearLayoutManager(this));
        loaiThietBiList = new ArrayList<>();
        adapter = new LoaiThietBiAdapter(loaiThietBiList);
        recyclerViewLoaiThietBi.setAdapter(adapter);

        loadData();

        adapter.setOnItemClickListener(new LoaiThietBiAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(LoaiThietBi item) {
                showEditDialog(item);
            }

            @Override
            public void onDeleteClick(LoaiThietBi item) {
                database.loaiThietBiDAO().delete(item);
                loadData();
                Toast.makeText(LoaiThietBiActivity.this, "Đã xóa: " + item.getTenthietbi(), Toast.LENGTH_SHORT).show();
            }
        });

        fabAdd.setOnClickListener(v -> showAddDialog());

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần xử lý
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Lọc dữ liệu khi người dùng nhập
                filterList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần xử lý
            }
        });
    }

    private void loadData() {
        loaiThietBiList = database.loaiThietBiDAO().getAll();
        adapter.updateList(loaiThietBiList);
    }

    private void filterList(String query) {
        List<LoaiThietBi> filteredList = database.loaiThietBiDAO().search("%" + query + "%");
        adapter.updateList(filteredList);
    }

    private void showEditDialog(LoaiThietBi item) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_loai_thiet_bi);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextInputEditText edtMaThietBi = dialog.findViewById(R.id.edtMaThietBi);
        TextInputEditText edtTenThietBi = dialog.findViewById(R.id.edtTenThietBi);
        MaterialButton btnSave = dialog.findViewById(R.id.btnSave);

        edtMaThietBi.setText(item.getMathietbi());
        edtTenThietBi.setText(item.getTenthietbi());

        btnSave.setOnClickListener(v -> {
            String newMaThietBi = edtMaThietBi.getText().toString().trim();
            String newTenThietBi = edtTenThietBi.getText().toString().trim();

            if (newMaThietBi.isEmpty() || newTenThietBi.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            LoaiThietBi updatedItem = new LoaiThietBi(newMaThietBi, newTenThietBi);
            updatedItem.setId(item.getId());
            database.loaiThietBiDAO().update(updatedItem);

            Toast.makeText(this, "Đã cập nhật: " + newTenThietBi, Toast.LENGTH_SHORT).show();
            loadData();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void showAddDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_loai_thiet_bi);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        TextInputEditText edtMaThietBi = dialog.findViewById(R.id.edtMaThietBi);
        TextInputEditText edtTenThietBi = dialog.findViewById(R.id.edtTenThietBi);
        MaterialButton btnSave = dialog.findViewById(R.id.btnSave);

        tvTitle.setText("Thêm loại thiết bị");
        edtMaThietBi.setText("");
        edtTenThietBi.setText("");

        btnSave.setOnClickListener(v -> {
            String maThietBi = edtMaThietBi.getText().toString().trim();
            String tenThietBi = edtTenThietBi.getText().toString().trim();

            if (maThietBi.isEmpty() || tenThietBi.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            LoaiThietBi newItem = new LoaiThietBi(maThietBi, tenThietBi);
            database.loaiThietBiDAO().insert(newItem);

            Toast.makeText(this, "Đã thêm: " + tenThietBi, Toast.LENGTH_SHORT).show();
            loadData();
            dialog.dismiss();
        });

        dialog.show();
    }
}