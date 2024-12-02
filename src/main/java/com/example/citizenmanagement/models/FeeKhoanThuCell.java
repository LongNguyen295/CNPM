package com.example.citizenmanagement.models;

import javafx.beans.property.*;

public class FeeKhoanThuCell {
    private final IntegerProperty maKhoanThu = new SimpleIntegerProperty();
    private final StringProperty tenKhoanThu = new SimpleStringProperty();
    private final StringProperty ngayTao = new SimpleStringProperty();

    public FeeKhoanThuCell() {
    }
    public FeeKhoanThuCell(int maKhoanThu, String tenKhoanThu,  String ngayTao) {
        this.maKhoanThu.setValue(maKhoanThu);
        this.tenKhoanThu.setValue(tenKhoanThu);

        this.ngayTao.setValue(ngayTao);
    }

    public int getMaKhoanThu() {
        return maKhoanThu.get();
    }

    public IntegerProperty maKhoanThuProperty() {
        return maKhoanThu;
    }

    public void setMaKhoanThu(int maKhoanThu) {
        this.maKhoanThu.set(maKhoanThu);
    }

    public String getTenKhoanThu() {
        return tenKhoanThu.get();
    }

    public StringProperty tenKhoanThuProperty() {
        return tenKhoanThu;
    }

    public void setTenKhoanThu(String tenKhoanThu) {
        this.tenKhoanThu.set(tenKhoanThu);
    }



    public String getNgayTao() {
        return ngayTao.get();
    }

    public StringProperty ngayTaoProperty() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao.set(ngayTao);
    }
}
