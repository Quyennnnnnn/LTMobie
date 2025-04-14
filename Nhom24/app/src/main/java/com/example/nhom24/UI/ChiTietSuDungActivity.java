package com.example.nhom24.UI;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom24.Adapter.ChiTietSuDungAdapter;
import com.example.nhom24.Database.AppDatabase;
import com.example.nhom24.Model.ChiTietSuDung;
import com.example.nhom24.Model.PhongHoc;
import com.example.nhom24.Model.ThietBi;
import com.example.nhom24.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ChiTietSuDungActivity extends AppCompatActivity {
    private RecyclerView rvChiTietSuDungCTSD;
    private FloatingActionButton fabAddCTSD;
    private Toolbar toolbarCTSD;
    private SearchView searchViewCTSD;
    private ChiTietSuDungAdapter adapterCTSD;
    private List<ChiTietSuDung> listChiTietSuDungCTSD;
    private List<PhongHoc> listPhongHocCTSD;
    private List<ThietBi> listThietBiCTSD;
    private Dialog dialogCTSD;
    private Spinner spinnerPhongHocCTSD;
    private Spinner spinnerThietBiCTSD;
    private Spinner spinnerTrangThaiCTSD;
    private EditText edtNgaySuDungCTSD;
    private MaterialButton btnLuuCTSD;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_su_dung);

        rvChiTietSuDungCTSD = findViewById(R.id.rvChiTietSuDung);
        fabAddCTSD = findViewById(R.id.fabAddCTSD);
        toolbarCTSD = findViewById(R.id.toolbarCTSD);
        searchViewCTSD = findViewById(R.id.searchViewCTSD);

        setSupportActionBar(toolbarCTSD);
        toolbarCTSD.setNavigationOnClickListener(v -> finish());

        listChiTietSuDungCTSD = AppDatabase.getInstance(this).chiTietSuDungDAO().getAllChiTietSuDung();
        listPhongHocCTSD = AppDatabase.getInstance(this).phongHocDAO().getAll();
        listThietBiCTSD = AppDatabase.getInstance(this).thietBiDAO().getAll();

        adapterCTSD = new ChiTietSuDungAdapter(this, listChiTietSuDungCTSD, listPhongHocCTSD, listThietBiCTSD, new ChiTietSuDungAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(ChiTietSuDung chiTietSuDung) {
                showDialogEditCTSD(chiTietSuDung);
            }

            @Override
            public void onDeleteClick(ChiTietSuDung chiTietSuDung) {
                AppDatabase.getInstance(ChiTietSuDungActivity.this).chiTietSuDungDAO().delete(chiTietSuDung);
                listChiTietSuDungCTSD.remove(chiTietSuDung);
                adapterCTSD.updateListCTSD(listChiTietSuDungCTSD);
                Toast.makeText(ChiTietSuDungActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }
        });

        rvChiTietSuDungCTSD.setLayoutManager(new LinearLayoutManager(this));
        rvChiTietSuDungCTSD.setAdapter(adapterCTSD);

        fabAddCTSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogEditCTSD(null);
            }
        });

        calendar = Calendar.getInstance();

        searchViewCTSD.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterCTSD.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterCTSD.filter(newText);
                return true;
            }
        });
    }

    private void showDialogEditCTSD(ChiTietSuDung chiTietSuDung) {
        dialogCTSD = new Dialog(this);
        dialogCTSD.setContentView(R.layout.dialog_edit_chi_tiet_su_dung);

        spinnerPhongHocCTSD = dialogCTSD.findViewById(R.id.spinnerPhongHocCTSD);
        spinnerThietBiCTSD = dialogCTSD.findViewById(R.id.spinnerThietBiCTSD);
        spinnerTrangThaiCTSD = dialogCTSD.findViewById(R.id.spinnerTrangThaiCTSD);
        edtNgaySuDungCTSD = dialogCTSD.findViewById(R.id.etNgaySuDungCTSD);
        btnLuuCTSD = dialogCTSD.findViewById(R.id.btnSaveCTSD);

        List<String> phongHocNames = new ArrayList<>();
        for (PhongHoc ph : listPhongHocCTSD) {
            phongHocNames.add(ph.getTenPhongHoc());
        }
        ArrayAdapter<String> phongHocAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, phongHocNames);
        phongHocAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPhongHocCTSD.setAdapter(phongHocAdapter);

        List<String> thietBiNames = new ArrayList<>();
        for (ThietBi tb : listThietBiCTSD) {
            thietBiNames.add(tb.getTenThietBi());
        }
        ArrayAdapter<String> thietBiAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, thietBiNames);
        thietBiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerThietBiCTSD.setAdapter(thietBiAdapter);

        List<String> trangThaiList = new ArrayList<>();
        trangThaiList.add("Đang sử dụng");
        trangThaiList.add("Không sử dụng");
        ArrayAdapter<String> trangThaiAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, trangThaiList);
        trangThaiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTrangThaiCTSD.setAdapter(trangThaiAdapter);

        edtNgaySuDungCTSD.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    ChiTietSuDungActivity.this,
                    (view, year1, month1, dayOfMonth) -> {
                        calendar.set(year1, month1, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        edtNgaySuDungCTSD.setText(sdf.format(calendar.getTime()));
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        if (chiTietSuDung != null) {
            for (int i = 0; i < listPhongHocCTSD.size(); i++) {
                if (listPhongHocCTSD.get(i).getId() == chiTietSuDung.getPhongHocId()) {
                    spinnerPhongHocCTSD.setSelection(i);
                    break;
                }
            }
            for (int i = 0; i < listThietBiCTSD.size(); i++) {
                if (listThietBiCTSD.get(i).getId() == chiTietSuDung.getThietBiId()) {
                    spinnerThietBiCTSD.setSelection(i);
                    break;
                }
            }
            spinnerTrangThaiCTSD.setSelection(trangThaiList.indexOf(chiTietSuDung.getTrangThai()));
            edtNgaySuDungCTSD.setText(chiTietSuDung.getNgaySuDung());
        } else {
            edtNgaySuDungCTSD.setText("");
            spinnerTrangThaiCTSD.setSelection(0);
        }

        btnLuuCTSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPhongHocIndex = spinnerPhongHocCTSD.getSelectedItemPosition();
                int selectedThietBiIndex = spinnerThietBiCTSD.getSelectedItemPosition();
                String trangThai = spinnerTrangThaiCTSD.getSelectedItem().toString();
                String ngaySuDung = edtNgaySuDungCTSD.getText().toString().trim();

                if (selectedPhongHocIndex == -1) {
                    Toast.makeText(ChiTietSuDungActivity.this, "Vui lòng chọn phòng học", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectedThietBiIndex == -1) {
                    Toast.makeText(ChiTietSuDungActivity.this, "Vui lòng chọn thiết bị", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ngaySuDung)) {
                    Toast.makeText(ChiTietSuDungActivity.this, "Vui lòng chọn ngày sử dụng", Toast.LENGTH_SHORT).show();
                    return;
                }

                int phongHocId = listPhongHocCTSD.get(selectedPhongHocIndex).getId();
                int thietBiId = listThietBiCTSD.get(selectedThietBiIndex).getId();

                if (chiTietSuDung == null) {
                    ChiTietSuDung newChiTietSuDung = new ChiTietSuDung(phongHocId, thietBiId, ngaySuDung, trangThai);
                    AppDatabase.getInstance(ChiTietSuDungActivity.this).chiTietSuDungDAO().insert(newChiTietSuDung);
                    listChiTietSuDungCTSD.add(newChiTietSuDung);
                    Toast.makeText(ChiTietSuDungActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    chiTietSuDung.setPhongHocId(phongHocId);
                    chiTietSuDung.setThietBiId(thietBiId);
                    chiTietSuDung.setNgaySuDung(ngaySuDung);
                    chiTietSuDung.setTrangThai(trangThai);
                    AppDatabase.getInstance(ChiTietSuDungActivity.this).chiTietSuDungDAO().update(chiTietSuDung);
                    Toast.makeText(ChiTietSuDungActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }

                listChiTietSuDungCTSD.clear();
                listChiTietSuDungCTSD.addAll(AppDatabase.getInstance(ChiTietSuDungActivity.this).chiTietSuDungDAO().getAllChiTietSuDung());
                adapterCTSD.updateListCTSD(listChiTietSuDungCTSD);
                dialogCTSD.dismiss();
            }
        });

        dialogCTSD.show();
    }
}