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

public class TopSanPhamAdapter extends BaseAdapter {
    HienThiSanPham context;
    private int layout;
    private List<SanPham> sanPhamList;
    private List<Integer> soLuongList;


    public TopSanPhamAdapter(HienThiTopSanPham context, int layout, List<SanPham> sanPhamList, List<Integer> soLuongList) {
        this.context = context;
        this.layout = layout;
        this.sanPhamList = sanPhamList;
        this.soLuongList = soLuongList;
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
        TextView txtTenSP =  (TextView) convertView.findViewById(R.id.hienthitensptop);
        txtTenSP.setText("Tên Sản Phẩm : " + sanPhamList.get(position).getTenSP());

        TextView txtXuatXu = convertView.findViewById(R.id.hienthixuatxutop);
        txtXuatXu.setText("Xuất Xứ : "+sanPhamList.get(position).getXuatXu());

        TextView txtDonGia = convertView.findViewById(R.id.hienthidongiatop);
        txtDonGia.setText("Đơn Gía : " + String.valueOf(sanPhamList.get(position).getDonGia()));

        TextView txtSoLuong = convertView.findViewById(R.id.hienthisoluong);
        txtSoLuong.setText("Số lượng: " + String.valueOf(soLuongList.get(position)));

        ImageView imgSP = (ImageView) convertView.findViewById(R.id.hienthihinhsanphamtop);
        Bitmap bitmap = BitmapFactory.decodeByteArray(sanPhamList.get(position).getHinhAnh(),0,sanPhamList.get(position).getHinhAnh().length);
        imgSP.setImageBitmap(bitmap);

        return convertView;
    }

}
