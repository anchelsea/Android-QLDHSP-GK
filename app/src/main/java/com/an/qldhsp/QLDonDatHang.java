package com.an.qldhsp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class QLDonDatHang extends AppCompatActivity {
    DatabaseHelper database;
    ListView lvDonDatHang;

    ArrayList<DonDatHang> donDatHangArrayList;
    ArrayList<KhachHang> khachHangArrayList;
    ArrayList<SanPham> sanPhamArrayList;
    DonDatHangAdapter adapterDonDatHang;
    ViewKhachHangAdapter adapterKhachHang;
    XemSanPhamAdapter adapterSanPham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlhoadon);

        lvDonDatHang = findViewById(R.id.lvdonhang);
        donDatHangArrayList = new ArrayList<>();
        khachHangArrayList = new ArrayList<>();
        sanPhamArrayList = new ArrayList<>();
        adapterDonDatHang = new DonDatHangAdapter(this, R.layout.dong_hoa_don, donDatHangArrayList);
        adapterKhachHang = new ViewKhachHangAdapter(this, R.layout.dong_view_khach_hang, khachHangArrayList);
        adapterSanPham = new XemSanPhamAdapter(this, R.layout.dong_xem_san_pham, sanPhamArrayList);
        lvDonDatHang.setAdapter(adapterDonDatHang);


        database = new DatabaseHelper(this, "QLDHDB.sqlite", null, 1);
        getDataDonHang();
    }

    private void getDataSanPham() {
        String sql = "SELECT * FROM SANPHAM";
        Cursor cursor = database.GetData(sql);
        sanPhamArrayList.clear();
        while (cursor.moveToNext()) {
            int MASP = cursor.getInt(0);
            String tensp = cursor.getString(1);
            String xuatxu = cursor.getString(2);
            int dongia = cursor.getInt(3);
            byte[] hinhanh = cursor.getBlob(4);
//            Log.e("SELECT * FROM :",MASP + "  "+ tensp + "  "+xuatxu+"  "+dongia+"  "+hinhanh);
            sanPhamArrayList.add(new SanPham(MASP, tensp, xuatxu, dongia, hinhanh));
        }
        adapterKhachHang.notifyDataSetChanged();
    }

    private void getDataKhachHang() {
        khachHangArrayList.clear();
        Cursor dataKhachHang = database.GetData("SELECT * FROM KHACHHANG");
        while (dataKhachHang.moveToNext()) {
            int id = dataKhachHang.getInt(0);
            String ten = dataKhachHang.getString(1);
            String diachi = dataKhachHang.getString(2);
            String phone = dataKhachHang.getString(3);
            khachHangArrayList.add(new KhachHang(id, ten, diachi, phone));
        }
        adapterKhachHang.notifyDataSetChanged();
    }

    private void getDataDonHang() {
        donDatHangArrayList.clear();
        Cursor dataDonHang = database.GetData("SELECT * FROM DONDATHANG");
        while (dataDonHang.moveToNext()) {
            int maDH = dataDonHang.getInt(0);
            int maKH = dataDonHang.getInt(2);
            String ngay = dataDonHang.getString(1);
            donDatHangArrayList.add(new DonDatHang(maDH, ngay, maKH));
        }
        adapterDonDatHang.notifyDataSetChanged();
    }

//    private void getDataTTDDH(SanPhamAdapter tempAdapter, ArrayList<SanPham> TTDDHarr, int maDH) {
//        TTDDHarr.clear();
//        Cursor dataTTDDH = database.GetData("SELECT * FROM THONGTINDDH WHERE MAKH=" + maDH);
//        while (dataTTDDH.moveToNext()) {
//            int maSP = dataTTDDH.getInt(1);
//            int slDat = dataTTDDH.getInt(2);
//            TTDDHarr.add(new ThongTinDDH(maDH, maSP, slDat));
//        }
//        tempAdapter.notifyDataSetChanged();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_dondathang, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_dondathang) {
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    private void DialogThem() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_don_dat_hang);


        Spinner spnKhachHang;
        spnKhachHang = (Spinner) dialog.findViewById(R.id.spnKH);
        spnKhachHang.setAdapter(adapterKhachHang);
        getDataKhachHang();

        EditText ngayDDH = dialog.findViewById(R.id.etNgayDH);
        Button btnThem = dialog.findViewById(R.id.btnThemDDH);
        Button btnHuy = dialog.findViewById(R.id.btnHuyDDH);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ngay = ngayDDH.getText().toString();
                if (ngay.equals("")) {
                    Toast.makeText(QLDonDatHang.this, "Vui lòng nhập ngày!", Toast.LENGTH_SHORT).show();
                } else {
                    KhachHang kh = khachHangArrayList.get(spnKhachHang.getSelectedItemPosition());
                    String query = String.format("INSERT INTO DONDATHANG(MADH,NGAYDH,MAKH) VALUES(null,'%s','%d')", ngay, kh.getId());
                    Log.i("hi", query);
                    database.QueryData(query);
                    Toast.makeText(QLDonDatHang.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDataDonHang();
                }
            }
        });
        dialog.show();
    }

    public void DialogSua(int maDDH, int maKH, String ngay) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua_don_dat_hang);

        ArrayList<ThongTinDDH> thongTinDDHArrayList = new ArrayList<>();
        ListView lvTTDDH = dialog.findViewById(R.id.lvTTDDH);

        ArrayList<SanPham> tempSPList = new ArrayList<>();
        XemSanPhamAdapter tempSPAdapter = new XemSanPhamAdapter(this, R.layout.dong_xem_san_pham, tempSPList);
