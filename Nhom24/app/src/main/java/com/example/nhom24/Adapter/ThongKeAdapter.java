package com.example.nhom24.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom24.Model.ThongKeItem;
import com.example.nhom24.R;

import java.util.List;

public class ThongKeAdapter extends RecyclerView.Adapter<ThongKeAdapter.ThongKeViewHolder> {
    private List<ThongKeItem> thongKeList;
    private Context context;

    public ThongKeAdapter(Context context, List<ThongKeItem> thongKeList) {
        this.context = context;
        this.thongKeList = thongKeList;
    }

    public void updateList(List<ThongKeItem> newList) {
        this.thongKeList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThongKeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thong_ke, parent, false);
        return new ThongKeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThongKeViewHolder holder, int position) {
        ThongKeItem thongKeItem = thongKeList.get(position);

        holder.tvNgaySuDung.setText(thongKeItem.getNgaySuDung());
        holder.tvTenThietBi.setText(thongKeItem.getTenThietBi());
        holder.tvMaPhong.setText(thongKeItem.getMaPhong());
        holder.tvSoLuong.setText(String.valueOf(thongKeItem.getSoLuongDaMuon()));
    }

    @Override
    public int getItemCount() {
        return thongKeList.size();
    }

    public static class ThongKeViewHolder extends RecyclerView.ViewHolder {
        TextView tvNgaySuDung, tvTenThietBi, tvMaPhong, tvSoLuong;

        public ThongKeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNgaySuDung = itemView.findViewById(R.id.tvNgaySuDung);
            tvTenThietBi = itemView.findViewById(R.id.tvTenThietBi);
            tvMaPhong = itemView.findViewById(R.id.tvMaPhong);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
        }
    }
}