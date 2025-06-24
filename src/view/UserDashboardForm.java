/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import db.Koneksi;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import javax.swing.Box;

public class UserDashboardForm extends javax.swing.JFrame {
    
    private model.User currentUser;

    public UserDashboardForm(model.User user) {
        initComponents(); 
        this.currentUser = user;
        this.setTitle("Selamat Datang, " + this.currentUser.getNamaLengkap());
        setLocationRelativeTo(null); // Form di tengah layar
         Date hariIni = new Date();
    dateCheckin.setSelectableDateRange(hariIni, null);
    dateCheckout.setSelectableDateRange(hariIni, null);
        loadTipeKamarFilter();
        btnCari.doClick(); 
    }
    
    private void setImageToLabel(String namaFileGambar, javax.swing.JLabel label) {
        if (namaFileGambar == null || namaFileGambar.isEmpty()) {
            label.setText("Gambar Tidak Tersedia");
            label.setIcon(null);
            return;
        }
        try {
            String pathGambar = System.getProperty("user.dir") + "/app_images/" + namaFileGambar;
            java.io.File fileGambar = new java.io.File(pathGambar);

            if (fileGambar.exists()) {
                ImageIcon icon = new ImageIcon(fileGambar.getAbsolutePath());
                int width = 300;
                int height = 500;
                Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                
                label.setIcon(new ImageIcon(img));
                label.setText("");
            } else {
                label.setText("Gambar Hilang");
                label.setIcon(null);
            }
        } catch (Exception e) {
            label.setText("Error Muat Gambar");
            e.printStackTrace();
        }
    }

