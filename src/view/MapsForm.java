package view;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.view.swing.BrowserView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Paths;
import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

public class MapsForm extends JFrame {

    public MapsForm() {
        initComponents();

        EngineOptions options = EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                .licenseKey("OK6AEKNYF3OGBOMM0MZICPVC7Q0C1K4YZTIYIJ6Z6XPIRS0BEIFYY5ERJAZ1GZXCNG3ZS3H8VKURP0X6P459KDB9OXV0W6R4R6KBMGII0MVGC1CK17Z1WRDDCHUJ70LXR69KLWZQZ6COADKUT")
                .userDataDir(Paths.get("./jxbrowser-data"))
                .build();
        Engine engine = Engine.newInstance(options);
        Browser browser = engine.newBrowser();

        this.setTitle("Tampilan Peta Hotel (Modern)");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                engine.close();
            }
        });

        BrowserView view = BrowserView.newInstance(browser);
        
        // --- INI PERBAIKAN UI YANG SANGAT PENTING ---
        panelPeta.setLayout(new BorderLayout());
        panelPeta.add(view, BorderLayout.CENTER);

        this.setSize(1024, 768);
        this.setLocationRelativeTo(null);

        // Baris ini untuk debugging, boleh dihapus jika sudah berhasil
        System.out.println("Program mencari file di path: " + Paths.get("map.html").toAbsolutePath().toString());

        String mapUrl = Paths.get("map.html").toUri().toString();
        browser.navigation().loadUrl(mapUrl);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPeta = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout panelPetaLayout = new javax.swing.GroupLayout(panelPeta);
        panelPeta.setLayout(panelPetaLayout);
        panelPetaLayout.setHorizontalGroup(
            panelPetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        panelPetaLayout.setVerticalGroup(
            panelPetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPeta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPeta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         System.out.println("Current Working Directory: " + Paths.get("").toAbsolutePath().toString());

        System.setProperty("jxbrowser.chromium.switches", "--allow-file-access-from-files");

        // Jalankan aplikasi di Event Dispatch Thread (EDT) Swing untuk keamanan thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Buat instance dari form dan tampilkan
                new MapsForm().setVisible(true);
            }
        });
    }




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelPeta;
    // End of variables declaration//GEN-END:variables
}
