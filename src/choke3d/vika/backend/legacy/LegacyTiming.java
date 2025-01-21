package choke3d.vika.backend.legacy;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author tocatoca
 */
public class LegacyTiming {
    /** time at last frame */
    public long last_frame;
     
    /** frames per second */
    public int fps;
    /** last fps time */
    public long time;
    public long lastFPS;
    public double delta;
    /**
     * Get the accurate system time
     * 
     * @return The system time in milliseconds
     */
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
    public double getDelta() { 
        return delta;
    }
    public void init() { 
        last_frame=getTime();
    }
    public double update(){   
        time=getTime();
        delta =  (time - last_frame) / 1000.0f;
        last_frame = time; 
        return delta; 
    }
}
