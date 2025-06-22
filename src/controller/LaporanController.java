
package controller;

import db.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import model.Kamar;
import model.LaporanData;
import model.Pemesanan;
import model.TipeKamar;
import model.User;

public class LaporanController {
    
    public LaporanData generateLaporan(Date tanggalMulai, Date tanggalSelesai) {
        List<Pemesanan> listPemesanan = new ArrayList<>();
        long totalPendapatan = 0;
        
        String sql = "SELECT p.id, u.nama_lengkap, tk.nama_tipe, tk.harga, p.tanggal_checkin, p.tanggal_checkout, " +
                     "DATEDIFF(p.tanggal_checkout, p.tanggal_checkin) AS jumlah_malam " +
                     "FROM pemesanan p " +
                     "JOIN users u ON p.id_user = u.id " +
                     "JOIN kamar k ON p.id_kamar = k.id " +
                     "JOIN tipe_kamar tk ON k.id_tipe_kamar = tk.id " +
                     "WHERE p.status_pemesanan IN ('Terkonfirmasi', 'Selesai') " +
                     "AND p.tanggal_checkin BETWEEN ? AND ?";
        
        try (Connection conn = Koneksi.configDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setDate(1, new java.sql.Date(tanggalMulai.getTime()));
            pst.setDate(2, new java.sql.Date(tanggalSelesai.getTime()));
            
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    long harga = res.getLong("tk.harga");
                    // Pastikan jumlah malam tidak 0 untuk menghindari pembagian dengan nol atau subtotal aneh
                    int jumlahMalam = res.getInt("jumlah_malam") == 0 ? 1 : res.getInt("jumlah_malam");
                    long subtotal = harga * jumlahMalam;
                    totalPendapatan += subtotal;
                    
                    User user = new User();
                    user.setNamaLengkap(res.getString("u.nama_lengkap"));
                    
                    TipeKamar tipe = new TipeKamar();
                    tipe.setNamaTipe(res.getString("tk.nama_tipe"));
                    tipe.setHarga((int) harga);
                    
                    Pemesanan p = new Pemesanan();
                    p.setId(res.getInt("p.id"));
                    p.setTanggalCheckin(res.getDate("p.tanggal_checkin"));
                    p.setTanggalCheckout(res.getDate("p.tanggal_checkout")); // <-- BARIS YANG HILANG, SEKARANG DITAMBAHKAN
                    p.setUser(user);
                    p.setKamar(new Kamar());
                    p.getKamar().setTipeKamar(tipe);
                    p.setTotalBiaya((int) subtotal);
                    
                    listPemesanan.add(p);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menghasilkan laporan: " + e.getMessage());
            e.printStackTrace();
        }
        
        return new LaporanData(listPemesanan, totalPendapatan);
    }
}