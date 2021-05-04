package com.an.qldhsp;

public class ThongTinDDH {
    private int MaDH;
    private int MaSP;
    private int soluongdat;

    public ThongTinDDH() {

    }

    public ThongTinDDH(int maDH, int maSP, int soluongdat) {
        MaDH = maDH;
        MaSP = maSP;
        this.soluongdat = soluongdat;
    }

    public int getMaDH() {
        return MaDH;
    }

    public void setMaDH(int maDH) {
        MaDH = maDH;
    }

    public int getMaSP() {
        return MaSP;
    }

    public void setMaSP(int maSP) {
        MaSP = maSP;
    }

    public int getSoluongdat() {
        return soluongdat;
    }

    public void setSoluongdat(int soluongdat) {
        this.soluongdat = soluongdat;
    }
}
