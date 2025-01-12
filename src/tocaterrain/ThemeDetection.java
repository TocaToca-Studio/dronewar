package tocaterrain;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ThemeDetection {
 
    public static void setLookAndFeel() {
        try {
            // Detectando o tema do sistema
            String lookAndFeel = UIManager.getSystemLookAndFeelClassName();

            // Aplicando o Look and Feel padrão do sistema
            UIManager.setLookAndFeel(lookAndFeel);

            // Alternativa para verificar se o sistema está em modo escuro ou claro
            // Isso pode ser feito em sistemas como macOS, ou por meio de propriedades
            if (isDarkMode()) {
                // Caso o modo escuro seja detectado, configure um tema escuro
                UIManager.put("control", new Color(43, 43, 43)); // Cor de fundo mais escura
                UIManager.put("text", Color.white); // Texto branco para o modo escuro
            }

            
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // Método de detecção de modo escuro para alguns sistemas
    private static boolean isDarkMode() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac")) {
            // No macOS, podemos usar a propriedade Apple para verificar o modo
            try {
                String theme = System.getProperty("apple.awt.application.appearance");
                return theme != null && theme.equals("dark");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (os.contains("win")) {
            // No Windows 10+, você pode verificar o registro ou outro método
            try {
                File file = new File(System.getenv("USERPROFILE") + "\\AppData\\Local\\Packages\\Microsoft.Windows.10.Home_8wekyb3d8bbwe\\LocalState");
                return file.exists();  // Um exemplo de verificar algum arquivo de configuração
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Para outras plataformas, um método alternativo pode ser usado
        return false;
    }
}
