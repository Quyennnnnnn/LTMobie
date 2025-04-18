package com.example.nhom24.UI;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom24.Adapter.ThongKeAdapter;
import com.example.nhom24.Database.AppDatabase;
import com.example.nhom24.Model.ChiTietSuDung;
import com.example.nhom24.Model.PhongHoc;
import com.example.nhom24.Model.ThietBi;
import com.example.nhom24.Model.ThongKeItem;
import com.example.nhom24.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThongKeActivity extends AppCompatActivity {
    private ImageView btnBack;
    private EditText etStartDate, etEndDate, edtSearchDevice;
    private Button btnFilter, btnChart;
    private RecyclerView rvThongKe;
    private ThongKeAdapter adapter;
    private List<ThongKeItem> thongKeList;
    private List<ThongKeItem> filteredThongKeList;
    private List<ChiTietSuDung> chiTietSuDungList;
    private List<ThietBi> thietBiList;
    private List<PhongHoc> phongHocList;
    private AppDatabase database;
    private ExecutorService executorService;
    private Calendar calendar;
    private SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        btnBack = findViewById(R.id.btnBack);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        edtSearchDevice = findViewById(R.id.edtSearchDevice);
        btnFilter = findViewById(R.id.btnFilter);
        btnChart = findViewById(R.id.btnChart);
        rvThongKe = findViewById(R.id.rvThongKe);

        database = AppDatabase.getInstance(this);
        executorService = Executors.newSingleThreadExecutor();
        calendar = Calendar.getInstance();
        sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        thongKeList = new ArrayList<>();
        filteredThongKeList = new ArrayList<>();
        chiTietSuDungList = new ArrayList<>();
        thietBiList = new ArrayList<>();
        phongHocList = new ArrayList<>();
        adapter = new ThongKeAdapter(this, filteredThongKeList);
        rvThongKe.setLayoutManager(new LinearLayoutManager(this));
        rvThongKe.setAdapter(adapter);

        loadInitialData();

        btnBack.setOnClickListener(v -> finish());

        etStartDate.setOnClickListener(v -> showDatePickerDialog(etStartDate));
        etEndDate.setOnClickListener(v -> showDatePickerDialog(etEndDate));

        btnFilter.setOnClickListener(v -> filterByDate());

        edtSearchDevice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterByDeviceName(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        setupClearButton(etStartDate);
        setupClearButton(etEndDate);
        setupClearButton(edtSearchDevice);

        btnChart.setOnClickListener(v -> {
            String startDate = etStartDate.getText().toString().trim();
            String endDate = etEndDate.getText().toString().trim();

            if (startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!startDate.matches("\\d{4}-\\d{2}-\\d{2}") || !endDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                Toast.makeText(this, "Ngày phải có định dạng YYYY-MM-DD!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(ThongKeActivity.this, ChartActivity.class);
            intent.putExtra("start_date", startDate);
            intent.putExtra("end_date", endDate);
            startActivity(intent);
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupClearButton(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    Drawable drawable = ContextCompat.getDrawable(ThongKeActivity.this, R.drawable.ic_clear);
                    int size = (int) (15 * getResources().getDisplayMetrics().density); // Kích thước 20dp
                    if (drawable != null) {
                        drawable.setBounds(0, 0, size, size);
                        editText.setCompoundDrawables(null, null, drawable, null);
                    }
                } else {
                    editText.setCompoundDrawables(null, null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        editText.setOnTouchListener((v, event) -> {
            if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                if (editText.getCompoundDrawables()[2] != null) {
                    if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[2].getBounds().width() - editText.getPaddingRight())) {
                        editText.setText(""); // Xóa nội dung
                        if (editText == etStartDate || editText == etEndDate) {
                            executorService.execute(() -> {
                                List<ThongKeItem> newThongKeList = aggregateData(chiTietSuDungList);
                                runOnUiThread(() -> {
                                    thongKeList = newThongKeList;
                                    filterByDeviceName(edtSearchDevice.getText().toString().trim());
                                });
                            });
                        }
                        return true;
                    }
                }
            }
            return false;
        });
    }

    private void showDatePickerDialog(final EditText editText) {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            editText.setText(sdf.format(calendar.getTime()));
        };

        new DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void loadInitialData() {
        executorService.execute(() -> {
            thietBiList = database.thietBiDAO().getAll();
            phongHocList = database.phongHocDAO().getAll();
            chiTietSuDungList = database.chiTietSuDungDAO().getAllChiTietSuDung();
            thongKeList = aggregateData(chiTietSuDungList);
            runOnUiThread(() -> {
                filteredThongKeList = new ArrayList<>(thongKeList);
                adapter.updateList(filteredThongKeList);
            });
        });
    }

    private List<ThongKeItem> aggregateData(List<ChiTietSuDung> chiTietList) {
        Map<String, Integer> usageCountMap = new HashMap<>();
        for (ChiTietSuDung chiTiet : chiTietList) {
            String key = chiTiet.getThietBiId() + "|" + chiTiet.getPhongHocId() + "|" + chiTiet.getNgaySuDung();
            usageCountMap.put(key, usageCountMap.getOrDefault(key, 0) + 1);
        }

        List<ThongKeItem> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : usageCountMap.entrySet()) {
            String[] parts = entry.getKey().split("\\|");
            int thietBiId = Integer.parseInt(parts[0]);
            int phongHocId = Integer.parseInt(parts[1]);
            String ngaySuDung = parts[2];
            int soLuongDaMuon = entry.getValue();

            String tenThietBi = "";
            for (ThietBi tb : thietBiList) {
                if (tb.getId() == thietBiId) {
                    tenThietBi = tb.getTenThietBi();
                    break;
                }
            }

            String maPhong = "";
            for (PhongHoc ph : phongHocList) {
                if (ph.getId() == phongHocId) {
                    maPhong = ph.getMaPhongHoc();
                    break;
                }
            }

            result.add(new ThongKeItem(ngaySuDung, tenThietBi, maPhong, soLuongDaMuon));
        }

        return result;
    }

    private void filterByDate() {
        String startDateStr = etStartDate.getText().toString().trim();
        String endDateStr = etEndDate.getText().toString().trim();

        if (startDateStr.isEmpty() || endDateStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ ngày bắt đầu và ngày kết thúc!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!startDateStr.matches("\\d{4}-\\d{2}-\\d{2}") || !endDateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
            Toast.makeText(this, "Ngày phải có định dạng YYYY-MM-DD!", Toast.LENGTH_SHORT).show();
            return;
        }

        executorService.execute(() -> {
            List<ChiTietSuDung> filteredChiTietList = new ArrayList<>();
            try {
                Date startDate = sdf.parse(startDateStr);
                Date endDate = sdf.parse(endDateStr);

                for (ChiTietSuDung chiTiet : chiTietSuDungList) {
                    Date usageDate = sdf.parse(chiTiet.getNgaySuDung());
                    if (usageDate.compareTo(startDate) >= 0 && usageDate.compareTo(endDate) <= 0) {
                        filteredChiTietList.add(chiTiet);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Lỗi định dạng ngày!", Toast.LENGTH_SHORT).show());
                return;
            }

            List<ThongKeItem> newThongKeList = aggregateData(filteredChiTietList);
            runOnUiThread(() -> {
                thongKeList = newThongKeList;
                filterByDeviceName(edtSearchDevice.getText().toString().trim()); // Kết hợp với tìm kiếm tên thiết bị
            });
        });
    }

    private void filterByDeviceName(String query) {
        filteredThongKeList = new ArrayList<>();
        for (ThongKeItem item : thongKeList) {
            if (item.getTenThietBi().toLowerCase().contains(query.toLowerCase())) {
                filteredThongKeList.add(item);
            }
        }
        adapter.updateList(filteredThongKeList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}