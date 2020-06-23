package com.example.vetau;

import java.io.Serializable;

public class VeTau implements Serializable {
    private int ma;
    private String gaDi;
    private String gaDen;
    private double donGia;
    private int khuHoi;

    public VeTau(int ma, String gaDi, String gaDen, double donGia, int khuHoi) {
        this.ma = ma;
        this.gaDi = gaDi;
        this.gaDen = gaDen;
        this.donGia = donGia;
        this.khuHoi = khuHoi;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getGaDi() {
        return gaDi;
    }

    public void setGaDi(String gaDi) {
        this.gaDi = gaDi;
    }

    public String getGaDen() {
        return gaDen;
    }

    public void setGaDen(String gaDen) {
        this.gaDen = gaDen;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public int getKhuHoi() {
        return khuHoi;
    }

    public void setKhuHoi(int khuHoi) {
        this.khuHoi = khuHoi;
    }
}
