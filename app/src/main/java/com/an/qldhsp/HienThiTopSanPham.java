package com.an.qldhsp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HienThiTopSanPham extends HienThiSanPham {
    ListView listViewSP;
    ArrayList<SanPham> sanPhamArrayList = new ArrayList<>();;

    public ArrayList<SanPham> getSanPhamArrayList() {
        return sanPhamArrayList;
    }

    public void setSanPhamArrayList(ArrayList<SanPham> sanPhamArrayList) {
        this.sanPhamArrayList = sanPhamArrayList;
    }

    public ArrayList<Integer> getSoLuongList() {
        return soLuongList;
    }

    public void setSoLuongList(ArrayList<Integer> soLuongList) {
        this.soLuongList = soLuongList;
    }

    ArrayList<Integer> soLuongList = new ArrayList<>();;
    TopSanPhamAdapter adapter;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hienthisanpham);

        listViewSP = findViewById(R.id.listview_sanpham);

        hienthitopsanpham(true);

    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menuhienthisp_them).setVisible(false);
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.menu_thongke,menu);
        return super.onCreatePanelMenu(featureId, menu);
    }
    @Override
    public boolean onOptionsItemSelected( MenuItem item) {

        LinearLayout layoyt_chart=findViewById(R.id.pieChart);
        switch (item.getItemId()){
            case R.id.menu_option0:
                Intent intentTK = new Intent(this,BarChart_1.class);
                Bundle bundle = new Bundle();
                ArrayList<String> tenSanPhamList = new ArrayList<>();
                for(int i=0;i<sanPhamArrayList.size();i++){
                    tenSanPhamList.add(sanPhamArrayList.get(i).getTenSP());
                }

                bundle.putStringArrayList("tenSp",tenSanPhamList);
                bundle.putIntegerArrayList("soluong",soLuongList);
                intentTK.putExtras(bundle);
                 startActivity(intentTK);
                break;
            case R.id.menu_option1:
                hienthitopsanpham(true);
                TextView text1 =  (TextView) findViewById(R.id.tv_sanpham);
                text1.setText("Top 3 của tháng");
                break;
            case R.id.menu_option2:
                hienthitopsanpham(false);
                TextView text2 =  (TextView) findViewById(R.id.tv_sanpham);
                text2.setText("Top 5 của cửa hàng");
                break;
            case R.id.menuthemsp_thoat:
                finish();

                break;
        }

        return super.onOptionsItemSelected(item);
    }
    public void hienthitopsanpham(boolean all){
        databaseHelper = new DatabaseHelper(HienThiTopSanPham.this,"QLDHDB.sqlite",null,1);
        //databaseHelper.QueryData("DELETE FROM SANPHAM WHERE MASP = '3'");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int thang = calendar.get(Calendar.MONTH) +1;
        int nam = calendar.get(Calendar.YEAR);
        System.out.println("thang"+ thang+"năm" + nam);

        String sql = "SELECT SANPHAM.MASP ,SANPHAM.TENSP ,SANPHAM.XUATXU ,SANPHAM.DONGIA ,SANPHAM.HINHANH ,SUM(SOLUONGDAT) AS TONG\n" +
                "FROM THONGTINDDH JOIN SANPHAM ON SANPHAM.MASP = THONGTINDDH.MASP \n" +
                "JOIN DONDATHANG ON DONDATHANG.MADH = THONGTINDDH.MADH\n" +
                "WHERE DONDATHANG.NGAYDH like '%"+thang+"/"+nam+"%' \n"+
                "GROUP BY SANPHAM.MASP\n" +
                "ORDER BY TONG DESC\n" +
                "LIMIT 3";

        String sql2 = "SELECT SANPHAM.MASP ,SANPHAM.TENSP ,SANPHAM.XUATXU ,SANPHAM.DONGIA ,SANPHAM.HINHANH ,SUM(SOLUONGDAT) AS TONG\n" +
                "FROM THONGTINDDH, SANPHAM WHERE SANPHAM.MASP = THONGTINDDH.MASP \n" +
                "GROUP BY SANPHAM.MASP\n" +
                "ORDER BY TONG DESC\n" +
                "LIMIT 5" ;
        Cursor cursor;
        if(all)
        cursor = databaseHelper.GetData(sql);
        else
            cursor = databaseHelper.GetData(sql2);
        sanPhamArrayList.clear();
        soLuongList.clear();
        while(cursor.moveToNext()){
            int MASP = cursor.getInt(0);
            String tensp = cursor.getString(1);
            String xuatxu = cursor.getString(2);
            int donGia = cursor.getInt(3);
            byte[] hinhanh = (byte[]) cursor.getBlob(4);
            int soLuong = cursor.getInt(5);
            Log.e("SELECT * FROM :",MASP + "  "+ tensp + "  "+xuatxu+"  "+donGia+"  "+hinhanh+"  "+soLuong);
//            Log.e("SELECT * FROM :",MASP + "  "+ tensp + "  "+xuatxu+"  "+dongia+"  "+hinhanh);
            sanPhamArrayList.add(new SanPham(MASP,tensp,xuatxu,donGia,hinhanh));
            soLuongList.add(soLuong);
        }



        adapter = new TopSanPhamAdapter(this,R.layout.tung_sanphamtop,sanPhamArrayList,soLuongList);
        listViewSP.setAdapter(adapter);
    }

}
