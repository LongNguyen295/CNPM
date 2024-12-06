package com.example.citizenmanagement.models;

public class FeeHoaDon {
    String chuHo;
    String canHo;
    int maDotThu;
    String dotThu;
    int tienNha;
    int tienDichVu;
    int tienDien;
    int tienNuoc;
    int tienInternet;
    int tienXeMay;
    int tienOto;
    int dienTichChungCu;
    int soLuongXeMay;
    int soLuongOto;
    int tongSoNuoc;
    int tongSoDien;
    String ngayDong;
    String tongSoTien;
    int tienUngHo;
    int tongLoaiPhi;

    public FeeHoaDon() {
    }

    public FeeHoaDon(int tienNha, int tienDichVu, int tienDien, int tienNuoc, int tienInternet, int tienXeMay, int tienOto, String tongSoTien) {
        this.tienNha = tienNha;
        this.tienDichVu = tienDichVu;
        this.tienDien = tienDien;
        this.tienNuoc = tienNuoc;
        this.tienInternet = tienInternet;
        this.tienXeMay = tienXeMay;
        this.tienOto = tienOto;
        this.tongSoTien = tongSoTien;
    }

    public FeeHoaDon(String chuHo, String canHo, int maDotThu, String dotThu, int tienNha, int tienDichVu, int tienDien, int tienNuoc, int tienInternet, int tienXeMay, int tienOto, int dienTichChungCu, int soLuongXeMay, int soLuongOto, int tongSoDien, int tongSoNuoc, String ngayDong, String tongSoTien) {
        this.chuHo = chuHo;
        this.canHo = canHo;
        this.maDotThu = maDotThu;
        this.dotThu = dotThu;
        this.tienNha = tienNha;
        this.tienDichVu = tienDichVu;
        this.tienDien = tienDien;
        this.tienNuoc = tienNuoc;
        this.tienInternet = tienInternet;
        this.tienXeMay = tienXeMay;
        this.tienOto = tienOto;
        this.dienTichChungCu = dienTichChungCu;
        this.soLuongXeMay = soLuongXeMay;
        this.soLuongOto = soLuongOto;
        this.tongSoNuoc = tongSoNuoc;
        this.tongSoDien = tongSoDien;
        this.ngayDong = ngayDong;
        this.tongSoTien = tongSoTien;
    }

    public FeeHoaDon(int tienUngHo, int tongLoaiPhi) {
        this.tienUngHo = tienUngHo;
        this.tongLoaiPhi = tongLoaiPhi;
    }

    public int getTongLoaiPhi() {
        return tongLoaiPhi;
    }

    public void setTongLoaiPhi(int tongLoaiPhi) {
        this.tongLoaiPhi = tongLoaiPhi;
    }

    public FeeHoaDon(String chuHo, String canHo, int maDotThu, String dotThu, int tienNha, int tienDichVu, int tienDien, int tienNuoc, int tienInternet, int tienXeMay, int tienOto, int dienTichChungCu, int soLuongXeMay, int soLuongOto, int tongSoNuoc, int tongSoDien, String ngayDong, String tongSoTien, int tienUngHo) {
        this.chuHo = chuHo;
        this.canHo = canHo;
        this.maDotThu = maDotThu;
        this.dotThu = dotThu;
        this.tienNha = tienNha;
        this.tienDichVu = tienDichVu;
        this.tienDien = tienDien;
        this.tienNuoc = tienNuoc;
        this.tienInternet = tienInternet;
        this.tienXeMay = tienXeMay;
        this.tienOto = tienOto;
        this.dienTichChungCu = dienTichChungCu;
        this.soLuongXeMay = soLuongXeMay;
        this.soLuongOto = soLuongOto;
        this.tongSoNuoc = tongSoNuoc;
        this.tongSoDien = tongSoDien;
        this.ngayDong = ngayDong;
        this.tongSoTien = tongSoTien;
        this.tienUngHo = tienUngHo;
    }

    public int getTienUngHo() {
        return tienUngHo;
    }

    public void setTienUngHo(int tienUngHo) {
        this.tienUngHo = tienUngHo;
    }

    public String getChuHo() {
        return chuHo;
    }

    public void setChuHo(String chuHo) {
        this.chuHo = chuHo;
    }

    public String getCanHo() {
        return canHo;
    }

    public void setCanHo(String canHo) {
        this.canHo = canHo;
    }

    public int getMaDotThu() {
        return maDotThu;
    }

    public void setMaDotThu(int maDotThu) {
        this.maDotThu = maDotThu;
    }

    public String getDotThu() {
        return dotThu;
    }

    public void setDotThu(String dotThu) {
        this.dotThu = dotThu;
    }

    public int getTienNha() {
        return tienNha;
    }

    public void setTienNha(int tienNha) {
        this.tienNha = tienNha;
    }

    public int getTienDichVu() {
        return tienDichVu;
    }

    public void setTienDichVu(int tienDichVu) {
        this.tienDichVu = tienDichVu;
    }

    public int getTienDien() {
        return tienDien;
    }

    public void setTienDien(int tienDien) {
        this.tienDien = tienDien;
    }

    public int getTienNuoc() {
        return tienNuoc;
    }

    public void setTienNuoc(int tienNuoc) {
        this.tienNuoc = tienNuoc;
    }

    public int getTienInternet() {
        return tienInternet;
    }

    public void setTienInternet(int tienInternet) {
        this.tienInternet = tienInternet;
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

    public int getDienTichChungCu() {
        return dienTichChungCu;
    }

    public void setDienTichChungCu(int dienTichChungCu) {
        this.dienTichChungCu = dienTichChungCu;
    }

    public int getSoLuongXeMay() {
        return soLuongXeMay;
    }

    public void setSoLuongXeMay(int soLuongXeMay) {
        this.soLuongXeMay = soLuongXeMay;
    }

    public int getSoLuongOto() {
        return soLuongOto;
    }

    public void setSoLuongOto(int soLuongOto) {
        this.soLuongOto = soLuongOto;
    }

    public int getTongSoNuoc() {
        return tongSoNuoc;
    }

    public void setTongSoNuoc(int tongSoNuoc) {
        this.tongSoNuoc = tongSoNuoc;
    }

    public int getTongSoDien() {
        return tongSoDien;
    }

    public void setTongSoDien(int tongSoDien) {
        this.tongSoDien = tongSoDien;
    }

    public String getNgayDong() {
        return ngayDong;
    }

    public void setNgayDong(String ngayDong) {
        this.ngayDong = ngayDong;
    }

    public String getTongSoTien() {
        return tongSoTien;
    }

    public void setTongSoTien(String tongSoTien) {
        this.tongSoTien = tongSoTien;
    }
}
