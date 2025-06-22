/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import db.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.User;

public class UserController {

    // Method untuk mengambil semua data user dari database
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT id, nama_lengkap, email, username, role FROM users ORDER BY id ASC";
        
        try (Connection conn = Koneksi.configDB();
             Statement stm = conn.createStatement();
             ResultSet res = stm.executeQuery(sql)) {
            
            while (res.next()) {
                User user = new User();
                user.setId(res.getInt("id"));
                user.setNamaLengkap(res.getString("nama_lengkap"));
                user.setEmail(res.getString("email"));
                user.setUsername(res.getString("username"));
                user.setRole(res.getString("role"));
                userList.add(user);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data user: " + e.getMessage());
        }
        return userList;
    }

    // Method untuk menyimpan user baru
    public boolean saveUser(User user) {
        String sql = "INSERT INTO users (nama_lengkap, email, username, password, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Koneksi.configDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, user.getNamaLengkap());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getUsername());
            pst.setString(4, user.getPassword());
            pst.setString(5, user.getRole());
            pst.execute();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data user: " + e.getMessage());
            return false;
        }
    }

    // Method untuk mengedit data user
    public boolean updateUser(User user, boolean isPasswordChanged) {
        String sql;
        if (isPasswordChanged) {
            sql = "UPDATE users SET nama_lengkap = ?, email = ?, username = ?, password = ?, role = ? WHERE id = ?";
        } else {
            sql = "UPDATE users SET nama_lengkap = ?, email = ?, username = ?, role = ? WHERE id = ?";
        }
        
        try (Connection conn = Koneksi.configDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, user.getNamaLengkap());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getUsername());
            
            if (isPasswordChanged) {
                pst.setString(4, user.getPassword());
                pst.setString(5, user.getRole());
                pst.setInt(6, user.getId());
            } else {
                pst.setString(4, user.getRole());
                pst.setInt(5, user.getId());
            }
            pst.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal mengedit data user: " + e.getMessage());
            return false;
        }
    }

    // Method untuk menghapus user
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = Koneksi.configDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menghapus data user: " + e.getMessage());
            return false;
        }
    }
}