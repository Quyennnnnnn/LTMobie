package com.example.nhom24.UI;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private Uri selectedImageUri;
    private ImageView dialogImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thiet_bi);

        toolbar = findViewById(R.id.toolbar);
        btnBack = findViewById(R.id.btnBack);
        edtSearch = findViewById(R.id.edtSearch);
        recyclerViewThietBi = findViewById(R.id.recyclerViewThietBi);
        fabAdd = findViewById(R.id.fabAdd);

        database = AppDatabase.getInstance(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();
                if (imageUri != null) {
                    try {
                        getContentResolver().takePersistableUriPermission(imageUri,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Không thể truy cập hình ảnh", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    selectedImageUri = imageUri;
                    if (dialogImageView != null) {
                        Glide.with(this)
                                .load(selectedImageUri)
                                .error(R.drawable.ic_camera)
                                .placeholder(R.drawable.ic_camera)
                                .into(dialogImageView);
                    }
                }
            } else {
                if (dialogImageView != null) {
                    dialogImageView.setImageResource(R.drawable.ic_camera);
                }
            }
        });

        btnBack.setOnClickListener(v -> finish());

        recyclerViewThietBi.setLayoutManager(new LinearLayoutManager(this));
        thietBiList = new ArrayList<>();
        loaiThietBiList = new ArrayList<>();
        adapter = new ThietBiAdapter(thietBiList, loaiThietBiList, this::showPopupImageDialog);
        recyclerViewThietBi.setAdapter(adapter);

        loadData();

        adapter.setOnItemClickListener(new ThietBiAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(ThietBi item) {
                showEditDialog(item);
            }

            @Override
            public void onDeleteClick(ThietBi item) {
                database.thietBiDAO().delete(item);
                loadData();
                Toast.makeText(ThietBiActivity.this, "Đã xóa: " + item.getTenThietBi(), Toast.LENGTH_SHORT).show();
            }
        });

        fabAdd.setOnClickListener(v -> showAddDialog());

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
        thietBiList = database.thietBiDAO().getAll();
        loaiThietBiList = database.loaiThietBiDAO().getAll();
        adapter.updateList(thietBiList, loaiThietBiList);
    }

    private void filterList(String query) {
        List<ThietBi> filteredList = database.thietBiDAO().search("%" + query + "%");
        adapter.updateList(filteredList, loaiThietBiList);
    }

    private void showPopupImageDialog(String imageUrl) {
        // Dim the background
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 0.5f; // Reduce brightness to 50%
        getWindow().setAttributes(layoutParams);

        Dialog popupDialog = new Dialog(this);
        popupDialog.setContentView(R.layout.dialog_fullscreen_image);
        popupDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ImageView popupImageView = popupDialog.findViewById(R.id.popupImageView);
        ImageButton btnClose = popupDialog.findViewById(R.id.btnClose);

        // Load image
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(Uri.parse(imageUrl))
                    .error(R.drawable.ic_camera)
                    .placeholder(R.drawable.ic_camera)
                    .into(popupImageView);
        } else if (selectedImageUri != null) {
            Glide.with(this)
                    .load(selectedImageUri)
                    .error(R.drawable.ic_camera)
                    .placeholder(R.drawable.ic_camera)
                    .into(popupImageView);
        } else {
            popupImageView.setImageResource(R.drawable.ic_camera);
        }

        View.OnClickListener dismissListener = v -> {
            layoutParams.alpha = 1.0f; // Restore full brightness
            getWindow().setAttributes(layoutParams);
            popupDialog.dismiss();
        };

        popupImageView.setOnClickListener(dismissListener);
        popupDialog.getWindow().getDecorView().setOnClickListener(dismissListener);
        btnClose.setOnClickListener(dismissListener);

        popupImageView.setAlpha(0f);
        popupImageView.animate().alpha(1f).setDuration(300).start();

        popupDialog.show();
    }

    private void showEditDialog(ThietBi item) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_thiet_bi);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextInputEditText edtMaThietBi = dialog.findViewById(R.id.edtMaThietBi);
        TextInputEditText edtTenThietBi = dialog.findViewById(R.id.edtTenThietBi);
        TextInputEditText edtXuatXu = dialog.findViewById(R.id.edtXuatXu);
        TextInputEditText edtSoLuong = dialog.findViewById(R.id.edtSoLuong);
        TextInputEditText edtTinhTrang = dialog.findViewById(R.id.edtTinhTrang);
        ImageView imgThietBi = dialog.findViewById(R.id.imgThietBi);
        MaterialButton btnChonHinh = dialog.findViewById(R.id.btnChonHinh);
        Spinner spinnerLoaiThietBi = dialog.findViewById(R.id.spinnerLoaiThietBi);
        MaterialButton btnSave = dialog.findViewById(R.id.btnSave);

        List<String> loaiThietBiNames = new ArrayList<>();
        for (LoaiThietBi loai : loaiThietBiList) {
            loaiThietBiNames.add(loai.getTenthietbi());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, loaiThietBiNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoaiThietBi.setAdapter(spinnerAdapter);

        edtMaThietBi.setText(item.getMaThietBi());
        edtTenThietBi.setText(item.getTenThietBi());
        edtXuatXu.setText(item.getXuatXu());
        edtSoLuong.setText(String.valueOf(item.getSoLuong()));
        edtTinhTrang.setText(item.getTinhTrang());
        selectedImageUri = item.getImageUrl() != null ? Uri.parse(item.getImageUrl()) : null;
        if (selectedImageUri != null) {
            Glide.with(this)
                    .load(selectedImageUri)
                    .error(R.drawable.ic_camera)
                    .placeholder(R.drawable.ic_camera)
                    .into(imgThietBi);
        } else {
            imgThietBi.setImageResource(R.drawable.ic_camera);
        }

        // Click to show pop-up
        imgThietBi.setOnClickListener(v -> showPopupImageDialog(item.getImageUrl()));

        for (int i = 0; i < loaiThietBiList.size(); i++) {
            if (loaiThietBiList.get(i).getId() == item.getLoaiThietBiId()) {
                spinnerLoaiThietBi.setSelection(i);
                break;
            }
        }

        btnChonHinh.setOnClickListener(v -> {
            dialogImageView = imgThietBi;
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        btnSave.setOnClickListener(v -> {
            String maThietBi = edtMaThietBi.getText().toString().trim();
            String tenThietBi = edtTenThietBi.getText().toString().trim();
            String xuatXu = edtXuatXu.getText().toString().trim();
            String soLuongStr = edtSoLuong.getText().toString().trim();
            String tinhTrang = edtTinhTrang.getText().toString().trim();
            int selectedPosition = spinnerLoaiThietBi.getSelectedItemPosition();

            if (maThietBi.isEmpty() || tenThietBi.isEmpty() || xuatXu.isEmpty() || soLuongStr.isEmpty() || tinhTrang.isEmpty()) {
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

            if (selectedPosition < 0 || selectedPosition >= loaiThietBiList.size()) {
                Toast.makeText(this, "Vui lòng chọn loại thiết bị!", Toast.LENGTH_SHORT).show();
                return;
            }

            int loaiThietBiId = loaiThietBiList.get(selectedPosition).getId();
            String imageUrl = selectedImageUri != null ? selectedImageUri.toString() : item.getImageUrl();

            ThietBi updatedItem = new ThietBi(
                    item.getId(),
                    maThietBi,
                    tenThietBi,
                    xuatXu,
                    soLuong,
                    tinhTrang,
                    imageUrl,
                    loaiThietBiId
            );

            database.thietBiDAO().update(updatedItem);
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
        TextInputEditText edtTinhTrang = dialog.findViewById(R.id.edtTinhTrang);
        ImageView imgThietBi = dialog.findViewById(R.id.imgThietBi);
        MaterialButton btnChonHinh = dialog.findViewById(R.id.btnChonHinh);
        Spinner spinnerLoaiThietBi = dialog.findViewById(R.id.spinnerLoaiThietBi);
        MaterialButton btnSave = dialog.findViewById(R.id.btnSave);

        tvTitle.setText("Thêm thiết bị");
        edtMaThietBi.setText("");
        edtTenThietBi.setText("");
        edtXuatXu.setText("");
        edtSoLuong.setText("");
        edtTinhTrang.setText("");
        imgThietBi.setImageResource(R.drawable.ic_camera);
        selectedImageUri = null;

        imgThietBi.setOnClickListener(v -> showPopupImageDialog(null));

        List<String> loaiThietBiNames = new ArrayList<>();
        for (LoaiThietBi loai : loaiThietBiList) {
            loaiThietBiNames.add(loai.getTenthietbi());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, loaiThietBiNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoaiThietBi.setAdapter(spinnerAdapter);

        btnChonHinh.setOnClickListener(v -> {
            dialogImageView = imgThietBi;
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        btnSave.setOnClickListener(v -> {
            String maThietBi = edtMaThietBi.getText().toString().trim();
            String tenThietBi = edtTenThietBi.getText().toString().trim();
            String xuatXu = edtXuatXu.getText().toString().trim();
            String soLuongStr = edtSoLuong.getText().toString().trim();
            String tinhTrang = edtTinhTrang.getText().toString().trim();
            int selectedPosition = spinnerLoaiThietBi.getSelectedItemPosition();

            if (maThietBi.isEmpty() || tenThietBi.isEmpty() || xuatXu.isEmpty() || soLuongStr.isEmpty() || tinhTrang.isEmpty()) {
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

            if (selectedPosition < 0 || selectedPosition >= loaiThietBiList.size()) {
                Toast.makeText(this, "Vui lòng chọn loại thiết bị!", Toast.LENGTH_SHORT).show();
                return;
            }

            int loaiThietBiId = loaiThietBiList.get(selectedPosition).getId();
            String imageUrl = selectedImageUri != null ? selectedImageUri.toString() : null;

            ThietBi newItem = new ThietBi(
                    0,
                    maThietBi,
                    tenThietBi,
                    xuatXu,
                    soLuong,
                    tinhTrang,
                    imageUrl,
                    loaiThietBiId
            );

            database.thietBiDAO().insert(newItem);
            Toast.makeText(this, "Đã thêm: " + tenThietBi, Toast.LENGTH_SHORT).show();
            loadData();
            dialog.dismiss();
        });

        dialog.show();
    }
}