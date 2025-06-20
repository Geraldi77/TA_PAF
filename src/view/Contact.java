/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
import com.teamdev.jxbrowser.view.swing.BrowserView;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import java.awt.Desktop;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.nio.file.Paths;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Contact extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    public Contact() {
        initComponents();
        lbla1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Engine engine = BrowserEngine.getEngine();
        Browser browser = engine.newBrowser();

        this.setTitle("Tampilan Peta Hotel (Modern)");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                engine.close();
                BrowserEngine.getEngine().close();
            }
        });

        BrowserView view = BrowserView.newInstance(browser);
        
        // --- INI PERBAIKAN UI YANG SANGAT PENTING ---
        panelPetaKontak.setLayout(new BorderLayout());
        panelPetaKontak.add(view, BorderLayout.CENTER);

        this.setSize(1024, 768);
        this.setLocationRelativeTo(null);

        // Baris ini untuk debugging, boleh dihapus jika sudah berhasil
        System.out.println("Program mencari file di path: " + Paths.get("map.html").toAbsolutePath().toString());

        String mapUrl = Paths.get("map.html").toUri().toString();
        browser.navigation().loadUrl(mapUrl);
        // Mengatur agar form terbuka di tengah layar dan maximized
        
        this.setLocationRelativeTo(null);
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        
        // Mengisi pilihan bahasa dan menghapus item default
        comboBahasa.removeAllItems();
        comboBahasa.addItem("Bahasa Indonesia");
        comboBahasa.addItem("English");
        
        // Mengatur bahasa default saat aplikasi dibuka
        ubahBahasa("in", "ID");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            // Panggil method khusus saat label diklik
            openWhatsApp();
        }
    });
    }
    

    // Method untuk mengubah bahasa
    private void ubahBahasa(String kodeBahasa, String kodeNegara) {
        try {
            // Tentukan Locale (bahasa dan negara)
            Locale locale = new Locale(kodeBahasa, kodeNegara);
            
            // Muat file properti berdasarkan Locale
            // Pastikan Anda membuat package "i18n" di dalam package "view"
            ResourceBundle messages = ResourceBundle.getBundle("i18n.messages", locale);
            
            // Terapkan teks ke setiap komponen
            
            lbls1.setText(messages.getString("welcome.s1"));
            lbls2.setText(messages.getString("welcome.s2"));
            
            lbla1.setText(messages.getString("welcome.a1"));
            lbla2.setText(messages.getString("welcome.a2"));
            lbla3.setText(messages.getString("welcome.a3"));
            lbla4.setText(messages.getString("welcome.a4"));
            lbla5.setText(messages.getString("welcome.a5"));
            lbla6.setText(messages.getString("welcome.a6"));
            lbla7.setText(messages.getString("welcome.a7"));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat file bahasa: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
    private void openWhatsApp() {
    try {
        // Ganti dengan nomor WhatsApp tujuan Anda (gunakan format internasional tanpa "+")
        String nomorWhatsApp = "6281316056818"; // Contoh: nomor Indonesia 0812...
        String url = "https://wa.me/" + nomorWhatsApp;

        // Cek apakah fitur Desktop didukung oleh sistem
        if (Desktop.isDesktopSupported()) {
            // Buka browser default dengan URL WhatsApp
            Desktop.getDesktop().browse(new URI(url));
        } else {
            JOptionPane.showMessageDialog(this, "Fitur ini tidak didukung di sistem Anda.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal membuka WhatsApp: " + e.getMessage());
        e.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        lbls2 = new javax.swing.JLabel();
        lbls1 = new javax.swing.JLabel();
        lbla1 = new javax.swing.JLabel();
        lbla7 = new javax.swing.JLabel();
        lbla3 = new javax.swing.JLabel();
        lbla2 = new javax.swing.JLabel();
        lbla4 = new javax.swing.JLabel();
        lbla5 = new javax.swing.JLabel();
        lbla6 = new javax.swing.JLabel();
        comboBahasa = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        panelPetaKontak = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1540, 840));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbls2.setFont(new java.awt.Font("Segoe UI", 1, 44)); // NOI18N
        lbls2.setForeground(new java.awt.Color(255, 255, 255));
        lbls2.setText("CONTACT US");
        jPanel1.add(lbls2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 280, 360, -1));

        lbls1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbls1.setForeground(new java.awt.Color(255, 204, 102));
        lbls1.setText("G E T  I N  T O U C H");
        jPanel1.add(lbls1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 260, -1, -1));

        lbla1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbla1.setForeground(new java.awt.Color(255, 255, 255));
        lbla1.setText("Home");
        lbla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbla1MouseClicked(evt);
            }
        });
        jPanel1.add(lbla1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, -1, -1));

        lbla7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbla7.setForeground(new java.awt.Color(255, 255, 255));
        lbla7.setText("Contact");
        jPanel1.add(lbla7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 90, -1, -1));

        lbla3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbla3.setForeground(new java.awt.Color(255, 255, 255));
        lbla3.setText("Restaurant");
        jPanel1.add(lbla3, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 90, -1, -1));

        lbla2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbla2.setForeground(new java.awt.Color(255, 255, 255));
        lbla2.setText("Rooms");
        jPanel1.add(lbla2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 90, -1, -1));

        lbla4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbla4.setForeground(new java.awt.Color(255, 255, 255));
        lbla4.setText("Facilities");
        jPanel1.add(lbla4, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 90, -1, -1));

        lbla5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbla5.setForeground(new java.awt.Color(255, 255, 255));
        lbla5.setText("Promotion");
        jPanel1.add(lbla5, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 90, -1, -1));

        lbla6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbla6.setForeground(new java.awt.Color(255, 255, 255));
        lbla6.setText("Activity & Gallery");
        jPanel1.add(lbla6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 90, -1, -1));

        comboBahasa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboBahasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBahasaActionPerformed(evt);
            }
        });
        jPanel1.add(comboBahasa, new org.netbeans.lib.awtextra.AbsoluteConstraints(1380, 90, -1, -1));

        jLabel2.setBackground(new java.awt.Color(0, 204, 51));
        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Documents\\java\\icons8-whatsapp-64.png")); // NOI18N
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1430, 740, 70, 50));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Documents\\java\\laras1.jpeg")); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(1540, 600));
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1540, 480));

        javax.swing.GroupLayout panelPetaKontakLayout = new javax.swing.GroupLayout(panelPetaKontak);
        panelPetaKontak.setLayout(panelPetaKontakLayout);
        panelPetaKontakLayout.setHorizontalGroup(
            panelPetaKontakLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1200, Short.MAX_VALUE)
        );
        panelPetaKontakLayout.setVerticalGroup(
            panelPetaKontakLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 460, Short.MAX_VALUE)
        );

        jPanel1.add(panelPetaKontak, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 1200, 460));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Jl. Diponegoro No.110A, Sidorejo Lor,");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 540, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Kec. Sidorejo, Kota Salatiga, Jawa Tengah ");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 570, 270, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("50714");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 600, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 0, 0));
        jLabel6.setText("Email Address");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 640, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 0, 0));
        jLabel7.setText("Telephone");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 740, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(153, 0, 0));
        jLabel8.setText("Address");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 510, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 153, 0));
        jLabel9.setText("Info@HotelSuryaIndahSalatiga.com");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 670, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(204, 153, 0));
        jLabel10.setText("Reservation@SuryaIndahSalatiga.com");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 700, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 153, 0));
        jLabel11.setText("0857-2761-4000");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 770, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboBahasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBahasaActionPerformed
        if (comboBahasa.getSelectedItem() == null) {
            return;
        }

        String pilihan = comboBahasa.getSelectedItem().toString();

        if (pilihan.equals("English")) {
            ubahBahasa("en", "US");
        } else {
            ubahBahasa("in", "ID");
        }
    }//GEN-LAST:event_comboBahasaActionPerformed

    private void lbla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbla1MouseClicked
        Home formHome = new Home();
    // 2. Tampilkan form Home tersebut
    formHome.setVisible(true);

    // 3. Tutup form yang sekarang (Contact)
    this.dispose();
    }//GEN-LAST:event_lbla1MouseClicked

    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) {
    // Baris ini tetap, karena berlaku global
    System.setProperty("jxbrowser.chromium.switches", "--allow-file-access-from-files");

    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            // Pastikan yang dijalankan adalah form Kontak
            new Contact().setVisible(true);
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboBahasa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbla1;
    private javax.swing.JLabel lbla2;
    private javax.swing.JLabel lbla3;
    private javax.swing.JLabel lbla4;
    private javax.swing.JLabel lbla5;
    private javax.swing.JLabel lbla6;
    private javax.swing.JLabel lbla7;
    private javax.swing.JLabel lbls1;
    private javax.swing.JLabel lbls2;
    private javax.swing.JPanel panelPetaKontak;
    // End of variables declaration//GEN-END:variables
}
