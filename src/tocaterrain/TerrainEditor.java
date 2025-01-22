package tocaterrain;

import choke3d.math.MathUtils;
import choke3d.math.Vec3f;

/**
 *
 * @author tocatoca
 */
public class TerrainEditor extends App {
    public Heightmap hmap=new Heightmap(64,64);
    
    public void run() {
        init();
        camera.setup2D(hmap.width);
        float halfx=hmap.width/2f;
        float halfz=hmap.length/2f;
        camera.orthoLeft=-halfx;
        camera.orthoRight=halfx;
        camera.orthoTop=+halfz;
        camera.orthoBottom=-halfz;
        camera.transform.position.y=30;
        camera.transform.rotation.rotate(MathUtils.TH_DEG_TO_RAD* 90, new Vec3f(1f,0,0));
        while(running) {
           update();
           hmap.draw();
        }
        destroy();
    }
    public static void main(String args[]) {
        (new TerrainEditor()).run();
    }
}
