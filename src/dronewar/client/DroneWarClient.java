package dronewar.client;

import tocaterrain.App;
import choke3d.vika.FPSCamera; 
import dronewar.server.game.Bullet;
import dronewar.server.game.Drone; 
import dronewar.server.game.Player;
import dronewar.server.protocol.ControlData;
import dronewar.server.protocol.GeneralUpdateData; 
import java.nio.FloatBuffer;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
/**
 *
 * @author tocatoca
 */
public class DroneWarClient extends App {
    GameMap map=new GameMap(); 
    
    GeneralUpdateData last_update=new GeneralUpdateData(); 
    
    ClientPacketHandler packetHandler=new ClientPacketHandler(this);
    Player player=new Player();
    ControlData client_control=new ControlData();
    
    @Override
    public void update() {
        super.update();
        map.update();
        Display.setTitle(player.name); 
        client_control.movement=getInputAxis();
        client_control.fire=Keyboard.isKeyDown(Keyboard.KEY_SPACE);
    } 
    void draw() { 
        map.draw();  
        synchronized(last_update.drones) {
            for(Drone d : last_update.drones) {
                if(d.player==player.id) {  
                    ((DroneCamera)camera).target=d; 
                }
                Drawer.draw_drone(d);
            }
        }
        synchronized(last_update.bullets) {
            for(Bullet b : last_update.bullets) { 
                Drawer.draw_bullet(b);
            }
        }
    }
    public void run() {
        init();  
        //camera=new FPSCamera();
        camera=new DroneCamera();
        camera.setup3D(60);
        
    // Habilitar a neblina
    glEnable(GL_FOG);

    // Configurar os parâmetros da neblina
        float fogColor[] = {0.5f, 0.5f, 0.5f, 1.0f};  // Cinza claro
        FloatBuffer f=BufferUtils.createFloatBuffer(4);  
        f.put(fogColor);
        f.flip();
        glFog(GL_FOG_COLOR, f);
        

        glFogi(GL_FOG_MODE, GL_LINEAR);   // Modo de neblina (GL_LINEAR, GL_EXP, GL_EXP2)
        glFogf(GL_FOG_START, 100.0f);      // Começa em 10.0 unidades
        glFogf(GL_FOG_END, 500.0f);        // Termina em 50.0 unidades
        glFogf(GL_FOG_DENSITY, 0.1f);     // Densidade (somente para GL_EXP ou GL_EXP2)

        glHint(GL_FOG_HINT, GL_NICEST);   // Qualidade da neblina

        map.init(); 
        while(running) {
           update(); 
           draw();
        }
        destroy();
        packetHandler.socket.close();
        System.exit(0);
    }
    public static void main(String args[]) {
        LoginFrame.main(args);
        //(new DroneWarClient()).run();
    }
}
