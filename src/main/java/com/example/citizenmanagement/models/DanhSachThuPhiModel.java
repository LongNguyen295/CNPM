package com.example.citizenmanagement.models;

public class DanhSachThuPhiModel {
    private int maDotThu;
    private int maCanHo;
    private String chuHo;
    private int tienNha;
    private int tienDv;
    private int tienUngHo;
    private int tienXeMay;
    private int tienOto;
    private int tienDien;
    private int soDien;
    private int tienNuoc;
    private int soNuoc;
    private int tienInternet;
    private int trangThai;

    public DanhSachThuPhiModel() {
    }

    public DanhSachThuPhiModel(int maDotThu, int maCanHo, String chuHo, int tienNha, int tienDv, int tienXeMay, int tienOto, int tienDien, int soDien, int tienNuoc, int soNuoc, int tienInternet) {
        this.maDotThu = maDotThu;
        this.maCanHo = maCanHo;
        this.chuHo = chuHo;
        this.tienNha = tienNha;
        this.tienDv = tienDv;
        this.tienXeMay = tienXeMay;
        this.tienOto = tienOto;
        this.tienDien = tienDien;
        this.soDien = soDien;
        this.tienNuoc = tienNuoc;
        this.soNuoc = soNuoc;
        this.tienInternet = tienInternet;
    }

    public DanhSachThuPhiModel(String chuHo, int tienNha, int tienDv, int tienXeMay, int tienOto, int tienDien, int soDien, int tienNuoc, int soNuoc, int tienInternet) {
        this.chuHo = chuHo;
        this.tienNha = tienNha;
        this.tienDv = tienDv;
        this.tienXeMay = tienXeMay;
        this.tienOto = tienOto;
        this.tienDien = tienDien;
        this.soDien = soDien;
        this.tienNuoc = tienNuoc;
        this.soNuoc = soNuoc;
        this.tienInternet = tienInternet;
    }

    public int getMaDotThu() {
        return maDotThu;
    }

    public void setMaDotThu(int maDotThu) {
        this.maDotThu = maDotThu;
    }

    public int getMaCanHo() {
        return maCanHo;
    }

    public void setMaCanHo(int maCanHo) {
        this.maCanHo = maCanHo;
    }

    public String getChuHo() {
        return chuHo;
    }

    public void setChuHo(String chuHo) {
        this.chuHo = chuHo;
    }

    public int getTienNha() {
        return tienNha;
    }

    public void setTienNha(int tienNha) {
        this.tienNha = tienNha;
    }

    public int getTienDv() {
        return tienDv;
    }

    public void setTienDv(int tienDv) {
        this.tienDv = tienDv;
    }

    public int getTienUngHo() {
        return tienUngHo;
    }

    public void setTienUngHo(int tienUngHo) {
        this.tienUngHo = tienUngHo;
    }

    public int getTienXeMay() {
        return tienXeMay;
    }

    public void setTienXeMay(int tienXeMay) {
        this.tienXeMay = tienXeMay;
    }

    public int getTienOto() {
        return tienOto;
    }

    public void setTienOto(int tienOto) {
        this.tienOto = tienOto;
    }

    public int getTienDien() {
        return tienDien;
    }

    public void setTienDien(int tienDien) {
        this.tienDien = tienDien;
    }

    public int getSoDien() {
        return soDien;
    }

    public void setSoDien(int soDien) {
        this.soDien = soDien;
    }

    public int getTienNuoc() {
        return tienNuoc;
    }

    public void setTienNuoc(int tienNuoc) {
        this.tienNuoc = tienNuoc;
    }

    public int getSoNuoc() {
        return soNuoc;
    }

    public void setSoNuoc(int soNuoc) {
        this.soNuoc = soNuoc;
    }

    public int getTienInternet() {
        return tienInternet;
    }

    public void setTienInternet(int tienInternet) {
        this.tienInternet = tienInternet;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
