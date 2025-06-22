
package controller;


import db.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Kamar;
import model.Pemesanan;
import model.TipeKamar;
import model.User;

public class ReservasiController {

    // Method untuk mengambil semua data reservasi
    public List<Pemesanan> getAllReservasi() {
        List<Pemesanan> listPemesanan = new ArrayList<>();
        String sql = "SELECT p.id AS id_pemesanan, p.tanggal_checkin, p.tanggal_checkout, p.status_pemesanan, " +
                     "u.nama_lengkap, " +
                     "k.id AS id_kamar, k.nomor_kamar, " +
                     "tk.nama_tipe " +
                     "FROM pemesanan p " +
                     "JOIN users u ON p.id_user = u.id " +
                     "JOIN kamar k ON p.id_kamar = k.id " +
                     "JOIN tipe_kamar tk ON k.id_tipe_kamar = tk.id " +
                     "ORDER BY p.id DESC";

        try (Connection conn = Koneksi.configDB();
             Statement stm = conn.createStatement();
             ResultSet res = stm.executeQuery(sql)) {

            while (res.next()) {
                User user = new User();
                user.setNamaLengkap(res.getString("nama_lengkap"));
                
                TipeKamar tipe = new TipeKamar();
                tipe.setNamaTipe(res.getString("nama_tipe"));
                
                Kamar kamar = new Kamar();
                kamar.setId(res.getInt("id_kamar"));
                kamar.setNomorKamar(res.getString("nomor_kamar"));
                kamar.setTipeKamar(tipe);
                
                Pemesanan p = new Pemesanan();
                p.setId(res.getInt("id_pemesanan"));
                p.setTanggalCheckin(res.getDate("tanggal_checkin"));
                p.setTanggalCheckout(res.getDate("tanggal_checkout"));
                p.setStatusPemesanan(res.getString("status_pemesanan"));
                p.setUser(user);
                p.setKamar(kamar);
                
                listPemesanan.add(p);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data reservasi: " + e.getMessage());
        }
        return listPemesanan;
    }

    // Method untuk update status reservasi
    public boolean updateStatus(int idPemesanan, String status) {
        String sql = "UPDATE pemesanan SET status_pemesanan = ? WHERE id = ?";
        try (Connection conn = Koneksi.configDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, status);
            pst.setInt(2, idPemesanan);
            pst.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal update status: " + e.getMessage());
            return false;
        }
    }

    // Method untuk menghapus reservasi dan mengupdate status kamar
    public boolean deleteReservasi(int idPemesanan) {
        Connection conn = null;
        try {
            conn = Koneksi.configDB();
            conn.setAutoCommit(false); // Memulai transaksi

            // 1. Ambil dulu id_kamar dari pemesanan yang akan dihapus
            int idKamar = 0;
            String sqlGetKamar = "SELECT id_kamar FROM pemesanan WHERE id = ?";
            try (PreparedStatement pstGet = conn.prepareStatement(sqlGetKamar)) {
                pstGet.setInt(1, idPemesanan);
                try (ResultSet rs = pstGet.executeQuery()) {
                    if (rs.next()) {
                        idKamar = rs.getInt("id_kamar");
                    }
                }
            }
            if (idKamar == 0) {
                throw new Exception("Kamar untuk reservasi ini tidak ditemukan.");
            }

            // 2. Hapus data pemesanan
            String sqlDelete = "DELETE FROM pemesanan WHERE id = ?";
            try (PreparedStatement pstDelete = conn.prepareStatement(sqlDelete)) {
                pstDelete.setInt(1, idPemesanan);
                pstDelete.executeUpdate();
            }

            // 3. Update status kamar kembali menjadi 'Tersedia'
            String sqlUpdateKamar = "UPDATE kamar SET status = 'Tersedia' WHERE id = ?";
            try (PreparedStatement pstUpdate = conn.prepareStatement(sqlUpdateKamar)) {
                pstUpdate.setInt(1, idKamar);
                pstUpdate.executeUpdate();
            }

            conn.commit(); // Simpan semua perubahan jika berhasil
            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menghapus reservasi: " + e.getMessage());
            try {
                if (conn != null) conn.rollback(); // Batalkan semua perubahan jika ada error
            } catch (SQLException ex) {
                System.err.println("Rollback gagal: " + ex.getMessage());
            }
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
