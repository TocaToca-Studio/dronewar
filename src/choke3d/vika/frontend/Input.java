package choke3d.vika.frontend;

import choke3d.math.Vec2f;
import java.util.HashMap;

/**
 *
 * @author tocatoca
 */
public abstract class Input {
    public final int KEY_FREE = 0;
    public final int KEY_PRESSED = 1;
    public final int KEY_PRESSING = 2;
    public final int KEY_RELEASED = 3;
    
    public final int NONE=0;
    public final int CROSS=1;
    public final int TRIANGLE=2;
    public final int CIRCLE=3;
    public final int QUAD=4;
    public final int R1=5;
    public final int R2=6;
    public final int R3=7;
    public final int L1=8;
    public final int L2=9;
    public final int L3=10;
    public final int UP=11;
    public final int DOWN=12;
    public final int LEFT=13;
    public final int RIGHT=14;
    public final int START=15;
    public final int SELECT=16; 
    
    private final int[] key_state=new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    
    public boolean pressing(int button){
        if(button>16 || button<0) return false;
        return key_state[button]==KEY_PRESSING || key_state[button]==KEY_RELEASED;
    }
    public boolean presed(int button) { 
        if(button>16 || button<0) return false;
        return key_state[button]==KEY_PRESSED;
    }
    public boolean released(int button) { 
        if(button>16 || button<0) return false;
        return key_state[button]==KEY_RELEASED;
    }  
    public Vec2f getAxis() {
        float delta=1;
        Vec2f mov=new Vec2f(0,0); 
        if(pressing(UP)) mov.y+=delta;
        if(pressing(DOWN)) mov.y-=delta;
        if(pressing(LEFT)) mov.x+=delta;
        if(pressing(RIGHT)) mov.x-=delta;
        return mov;
    }
    protected abstract int get_key_state(int key);
    public void update() {
        for(int i=0;i<key_state.length;i++) key_state[i]=get_key_state(i);
    }

   
}
