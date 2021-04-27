package com.an.qldhsp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ViewKhachHangAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<KhachHang> khachHangList;

    public ViewKhachHangAdapter(Context context, int layout, List<KhachHang> khachHangList) {
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
        TextView tvTen,tvID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.tvTen = convertView.findViewById(R.id.tvXemTen);
            holder.tvID=convertView.findViewById(R.id.tvXemId);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        KhachHang khachHang = khachHangList.get(position);
        holder.tvTen.setText(khachHang.getTen());
        holder.tvID.setText("ID: "+String.valueOf(khachHang.getId()));


        return convertView;
    }
}
