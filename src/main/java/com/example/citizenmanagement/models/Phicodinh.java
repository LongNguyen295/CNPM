package com.example.citizenmanagement.models;

public class Phicodinh {
    private  int loaicanho;
    private int giatienloaicanho;
    private int feeqlchungcu;
    private int feexedap;
    private int feexeoto;

    public Phicodinh(int loaicanho, int giatienloaicanho, int feeqlchungcu, int feexedap, int feexeoto) {
        this.loaicanho = loaicanho;
        this.giatienloaicanho = giatienloaicanho;
        this.feeqlchungcu = feeqlchungcu;
        this.feexedap = feexedap;
        this.feexeoto = feexeoto;
    }

    public Phicodinh() {
    }

    public int getLoaicanho() {
        return loaicanho;
    }

    public void setLoaicanho(int loaicanho) {
        this.loaicanho = loaicanho;
    }

    public int getGiatienloaicanho() {
        return giatienloaicanho;
    }

    public void setGiatienloaicanho(int giatienloaicanho) {
        this.giatienloaicanho = giatienloaicanho;
    }

    public int getFeeqlchungcu() {
        return feeqlchungcu;
    }

    public void setFeeqlchungcu(int feeqlchungcu) {
        this.feeqlchungcu = feeqlchungcu;
    }

    public int getFeexedap() {
        return feexedap;
    }

    public void setFeexedap(int feexedap) {
        this.feexedap = feexedap;
    }

    public int getFeexeoto() {
        return feexeoto;
    }

    public void setFeexeoto(int feexeoto) {
        this.feexeoto = feexeoto;
    }
}
