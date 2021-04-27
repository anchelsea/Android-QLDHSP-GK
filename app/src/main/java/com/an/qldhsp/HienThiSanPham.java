package com.an.qldhsp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class HienThiSanPham extends AppCompatActivity {
    ListView listViewSP;
    ArrayList<SanPham> sanPhamArrayList = new ArrayList<>();;
    SanPhamAdapter adapter;
    DatabaseHelper databaseHelper;
    ImageButton imgBtn_suasp,imgBtn_xoasp;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    Dialog dialog;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hienthisanpham);

        listViewSP = findViewById(R.id.listview_sanpham);
        imgBtn_suasp = findViewById(R.id.imgBtn_suasp);
        imgBtn_xoasp = findViewById(R.id.imgBtn_xoasp);


        hienthisanpham();

    }


    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {

        getMenuInflater().inflate(R.menu.menu_hienthisanpham,menu);
        return super.onCreatePanelMenu(featureId, menu);

    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuhienthisp_them:
                Intent intent =new Intent(this, ThemSanPham.class);
                startActivity(intent);
                break;

            case R.id.menuhienthisp_thoat:
                finish();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        hienthisanpham();
    }

    public void hienthisanpham(){
        databaseHelper = new DatabaseHelper(HienThiSanPham.this,"QLDHDB.sqlite",null,1);
        //databaseHelper.QueryData("DELETE FROM SANPHAM WHERE MASP = '3'");
        String sql = "SELECT * FROM SANPHAM";
        Cursor cursor = databaseHelper.GetData(sql);
        sanPhamArrayList.clear();
        while(cursor.moveToNext()){
            int MASP = cursor.getInt(0);
            String tensp = cursor.getString(1);
            String xuatxu = cursor.getString(2);
            int dongia = cursor.getInt(3);
            byte[] hinhanh = cursor.getBlob(4);
            Log.e("SELECT * FROM :",MASP + "  "+ tensp + "  "+xuatxu+"  "+dongia+"  "+hinhanh);
            sanPhamArrayList.add(new SanPham(MASP,tensp,xuatxu,dongia,hinhanh));
        }

        adapter = new SanPhamAdapter(this,R.layout.tung_sanpham,sanPhamArrayList);

        listViewSP.setAdapter(adapter);

    }

    public void DialogXoa(int masp,String tensp){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Bạn có muốn xoá sản phẩm : " + tensp +"  ????");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseHelper.QueryData("DELETE FROM SANPHAM WHERE MASP = '"+masp+"'");
                Toast.makeText(HienThiSanPham.this, "Đã Xoá Thành Công !!!", Toast.LENGTH_SHORT).show();
                hienthisanpham();
            }
        });
        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    public void DialogSua(int maSP,String tensp,String xuatxu,int gia,byte[] hinhAnh){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_suasanpham);

        EditText suaten = dialog.findViewById(R.id.suatensanpham);
        EditText suaxuatxu = dialog.findViewById(R.id.suaxuatxu);
        EditText suagia = dialog.findViewById(R.id.suadongia);
        img = dialog.findViewById(R.id.suahinhanh);
        Button capnhat = dialog.findViewById(R.id.btn_suaSanPham);
        Button huy = dialog.findViewById(R.id.btn_huysuaSanPham);

        suaten.setText(tensp);
        suaxuatxu.setText(xuatxu);
        suagia.setText(String.valueOf(gia));
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0,hinhAnh.length);
        img.setImageBitmap(bitmap);

        // chon hinh anh
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(gallery.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery,"Chọn Hình Ảnh"), PICK_IMAGE);
            }
        });

        Log.e("eccecec ",String.valueOf(ConverttoArrayByte(img)));

        capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    databaseHelper.UpdateSP(maSP,suaten.getText().toString(),suaxuatxu.getText().toString(),Integer.valueOf(suagia.getText().toString()),ConverttoArrayByte(img));
                    Toast.makeText(dialog.getContext(), "Cập nhật sản phẩm thành công !!!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    hienthisanpham();

                }catch (Exception e)
                {
                    Toast.makeText(dialog.getContext(), "Lỗi cập nhật sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }
        });

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap,600,600,true);
                img = dialog.findViewById(R.id.suahinhanh);
                img.setImageBitmap(bitmap1);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public byte[] ConverttoArrayByte(ImageView img){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap =bitmapDrawable.getBitmap();

        ByteArrayOutputStream stream =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        return stream.toByteArray();
    }
}
