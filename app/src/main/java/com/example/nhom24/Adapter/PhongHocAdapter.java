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

import java.util.List;

public class PhongHocAdapter extends RecyclerView.Adapter<PhongHocAdapter.PhongHocViewHolder> {
    private Context context;
    private List<PhongHoc> listPhongHoc;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(PhongHoc phongHoc);
        void onDeleteClick(PhongHoc phongHoc);
    }

    public PhongHocAdapter(Context context, List<PhongHoc> listPhongHoc, OnItemClickListener listener) {
        this.context = context;
        this.listPhongHoc = listPhongHoc;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PhongHocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_phong_hoc, parent, false);
        return new PhongHocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhongHocViewHolder holder, int position) {
        PhongHoc phongHoc = listPhongHoc.get(position);
        holder.tvMaPhongHoc.setText("(" + phongHoc.getMaPhongHoc() + ")");
        holder.tvTenPhongHoc.setText(phongHoc.getTenPhongHoc());

        holder.ivEdit.setOnClickListener(v -> listener.onEditClick(phongHoc));
        holder.ivDelete.setOnClickListener(v -> listener.onDeleteClick(phongHoc));
    }

    @Override
    public int getItemCount() {
        return listPhongHoc.size();
    }

    public void updateListPH(List<PhongHoc> newList) {
        this.listPhongHoc = newList;
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