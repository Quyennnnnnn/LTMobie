package com.example.nhom24.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom24.Model.LoaiThietBi;
import com.example.nhom24.R;

import java.util.List;

public class LoaiThietBiAdapter extends RecyclerView.Adapter<LoaiThietBiAdapter.ViewHolder> {

    private List<LoaiThietBi> loaiThietBiList;
    private OnItemClickListener listener;

    public LoaiThietBiAdapter(List<LoaiThietBi> loaiThietBiList) {
        this.loaiThietBiList = loaiThietBiList;
    }

    public interface OnItemClickListener {
        void onEditClick(LoaiThietBi item);
        void onDeleteClick(LoaiThietBi item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_loai_thiet_bi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiThietBi item = loaiThietBiList.get(position);
        holder.tvMaLoaiThietBi.setText("(" + item.getMathietbi() + ")");
        holder.tvTenLoaiThietBi.setText(item.getTenthietbi());

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(item);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return loaiThietBiList.size();
    }

    public void updateList(List<LoaiThietBi> newList) {
        loaiThietBiList = newList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaLoaiThietBi, tvTenLoaiThietBi;
        ImageButton btnEdit, btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            tvMaLoaiThietBi = itemView.findViewById(R.id.tvMaLoaiThietBi);
            tvTenLoaiThietBi = itemView.findViewById(R.id.tvTenLoaiThietBi);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}