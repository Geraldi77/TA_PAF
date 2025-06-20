/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import java.nio.file.Paths;
import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

/**
 * Kelas ini mengelola satu instance Engine JxBrowser untuk seluruh aplikasi.
 * Ini mencegah error UserDataDirectoryAlreadyInUseException.
 */
public final class BrowserEngine {

    private static Engine instance;

    // Constructor dibuat private agar tidak ada yang bisa membuat instance dari luar.
    private BrowserEngine() {
    }

    /**
     * Mengembalikan satu-satunya instance Engine. Jika belum ada, maka akan dibuat.
     * @return instance Engine yang dipakai bersama.
     */
    public static synchronized Engine getEngine() {
        if (instance == null) {
            // Atur Switch Chromium secara global sebelum Engine dibuat.
            System.setProperty("jxbrowser.chromium.switches", "--allow-file-access-from-files");

            // Buat EngineOptions di sini, hanya sekali.
            EngineOptions options = EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                    .licenseKey("OK6AEKNYF3OGBOMM0MZICPVC7Q0C1K4YZTIYIJ6Z6XPIRS0BEIFYY5ERJAZ1GZXCNG3ZS3H8VKURP0X6P459KDB9OXV0W6R4R6KBMGII0MVGC1CK17Z1WRDDCHUJ70LXR69KLWZQZ6COADKUT")
                    .userDataDir(Paths.get("./jxbrowser-data")) // Opsional, untuk cache baru
                    .build();
            
            instance = Engine.newInstance(options);
        }
        return instance;
    }
}
