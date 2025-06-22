/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import db.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.User;

public class LoginController {

    /**
     * Method untuk melakukan autentikasi user.
     * @param username Username yang diinput.
     * @param password Password yang diinput.
     * @return Mengembalikan objek User jika berhasil, atau null jika gagal.
     */
    public User authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        // Menggunakan try-with-resources untuk memastikan koneksi ditutup otomatis
        try (Connection conn = Koneksi.configDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, username);
            pst.setString(2, password);

            try (ResultSet res = pst.executeQuery()) {
                // Jika data ditemukan, buat objek User dan isi datanya
                if (res.next()) {
                    User user = new User();
                    user.setId(res.getInt("id"));
                    user.setNamaLengkap(res.getString("nama_lengkap"));
                    user.setEmail(res.getString("email"));
                    user.setUsername(res.getString("username"));
                    user.setRole(res.getString("role"));
                    return user; // Berhasil, kembalikan objek User yang sudah terisi data
                }
            }
        } catch (Exception e) {
            // Sebaiknya tidak ada JOptionPane di Controller.
            // Biarkan View yang menampilkan pesan error. Cukup print error untuk debug.
            System.err.println("Autentikasi gagal: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null; // Gagal, kembalikan null
    }
}