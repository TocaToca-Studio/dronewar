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
    public Map<Integer,Integer> keymap= Map.of(
        CROSS, Keyboard.KEY_S,
        TRIANGLE,Keyboard.KEY_W
    );  
    @Override
    protected int get_key_state(int key) {
       return (Keyboard.isKeyDown(keymap.get(key))) 
               ? super.KEY_PRESSING : super.KEY_FREE;
    }
}
