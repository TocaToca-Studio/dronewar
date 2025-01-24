package choke3d.vika.backend.legacy;

import choke3d.math.Vec2f;
import choke3d.vika.frontend.Input;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author tocatoca
 */
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

public class LegacyInput extends Input {
    private static final Map<Integer, Integer> keymap = new HashMap<>();
    private static final Map<Integer, Integer> joystickButtonMap = new HashMap<>();
    private static final float JOYSTICK_AXIS_THRESHOLD = 0.5f; // Limite para detectar movimento em eixos
    private static Controller joystick = null;

    static {
        // Mapear teclas do teclado com padrão mais intuitivo (sem botões de mouse)
        keymap.put(CROSS, Keyboard.KEY_SPACE);        // Barra de espaço para pular/confirmar
        keymap.put(CIRCLE, Keyboard.KEY_E);           // E para interagir
        keymap.put(TRIANGLE, Keyboard.KEY_Q);         // Q para ação alternativa
        keymap.put(QUAD, Keyboard.KEY_F);             // F para usar ou ação especial
        keymap.put(R1, Keyboard.KEY_E);               // E para ação avançada ou ataque primário
        keymap.put(R2, Keyboard.KEY_R);               // R para recarregar
        keymap.put(R3, Keyboard.KEY_RSHIFT);          // RShift para ação avançada
        keymap.put(L1, Keyboard.KEY_RSHIFT);          // RShift para correr ou mirar
        keymap.put(L2, Keyboard.KEY_LSHIFT);          // LShift para defesa ou ação alternativa
        keymap.put(L3, Keyboard.KEY_LCONTROL);        // LCtrl para agachar
        keymap.put(UP, Keyboard.KEY_UP);              // Setas direcionais
        keymap.put(DOWN, Keyboard.KEY_DOWN);
        keymap.put(LEFT, Keyboard.KEY_LEFT);
        keymap.put(RIGHT, Keyboard.KEY_RIGHT);
        keymap.put(START, Keyboard.KEY_RETURN);       // Enter para pausar ou confirmar
        keymap.put(SELECT, Keyboard.KEY_TAB);         // Tab para abrir menu ou alternar

        // Mapear botões do joystick
        joystickButtonMap.put(CROSS, 0);          // Botão A ou X (pular ou confirmar)
        joystickButtonMap.put(CIRCLE, 1);         // Botão B ou O (voltar ou ação secundária)
        joystickButtonMap.put(TRIANGLE, 2);       // Botão Y ou Δ (ação alternativa)
        joystickButtonMap.put(QUAD, 3);           // Botão X ou □ (interagir ou usar)
        
        joystickButtonMap.put(L1, 4);             // Botão L1 (ombro esquerdo)
        joystickButtonMap.put(R1, 5);             // Botão R1 (ombro direito)
        joystickButtonMap.put(L2, 6);             // Botão L2 (gatilho esquerdo)
        joystickButtonMap.put(R2, 7);  
        
        joystickButtonMap.put(SELECT, 8);          // Botão A ou X (pular ou confirmar)
        joystickButtonMap.put(START, 9);         // Botão B ou O (voltar ou ação secundária)
        joystickButtonMap.put(L3, 10);       // Botão Y ou Δ (ação alternativa)
        joystickButtonMap.put(R3, 11);   
        
       
    }
    @Override
    public Vec2f getAxis() {
        Vec2f ax=super.getAxis();
        if(Math.abs(ax.x) > 0.1f || Math.abs(ax.y) > 0.1f )  return ax;
        return new Vec2f(-joystick.getAxisValue(0),-joystick.getAxisValue(1));
    }
    @Override
    protected int get_key_state(int key) {
        // Verifica estado do teclado
        if (keymap.containsKey(key) && Keyboard.isKeyDown(keymap.get(key))) {
            return KEY_PRESSING;
        }

        // Verifica estado do joystick
        if (joystick != null && joystickButtonMap.containsKey(key)) {
            int buttonIndex = joystickButtonMap.get(key);
            if (joystick.isButtonPressed(buttonIndex)) {
                return KEY_PRESSING;
            }
        }

        // Detecta movimento nos eixos do joystick
        if (joystick != null && false) {
            switch (key) {
                case UP:
                    if (joystick.getAxisValue(1) < -JOYSTICK_AXIS_THRESHOLD) {
                        return KEY_PRESSING;
                    }
                    break;
                case DOWN:
                    if (joystick.getAxisValue(1) > JOYSTICK_AXIS_THRESHOLD) {
                        return KEY_PRESSING;
                    }
                    break;
                case RIGHT:
                    if (joystick.getAxisValue(0) < -JOYSTICK_AXIS_THRESHOLD) {
                        return KEY_PRESSING;
                    }
                    break;
                case LEFT:
                    if (joystick.getAxisValue(0) > JOYSTICK_AXIS_THRESHOLD) {
                        return KEY_PRESSING;
                    }
                    break;
            }
        }

        return KEY_FREE;
    }

    @Override
    public void init() {
         // Inicializar suporte ao joystick
        try {
            Controllers.create();
            if (Controllers.getControllerCount() > 0) {
                joystick = Controllers.getController(0); // Usa o primeiro joystick disponível
            }
        } catch (Exception e) {
            System.err.println("Falha ao inicializar controle: " + e.getMessage());
        }
    }
}

