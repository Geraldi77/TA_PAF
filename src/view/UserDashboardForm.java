/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import db.Koneksi;
import java.awt.BorderLayout;
import java.awt.Image;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class UserDashboardForm extends javax.swing.JFrame {

    public UserDashboardForm() {
        initComponents();
        loadTipeKamarFilter();
        // Query default sudah diperbaiki untuk mengambil gambar
        String queryDefault = "SELECT k.id, tk.nama_tipe, tk.fasilitas, tk.harga, tk.gambar " +
                          "FROM kamar k JOIN tipe_kamar tk ON k.id_tipe_kamar = tk.id " +
                          "WHERE k.status = 'Tersedia'";
        tampilkanKamar(queryDefault);
    }
    
    // Method bantuan untuk menampilkan gambar di JLabel
    private void setImageToLabel(String namaFileGambar, javax.swing.JLabel label) {
        if (namaFileGambar == null || namaFileGambar.isEmpty()) {
            label.setText("Gambar Tidak Tersedia");
            label.setIcon(null);
            return;
        }
        try {
            // Mengambil gambar dari folder eksternal 'app_images'
            String pathGambar = System.getProperty("user.dir") + "/app_images/" + namaFileGambar;
            java.io.File fileGambar = new java.io.File(pathGambar);

            if (fileGambar.exists()) {
                ImageIcon icon = new ImageIcon(fileGambar.getAbsolutePath());
                // Mengubah ukuran gambar agar pas dengan JLabel
                // Penting: agar getWidth() dan getHeight() tidak 0, pastikan label sudah punya ukuran.
                // Jika masih 0, kita beri ukuran default.
                int width = label.getWidth() == 0 ? 250 : label.getWidth();
                int height = label.getHeight() == 0 ? 180 : label.getHeight();
                Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                
                label.setIcon(new ImageIcon(img));
                label.setText(""); // Hapus teks jika gambar berhasil dimuat
            } else {
                label.setText("Gambar Hilang");
                label.setIcon(null);
                System.err.println("File gambar tidak ditemukan di path: " + pathGambar);
            }
        } catch (Exception e) {
            label.setText("Error Muat Gambar");
            e.printStackTrace();
        }
    }

    private void tampilkanKamar(String sql) {
        // Kosongkan panel setiap kali method dipanggil
        panelHasil.removeAll();
        
        try {
            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            if (!res.isBeforeFirst() ) { // Cek apakah ResultSet kosong
                JLabel lblKosong = new JLabel("Tidak ada kamar yang tersedia untuk kriteria ini.");
                lblKosong.setHorizontalAlignment(JLabel.CENTER);
                panelHasil.setLayout(new BorderLayout());
                panelHasil.add(lblKosong, BorderLayout.CENTER);
            } else {
                 panelHasil.setLayout(new java.awt.GridLayout(0, 3, 15, 15)); // Kembalikan ke GridLayout
                 while (res.next()) {
                    // Membuat komponen-komponen untuk setiap kartu kamar
                    javax.swing.JPanel kartuKamar = new javax.swing.JPanel();
                    kartuKamar.setLayout(new javax.swing.BoxLayout(kartuKamar, javax.swing.BoxLayout.Y_AXIS));
                    kartuKamar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                    kartuKamar.setBackground(new java.awt.Color(255, 255, 255));

                    // --- INI BAGIAN BARU UNTUK GAMBAR ---
                    JLabel lblGambar = new JLabel();
                    lblGambar.setPreferredSize(new java.awt.Dimension(250, 180)); // Atur ukuran area gambar
                    lblGambar.setHorizontalAlignment(JLabel.CENTER);
                    String namaFileGambar = res.getString("tk.gambar");
                    // Panggil method bantuan untuk set gambar
                    setImageToLabel(namaFileGambar, lblGambar);
                    // --- AKHIR BAGIAN GAMBAR ---

                    JLabel lblNamaTipe = new JLabel("  " + res.getString("tk.nama_tipe"));
                    lblNamaTipe.setFont(new java.awt.Font("Segoe UI", 1, 18));

                    javax.swing.JTextArea txtAreaFasilitas = new javax.swing.JTextArea("  Fasilitas: " + res.getString("tk.fasilitas"));
                    txtAreaFasilitas.setWrapStyleWord(true);
                    txtAreaFasilitas.setLineWrap(true);
                    txtAreaFasilitas.setEditable(false);
                    txtAreaFasilitas.setOpaque(false);

                    JLabel lblHarga = new JLabel("  Rp " + res.getString("tk.harga") + " / malam");
                    lblHarga.setFont(new java.awt.Font("Segoe UI", 1, 14));

                    javax.swing.JButton btnPesan = new javax.swing.JButton("Pesan Sekarang");
                    final String idKamar = res.getString("k.id");
                    btnPesan.addActionListener(evt -> prosesPemesanan(idKamar));

                    // Menambahkan semua komponen ke kartu (lblGambar ditaruh paling atas)
                    kartuKamar.add(lblGambar);
                    kartuKamar.add(lblNamaTipe);
                    kartuKamar.add(txtAreaFasilitas);
                    kartuKamar.add(lblHarga);
                    kartuKamar.add(btnPesan);

                    // Menambahkan kartu ke panel utama
                    panelHasil.add(kartuKamar);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan kamar: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Refresh tampilan panel
        panelHasil.revalidate();
        panelHasil.repaint();
    }
    
    // Method prosesPemesanan Anda biarkan apa adanya
    private void prosesPemesanan(String idKamar) {
        if (dateCheckin.getDate() == null || dateCheckout.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Silakan pilih tanggal Check-in dan Check-out terlebih dahulu.", "Tanggal Belum Dipilih", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int konfirmasi = JOptionPane.showConfirmDialog(this, 
            "Anda akan memesan kamar ini. Lanjutkan ke pembayaran?", 
            "Konfirmasi Pemesanan", 
            JOptionPane.YES_NO_OPTION);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            // ... (sisa logika pemesanan Anda tidak berubah) ...
        }
    }
    
    // Method loadTipeKamarFilter Anda biarkan apa adanya
    private void loadTipeKamarFilter() {
        // ... (logika load filter Anda tidak berubah) ...
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
       try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            // Query dasar sudah diperbaiki untuk mengambil gambar
            StringBuilder queryBuilder = new StringBuilder(
                "SELECT k.id, tk.nama_tipe, tk.fasilitas, tk.harga, tk.gambar " +
                "FROM kamar k JOIN tipe_kamar tk ON k.id_tipe_kamar = tk.id "
            );

            StringBuilder whereClause = new StringBuilder("WHERE k.status = 'Tersedia'");

            if (dateCheckin.getDate() != null && dateCheckout.getDate() != null) {
                 String checkinDate = sdf.format(dateCheckin.getDate());
                 String checkoutDate = sdf.format(dateCheckout.getDate());
                 whereClause.append(" AND k.id NOT IN (");
                 whereClause.append("SELECT id_kamar FROM pemesanan WHERE id_kamar IS NOT NULL AND (");
                 whereClause.append("('").append(checkinDate).append("' < tanggal_checkout) AND ");
                 whereClause.append("('").append(checkoutDate).append("' > tanggal_checkin)");
                 whereClause.append("))");
            }
            
            String tipeDipilih = comboTipeFilter.getSelectedItem().toString();
            if (!tipeDipilih.equals("Semua Tipe")) {
                whereClause.append(" AND tk.nama_tipe = '").append(tipeDipilih).append("'");
            }
            
            queryBuilder.append(" ").append(whereClause);
            
            tampilkanKamar(queryBuilder.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mencari: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnCariActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserDashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserDashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserDashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserDashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserDashboardForm().setVisible(true);
            }
        });
    }

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
