package com.an.qldhsp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class XemSanPhamAdapter extends BaseAdapter {
    Context context;
    private int layout;
    private List<SanPham> sanPhamList;



    public XemSanPhamAdapter(Context context, int layout, List<SanPham> sanPhamList) {
        this.context = context;
        this.layout = layout;
        this.sanPhamList = sanPhamList;
    }

    @Override
    public int getCount() {
        return sanPhamList.size();
    }

    @Override
    public Object getItem(int position) {
        return sanPhamList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(layout,null);

        // ánh xạ và gán giá trị
        TextView txtTenSP =  (TextView) convertView.findViewById(R.id.tvXemTenSP);
        txtTenSP.setText("Tên Sản Phẩm : " + sanPhamList.get(position).getTenSP());

        TextView txtDonGia = convertView.findViewById(R.id.tvXemGiaSP);
        txtDonGia.setText("Đơn Giá : " + String.valueOf(sanPhamList.get(position).getDonGia()));

        ImageView imgSP = (ImageView) convertView.findViewById(R.id.ivXemHinhSP);
        Bitmap bitmap = BitmapFactory.decodeByteArray(sanPhamList.get(position).getHinhAnh(),0,sanPhamList.get(position).getHinhAnh().length);
        imgSP.setImageBitmap(bitmap);


        return convertView;
    }

}
