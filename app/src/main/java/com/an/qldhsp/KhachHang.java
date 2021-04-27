package com.an.qldhsp;

public class KhachHang {
    private int id;
    private String ten;
    private String diachi;
    private String phone;

    public KhachHang(int id, String ten, String diachi, String phone) {
        this.id = id;
        this.ten = ten;
        this.diachi = diachi;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
