package com.an.qldhsp;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DonDatHangAdapter extends BaseAdapter {

    private QLDonDatHang context;
    private int layout;
    private List<DonDatHang> DonDatHangList;

    public DonDatHangAdapter(QLDonDatHang context, int layout, List<DonDatHang> DonDatHangList) {
        this.context = context;
        this.layout = layout;
        this.DonDatHangList = DonDatHangList;
    }

    @Override
    public int getCount() {
        return DonDatHangList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView tvMaDH, tvMaKH, tvNgay, tvThanhTien;
        ImageView ivDelete, ivEdit;
        LinearLayout layoutDDH;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.tvMaDH = convertView.findViewById(R.id.tvMaHD);
            holder.tvMaKH = convertView.findViewById(R.id.tvMaKH);
            holder.tvNgay = convertView.findViewById(R.id.tvNgay);
            holder.tvThanhTien = convertView.findViewById(R.id.tvThanhTien);
            holder.ivEdit = convertView.findViewById(R.id.ivEditHD);
            holder.ivDelete = convertView.findViewById(R.id.ivDeleteHD);
            holder.layoutDDH=convertView.findViewById(R.id.layout_dong_HD);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DonDatHang donDatHang = DonDatHangList.get(position);
        holder.tvMaDH .setText("Mã DDH: "+String.valueOf(donDatHang.getMaDH()));
        holder.tvMaKH.setText("Mã KH: " + donDatHang.getMaKH());
        holder.tvNgay.setText("Ngày: " + donDatHang.getNgay());
        holder.tvThanhTien.setText("Thành tiền: "  );

        //bat su kien xoa sua
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSua(donDatHang.getMaDH(),donDatHang.getMaKH(),donDatHang.getNgay());
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoa(donDatHang.getMaDH());
            }
        });

        holder.layoutDDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogHienThi(donDatHang.getMaDH());
            }
        });

        return convertView;
    }
}
