package com.example.citizenmanagement.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class FeeHoKhauCellDot {
    private BooleanProperty selected = new SimpleBooleanProperty();
    private int maHoKhau;
    private String tenChuHo;
    private String diaChi;
    private Long tongTien;
    private int trangThai = 0; // Đã đóng phí hay chưa, khởi tạo là chưa.
    public FeeHoKhauCellDot(boolean selected, int maHoKhau, String tenChuHo, String diaChi, Long tongTien){
        this.selected.setValue(selected);
        this.maHoKhau = maHoKhau;
        this.tenChuHo = tenChuHo;
        this.diaChi = diaChi;
        this.tongTien = tongTien;
    }
    public FeeHoKhauCellDot(int maHoKhau, String tenChuHo, String diaChi, Long tongTien){
        // Không có checkbox
        this.maHoKhau = maHoKhau;
        this.tenChuHo = tenChuHo;
        this.diaChi = diaChi;
        this.tongTien = tongTien;
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

    public Long getTongTien() {
        return tongTien;
    }

    public void setTongTien(Long tongTien) {
        this.tongTien = tongTien;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
