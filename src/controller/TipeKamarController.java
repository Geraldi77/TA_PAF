/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import db.Koneksi;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.TipeKamar;

public class TipeKamarController {

    // Method untuk mengambil semua tipe kamar
    public List<TipeKamar> getAllTipeKamar() {
        List<TipeKamar> tipeKamarList = new ArrayList<>();
        String sql = "SELECT id, nama_tipe, fasilitas, harga, gambar FROM tipe_kamar ORDER BY id ASC";

        try (Connection conn = Koneksi.configDB();
             Statement stm = conn.createStatement();
             ResultSet res = stm.executeQuery(sql)) {

            while (res.next()) {
                TipeKamar tipe = new TipeKamar();
                tipe.setId(res.getInt("id"));
                tipe.setNamaTipe(res.getString("nama_tipe"));
                tipe.setFasilitas(res.getString("fasilitas"));
                tipe.setHarga(res.getInt("harga"));
                tipe.setGambar(res.getString("gambar"));
                tipeKamarList.add(tipe);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data tipe kamar: " + e.getMessage());
        }
        return tipeKamarList;
    }
    
    // Method untuk menyimpan tipe kamar baru (termasuk file gambar)
    public boolean saveTipeKamar(TipeKamar tipe, File fileGambar) {
        String namaFileGambar = null;
        if (fileGambar != null) {
            try {
                String folderTujuan = System.getProperty("user.dir") + "/app_images/";
                File folder = new File(folderTujuan);
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                String ekstensi = fileGambar.getName().substring(fileGambar.getName().lastIndexOf("."));
                namaFileGambar = "kamar_" + System.currentTimeMillis() + ekstensi;
                Files.copy(fileGambar.toPath(), new File(folderTujuan + namaFileGambar).toPath(), StandardCopyOption.REPLACE_EXISTING);
                tipe.setGambar(namaFileGambar);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal menyimpan file gambar: " + e.getMessage());
                return false;
            }
        }

        // Simpan data ke database
        String sql = "INSERT INTO tipe_kamar (nama_tipe, fasilitas, harga, gambar) VALUES (?, ?, ?, ?)";
        try (Connection conn = Koneksi.configDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, tipe.getNamaTipe());
            pst.setString(2, tipe.getFasilitas());
            pst.setInt(3, tipe.getHarga());
            pst.setString(4, tipe.getGambar()); 
            pst.execute();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data ke DB: " + e.getMessage());
            return false;
        }
    }

    // Method untuk mengedit tipe kamar
    public boolean updateTipeKamar(TipeKamar tipe, File fileGambar) {
        String namaFileGambar = null;
        if (fileGambar != null) {
            try {
                String folderTujuan = System.getProperty("user.dir") + "/app_images/";
                File folder = new File(folderTujuan);
                if (!folder.exists()) folder.mkdirs();
                String ekstensi = fileGambar.getName().substring(fileGambar.getName().lastIndexOf("."));
                namaFileGambar = "kamar_" + System.currentTimeMillis() + ekstensi;
                Files.copy(fileGambar.toPath(), new File(folderTujuan + namaFileGambar).toPath(), StandardCopyOption.REPLACE_EXISTING);
                tipe.setGambar(namaFileGambar);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal menyimpan file gambar baru: " + e.getMessage());
                return false;
            }
        }
        
        // Update data di database
        try {
            String sql;
            if (namaFileGambar != null) {
                sql = "UPDATE tipe_kamar SET nama_tipe = ?, fasilitas = ?, harga = ?, gambar = ? WHERE id = ?";
            } else {
                sql = "UPDATE tipe_kamar SET nama_tipe = ?, fasilitas = ?, harga = ? WHERE id = ?";
            }
            
            Connection conn = (Connection) Koneksi.configDB();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, tipe.getNamaTipe());
            pst.setString(2, tipe.getFasilitas());
            pst.setInt(3, tipe.getHarga());
            
            if (namaFileGambar != null) {
                pst.setString(4, tipe.getGambar());
                pst.setInt(5, tipe.getId());
            } else {
                pst.setInt(4, tipe.getId());
            }

            pst.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal mengedit data: " + e.getMessage());
            return false;
        }
    }

    // Method untuk menghapus tipe kamar
    public boolean deleteTipeKamar(int id) {
        // Sebaiknya ada logika untuk menghapus file gambar dari folder juga
        String sql = "DELETE FROM tipe_kamar WHERE id = ?";
        try (Connection conn = Koneksi.configDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menghapus data: " + e.getMessage());
            return false;
        }
    }
}