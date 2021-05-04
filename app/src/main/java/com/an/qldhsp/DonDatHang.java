package com.an.qldhsp;

public class DonDatHang {
    private int maDH;
    private String ngay;
    private int maKH;

    public DonDatHang(int maDH, String ngay, int maKH) {
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

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }
}
