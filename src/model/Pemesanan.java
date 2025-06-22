/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;


public class Pemesanan {
    private int id;
    private int idUser;
    private int idKamar;
    private Date tanggalCheckin;
    private Date tanggalCheckout;
    private int totalBiaya;
    private String statusPemesanan;
    private User user;   
    private Kamar kamar;

    public Pemesanan() {
    }

    public Pemesanan(int id, int idUser, int idKamar, Date tanggalCheckin, Date tanggalCheckout, int totalBiaya, String statusPemesanan, Kamar kamar) {
        this.id = id;
        this.idUser = idUser;
        this.idKamar = idKamar;
        this.tanggalCheckin = tanggalCheckin;
        this.tanggalCheckout = tanggalCheckout;
        this.totalBiaya = totalBiaya;
        this.statusPemesanan = statusPemesanan;
        this.kamar = kamar;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdKamar() {
        return idKamar;
    }

    public void setIdKamar(int idKamar) {
        this.idKamar = idKamar;
    }

    public Date getTanggalCheckin() {
        return tanggalCheckin;
    }

    public void setTanggalCheckin(Date tanggalCheckin) {
        this.tanggalCheckin = tanggalCheckin;
    }

    public Date getTanggalCheckout() {
        return tanggalCheckout;
    }

    public void setTanggalCheckout(Date tanggalCheckout) {
        this.tanggalCheckout = tanggalCheckout;
    }

    public int getTotalBiaya() {
        return totalBiaya;
    }

    public void setTotalBiaya(int totalBiaya) {
        this.totalBiaya = totalBiaya;
    }

    public String getStatusPemesanan() {
        return statusPemesanan;
    }

    public void setStatusPemesanan(String statusPemesanan) {
        this.statusPemesanan = statusPemesanan;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Kamar getKamar() {
        return kamar;
    }

    public void setKamar(Kamar kamar) {
        this.kamar = kamar;
    }
    
}
