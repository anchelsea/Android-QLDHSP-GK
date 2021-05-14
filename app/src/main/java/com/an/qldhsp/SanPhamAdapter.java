package com.an.qldhsp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SanPhamAdapter extends BaseAdapter {
    HienThiSanPham context;
    private int layout;
    private List<SanPham> sanPhamList;



    public SanPhamAdapter(HienThiSanPham context, int layout, List<SanPham> sanPhamList) {
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
        TextView txtTenSP =  (TextView) convertView.findViewById(R.id.hienthitensp);
        txtTenSP.setText("Tên Sản Phẩm : " + sanPhamList.get(position).getTenSP());

        TextView txtXuatXu = convertView.findViewById(R.id.hienthixuatxu);
        txtXuatXu.setText("Xuất Xứ : "+sanPhamList.get(position).getXuatXu());

        TextView txtDonGia = convertView.findViewById(R.id.hienthidongia);
        txtDonGia.setText("Đơn Gía : " + String.valueOf(sanPhamList.get(position).getDonGia()));

        ImageView imgSP = (ImageView) convertView.findViewById(R.id.hienthihinhsanpham);
        Bitmap bitmap = BitmapFactory.decodeByteArray(sanPhamList.get(position).getHinhAnh(),0,sanPhamList.get(position).getHinhAnh().length);
        imgSP.setImageBitmap(bitmap);


        ///// su kien xoa sua
        final SanPham sanPham = sanPhamList.get(position);


        /// sua san pham
        ImageButton ibtn_sua = convertView.findViewById(R.id.imgBtn_suasp);
        ibtn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSua(sanPham.getMaSP(),sanPham.getTenSP(),sanPham.getXuatXu(),sanPham.getDonGia(),sanPham.getHinhAnh());

            }
        });


        /// xoa sp
        ImageButton ibtn_xoa = convertView.findViewById(R.id.imgBtn_xoasp);
        ibtn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tensp = sanPham.getTenSP();
                int masp = sanPham.getMaSP();
                context.DialogXoa(masp,tensp);
            }
        });

        return convertView;
    }

}
