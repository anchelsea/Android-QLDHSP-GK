package com.an.qldhsp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TrangChu extends AppCompatActivity {

    LinearLayout layoutSP, layoutKH,layoutDDH,layoutTK;
    TextView tvInfo;
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

        Intent intentDDH = new Intent(this,QLDonDatHang.class);
        layoutDDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentDDH);
            }
        });

        Intent intentTK = new Intent(this,HienThiTopSanPham.class);
        layoutTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentTK);
            }
        });



//        findViewById(R.id.buttonBarChart).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), BarChart_1.class));
//            }
//        });
//
//        findViewById(R.id.buttonPieChart).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),PieChart.class));
//            }
//        });

        tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog infoDialog = new Dialog(TrangChu.this);
                infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                infoDialog.setContentView(R.layout.dialog_thong_tin);
                infoDialog.show();
            }
        });
    }


    private void setControl() {
        layoutSP = findViewById(R.id.layoutsanpham);
        layoutKH = findViewById(R.id.layoutKH);
        layoutDDH = findViewById(R.id.layoutDDH);
        layoutTK = findViewById(R.id.layoutTK);
        tvInfo=findViewById(R.id.tvInfo);
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