//        getDataTTDDH(tempSPAdapter,tempSPList,maDDH);
        lvTTDDH.setAdapter(tempSPAdapter);

        Spinner spnKhachHang;
        spnKhachHang = (Spinner) dialog.findViewById(R.id.spnKH);
        spnKhachHang.setAdapter(adapterKhachHang);
        getDataKhachHang();
        for (int i = 0; i < khachHangArrayList.size(); i++) {
            if (khachHangArrayList.get(i).getId() == maKH)
                spnKhachHang.setSelection(i);
        }

        EditText ngayDDH = dialog.findViewById(R.id.etNgayDH);
        ngayDDH.setText(ngay);
        Button btnSua = dialog.findViewById(R.id.btnSuaDDH);
        Button btnHuySua = dialog.findViewById(R.id.btnHuySuaDDH);

        btnHuySua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ngay = ngayDDH.getText().toString();
                if (ngay.equals("")) {
                    Toast.makeText(QLDonDatHang.this, "Vui lòng nhập ngày!", Toast.LENGTH_SHORT).show();
                } else {
                    KhachHang kh = khachHangArrayList.get(spnKhachHang.getSelectedItemPosition());
                    String query = String.format("UPDATE DONDATHANG SET NGAYDH='%s',MAKH=%d WHERE MADH=%d", ngay, kh.getId(), maDDH);
                    database.QueryData(query);
                    for (ThongTinDDH i : thongTinDDHArrayList) {
                        String query2 = String.format("INSERT INTO THONGTINDDH(MADH,MASP,SOLUONGDAT) VALUES(%d,%d,%d)", maDDH, i.getMaSP(), 1);
                        database.QueryData(query2);
                    }
                    Toast.makeText(QLDonDatHang.this, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDataDonHang();
                }
            }
        });
        dialog.show();
        Button btnpicker = dialog.findViewById(R.id.btnpicker);
        btnpicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThemSP(thongTinDDHArrayList, tempSPAdapter, tempSPList);

            }
        });

    }

    public void DialogThemSP(ArrayList<ThongTinDDH> thongTinDDHarr, XemSanPhamAdapter fakeAdapter, ArrayList<SanPham> fakearr) {
        getDataSanPham();
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_sp);

        Spinner spnSP;
        spnSP = (Spinner) dialog.findViewById(R.id.spnSP);
        spnSP.setAdapter(adapterSanPham);
        Button btnOk = dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThongTinDDH tempTT = new ThongTinDDH(-1, sanPhamArrayList.get(spnSP.getSelectedItemPosition()).getMaSP(), 1);
                thongTinDDHarr.add(tempTT);
                dialog.dismiss();
                SanPham tempSP = null;
                for (SanPham i : sanPhamArrayList) {
                    if (i.getMaSP() == tempTT.getMaSP()) {
                        tempSP = i;
                    }
                }
                fakearr.add(tempSP);
                Log.i("hi", String.valueOf(tempSP.getTenSP()));
                fakeAdapter.notifyDataSetChanged();
            }
        });

        dialog.show();
    }

    public void DialogHienThi(int maDH) {
        getDataSanPham();
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_xem_don_dat_hang);

        TextView tvHienThi = dialog.findViewById(R.id.tvXemDDH);
        tvHienThi.setText("Mã DDH: "+maDH+"\n");

        ArrayList<ThongTinDDH> thongTinDDHArrayList = new ArrayList<>();
        ListView lvTTDDH = dialog.findViewById(R.id.lvXEMTTDDH);

        ArrayList<SanPham> tempSPList = new ArrayList<>();
        XemSanPhamAdapter tempSPAdapter = new XemSanPhamAdapter(this, R.layout.dong_xem_san_pham, tempSPList);
        lvTTDDH.setAdapter(tempSPAdapter);

        thongTinDDHArrayList.clear();
        Cursor dataTTDDH = database.GetData("SELECT * FROM THONGTINDDH WHERE MADH=" + maDH);
        while (dataTTDDH.moveToNext()) {
            int maSP = dataTTDDH.getInt(1);
            int slDat = dataTTDDH.getInt(2);
            thongTinDDHArrayList.add(new ThongTinDDH(maDH, maSP, slDat));
        }
        tempSPList.clear();
        for (ThongTinDDH i : thongTinDDHArrayList) {
            for (int j = 0; j < sanPhamArrayList.size(); j++) {
                if (sanPhamArrayList.get(j).getMaSP() == i.getMaSP()) {
                    tempSPList.add(sanPhamArrayList.get(j));
                    break;
                }
            }
        }

        tempSPAdapter.notifyDataSetChanged();
        dialog.show();
    }

    public void DialogXoa(int id) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Xóa đơn hàng ?");
        dialogXoa.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM DONDATHANG WHERE MADH=" + id);
                Toast.makeText(QLDonDatHang.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                getDataDonHang();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogXoa.show();
    }


}