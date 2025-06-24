
package controller;


import db.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import model.Kamar;
import model.TipeKamar;

public class KamarController {

    public List<Kamar> getAllKamar() {
        List<Kamar> kamarList = new ArrayList<>();
        String sql = "SELECT k.id AS id_kamar, k.nomor_kamar, k.status, " +
                     "tk.id AS id_tipe_kamar, tk.nama_tipe " +
                     "FROM kamar k " +
                     "JOIN tipe_kamar tk ON k.id_tipe_kamar = tk.id " +
                     "ORDER BY k.nomor_kamar ASC";

        try (Connection conn = Koneksi.configDB();
             Statement stm = conn.createStatement();
             ResultSet res = stm.executeQuery(sql)) {

            while (res.next()) {
                TipeKamar tipe = new TipeKamar();
                tipe.setId(res.getInt("id_tipe_kamar"));
                tipe.setNamaTipe(res.getString("nama_tipe"));

                Kamar kamar = new Kamar();
                kamar.setId(res.getInt("id_kamar"));
                kamar.setNomorKamar(res.getString("nomor_kamar"));
                kamar.setStatus(res.getString("status"));
                kamar.setTipeKamar(tipe); // Menyimpan objek TipeKamar

                kamarList.add(kamar);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data kamar: " + e.getMessage());
        }
        return kamarList;
    }

    // Method untuk mengambil daftar tipe kamar dalam bentuk Map
    public Map<String, Integer> getTipeKamarMap() {
        Map<String, Integer> tipeKamarMap = new LinkedHashMap<>(); 
        String sql = "SELECT id, nama_tipe FROM tipe_kamar ORDER BY nama_tipe ASC";

        try (Connection conn = Koneksi.configDB();
             Statement stm = conn.createStatement();
             ResultSet res = stm.executeQuery(sql)) {

            while (res.next()) {
                tipeKamarMap.put(res.getString("nama_tipe"), res.getInt("id"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat daftar tipe kamar: " + e.getMessage());
        }
        return tipeKamarMap;
    }

    // Method untuk menyimpan kamar baru
    public boolean saveKamar(Kamar kamar) {
        String sql = "INSERT INTO kamar (nomor_kamar, id_tipe_kamar, status) VALUES (?, ?, ?)";
        try (Connection conn = Koneksi.configDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, kamar.getNomorKamar());
            pst.setInt(2, kamar.getIdTipeKamar());
            pst.setString(3, kamar.getStatus());
            pst.execute();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data kamar: " + e.getMessage());
            return false;
        }
    }

    // Method untuk mengedit data kamar berdasarkan nomor kamar
    public boolean updateKamar(Kamar kamar) {
        String sql = "UPDATE kamar SET id_tipe_kamar = ?, status = ? WHERE nomor_kamar = ?";
        try (Connection conn = Koneksi.configDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, kamar.getIdTipeKamar());
            pst.setString(2, kamar.getStatus());
            pst.setString(3, kamar.getNomorKamar());
            pst.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal mengedit data kamar: " + e.getMessage());
            return false;
        }
    }

    // Method untuk menghapus kamar berdasarkan nomor kamar
    public boolean deleteKamar(String nomorKamar) {
        String sql = "DELETE FROM kamar WHERE nomor_kamar = ?";
        try (Connection conn = Koneksi.configDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, nomorKamar);
            pst.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menghapus data kamar: " + e.getMessage());
            return false;
        }
    }
}
