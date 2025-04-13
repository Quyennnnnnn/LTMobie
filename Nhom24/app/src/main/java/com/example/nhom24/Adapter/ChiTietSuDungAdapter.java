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
    private Context context;
    private List<ChiTietSuDung> listChiTietSuDung;
    private List<PhongHoc> listPhongHoc;
    private List<ThietBi> listThietBi;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(ChiTietSuDung chiTietSuDung);
        void onDeleteClick(ChiTietSuDung chiTietSuDung);
    }

    public ChiTietSuDungAdapter(Context context, List<ChiTietSuDung> listChiTietSuDung, List<PhongHoc> listPhongHoc, List<ThietBi> listThietBi, OnItemClickListener listener) {
        this.context = context;
        this.listChiTietSuDung = listChiTietSuDung;
        this.listPhongHoc = listPhongHoc;
        this.listThietBi = listThietBi;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChiTietSuDungViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chi_tiet_su_dung, parent, false);
        return new ChiTietSuDungViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietSuDungViewHolder holder, int position) {
        ChiTietSuDung chiTietSuDung = listChiTietSuDung.get(position);

        String tenPhongHoc = "";
        for (PhongHoc ph : listPhongHoc) {
            if (ph.getId() == chiTietSuDung.getPhongHocId()) {
                tenPhongHoc = ph.getTenPhongHoc();
                break;
            }
        }

        String tenThietBi = "";
        for (ThietBi tb : listThietBi) {
            if (tb.getId() == chiTietSuDung.getThietBiId()) {
                tenThietBi = tb.getTenThietBi();
                break;
            }
        }

        holder.tvPhongHocCTSD.setText("(" + tenPhongHoc + ")");
        holder.tvThietBiCTSD.setText(tenThietBi);

        holder.ivEditCTSD.setOnClickListener(v -> listener.onEditClick(chiTietSuDung));
        holder.ivDeleteCTSD.setOnClickListener(v -> listener.onDeleteClick(chiTietSuDung));
    }

    @Override
    public int getItemCount() {
        return listChiTietSuDung.size();
    }

    public void updateListCTSD(List<ChiTietSuDung> newList) {
        this.listChiTietSuDung = newList;
        notifyDataSetChanged();
    }

    static class ChiTietSuDungViewHolder extends RecyclerView.ViewHolder {
        TextView tvPhongHocCTSD, tvThietBiCTSD;
        ImageView ivEditCTSD, ivDeleteCTSD;

        public ChiTietSuDungViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPhongHocCTSD = itemView.findViewById(R.id.tvPhongHocCTSD);
            tvThietBiCTSD = itemView.findViewById(R.id.tvThietBiCTSD);
            ivEditCTSD = itemView.findViewById(R.id.ivEditCTSD);
            ivDeleteCTSD = itemView.findViewById(R.id.ivDeleteCTSD);
        }
    }
}