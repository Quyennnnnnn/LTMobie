package com.example.nhom24.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom24.Model.NhanVien;
import com.example.nhom24.R;

import java.util.List;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.NhanVienViewHolder> {

    private List<NhanVien> nhanVienList;

    public NhanVienAdapter(List<NhanVien> nhanVienList) {
        this.nhanVienList = nhanVienList;
    }

    @NonNull
    @Override
    public NhanVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhan_vien, parent, false);
        return new NhanVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienViewHolder holder, int position) {
        NhanVien nhanVien = nhanVienList.get(position);
        holder.tvEmail.setText(nhanVien.getEmail());
    }

    @Override
    public int getItemCount() {
        return nhanVienList != null ? nhanVienList.size() : 0;
    }

    public static class NhanVienViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvEmail;

        public NhanVienViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvEmail = itemView.findViewById(R.id.tvEmail);
        }
    }
}