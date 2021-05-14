package com.an.qldhsp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    void InsertSP(String tenSP,String xuatXu, int donGia, byte[] hinh)
    {
        SQLiteDatabase db=getWritableDatabase();
        String sql="INSERT INTO SANPHAM VALUES (NUll,?,?,?,?)";
        SQLiteStatement statement=db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,tenSP);
        statement.bindString(2,xuatXu);
        statement.bindDouble(3,donGia);
        statement.bindBlob(4,hinh);
        statement.executeInsert();
    }
    void UpdateSP(int maSP,String tenSP,String xuatXu, int donGia, byte[] hinh){
        SQLiteDatabase db=getWritableDatabase();
        String sql="UPDATE SANPHAM SET TENSP = '"+tenSP+"', XUATXU = '"+xuatXu+"', HINHANH = ?,DONGIA = "+donGia+" WHERE MASP = "+maSP+"";
        SQLiteStatement statement=db.compileStatement(sql);
        statement.bindBlob(1,hinh);
        statement.executeUpdateDelete();

    }
    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }
}
