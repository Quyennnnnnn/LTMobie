package com.example.nhom24.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom24.Model.PhongHoc;
import com.example.nhom24.R;

import java.util.ArrayList;
import java.util.List;

public class PhongHocAdapter extends RecyclerView.Adapter<PhongHocAdapter.PhongHocViewHolder> {
    private Context contextPH;
    private List<PhongHoc> listPhongHocPH;
    private List<PhongHoc> listPhongHocFull; // Danh sách đầy đủ để lọc
    private OnItemClickListener listenerPH;

    public interface OnItemClickListener {
        void onEditClick(PhongHoc phongHoc);

        void onDeleteClick(PhongHoc phongHoc);
    }

    public PhongHocAdapter(Context context, List<PhongHoc> listPhongHoc, OnItemClickListener listener) {
        this.contextPH = context;
        this.listPhongHocPH = listPhongHoc;
        this.listPhongHocFull = new ArrayList<>(listPhongHoc); // Sao chép danh sách đầy đủ
        this.listenerPH = listener;
    }

    @NonNull
    @Override
    public PhongHocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contextPH).inflate(R.layout.item_phong_hoc, parent, false);
        return new PhongHocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhongHocViewHolder holder, int position) {
        PhongHoc phongHoc = listPhongHocPH.get(position);
        holder.tvMaPhongHoc.setText("Mã phòng: " + phongHoc.getMaPhongHoc());
        holder.tvTenPhongHoc.setText("Tên phòng: " + phongHoc.getTenPhongHoc());

        holder.ivEdit.setOnClickListener(v -> listenerPH.onEditClick(phongHoc));
        holder.ivDelete.setOnClickListener(v -> listenerPH.onDeleteClick(phongHoc));
    }

    @Override
    public int getItemCount() {
        return listPhongHocPH.size();
    }

    public void updateListPH(List<PhongHoc> newList) {
        this.listPhongHocPH = newList;
        this.listPhongHocFull = new ArrayList<>(newList); // Cập nhật danh sách đầy đủ
        notifyDataSetChanged();
    }

    // Hàm lọc danh sách dựa trên từ khóa tìm kiếm
    public void filter(String query) {
        listPhongHocPH.clear();
        if (query.isEmpty()) {
            listPhongHocPH.addAll(listPhongHocFull);
        } else {
            query = query.toLowerCase();
            for (PhongHoc phongHoc : listPhongHocFull) {
                if (phongHoc.getMaPhongHoc().toLowerCase().contains(query) ||
                        phongHoc.getTenPhongHoc().toLowerCase().contains(query)) {
                    listPhongHocPH.add(phongHoc);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class PhongHocViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaPhongHoc, tvTenPhongHoc;
        ImageView ivEdit, ivDelete;

        public PhongHocViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaPhongHoc = itemView.findViewById(R.id.tvMaPhongHoc);
            tvTenPhongHoc = itemView.findViewById(R.id.tvTenPhongHoc);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }
}