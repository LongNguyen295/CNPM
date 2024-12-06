package com.example.citizenmanagement.models;

import javafx.beans.property.*;

public class FeeKhoanThuCell {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty maKhoanThu = new SimpleIntegerProperty();
    private final StringProperty tenKhoanThu = new SimpleStringProperty();
    private final StringProperty ngayTao = new SimpleStringProperty();

    public FeeKhoanThuCell() {
    }
    public FeeKhoanThuCell(int id, int maKhoanThu, String tenKhoanThu,  String ngayTao) {
        this.id.setValue(id);
        this.maKhoanThu.setValue(maKhoanThu);
        this.tenKhoanThu.setValue(tenKhoanThu);
        this.ngayTao.setValue(ngayTao);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getMaDotThu() {
        return maKhoanThu.get();
    }

    public IntegerProperty maDotThuProperty() {
        return maKhoanThu;
    }

    public void setMaDotThu(int maDotThu) {
        this.maKhoanThu.set(maDotThu);
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
