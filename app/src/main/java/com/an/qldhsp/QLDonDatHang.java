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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class QLDonDatHang extends AppCompatActivity {
    DatabaseHelper database;
    ListView lvDonDatHang;

    ArrayList<DonDatHang> donDatHangArrayList;
    ArrayList<KhachHang> khachHangArrayList;
    DonDatHangAdapter adapterDonDatHang;
    ViewKhachHangAdapter adapterKhachHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlhoadon);

        lvDonDatHang = findViewById(R.id.lvdonhang);
        donDatHangArrayList = new ArrayList<>();
        khachHangArrayList = new ArrayList<>();
        adapterDonDatHang = new DonDatHangAdapter(this, R.layout.dong_hoa_don, donDatHangArrayList);
        adapterKhachHang = new ViewKhachHangAdapter(this, R.layout.dong_view_khach_hang, khachHangArrayList);
        lvDonDatHang.setAdapter(adapterDonDatHang);

        database = new DatabaseHelper(this, "QLDHDB.sqlite", null, 1);

//        database.QueryData("CREATE TABLE IF NOT EXISTS KHACHHANG (MAKH INTEGER NOT NULL,TENKH TEXT,DIACHI TEXT,DIENTHOAI TEXT,PRIMARY KEY(MAKH AUTOINCREMENT))");

       // database.QueryData("INSERT INTO DONDATHANG VALUES(null,'1','ahihi')");
        getDataDonHang();

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
            String maKH = dataDonHang.getString(1);
            String ngay = dataDonHang.getString(2);
            //   String  = dataDonHang.getString(3);
            donDatHangArrayList.add(new DonDatHang(maDH, maKH, ngay));
        }
        adapterDonDatHang.notifyDataSetChanged();
    }

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
                }
                else {
                    KhachHang kh = khachHangArrayList.get(spnKhachHang.getSelectedItemPosition());
                    String query=String.format("INSERT INTO DONDATHANG VALUES(null,'%s','%s')",kh.getId(),ngay);
                    database.QueryData(query);
                    Toast.makeText(QLDonDatHang.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDataDonHang();
                }
            }
        });
        dialog.show();
    }

//    public void DialogSua(int id, String ten,String diachi,String SDT) {
//        Dialog dialog = new Dialog(this);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_sua_khach_hang);
//
//        EditText etTenKhachHang = dialog.findViewById(R.id.etSuaTenKhachHang);
//        EditText etSDTKhachHang = dialog.findViewById(R.id.etSuaSDTKhachHang);
//        EditText etDiaChiKhachHang = dialog.findViewById(R.id.etSuaDiachiKhachHang);
//        Button btnSua = dialog.findViewById(R.id.btnSua);
//        Button btnHuy = dialog.findViewById(R.id.btnHuys);
//        etTenKhachHang.setText(ten);
//        etSDTKhachHang.setText(SDT);
//        etDiaChiKhachHang.setText(diachi);
//
//        btnHuy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        btnSua.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String tenMoi = etTenKhachHang.getText().toString();
//                String SDTMoi = etSDTKhachHang.getText().toString();
//                String diaChiMoi=etDiaChiKhachHang.getText().toString();
//                if (tenMoi.equals("")) {
//                    Toast.makeText(QLDonDatHang.this, "Vui lòng nhập tên!", Toast.LENGTH_SHORT).show();
//                }
//                else if (SDTMoi.equals("")){
//                    Toast.makeText(QLDonDatHang.this, "Vui lòng nhập SDT!", Toast.LENGTH_SHORT).show();
//                }else if (diaChiMoi.equals("")){
//                    Toast.makeText(QLDonDatHang.this, "Vui lòng nhập địa chỉ!", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    String query=String.format("UPDATE KHACHHANG SET TENKH='%s',DIACHI='%s',DIENTHOAI='%s' WHERE MAKH=%d",tenMoi,diaChiMoi,SDTMoi,id);
//                    database.QueryData(query);
//                    Toast.makeText(QLDonDatHang.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                    getDataDonHang();
//                }
//            }
//        });
//        dialog.show();
//    }

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