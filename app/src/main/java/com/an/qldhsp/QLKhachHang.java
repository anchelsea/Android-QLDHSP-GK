package com.an.qldhsp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class QLKhachHang extends AppCompatActivity {
    DatabaseHelper database;
    ListView lvKhachHang;
    ArrayList<KhachHang> khachHangArrayList;
    KhachHangAdapter adapterKhachHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlkhachhang);
        lvKhachHang = findViewById(R.id.lvkhachhang);
        khachHangArrayList = new ArrayList<>();
        adapterKhachHang = new KhachHangAdapter(this, R.layout.dong_khach_hang, khachHangArrayList);
        lvKhachHang.setAdapter(adapterKhachHang);

        database = new DatabaseHelper(this, "QLDHDB.sqlite", null, 1);

//        database.QueryData("CREATE TABLE IF NOT EXISTS KHACHHANG (MAKH INTEGER NOT NULL,TENKH TEXT,DIACHI TEXT,DIENTHOAI TEXT,PRIMARY KEY(MAKH AUTOINCREMENT))");

//        database.QueryData("INSERT INTO KHACHHANG VALUES(null,'Chiến nè','ahihi','0123456789')");
        getDataKhachHang();


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_khachhang, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_khachhang) {
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    private void DialogThem() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_khach_hang);

        EditText etTenAdd = dialog.findViewById(R.id.etThemTenKhachHang);
        EditText etSDTAdd = dialog.findViewById(R.id.etThemSDTKhachHang);
        EditText etDiachiAdd = dialog.findViewById(R.id.etThemDiachiKhachHang);
        Button btnThem = dialog.findViewById(R.id.btnThem);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);

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
                String tenKhachHang = etTenAdd.getText().toString();
                String SDTKhachHang = etSDTAdd.getText().toString();
                String DiachiKhachHang = etDiachiAdd.getText().toString();
                if (tenKhachHang.equals("")) {
                    Toast.makeText(com.an.qldhsp.QLKhachHang.this, "Vui lòng nhập tên!", Toast.LENGTH_SHORT).show();
                }else if (SDTKhachHang.equals("")){
                    Toast.makeText(com.an.qldhsp.QLKhachHang.this, "Vui lòng nhập SDT!", Toast.LENGTH_SHORT).show();

                }else if (DiachiKhachHang.equals("")){
                    Toast.makeText(com.an.qldhsp.QLKhachHang.this, "Vui lòng nhập địa chỉ!", Toast.LENGTH_SHORT).show();

                }
                else {
                    String query=String.format("INSERT INTO KHACHHANG VALUES(null,'%s','%s','%s')",tenKhachHang,DiachiKhachHang,SDTKhachHang);
                    database.QueryData(query);
                    Toast.makeText(com.an.qldhsp.QLKhachHang.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDataKhachHang();
                }
            }
        });
        dialog.show();
    }

    public void DialogSua(int id, String ten,String diachi,String SDT) {
        Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua_khach_hang);

        EditText etTenKhachHang = dialog.findViewById(R.id.etSuaTenKhachHang);
        EditText etSDTKhachHang = dialog.findViewById(R.id.etSuaSDTKhachHang);
        EditText etDiaChiKhachHang = dialog.findViewById(R.id.etSuaDiachiKhachHang);
        Button btnSua = dialog.findViewById(R.id.btnSua);
        Button btnHuy = dialog.findViewById(R.id.btnHuys);
        etTenKhachHang.setText(ten);
        etSDTKhachHang.setText(SDT);
        etDiaChiKhachHang.setText(diachi);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi = etTenKhachHang.getText().toString();
                String SDTMoi = etSDTKhachHang.getText().toString();
                String diaChiMoi=etDiaChiKhachHang.getText().toString();
                if (tenMoi.equals("")) {
                    Toast.makeText(com.an.qldhsp.QLKhachHang.this, "Vui lòng nhập tên!", Toast.LENGTH_SHORT).show();
                }
                else if (SDTMoi.equals("")){
                    Toast.makeText(com.an.qldhsp.QLKhachHang.this, "Vui lòng nhập SDT!", Toast.LENGTH_SHORT).show();
                }else if (diaChiMoi.equals("")){
                    Toast.makeText(com.an.qldhsp.QLKhachHang.this, "Vui lòng nhập địa chỉ!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String query=String.format("UPDATE KHACHHANG SET TENKH='%s',DIACHI='%s',DIENTHOAI='%s' WHERE MAKH=%d",tenMoi,diaChiMoi,SDTMoi,id);
                    database.QueryData(query);
                    Toast.makeText(com.an.qldhsp.QLKhachHang.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDataKhachHang();
                }
            }
        });
        dialog.show();
    }

    public void DialogXoa(int id, String ten) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Xóa khách hàng: '" + ten + "' ?");
        dialogXoa.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM KHACHHANG WHERE MAKH=" + id);
                Toast.makeText(com.an.qldhsp.QLKhachHang.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                getDataKhachHang();
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