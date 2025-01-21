package choke3d.vika;

import choke3d.vika.backend.legacy.LegacyPlatform;

/**
 *
 * @author tocatoca
 */
public class vikaTest {
    public static void main(String[] args) {
        LegacyPlatform app=new LegacyPlatform();
        app.init();
        while(app.running) {
            app.update();
        }
        app.destroy();
    }
}
