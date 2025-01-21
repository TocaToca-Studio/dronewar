package tocaterrain;

import java.awt.Component;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane; 
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.lwjgl.input.Keyboard;
import choke3d.App;
import choke3d.gl.FPSCamera;
  
/**
 *
 * @author tocatoca
 */
public class TocaTerrain extends App {  
    public TerrainEditor editor=new TerrainEditor();
    public Heightmap heightmap= new Heightmap(128,128);
    File imported_hmap_file = null;
    File imported_texture_file = null;
    Component context = null;
    Painel painel=new Painel();
    public void import_heightmap() { 
        try {
            // Criar o seletor de arquivos
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecione o arquivo do heightmap");
                // Use FileNameExtensionFilter for image files
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Image Files (*.jpg, *.jpeg, *.png, *.gif, *.bmp)",
                    "jpg", "jpeg", "png", "gif", "bmp");
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(context);

            if (result == JFileChooser.APPROVE_OPTION) {
                // Obter o arquivo selecionado
                File imageFile = fileChooser.getSelectedFile();
                
                heightmap.load(ImageIO.read(imageFile)); 
                imported_hmap_file=imageFile;
                JOptionPane.showMessageDialog(context, "Heightmap carregado! Dimensões: " 
                    + heightmap.heightmap.length + "x" + heightmap.heightmap[0].length);
                
                painel.load_data();
                // Informar o usuário
            } else {
                JOptionPane.showMessageDialog(context, "Nenhum arquivo foi selecionado.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(context, "Erro ao carregar o heightmap: " + e.getMessage());
        } 
    }
    public void reload() { 
        try {  
            if(imported_hmap_file!=null) heightmap.load(ImageIO.read(imported_hmap_file));  
            if(imported_texture_file!=null)  heightmap.texture.load(ImageIO.read(imported_texture_file));  
            
            painel.load_data();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(context, "Erro ao recarregar o heightmap: " + ex.getMessage());
        } 
    }
    
    public void import_texture() {
        try {
            // Criar o seletor de arquivos
            JFileChooser fileChooser = new JFileChooser();   
            // Use FileNameExtensionFilter for image files
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Image Files (*.jpg, *.jpeg, *.png, *.gif, *.bmp)",
                    "jpg", "jpeg", "png", "gif", "bmp");
            fileChooser.setFileFilter(filter);

            fileChooser.setDialogTitle("Selecione o arquivo de textura");

            int result = fileChooser.showOpenDialog(context);

            if (result == JFileChooser.APPROVE_OPTION) {
                // Obter o arquivo selecionado
                File imageFile = fileChooser.getSelectedFile();
                heightmap.texture.load(ImageIO.read(imageFile));
                imported_texture_file=imageFile;
                heightmap.draw_terrain=true;
                heightmap.colored_terrain=false;
                painel.load_data();
                JOptionPane.showMessageDialog(context, "textura carregada!");
 
                // Informar o usuário
            } else {
                JOptionPane.showMessageDialog(context, "Nenhum arquivo foi selecionado.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(context, "Erro ao carregar o heightmap: " + e.getMessage());
        } 
    }
    public boolean create_new=false;
    public int new_width=128;
    public int new_length=128;
    public void create_new_project() {
         // Criação do painel para entrada de dados
        JPanel panel = new JPanel();
        
        // Campos para entrada de largura e comprimento
        JTextField widthField = new JTextField(5);
        JTextField heightField = new JTextField(5);
        
        panel.add(new JLabel("Largura:"));
        panel.add(widthField);
        panel.add(Box.createHorizontalStrut(15)); // Espaço entre os campos
        panel.add(new JLabel("Comprimento:"));
        panel.add(heightField);

        // Exibe o diálogo
        int result = JOptionPane.showConfirmDialog(context, panel, 
            "Insira as dimensões do mapa", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                // Obtém os valores e converte para inteiros
                new_width = Integer.parseInt(widthField.getText());
                new_length = Integer.parseInt(heightField.getText());
                create_new=true;
                
            } catch (NumberFormatException e) {
                // Trata entradas inválidas
                JOptionPane.showMessageDialog(context, "Por favor, insira números válidos!", 
                                              "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Operação cancelada.");
        } 
    }
    boolean do_import_texture=false;
    boolean do_reload=false;
    public void run() {
       painel.setVisible(true);
       painel.app=this;
       context=painel;
       init();
       painel.load_data();
       while(running && painel.isVisible()) {
           if(do_import_texture) {
               do_import_texture=false;
               import_texture();
           }
           if(do_reload) {
               reload();
               do_reload=false;
           }
           if(create_new) {
               destroy();
               init();
               heightmap=new Heightmap(new_width,new_length);
               painel.load_data();
               create_new=false;
           }
           if(Keyboard.isKeyDown(Keyboard.KEY_F1)) {
               import_heightmap();
           }
           if(Keyboard.isKeyDown(Keyboard.KEY_F2)) {
              import_texture();
           }
           if(Keyboard.isKeyDown(Keyboard.KEY_F5)) {
               reload();
           }
           update();
           heightmap.draw();
       }
       destroy();
       painel.setVisible(false);
       System.exit(0);
    }
    public void init() {
        super.init();
        camera=new FPSCamera();
    }
     
    public static String exportToOBJ(float[][] heightmap) {
        StringBuilder obj = new StringBuilder();

        int rows = heightmap.length;
        int cols = heightmap[0].length;

        // Adicionar vértices
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                obj.append(String.format("v %f %f %f%n", (float) j, heightmap[i][j], (float) i));
            }
        }

        // Adicionar faces
        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < cols - 1; j++) {
                int v1 = i * cols + j + 1;
                int v2 = v1 + 1;
                int v3 = v1 + cols;
                int v4 = v3 + 1;

                //obj.append(String.format("f %d %d %d%n", v1, v3, v2));
                //obj.append(String.format("f %d %d %d%n", v2, v3, v4));
                obj.append(String.format("f %d %d %d%n", v2, v1, v4));
                obj.append(String.format("f %d %d %d%n", v4, v1, v3));
                
            }
        }

        return obj.toString();
    }
    public void export() {
           // Cria o JFileChooser para permitir ao usuário escolher onde salvar o arquivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export terrain OBJ");
        
        // Filtra para que apenas arquivos .txt possam ser selecionados
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Wavefront OBJ (.obj)", "obj"));

        // Abre a janela de salvar arquivo
        int escolha = fileChooser.showSaveDialog(null);

        if (escolha == JFileChooser.APPROVE_OPTION) {
            // Obtém o arquivo selecionado
            File arquivo = fileChooser.getSelectedFile();

            // Se o arquivo não tiver a extensão .txt, adiciona-a
            if (!arquivo.getName().endsWith(".obj")) {
                arquivo = new File(arquivo.getAbsolutePath() + ".obj");
            }

            // Tenta salvar a string no arquivo
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
                writer.write(exportToOBJ(heightmap.heightmap));
                JOptionPane.showMessageDialog(context, "Exported!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(context, "Error when exporting: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public static void main(String[] argv) {
       ThemeDetection.setLookAndFeel();
       new Thread(() -> { 
            (new TocaTerrain()).run(); 
       }).start(); 
    } 
    
}
