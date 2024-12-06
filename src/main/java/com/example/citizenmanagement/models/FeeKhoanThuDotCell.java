package com.example.citizenmanagement.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
//
public class FeeKhoanThuDotCell  {

    private final IntegerProperty maDotThu = new SimpleIntegerProperty();
    private final StringProperty tenKhoanThu = new SimpleStringProperty();
    private final StringProperty ngayTao = new SimpleStringProperty();

    public FeeKhoanThuDotCell() {
    }
    public FeeKhoanThuDotCell(int maKhoanThu, String tenKhoanThu, String ngayTao) {

        this.maDotThu.setValue(maKhoanThu);
        this.tenKhoanThu.setValue(tenKhoanThu);
        this.ngayTao.setValue(ngayTao);
    }


    public int getMaDotThu() {
        return maDotThu.get();
    }

    public IntegerProperty maDotThuProperty() {
        return maDotThu;
    }

    public void setMaDotThu(int maDotThu) {
        this.maDotThu.set(maDotThu);
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
