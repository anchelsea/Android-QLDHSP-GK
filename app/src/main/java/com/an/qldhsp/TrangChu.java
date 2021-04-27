package com.an.qldhsp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class TrangChu extends AppCompatActivity {

    LinearLayout layoutSP, layoutKH;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        setControl();
        // khoi tao database
        khoiTaoSP();

        Intent intentSp = new Intent(this, HienThiSanPham.class);
        layoutSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentSp);
            }
        });

        Intent intentKH = new Intent(this,QLKhachHang.class);
        layoutKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentKH);
            }
        });
    }


    private void setControl() {
        layoutSP = findViewById(R.id.layoutsanpham);
        layoutKH = findViewById(R.id.layoutKH);
    }

    public void khoiTaoSP() {
        databaseHelper = new DatabaseHelper(this, "QLDHDB.sqlite", null, 1);
        String DONDATHANG = "CREATE TABLE IF NOT EXISTS DONDATHANG (MADH INTEGER NOT NULL,NGAYDH TEXT,MAKH INTEGER NOT NULL, FOREIGN KEY (MAKH) REFERENCES KHACHANG(MAKH),PRIMARY KEY (MADH AUTOINCREMENT))";
        String SANPHAM = "CREATE TABLE IF NOT EXISTS SANPHAM ( MASP INTEGER NOT NULL,TENSP TEXT, XUATXU TEXT,DONGIA INTEGER,HINHANH BLOB, PRIMARY KEY(MASP AUTOINCREMENT))";
        String KHACHHANG = "CREATE TABLE IF NOT EXISTS KHACHHANG (MAKH INTEGER NOT NULL,TENKH TEXT,DIACHI TEXT,DIENTHOAI TEXT,PRIMARY KEY(MAKH AUTOINCREMENT))";
        String THONGTINDDH = "CREATE TABLE IF NOT EXISTS THONGTINDDH (MADH INTEGER NOT NULL,MASP INTEGER NOT NULL,SOLUONGDAT INTEGER,FOREIGN KEY(MADH) REFERENCES DONDATHANG(MADH),FOREIGN KEY(MASP) REFERENCES SANPHAM(MASP))";
        databaseHelper.QueryData(SANPHAM);
        databaseHelper.QueryData(KHACHHANG);
        databaseHelper.QueryData(DONDATHANG);
        databaseHelper.QueryData(THONGTINDDH);
    }
}