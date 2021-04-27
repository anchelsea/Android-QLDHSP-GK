package com.an.qldhsp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ThemSanPham extends AppCompatActivity {

    Button btn_ThemMoiSanPham;
    EditText txt_TenSP,txt_XuatXu,txt_DonGia;
    private static final int PICK_IMAGE = 1;
    ImageView imv_hienThiHinhAnh;
    Uri imageUri;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);

        // tao database co ten la QLDHDB trong devine
        databaseHelper = new DatabaseHelper(this,"QLDHDB.sqlite",null,1);

        setControl();
        setEvent();
    }

    private void setEvent() {
        imv_hienThiHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(gallery.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery,"Chọn Hình Ảnh"), PICK_IMAGE);
            }
        });
        btn_ThemMoiSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSP = txt_TenSP.getText().toString();
                String xuatXu = txt_XuatXu.getText().toString();
                int donGia = Integer.valueOf(txt_DonGia.getText().toString());

                databaseHelper.InsertSP(tenSP,xuatXu,donGia,ConverttoArrayByte(imv_hienThiHinhAnh));
                Toast.makeText(ThemSanPham.this, "Thêm sản phẩm thành công !!!!", Toast.LENGTH_SHORT).show();

                imv_hienThiHinhAnh.setImageResource(R.drawable.avt);
                btn_ThemMoiSanPham.setText("");
                txt_TenSP.setText("");
                txt_XuatXu.setText("");
                txt_DonGia.setText("");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap,600,600,true);

                imv_hienThiHinhAnh.setImageBitmap(bitmap1);
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
    private void setControl() {
        imv_hienThiHinhAnh = findViewById(R.id.hienThiHinhAnh);
        btn_ThemMoiSanPham = findViewById(R.id.btn_ThemMoiSanPham);
        txt_TenSP = findViewById(R.id.tenSanPham);
        txt_XuatXu = findViewById(R.id.XuatXu);
        txt_DonGia = findViewById(R.id.DonGia);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId,  Menu menu) {
        getMenuInflater().inflate(R.menu.menu_themsanpham,menu);


        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuthemsp_thoat:
//                Intent intent =new Intent(this, HienThiSanPham.class);
//                startActivity(intent);
                finish();

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}