    private void tampilkanKamar(String sql, List<Object> params) {
        panelHasil.removeAll();
        
        try (Connection conn = Koneksi.configDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            // Set parameter untuk PreparedStatement
            for (int i = 0; i < params.size(); i++) {
                pst.setObject(i + 1, params.get(i));
            }

            try (ResultSet res = pst.executeQuery()) {
                if (!res.isBeforeFirst()) {
                    JLabel lblKosong = new JLabel("Tidak ada kamar yang tersedia untuk kriteria ini.");
                    lblKosong.setHorizontalAlignment(JLabel.CENTER);
                    panelHasil.setLayout(new BorderLayout());
                    panelHasil.add(lblKosong, BorderLayout.CENTER);
                } else {
                    panelHasil.setLayout(new java.awt.GridLayout(0, 3, 15, 15));
                    while (res.next()) {
                        javax.swing.JPanel kartuKamar = new javax.swing.JPanel();
            kartuKamar.setLayout(new javax.swing.BoxLayout(kartuKamar, javax.swing.BoxLayout.Y_AXIS));
            kartuKamar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
            kartuKamar.setBackground(new java.awt.Color(255, 255, 255));

            JLabel lblGambar = new JLabel();
            String namaFileGambar = res.getString("gambar");
            setImageToLabel(namaFileGambar, lblGambar);
            lblGambar.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel lblNamaTipe = new JLabel(res.getString("nama_tipe"));
            lblNamaTipe.setFont(new java.awt.Font("Segoe UI", 1, 18));
            lblNamaTipe.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 0, 10));
            lblNamaTipe.setAlignmentX(Component.LEFT_ALIGNMENT);

            javax.swing.JTextArea txtAreaFasilitas = new javax.swing.JTextArea("Fasilitas: " + res.getString("fasilitas"));
            txtAreaFasilitas.setWrapStyleWord(true);
            txtAreaFasilitas.setLineWrap(true);
            txtAreaFasilitas.setEditable(false);
            txtAreaFasilitas.setOpaque(false);
            txtAreaFasilitas.setFont(new java.awt.Font("Segoe UI", 0, 12));
            txtAreaFasilitas.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10));
            txtAreaFasilitas.setAlignmentX(Component.LEFT_ALIGNMENT);

            txtAreaFasilitas.setMaximumSize(txtAreaFasilitas.getPreferredSize());

            JLabel lblHarga = new JLabel("Rp " + res.getString("harga") + " / malam");
            lblHarga.setFont(new java.awt.Font("Segoe UI", 1, 14));
            lblHarga.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 5, 10));
            lblHarga.setAlignmentX(Component.LEFT_ALIGNMENT);

            javax.swing.JButton btnPesan = new javax.swing.JButton("Pesan Sekarang");
            btnPesan.setAlignmentX(Component.LEFT_ALIGNMENT);
            final String idKamar = res.getString("id");
            btnPesan.addActionListener(evt -> prosesPemesanan(idKamar));

            javax.swing.JPanel panelTombol = new javax.swing.JPanel(new BorderLayout());
            panelTombol.setOpaque(false);
            panelTombol.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 8, 8, 0));
            panelTombol.add(btnPesan, BorderLayout.WEST);
            panelTombol.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelTombol.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, panelTombol.getPreferredSize().height));


            kartuKamar.add(lblGambar);
            kartuKamar.add(lblNamaTipe);
            kartuKamar.add(txtAreaFasilitas);
            kartuKamar.add(Box.createVerticalGlue()); 
            kartuKamar.add(lblHarga);
            kartuKamar.add(panelTombol); 

            panelHasil.add(kartuKamar);
                            }
                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Gagal menampilkan kamar: " + e.getMessage());
                    e.printStackTrace();
                }
        
            panelHasil.revalidate();
            panelHasil.repaint();
    }
    
    private void prosesPemesanan(String idKamar) {
        if (dateCheckin.getDate() == null || dateCheckout.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Silakan pilih tanggal Check-in dan Check-out.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        LocalDate tanggalHarIni= LocalDate.now();
        LocalDate tanggalPilihan = dateCheckin.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        if (tanggalPilihan.isBefore(tanggalHarIni)){
        JOptionPane.showMessageDialog(this, "Tanggal tidak valid.", "Pemesanan hanya bisa dilakukan perhari ini atau setelahnya", JOptionPane.ERROR_MESSAGE);
        return;
        }
        
        
        if (dateCheckin.getDate().after(dateCheckout.getDate())) {
            JOptionPane.showMessageDialog(this, "Tanggal Check-out harus setelah tanggal Check-in.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(this, "Anda akan memesan kamar ini. Lanjutkan?", "Konfirmasi Pemesanan", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            Connection conn = null;
            try {
                conn = Koneksi.configDB();
                conn.setAutoCommit(false);

                String sqlPemesanan = "INSERT INTO pemesanan (id_user, id_kamar, tanggal_checkin, tanggal_checkout, status_pemesanan) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstPemesanan = conn.prepareStatement(sqlPemesanan, Statement.RETURN_GENERATED_KEYS);

                pstPemesanan.setInt(1, this.currentUser.getId());
                pstPemesanan.setInt(2, Integer.parseInt(idKamar));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                pstPemesanan.setString(3, sdf.format(dateCheckin.getDate()));
                pstPemesanan.setString(4, sdf.format(dateCheckout.getDate()));
                pstPemesanan.setString(5, "Menunggu Pembayaran");
                pstPemesanan.executeUpdate();

                ResultSet generatedKeys = pstPemesanan.getGeneratedKeys();
                int idPemesananBaru = 0;
                if (generatedKeys.next()) {
                    idPemesananBaru = generatedKeys.getInt(1);
                }

                String sqlUpdateKamar = "UPDATE kamar SET status = 'Dipesan' WHERE id = ?";
                PreparedStatement pstUpdateKamar = conn.prepareStatement(sqlUpdateKamar);
                pstUpdateKamar.setInt(1, Integer.parseInt(idKamar));
                pstUpdateKamar.executeUpdate();

                conn.commit();

                String subjekEmail = "Konfirmasi Pemesanan Hotel - ID #" + idPemesananBaru;
                String isiEmail = "Halo " + this.currentUser.getNamaLengkap() + ",\n\nPemesanan Anda telah kami terima.\n\nDetail:\nID Pesanan: " + idPemesananBaru + "\nCheck-in: " + sdf.format(dateCheckin.getDate()) + "\nCheck-out: " + sdf.format(dateCheckout.getDate()) + "\n\nStatus: Silakan Tranfer bank papua norek 12345.";
                
                if (this.currentUser.getEmail() != null && !this.currentUser.getEmail().isEmpty()) {
                    util.EmailService.kirimStruk(this.currentUser.getEmail(), subjekEmail, isiEmail);
                    JOptionPane.showMessageDialog(this, "Pemesanan berhasil! Bukti konfirmasi telah dikirim ke email Anda.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                } else {
                     JOptionPane.showMessageDialog(this, "Pemesanan berhasil! (Email tidak terkirim karena tidak terdaftar)", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                }
                
                btnCari.doClick();

            } catch (Exception e) {
                try {
                    if (conn != null) conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Rollback gagal: " + ex.getMessage());
                }
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) conn.setAutoCommit(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    private void loadTipeKamarFilter() {
        try (Connection conn = Koneksi.configDB();
             Statement stm = conn.createStatement();
             ResultSet res = stm.executeQuery("SELECT nama_tipe FROM tipe_kamar GROUP BY nama_tipe ORDER BY nama_tipe ASC")) {
            
            comboTipeFilter.removeAllItems();
            comboTipeFilter.addItem("Semua Tipe"); 
            while(res.next()){
                comboTipeFilter.addItem(res.getString("nama_tipe"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat filter tipe kamar: " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPencarian = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        dateCheckin = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        dateCheckout = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        comboTipeFilter = new javax.swing.JComboBox<>();
        btnCari = new javax.swing.JButton();
        jScrollPaneHasil = new javax.swing.JScrollPane();
        panelHasil = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Selamat Datang - Hotel Surya Indah Salatiga");

        panelPencarian.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("CHECK IN");

        dateCheckin.setPreferredSize(new java.awt.Dimension(150, 30));

        jLabel3.setText("CHECK OUT");

        dateCheckout.setPreferredSize(new java.awt.Dimension(150, 30));

        jLabel4.setText("Tipe Kamar:");

        comboTipeFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboTipeFilter.setPreferredSize(new java.awt.Dimension(150, 30));

        btnCari.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCari.setText("Cari Kamar");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPencarianLayout = new javax.swing.GroupLayout(panelPencarian);
        panelPencarian.setLayout(panelPencarianLayout);
        panelPencarianLayout.setHorizontalGroup(
            panelPencarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPencarianLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(panelPencarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelPencarianLayout.createSequentialGroup()
                        .addComponent(dateCheckin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(135, 135, 135)
                        .addComponent(dateCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPencarianLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(223, 223, 223)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(196, 196, 196)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboTipeFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCari)
                .addGap(37, 37, 37))
        );
        panelPencarianLayout.setVerticalGroup(
            panelPencarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPencarianLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelPencarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelPencarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(comboTipeFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCari))
                    .addGroup(panelPencarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel2)))
                .addGap(18, 18, 18)
                .addGroup(panelPencarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateCheckin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        panelHasil.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelHasil.setLayout(new java.awt.GridLayout(0, 3, 15, 15));
        jScrollPaneHasil.setViewportView(panelHasil);

        jPanel1.setBackground(new java.awt.Color(0, 0, 51));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SURYA INDAH SALATIGA");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(621, 621, 621)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPaneHasil, javax.swing.GroupLayout.PREFERRED_SIZE, 1013, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelPencarian, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPencarian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPaneHasil, javax.swing.GroupLayout.PREFERRED_SIZE, 713, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
       StringBuilder queryBuilder = new StringBuilder(
            "SELECT k.id, tk.nama_tipe, tk.fasilitas, tk.harga, tk.gambar " +
            "FROM kamar k JOIN tipe_kamar tk ON k.id_tipe_kamar = tk.id "
        );
        StringBuilder whereClause = new StringBuilder("WHERE 1=1");
        List<Object> params = new ArrayList<>();

        whereClause.append(" AND k.status = ?");
        params.add("Tersedia");

        if (dateCheckin.getDate() != null && dateCheckout.getDate() != null) {
            whereClause.append(" AND k.id NOT IN (");
            whereClause.append("SELECT id_kamar FROM pemesanan WHERE id_kamar IS NOT NULL AND (");
            whereClause.append("? < tanggal_checkout AND ? > tanggal_checkin");
            whereClause.append("))");
            
            java.sql.Date checkinDate = new java.sql.Date(dateCheckin.getDate().getTime());
            java.sql.Date checkoutDate = new java.sql.Date(dateCheckout.getDate().getTime());
            params.add(checkinDate);
            params.add(checkoutDate);
        }
        
        String tipeDipilih = comboTipeFilter.getSelectedItem().toString();
        if (!tipeDipilih.equals("Semua Tipe")) {
            whereClause.append(" AND tk.nama_tipe = ?");
            params.add(tipeDipilih);
        }
        
        queryBuilder.append(" ").append(whereClause);
        
        tampilkanKamar(queryBuilder.toString(), params);
    }//GEN-LAST:event_btnCariActionPerformed

    /**
     * @param args the command line arguments
     */
//    
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(UserDashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(UserDashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(UserDashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(UserDashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new UserDashboardForm().setVisible(true);
//            }
//        });
//    }
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JComboBox<String> comboTipeFilter;
    private com.toedter.calendar.JDateChooser dateCheckin;
    private com.toedter.calendar.JDateChooser dateCheckout;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPaneHasil;
    private javax.swing.JPanel panelHasil;
    private javax.swing.JPanel panelPencarian;
    // End of variables declaration//GEN-END:variables
}
