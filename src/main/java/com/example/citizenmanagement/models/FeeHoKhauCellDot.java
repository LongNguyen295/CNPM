package com.example.citizenmanagement.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class FeeHoKhauCellDot {
    private BooleanProperty selected = new SimpleBooleanProperty();
    private int maHoKhau;
    private String tenChuHo;
    private String diaChi;
    private int tongTien;
    private int maDotThu;
    private String ngayThu;
    private int trangThai = 0; // Đã đóng phí hay chưa, khởi tạo là chưa.
    public FeeHoKhauCellDot(boolean selected, int maHoKhau, String tenChuHo, String diaChi, int tongTien){
        this.selected.setValue(selected);
        this.maHoKhau = maHoKhau;
        this.tenChuHo = tenChuHo;
        this.diaChi = diaChi;
        this.tongTien = tongTien;
    }
    public FeeHoKhauCellDot(int maDotThu,int maHoKhau, String tenChuHo, String diaChi, int tongTien){
        // Không có checkbox
        this.maDotThu = maDotThu;
        this.maHoKhau = maHoKhau;
        this.tenChuHo = tenChuHo;
        this.diaChi = diaChi;
        this.tongTien = tongTien;
    }
    public FeeHoKhauCellDot(int maDotThu,int maHoKhau, String tenChuHo, String diaChi, int tongTien, String ngayThu){
        // Không có checkbox
        this.maDotThu = maDotThu;
        this.maHoKhau = maHoKhau;
        this.tenChuHo = tenChuHo;
        this.diaChi = diaChi;
        this.tongTien = tongTien;
        this.ngayThu = ngayThu;
    }

    public FeeHoKhauCellDot(int maHoKhau, String tenChuHo, String diaChi, int tongTien) {
        this.maHoKhau = maHoKhau;
        this.tenChuHo = tenChuHo;
        this.diaChi = diaChi;
        this.tongTien = tongTien;

    }

    public FeeHoKhauCellDot(int maHoKhau, String tenChuHo, String diaChi, int tongTien, String ngayThu) {
        this.maHoKhau = maHoKhau;
        this.tenChuHo = tenChuHo;
        this.diaChi = diaChi;
        this.tongTien = tongTien;
        this.ngayThu = ngayThu;
    }

    public String getNgayThu() {
        return ngayThu;
    }

    public void setNgayThu(String ngayThu) {
        this.ngayThu = ngayThu;
    }

    public int getMaDotThu() {
        return maDotThu;
    }

    public void setMaDotThu(int maDotThu) {
        this.maDotThu = maDotThu;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public int getMaHoKhau() {
        return maHoKhau;
    }

    public void setMaHoKhau(int maHoKhau) {
        this.maHoKhau = maHoKhau;
    }

    public String getTenChuHo() {
        return tenChuHo;
    }

    public void setTenChuHo(String tenChuHo) {
        this.tenChuHo = tenChuHo;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
