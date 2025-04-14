package com.example.nhom24.UI;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom24.Database.AppDatabase;
import com.example.nhom24.Model.ChiTietSuDung;
import com.example.nhom24.Model.ThietBi;
import com.example.nhom24.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChartActivity extends AppCompatActivity {
    private ImageView btnBack;
    private PieChart pieChart;
    private AppDatabase database;
    private ExecutorService executorService;
    private SimpleDateFormat sdf;
    private String startDateStr, endDateStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        // Ánh xạ view
        btnBack = findViewById(R.id.btnBack);
        pieChart = findViewById(R.id.pieChart);

        // Khởi tạo database và executor
        database = AppDatabase.getInstance(this);
        executorService = Executors.newSingleThreadExecutor();
        sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        // Lấy ngày bắt đầu và ngày kết thúc từ Intent
        startDateStr = getIntent().getStringExtra("start_date");
        endDateStr = getIntent().getStringExtra("end_date");

        // Kiểm tra dữ liệu đầu vào
        if (startDateStr == null || endDateStr == null || startDateStr.isEmpty() || endDateStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Xử lý nút Back
        btnBack.setOnClickListener(v -> finish());

        // Tải và hiển thị biểu đồ
        loadChartData();
    }

    private void loadChartData() {
        executorService.execute(() -> {
            // Lấy dữ liệu từ database
            List<ChiTietSuDung> chiTietSuDungList = database.chiTietSuDungDAO().getAllChiTietSuDung();
            List<ThietBi> thietBiList = database.thietBiDAO().getAll();

            // Lọc dữ liệu theo khoảng thời gian
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

            // Tính toán tỷ lệ sử dụng theo thiết bị
            Map<Integer, Integer> usageCountMap = new HashMap<>();
            int totalUsage = 0;

            for (ChiTietSuDung chiTiet : filteredChiTietList) {
                int thietBiId = chiTiet.getThietBiId();
                usageCountMap.put(thietBiId, usageCountMap.getOrDefault(thietBiId, 0) + 1);
                totalUsage++;
            }

            // Chuẩn bị dữ liệu cho biểu đồ
            List<PieEntry> entries = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : usageCountMap.entrySet()) {
                int thietBiId = entry.getKey();
                int count = entry.getValue();

                // Tìm tên thiết bị
                String tenThietBi = "";
                for (ThietBi tb : thietBiList) {
                    if (tb.getId() == thietBiId) {
                        tenThietBi = tb.getTenThietBi();
                        break;
                    }
                }


                // Tính tỷ lệ phần trăm
                float percentage = (count * 100f) / totalUsage;
                // Lưu cả giá trị tuyệt đối (count) và phần trăm (percentage) vào PieEntry
                PieEntry pieEntry = new PieEntry(percentage, tenThietBi);
                pieEntry.setData(count); // Lưu giá trị tuyệt đối vào data
                entries.add(pieEntry);
            }


            // Cập nhật biểu đồ trên main thread
            runOnUiThread(() -> {
                if (entries.isEmpty()) {
                    Toast.makeText(this, "Không có dữ liệu để hiển thị biểu đồ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Cấu hình dữ liệu biểu đồ
                PieDataSet dataSet = new PieDataSet(entries, "");
                dataSet.setColors(ColorTemplate.MATERIAL_COLORS); // Sử dụng màu sắc có sẵn
                dataSet.setValueTextSize(20f); // Kích thước chữ của số phần trăm
                dataSet.setValueTextColor(Color.BLACK); // Màu chữ số phần trăm
                dataSet.setDrawValues(true); // Bật hiển thị số phần trăm trên biểu đồ
                dataSet.setValueFormatter(new PercentFormatter(pieChart)); // Hiển thị giá trị dưới dạng %



                PieData pieData = new PieData(dataSet);
                pieChart.setData(pieData);

                // Cấu hình giao diện biểu đồ
                pieChart.getDescription().setEnabled(false); // Tắt mô tả
                pieChart.setUsePercentValues(true); // Hiển thị giá trị dưới dạng %
                pieChart.setDrawEntryLabels(true); // Tắt nhãn trên các phần (chỉ hiển thị số %)
                pieChart.setDrawHoleEnabled(false); // Tạo lỗ ở giữa
                pieChart.setHoleColor(android.R.color.white); // Màu lỗ ở giữa
                pieChart.setTransparentCircleRadius(40f);
                pieChart.setHoleRadius(35f);

                // Cấu hình chú thích (legend)
                Legend legend = pieChart.getLegend();
                legend.setEnabled(true); // Bật chú thích
                legend.setTextSize(11f); // Kích thước chữ chú thích
                legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM); // Đặt chú thích ở dưới
                legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT); // Căn giữa ngang
                legend.setOrientation(Legend.LegendOrientation.HORIZONTAL); // Chú thích hiển thị theo chiều ngang
                legend.setDrawInside(false); // Không vẽ bên trong biểu đồ
                legend.setWordWrapEnabled(true); // Tự động xuống dòng nếu chú thích dài
                legend.setMaxSizePercent(1.0f); // Cho phép chú thích sử dụng toàn bộ không gian khả dụng
                legend.setFormSize(8f); // Giảm kích thước ô màu để hiển thị nhiều mục hơn
                legend.setXEntrySpace(15f); // Khoảng cách ngang giữa các mục
                legend.setYEntrySpace(5f); // Khoảng cách dọc giữa các mục
                legend.setTextColor(Color.BLACK); // Màu chữ chú thích

                // Cập nhật biểu đồ
                pieChart.invalidate();
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}