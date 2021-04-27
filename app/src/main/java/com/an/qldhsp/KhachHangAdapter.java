package com.an.qldhsp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class KhachHangAdapter extends BaseAdapter {

    private com.an.qldhsp.QLKhachHang context;
    private int layout;
    private List<KhachHang> khachHangList;

    public KhachHangAdapter(QLKhachHang context, int layout, List<KhachHang> khachHangList) {
        this.context = context;
        this.layout = layout;
        this.khachHangList = khachHangList;
    }

    @Override
    public int getCount() {
        return khachHangList.size();
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
        TextView tvTen,tvID,tvDiaChi,tvPhone;
        ImageView ivDelete, ivEdit;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.tvTen = convertView.findViewById(R.id.tvTen);
            holder.ivEdit = convertView.findViewById(R.id.ivEdit);
            holder.ivDelete = convertView.findViewById(R.id.ivDelete);
            holder.tvID=convertView.findViewById(R.id.tvId);
            holder.tvDiaChi=convertView.findViewById(R.id.tvDiachi);
            holder.tvPhone=convertView.findViewById(R.id.tvDienthoai);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        KhachHang khachHang = khachHangList.get(position);
        holder.tvTen.setText(khachHang.getTen());
        holder.tvID.setText("ID: "+String.valueOf(khachHang.getId()));
        holder.tvDiaChi.setText("Địa chỉ: "+khachHang.getDiachi());
        holder.tvPhone.setText("Phone: "+khachHang.getPhone());
        
        //bat su kien xoa sua
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSua(khachHang.getId(),khachHang.getTen(),khachHang.getDiachi(),khachHang.getPhone());
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoa(khachHang.getId(),khachHang.getTen());
            }
        });
        
        return convertView;
    }
}
