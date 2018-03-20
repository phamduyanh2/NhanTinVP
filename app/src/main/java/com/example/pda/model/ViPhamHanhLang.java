package com.example.pda.model;

import java.util.Date;
import java.util.Timer;

/**
 * Created by pda on 03/13/2018.
 */
public class ViPhamHanhLang {

    private String soDienThoai;
    private Date ngay;
    private String gio;
    private String doi;
    private String duong;
    private String loaiViPham;
    private String noiDung;

    @Override
    public String toString() {
        return "ViPhamHanhLang{" +
                "soDienThoai='" + soDienThoai + '\'' +
                ", ngay=" + ngay +
                ", gio=" + gio +
                ", doi='" + doi + '\'' +
                ", duong='" + duong + '\'' +
                ", loaiViPham='" + loaiViPham + '\'' +
                ", noiDung='" + noiDung + '\'' +
                '}';
    }

    public ViPhamHanhLang(String soDienThoai, Date ngay, String gio, String loaiViPham, String duong, String noiDung, String doi) {
        this.soDienThoai = soDienThoai;
        this.ngay = ngay;
        this.gio = gio;
        this.loaiViPham = loaiViPham;
        this.duong = duong;
        this.noiDung = noiDung;
        this.doi = doi;
    }

    public ViPhamHanhLang() {
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    public void setLoaiViPham(String loaiViPham) {
        this.loaiViPham = loaiViPham;
    }

    public void setDuong(String duong) {
        this.duong = duong;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public Date getNgay() {
        return ngay;
    }

    public String getGio() {
        return gio;
    }

    public String getLoaiViPham() {
        return loaiViPham;
    }

    public String getDuong() {
        return duong;
    }

    public String getNoiDung() {
        return noiDung;
    }
}
