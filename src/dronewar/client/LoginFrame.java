package dronewar.client;

import dronewar.server.Server;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author tocatoca
 */
public class LoginFrame extends JFrame { 
    JTextField ipField;
    JTextField nickField;
    public void conectar() {
        String ip = ipField.getText();
        String nick = nickField.getText();

        // Aqui você pode adicionar o código para conectar ao servidor com o nick e senha
        System.out.println("Tentando login com o IP: " + ip + " e Nick: " + nick);
        if(client.packetHandler.connect(nick, ip, 25532)) {
            //JOptionPane.showMessageDialog(this,"Conectado!");
            setVisible(false);
            client.run();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao conectar!", "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }
    DroneWarClient client=new DroneWarClient();
    LoginFrame() {
        super("Tela de login");
        // Criando o JFrame 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 250); // Tamanho maior para melhor disposição
        setLocationRelativeTo(null); // Centraliza a janela na tela

        // Definindo um layout para a janela
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // Faz os campos de texto ocuparem toda a largura

        // Definindo um painel de fundo
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 240, 240)); // Cor de fundo mais suave
        panel.setLayout(new GridBagLayout());

        // Criando o campo para a senha
        JLabel ipLabel = new JLabel("IP do Servidor:");
        ipLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ipField = new JTextField(255);
        ipField.setFont(new Font("Arial", Font.PLAIN, 14));
        ipField.setText("127.0.0.1");

        // Criando o campo para o nome (nick)
        JLabel nickLabel = new JLabel("Nick:");
        nickLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nickField = new JTextField(20);
        nickField.setFont(new Font("Arial", Font.PLAIN, 14));
        nickField.setText("Choke");

        // Criando o botão para iniciar o jogo
        JButton loginButton = new JButton("Iniciar Jogo");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(34, 139, 34)); // Cor verde para o botão
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false); // Remove a borda de foco do botão
        loginButton.setPreferredSize(new Dimension(150, 40)); // Tamanho maior para o botão

        // Adicionando os componentes ao painel com o GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nickLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nickField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(ipLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(ipField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(loginButton, gbc);

        // Adicionando o painel à janela principal
        add(panel);

        // Definindo a ação do botão
        loginButton.addActionListener((ActionEvent e) -> {
           conectar();
        });
        //conectar();
        // Tornar a janela visível
        setVisible(true);
    }
    public static void main(String[] args) {
        (new Thread(()->{
            Server server=new Server();
            server.run();
        })).start();
        (new LoginFrame()).setVisible(true);
    }
}
