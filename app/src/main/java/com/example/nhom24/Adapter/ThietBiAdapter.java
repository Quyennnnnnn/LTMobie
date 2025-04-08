package com.example.nhom24.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom24.Model.ThietBi;
import com.example.nhom24.R;

import java.util.List;

public class ThietBiAdapter extends RecyclerView.Adapter<ThietBiAdapter.ViewHolder> {

    private List<ThietBi> thietBiList;
    private OnItemClickListener listener;

    public ThietBiAdapter(List<ThietBi> thietBiList) {
        this.thietBiList = thietBiList;
    }

    public interface OnItemClickListener {
        void onEditClick(ThietBi item);
        void onDeleteClick(ThietBi item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_thiet_bi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThietBi item = thietBiList.get(position);
        holder.tvMaThietBi.setText("(" + item.getMaThietBi() + ")");
        holder.tvTenThietBi.setText(item.getTenThietBi());
        holder.tvXuatXu.setText("Xuất xứ: " + item.getXuatXu());
        holder.tvSoLuong.setText("SL: " + item.getSoLuong());

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(item);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return thietBiList.size();
    }

    public void updateList(List<ThietBi> newList) {
        thietBiList = newList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaThietBi, tvTenThietBi, tvXuatXu, tvSoLuong;
        ImageButton btnEdit, btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            tvMaThietBi = itemView.findViewById(R.id.tvMaThietBi);
            tvTenThietBi = itemView.findViewById(R.id.tvTenThietBi);
            tvXuatXu = itemView.findViewById(R.id.tvXuatXu);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}