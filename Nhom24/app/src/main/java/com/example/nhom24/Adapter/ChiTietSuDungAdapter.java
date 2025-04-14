package com.example.nhom24.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom24.Model.ChiTietSuDung;
import com.example.nhom24.Model.PhongHoc;
import com.example.nhom24.Model.ThietBi;
import com.example.nhom24.R;

import java.util.List;

public class ChiTietSuDungAdapter extends RecyclerView.Adapter<ChiTietSuDungAdapter.ChiTietSuDungViewHolder> {
    private Context contextCTSD;
    private List<ChiTietSuDung> listChiTietSuDungCTSD;
    private List<PhongHoc> listPhongHocCTSD;
    private List<ThietBi> listThietBiCTSD;
    private OnItemClickListener listenerCTSD;

    public interface OnItemClickListener {
        void onEditClick(ChiTietSuDung chiTietSuDung);

        void onDeleteClick(ChiTietSuDung chiTietSuDung);
    }

    public ChiTietSuDungAdapter(Context context, List<ChiTietSuDung> listChiTietSuDung, List<PhongHoc> listPhongHoc, List<ThietBi> listThietBi, OnItemClickListener listener) {
        this.contextCTSD = context;
        this.listChiTietSuDungCTSD = listChiTietSuDung;
        this.listPhongHocCTSD = listPhongHoc;
        this.listThietBiCTSD = listThietBi;
        this.listenerCTSD = listener;
    }

    @NonNull
    @Override
    public ChiTietSuDungViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contextCTSD).inflate(R.layout.item_chi_tiet_su_dung, parent, false);
        return new ChiTietSuDungViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietSuDungViewHolder holder, int position) {
        ChiTietSuDung chiTietSuDung = listChiTietSuDungCTSD.get(position);

        // Tìm tên phòng học
        String tenPhongHoc = "";
        for (PhongHoc ph : listPhongHocCTSD) {
            if (ph.getId() == chiTietSuDung.getPhongHocId()) {
                tenPhongHoc = ph.getTenPhongHoc();
                break;
            }
        }

        // Tìm tên thiết bị
        String tenThietBi = "";
        for (ThietBi tb : listThietBiCTSD) {
            if (tb.getId() == chiTietSuDung.getThietBiId()) {
                tenThietBi = tb.getTenThietBi();
                break;
            }
        }

        holder.tvPhongHocCTSD.setText("Phòng học: " + tenPhongHoc);
        holder.tvThietBiCTSD.setText("Thiết bị: " + tenThietBi);
        holder.tvNgaySuDungCTSD.setText("Ngày sử dụng: " + chiTietSuDung.getNgaySuDung());
        holder.tvTrangThaiCTSD.setText("Trạng thái: " + chiTietSuDung.getTrangThai());

        holder.ivEditCTSD.setOnClickListener(v -> listenerCTSD.onEditClick(chiTietSuDung));
        holder.ivDeleteCTSD.setOnClickListener(v -> listenerCTSD.onDeleteClick(chiTietSuDung));
    }

    @Override
    public int getItemCount() {
        return listChiTietSuDungCTSD.size();
    }

    public void updateListCTSD(List<ChiTietSuDung> newList) {
        this.listChiTietSuDungCTSD = newList;
        notifyDataSetChanged();
    }

    static class ChiTietSuDungViewHolder extends RecyclerView.ViewHolder {
        TextView tvPhongHocCTSD, tvThietBiCTSD, tvNgaySuDungCTSD, tvTrangThaiCTSD;
        ImageView ivEditCTSD, ivDeleteCTSD;

        public ChiTietSuDungViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPhongHocCTSD = itemView.findViewById(R.id.tvPhongHocCTSD);
            tvThietBiCTSD = itemView.findViewById(R.id.tvThietBiCTSD);
            tvNgaySuDungCTSD = itemView.findViewById(R.id.tvNgaySuDungCTSD);
            tvTrangThaiCTSD = itemView.findViewById(R.id.tvTrangThaiCTSD);
            ivEditCTSD = itemView.findViewById(R.id.ivEditCTSD);
            ivDeleteCTSD = itemView.findViewById(R.id.ivDeleteCTSD);
        }
    }
}