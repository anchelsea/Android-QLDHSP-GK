package com.an.qldhsp;

public class DonDatHang {
    private int maDH;
    private String ngay;
    private String maKH;

    public DonDatHang(int maDH, String ngay, String maKH) {
        this.maDH = maDH;
        this.ngay = ngay;
        this.maKH = maKH;
    }

    public int getMaDH() {
        return maDH;
    }

    public void setMaDH(int maDH) {
        this.maDH = maDH;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }
}
