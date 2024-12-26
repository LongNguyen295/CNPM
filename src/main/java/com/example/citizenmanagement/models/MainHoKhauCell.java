package com.example.citizenmanagement.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MainHoKhauCell {
    private final StringProperty id;
    private final StringProperty owner;
    private final StringProperty address;
    private final StringProperty date_tao;
    private final StringProperty ghi_chu;
    private final StringProperty xe_may;
    private final StringProperty o_to;
    private final StringProperty trang_thai; // Thêm thuộc tính trạng thái

    // Constructor
    public MainHoKhauCell(String id, String owner, String address, String ngaytao, String ghichu, String xemay, String oto) {
        this.id = new SimpleStringProperty(this, "id", id);
        this.owner = new SimpleStringProperty(this, "owner", owner);
        this.address = new SimpleStringProperty(this, "address", address);
        this.date_tao = new SimpleStringProperty(this, "date_tao", ngaytao);
        this.ghi_chu = new SimpleStringProperty(this, "ghi_chu", ghichu);
        this.xe_may = new SimpleStringProperty(this, "xe_may", xemay);
        this.o_to = new SimpleStringProperty(this, "o_to", oto);
        this.trang_thai = new SimpleStringProperty(this, "trang_thai", "1"); // Giá trị mặc định là 1
    }

    // Getter cho ID
    public StringProperty getId() {
        return this.id;
    }

    // Getter cho Owner
    public StringProperty getOwner() {
        return this.owner;
    }

    // Getter cho Address
    public StringProperty getAddress() {
        return this.address;
    }

    // Getter cho Date_tao
    public StringProperty getDate_tao() {
        return this.date_tao;
    }

    // Getter cho Ghi_chu
    public StringProperty getGhi_chu() {
        return this.ghi_chu;
    }

    // Getter cho Xe_may
    public StringProperty getXe_may() {
        return this.xe_may;
    }

    // Getter cho O_to
    public StringProperty getO_to() {
        return this.o_to;
    }

    // Getter cho Trang_thai
    public StringProperty getTrang_thai() {
        return this.trang_thai;
    }

    // Setter cho ID
    public void setId(String id) {
        this.id.set(id);
    }

    // Setter cho Owner
    public void setOwner(String owner) {
        this.owner.set(owner);
    }

    // Setter cho Address
    public void setAddress(String address) {
        this.address.set(address);
    }

    // Setter cho Date_tao
    public void setDate_tao(String date_tao) {
        this.date_tao.set(date_tao);
    }

    // Setter cho Ghi_chu
    public void setGhi_chu(String ghi_chu) {
        this.ghi_chu.set(ghi_chu);
    }

    // Setter cho Xe_may
    public void setXe_may(String xe_may) {
        this.xe_may.set(xe_may);
    }

    // Setter cho O_to
    public void setO_to(String o_to) {
        this.o_to.set(o_to);
    }

    // Setter cho Trang_thai
    public void setTrang_thai(String trang_thai) {
        this.trang_thai.set(trang_thai);
    }
}
