package com.example.nhom24.UI;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom24.Adapter.ThietBiAdapter;
import com.example.nhom24.Database.AppDatabase;
import com.example.nhom24.Model.LoaiThietBi;
import com.example.nhom24.Model.ThietBi;
import com.example.nhom24.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class ThietBiActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView btnBack;
    private EditText edtSearch;
    private RecyclerView recyclerViewThietBi;
    private FloatingActionButton fabAdd;
    private ThietBiAdapter adapter;
    private List<ThietBi> thietBiList;
    private List<LoaiThietBi> loaiThietBiList;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thiet_bi);

        // Ánh xạ view
        toolbar = findViewById(R.id.toolbar);
        btnBack = findViewById(R.id.btnBack);
        edtSearch = findViewById(R.id.edtSearch);
        recyclerViewThietBi = findViewById(R.id.recyclerViewThietBi);
        fabAdd = findViewById(R.id.fabAdd);

        // Khởi tạo database
        database = AppDatabase.getInstance(this);

        // Thiết lập Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Xử lý nút Back
        btnBack.setOnClickListener(v -> finish());

        // Thiết lập RecyclerView
        recyclerViewThietBi.setLayoutManager(new LinearLayoutManager(this));
        thietBiList = new ArrayList<>();
        loaiThietBiList = new ArrayList<>();
        adapter = new ThietBiAdapter(thietBiList, loaiThietBiList); // Truyền loaiThietBiList
        recyclerViewThietBi.setAdapter(adapter);

        // Load dữ liệu
        loadData();

        // Xử lý sự kiện Sửa/Xóa
        adapter.setOnItemClickListener(new ThietBiAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(ThietBi item) {
                showEditDialog(item);
            }

            @Override
            public void onDeleteClick(ThietBi item) {
                database.thietBiDAO().delete(item); // Sửa thietBiDAO thành thietBiDao
                loadData();
                Toast.makeText(ThietBiActivity.this, "Đã xóa: " + item.getTenThietBi(), Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý nút Thêm
        fabAdd.setOnClickListener(v -> showAddDialog());

        // Xử lý tìm kiếm
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadData() {
        thietBiList = database.thietBiDAO().getAll(); // Sửa thietBiDAO thành thietBiDao
        loaiThietBiList = database.loaiThietBiDAO().getAll(); // Sửa loaiThietBiDAO thành loaiThietBiDao
        adapter.updateList(thietBiList, loaiThietBiList); // Cập nhật cả hai danh sách
    }

    private void filterList(String query) {
        List<ThietBi> filteredList = database.thietBiDAO().search("%" + query + "%"); // Sửa thietBiDAO thành thietBiDao
        adapter.updateList(filteredList, loaiThietBiList); // Giữ nguyên loaiThietBiList
    }

    private void showEditDialog(ThietBi item) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_thiet_bi);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextInputEditText edtMaThietBi = dialog.findViewById(R.id.edtMaThietBi);
        TextInputEditText edtTenThietBi = dialog.findViewById(R.id.edtTenThietBi);
        TextInputEditText edtXuatXu = dialog.findViewById(R.id.edtXuatXu);
        TextInputEditText edtSoLuong = dialog.findViewById(R.id.edtSoLuong);
        Spinner spinnerLoaiThietBi = dialog.findViewById(R.id.spinnerLoaiThietBi);
        MaterialButton btnSave = dialog.findViewById(R.id.btnSave);

        // Thiết lập Spinner
        List<String> loaiThietBiNames = new ArrayList<>();
        if (loaiThietBiList != null) {
            for (LoaiThietBi loai : loaiThietBiList) {
                loaiThietBiNames.add(loai.getTenthietbi());
            }
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, loaiThietBiNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoaiThietBi.setAdapter(spinnerAdapter);

        // Hiển thị dữ liệu cũ
        edtMaThietBi.setText(item.getMaThietBi());
        edtTenThietBi.setText(item.getTenThietBi());
        edtXuatXu.setText(item.getXuatXu());
        edtSoLuong.setText(String.valueOf(item.getSoLuong()));
        if (loaiThietBiList != null) {
            for (int i = 0; i < loaiThietBiList.size(); i++) {
                if (loaiThietBiList.get(i).getId() == item.getLoaiThietBiId()) {
                    spinnerLoaiThietBi.setSelection(i);
                    break;
                }
            }
        }

        btnSave.setOnClickListener(v -> {
            String maThietBi = edtMaThietBi.getText().toString().trim();
            String tenThietBi = edtTenThietBi.getText().toString().trim();
            String xuatXu = edtXuatXu.getText().toString().trim();
            String soLuongStr = edtSoLuong.getText().toString().trim();
            int selectedPosition = spinnerLoaiThietBi.getSelectedItemPosition();

            if (maThietBi.isEmpty() || tenThietBi.isEmpty() || xuatXu.isEmpty() || soLuongStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            int soLuong;
            try {
                soLuong = Integer.parseInt(soLuongStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Số lượng phải là số nguyên!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (loaiThietBiList == null || selectedPosition < 0 || selectedPosition >= loaiThietBiList.size()) {
                Toast.makeText(this, "Vui lòng chọn loại thiết bị!", Toast.LENGTH_SHORT).show();
                return;
            }

            int loaiThietBiId = loaiThietBiList.get(selectedPosition).getId();
            ThietBi updatedItem = new ThietBi(maThietBi, tenThietBi, xuatXu, soLuong, loaiThietBiId);
            updatedItem.setId(item.getId());
            database.thietBiDAO().update(updatedItem); // Sửa thietBiDAO thành thietBiDao

            Toast.makeText(this, "Đã cập nhật: " + tenThietBi, Toast.LENGTH_SHORT).show();
            loadData();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void showAddDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_thiet_bi);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        TextInputEditText edtMaThietBi = dialog.findViewById(R.id.edtMaThietBi);
        TextInputEditText edtTenThietBi = dialog.findViewById(R.id.edtTenThietBi);
        TextInputEditText edtXuatXu = dialog.findViewById(R.id.edtXuatXu);
        TextInputEditText edtSoLuong = dialog.findViewById(R.id.edtSoLuong);
        Spinner spinnerLoaiThietBi = dialog.findViewById(R.id.spinnerLoaiThietBi);
        MaterialButton btnSave = dialog.findViewById(R.id.btnSave);

        tvTitle.setText("Thêm thiết bị");
        edtMaThietBi.setText("");
        edtTenThietBi.setText("");
        edtXuatXu.setText("");
        edtSoLuong.setText("");

        // Thiết lập Spinner
        List<String> loaiThietBiNames = new ArrayList<>();
        if (loaiThietBiList != null) {
            for (LoaiThietBi loai : loaiThietBiList) {
                loaiThietBiNames.add(loai.getTenthietbi());
            }
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, loaiThietBiNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoaiThietBi.setAdapter(spinnerAdapter);

        btnSave.setOnClickListener(v -> {
            String maThietBi = edtMaThietBi.getText().toString().trim();
            String tenThietBi = edtTenThietBi.getText().toString().trim();
            String xuatXu = edtXuatXu.getText().toString().trim();
            String soLuongStr = edtSoLuong.getText().toString().trim();
            int selectedPosition = spinnerLoaiThietBi.getSelectedItemPosition();

            if (maThietBi.isEmpty() || tenThietBi.isEmpty() || xuatXu.isEmpty() || soLuongStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            int soLuong;
            try {
                soLuong = Integer.parseInt(soLuongStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Số lượng phải là số nguyên!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (loaiThietBiList == null || selectedPosition < 0 || selectedPosition >= loaiThietBiList.size()) {
                Toast.makeText(this, "Vui lòng chọn loại thiết bị!", Toast.LENGTH_SHORT).show();
                return;
            }

            int loaiThietBiId = loaiThietBiList.get(selectedPosition).getId();
            ThietBi newItem = new ThietBi(maThietBi, tenThietBi, xuatXu, soLuong, loaiThietBiId);
            database.thietBiDAO().insert(newItem); // Sửa thietBiDAO thành thietBiDao

            Toast.makeText(this, "Đã thêm: " + tenThietBi, Toast.LENGTH_SHORT).show();
            loadData();
            dialog.dismiss();
        });

        dialog.show();
    }
}