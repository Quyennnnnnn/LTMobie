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

        btnBack = findViewById(R.id.btnBack);
        pieChart = findViewById(R.id.pieChart);

        database = AppDatabase.getInstance(this);
        executorService = Executors.newSingleThreadExecutor();
        sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        startDateStr = getIntent().getStringExtra("start_date");
        endDateStr = getIntent().getStringExtra("end_date");

        if (startDateStr == null || endDateStr == null || startDateStr.isEmpty() || endDateStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        btnBack.setOnClickListener(v -> finish());

        loadChartData();
    }

    private void loadChartData() {
        executorService.execute(() -> {
            List<ChiTietSuDung> chiTietSuDungList = database.chiTietSuDungDAO().getAllChiTietSuDung();
            List<ThietBi> thietBiList = database.thietBiDAO().getAll();

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

            Map<Integer, Integer> usageCountMap = new HashMap<>();
            int totalUsage = 0;

            for (ChiTietSuDung chiTiet : filteredChiTietList) {
                int thietBiId = chiTiet.getThietBiId();
                usageCountMap.put(thietBiId, usageCountMap.getOrDefault(thietBiId, 0) + 1);
                totalUsage++;
            }

            List<PieEntry> entries = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : usageCountMap.entrySet()) {
                int thietBiId = entry.getKey();
                int count = entry.getValue();

                String tenThietBi = "";
                for (ThietBi tb : thietBiList) {
                    if (tb.getId() == thietBiId) {
                        tenThietBi = tb.getTenThietBi();
                        break;
                    }
                }

                float percentage = (count * 100f) / totalUsage;
                PieEntry pieEntry = new PieEntry(percentage, tenThietBi);
                pieEntry.setData(count);
                entries.add(pieEntry);
            }

            runOnUiThread(() -> {
                if (entries.isEmpty()) {
                    Toast.makeText(this, "Không có dữ liệu để hiển thị biểu đồ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                PieDataSet dataSet = new PieDataSet(entries, "");
                dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                dataSet.setValueTextSize(20f);
                dataSet.setValueTextColor(Color.BLACK);
                dataSet.setDrawValues(true);
                dataSet.setValueFormatter(new PercentFormatter(pieChart));



                PieData pieData = new PieData(dataSet);
                pieChart.setData(pieData);

                pieChart.getDescription().setEnabled(false);
                pieChart.setUsePercentValues(true);
                pieChart.setDrawEntryLabels(true);
                pieChart.setDrawHoleEnabled(false);
                pieChart.setHoleColor(android.R.color.white);
                pieChart.setTransparentCircleRadius(40f);
                pieChart.setHoleRadius(35f);

                Legend legend = pieChart.getLegend();
                legend.setEnabled(true);
                legend.setTextSize(11f);
                legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
                legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                legend.setDrawInside(false);
                legend.setWordWrapEnabled(true);
                legend.setMaxSizePercent(1.0f);
                legend.setFormSize(8f);
                legend.setXEntrySpace(15f);
                legend.setYEntrySpace(5f);
                legend.setTextColor(Color.BLACK);

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