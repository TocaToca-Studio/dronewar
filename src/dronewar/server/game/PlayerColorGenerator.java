package dronewar.server.game;

/**
 *
 * @author tocatoca
 */
import choke3d.math.Color4f;
import java.util.Arrays;
import java.util.List;
import java.awt.Color;

public class PlayerColorGenerator {
    private static final List<Color> COLORS = Arrays.asList(
        Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
        Color.ORANGE, Color.CYAN, Color.MAGENTA, Color.PINK,
        Color.WHITE, Color.GRAY, Color.BLACK, 
        new Color(255, 105, 180), // Hot Pink
        new Color(128, 0, 128),   // Purple
        new Color(255, 165, 0),   // Orange
        new Color(0, 128, 128),   // Teal
        new Color(128, 128, 0),   // Olive
        new Color(0, 0, 128),     // Navy
        new Color(139, 69, 19),   // Brown
        new Color(255, 215, 0),   // Gold
        new Color(72, 209, 204),  // Medium Turquoise
        // Adicione mais cores personalizadas conforme necessário
        new Color(255, 69, 0),    // Red-Orange
        new Color(135, 206, 250)  // Light Sky Blue
    );

    public static Color4f getColor(int player,float life) {
        // Usa o índice do jogador para selecionar uma cor da lista
        Color color = COLORS.get(player % COLORS.size());

        // Converte a cor de java.awt.Color para Color4f
        float red = color.getRed() / 255.0f;
        float green = color.getGreen() / 255.0f;
        float blue = color.getBlue() / 255.0f;
        float alpha = Math.clamp(life, 0.4f, 1f); // Opacidade fixa

        return new Color4f(red, green, blue, alpha);
    }
}
