/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;

public class LaporanData {
    
    private List<Pemesanan> detailPemesanan;
    private long totalPendapatan;

    public LaporanData(List<Pemesanan> detailPemesanan, long totalPendapatan) {
        this.detailPemesanan = detailPemesanan;
        this.totalPendapatan = totalPendapatan;
    }

    public List<Pemesanan> getDetailPemesanan() {
        return detailPemesanan;
    }

    public void setDetailPemesanan(List<Pemesanan> detailPemesanan) {
        this.detailPemesanan = detailPemesanan;
    }

    public long getTotalPendapatan() {
        return totalPendapatan;
    }

    public void setTotalPendapatan(long totalPendapatan) {
        this.totalPendapatan = totalPendapatan;
    }
}

