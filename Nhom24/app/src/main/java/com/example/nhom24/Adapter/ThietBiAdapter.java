package com.example.nhom24.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhom24.Model.LoaiThietBi;
import com.example.nhom24.Model.ThietBi;
import com.example.nhom24.R;

import java.util.List;

public class ThietBiAdapter extends RecyclerView.Adapter<ThietBiAdapter.ViewHolder> {

    private List<ThietBi> thietBiList;
    private List<LoaiThietBi> loaiThietBiList;
    private OnItemClickListener listener;
    private final OnImageClickListener imageClickListener;

    public interface OnItemClickListener {
        void onEditClick(ThietBi item);
        void onDeleteClick(ThietBi item);
    }

    public interface OnImageClickListener {
        void onImageClick(String imageUrl);
    }

    public ThietBiAdapter(List<ThietBi> thietBiList, List<LoaiThietBi> loaiThietBiList, OnImageClickListener imageClickListener) {
        this.thietBiList = thietBiList;
        this.loaiThietBiList = loaiThietBiList;
        this.imageClickListener = imageClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void updateList(List<ThietBi> thietBiList, List<LoaiThietBi> loaiThietBiList) {
        this.thietBiList = thietBiList;
        this.loaiThietBiList = loaiThietBiList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thiet_bi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThietBi thietBi = thietBiList.get(position);
        holder.tvMaThietBi.setText(thietBi.getMaThietBi());
        holder.tvTenThietBi.setText(thietBi.getTenThietBi());
        holder.tvXuatXu.setText("Xuất xứ: " + thietBi.getXuatXu());
        holder.tvTinhTrang.setText("Tình trạng: " + thietBi.getTinhTrang());
        holder.tvSoLuong.setText("SL: " + thietBi.getSoLuong());

        String loaiThietBiName = "Không xác định";
        if (loaiThietBiList != null) {
            for (LoaiThietBi loai : loaiThietBiList) {
                if (loai.getId() == thietBi.getLoaiThietBiId()) {
                    loaiThietBiName = loai.getTenthietbi();
                    break;
                }
            }
        }
        holder.tvLoaiThietBi.setText("Loại: " + loaiThietBiName);

        if (thietBi.getImageUrl() != null && !thietBi.getImageUrl().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(Uri.parse(thietBi.getImageUrl()))
                    .error(R.drawable.ic_camera)
                    .placeholder(R.drawable.ic_camera)
                    .into(holder.imgThietBi);
        } else {
            holder.imgThietBi.setImageResource(R.drawable.ic_camera);
        }

        // Image click listener
        holder.imgThietBi.setOnClickListener(v -> {
            if (imageClickListener != null) {
                imageClickListener.onImageClick(thietBi.getImageUrl());
            }
        });

        // Edit and delete listeners
        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(thietBi);
        });
        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(thietBi);
        });
    }

    @Override
    public int getItemCount() {
        return thietBiList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaThietBi, tvTenThietBi, tvXuatXu, tvTinhTrang, tvSoLuong, tvLoaiThietBi;
        ImageView imgThietBi;
        ImageButton btnEdit, btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaThietBi = itemView.findViewById(R.id.tvMaThietBi);
            tvTenThietBi = itemView.findViewById(R.id.tvTenThietBi);
            tvXuatXu = itemView.findViewById(R.id.tvXuatXu);
            tvTinhTrang = itemView.findViewById(R.id.tvTinhTrang);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            tvLoaiThietBi = itemView.findViewById(R.id.tvLoaiThietBi);
            imgThietBi = itemView.findViewById(R.id.imgThietBi);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}