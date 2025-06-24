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

    
    public User authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = Koneksi.configDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, username);
            pst.setString(2, password);

            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    User user = new User();
                    user.setId(res.getInt("id"));
                    user.setNamaLengkap(res.getString("nama_lengkap"));
                    user.setEmail(res.getString("email"));
                    user.setUsername(res.getString("username"));
                    user.setRole(res.getString("role"));
                    return user;
                }
            }
        } catch (Exception e) {
            System.err.println("Autentikasi gagal: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null; 
    }
}