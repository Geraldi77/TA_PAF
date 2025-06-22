package view;

import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
import java.nio.file.Paths;

public final class BrowserEngine {

    private static Engine instance;

    private BrowserEngine() {}

    public static synchronized Engine getEngine() {
        if (instance == null || instance.isClosed()) {
            System.setProperty("jxbrowser.chromium.switches", "--allow-file-access-from-files");
            
            EngineOptions options = EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                    // Ganti dengan license key JxBrowser Anda
                    .licenseKey("OK6AEKNYF3OGBOMM0MZICPVC7Q0C1K4YZTIYIJ6Z6XPIRS0BEIFYY5ERJAZ1GZXCNG3ZS3H8VKURP0X6P459KDB9OXV0W6R4R6KBMGII0MVGC1CK17Z1WRDDCHUJ70LXR69KLWZQZ6COADKUT ") 
                    .userDataDir(Paths.get("./jxbrowser-data"))
                    .build();
            
            instance = Engine.newInstance(options);
            
            // Hook untuk menutup Engine secara otomatis saat aplikasi ditutup
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (!instance.isClosed()) {
                    instance.close();
                }
            }));
        }
        return instance;
    }
}