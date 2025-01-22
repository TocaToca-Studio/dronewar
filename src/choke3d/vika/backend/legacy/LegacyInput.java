package choke3d.vika.backend.legacy;

import choke3d.vika.frontend.Input;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author tocatoca
 */
public class LegacyInput extends Input {
    private static final Map<Integer, Integer> keymap;

    static {
        keymap = new HashMap<>();
        keymap.put(CROSS, Keyboard.KEY_S);
        keymap.put(TRIANGLE, Keyboard.KEY_W);
        keymap.put(CIRCLE, Keyboard.KEY_D);
        keymap.put(QUAD, Keyboard.KEY_A);
        keymap.put(R1, Keyboard.KEY_E);
        keymap.put(R2, Keyboard.KEY_RCONTROL);
        keymap.put(R3, Keyboard.KEY_RSHIFT);
        keymap.put(L1, Keyboard.KEY_Q);
        keymap.put(L2, Keyboard.KEY_LCONTROL);
        keymap.put(L3, Keyboard.KEY_LSHIFT);
        keymap.put(UP, Keyboard.KEY_UP);
        keymap.put(DOWN, Keyboard.KEY_DOWN);
        keymap.put(LEFT, Keyboard.KEY_LEFT);
        keymap.put(RIGHT, Keyboard.KEY_RIGHT);
        keymap.put(START, Keyboard.KEY_F);
        keymap.put(SELECT, Keyboard.KEY_TAB);
    }
    @Override
    protected int get_key_state(int key) {
       if(!keymap.containsKey(key)) return KEY_FREE;
       
       return (Keyboard.isKeyDown(keymap.get(key))) 
               ? super.KEY_PRESSING : super.KEY_FREE;
    }
}
