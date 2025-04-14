package com.example.nhom24.UI;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom24.Adapter.PhongHocAdapter;
import com.example.nhom24.Database.AppDatabase;
import com.example.nhom24.Model.PhongHoc;
import com.example.nhom24.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PhongHocActivity extends AppCompatActivity {
    private RecyclerView rvPhongHocPH;
    private FloatingActionButton fabAddPH;
    private Toolbar toolbarPH;
    private SearchView searchViewPH;
    private PhongHocAdapter adapterPH;
    private List<PhongHoc> listPhongHocPH;
    private Dialog dialogPH;
    private EditText edtMaPhongHocPH;
    private EditText edtTenPhongHocPH;
    private MaterialButton btnLuuPH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_hoc);

        rvPhongHocPH = findViewById(R.id.rvPhongHoc);
        fabAddPH = findViewById(R.id.fabAdd);
        toolbarPH = findViewById(R.id.toolbar);
        searchViewPH = findViewById(R.id.searchViewPH);

        setSupportActionBar(toolbarPH);
        toolbarPH.setNavigationOnClickListener(v -> finish());

        listPhongHocPH = AppDatabase.getInstance(this).phongHocDAO().getAll();

        adapterPH = new PhongHocAdapter(this, listPhongHocPH, new PhongHocAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(PhongHoc phongHoc) {
                showDialogEditPH(phongHoc);
            }

            @Override
            public void onDeleteClick(PhongHoc phongHoc) {
                AppDatabase.getInstance(PhongHocActivity.this).phongHocDAO().delete(phongHoc);
                listPhongHocPH.remove(phongHoc);
                adapterPH.updateListPH(listPhongHocPH);
                Toast.makeText(PhongHocActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }
        });

        rvPhongHocPH.setLayoutManager(new LinearLayoutManager(this));
        rvPhongHocPH.setAdapter(adapterPH);

        fabAddPH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogEditPH(null);
            }
        });

        // Xử lý sự kiện tìm kiếm
        searchViewPH.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterPH.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterPH.filter(newText);
                return true;
            }
        });
    }

    private void showDialogEditPH(PhongHoc phongHoc) {
        dialogPH = new Dialog(this);
        dialogPH.setContentView(R.layout.dialog_edit_phong_hoc);

        edtMaPhongHocPH = dialogPH.findViewById(R.id.etMaPhongHoc);
        edtTenPhongHocPH = dialogPH.findViewById(R.id.etTenPhongHoc);
        btnLuuPH = dialogPH.findViewById(R.id.btnSave);

        if (phongHoc != null) {
            edtMaPhongHocPH.setText(phongHoc.getMaPhongHoc());
            edtTenPhongHocPH.setText(phongHoc.getTenPhongHoc());
        } else {
            edtMaPhongHocPH.setText("");
            edtTenPhongHocPH.setText("");
        }

        btnLuuPH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maPhongHoc = edtMaPhongHocPH.getText().toString().trim();
                String tenPhongHoc = edtTenPhongHocPH.getText().toString().trim();

                if (TextUtils.isEmpty(maPhongHoc) || TextUtils.isEmpty(tenPhongHoc)) {
                    Toast.makeText(PhongHocActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phongHoc == null) {
                    PhongHoc newPhongHoc = new PhongHoc(maPhongHoc, tenPhongHoc);
                    AppDatabase.getInstance(PhongHocActivity.this).phongHocDAO().insert(newPhongHoc);
                    listPhongHocPH.add(newPhongHoc);
                    Toast.makeText(PhongHocActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    phongHoc.setMaPhongHoc(maPhongHoc);
                    phongHoc.setTenPhongHoc(tenPhongHoc);
                    AppDatabase.getInstance(PhongHocActivity.this).phongHocDAO().update(phongHoc);
                    Toast.makeText(PhongHocActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }

                listPhongHocPH.clear();
                listPhongHocPH.addAll(AppDatabase.getInstance(PhongHocActivity.this).phongHocDAO().getAll());
                adapterPH.updateListPH(listPhongHocPH);
                dialogPH.dismiss();
            }
        });

        dialogPH.show();
    }
}