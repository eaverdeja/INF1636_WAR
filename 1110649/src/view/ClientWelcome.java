package view;

import javax.swing.*;

import network.Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import static java.awt.Component.BOTTOM_ALIGNMENT;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClientWelcome extends JFrame{
	Client client;
	String ip;
	
    public final int DEF_WIDTH = 455;
    public final int DEF_HEIGHT = 720;
    
    private Welcome welcomePanel;
    private JLabel label;
    private JTextField textField;
    private JPanel box;
    
    public ClientWelcome(){
        setSize(DEF_WIDTH, DEF_HEIGHT);
        setLocation(0,0);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //load welcome screen
        welcomePanel = new Welcome();
        welcomePanel.setLayout(new BorderLayout());
        
        //add welcome to pane
        getContentPane().add(welcomePanel);
        
        //creating text field
        createBox();
        createRadioOptions();
        createTextField();
    }
    
    private void createBox(){
        
        box = new JPanel();
        box.setAlignmentY(BOTTOM_ALIGNMENT);
        box.setPreferredSize(new Dimension(400, 80));
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBackground(Color.WHITE);
        box.setOpaque(true);
        welcomePanel.add(box, BorderLayout.PAGE_END);
    }
    
    private void createRadioOptions(){
    	
    	label = new JLabel("Digite o ip do servidor",JLabel.CENTER);
        label.setPreferredSize(new Dimension(200, 100));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBackground(Color.WHITE);
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        box.add(label, BorderLayout.CENTER);
        
    }
    
    
    private void createTextField(){
        
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(100,80));
        textField.addActionListener((ActionEvent e) -> {
        
            try {
                ip = textField.getText();
                client = Client.getInstance();
                if (client.connect(ip)) {
                	//Feedback
                    JOptionPane.showMessageDialog(null,"Conectado!");
                	box.remove(textField);
                	
                	//Start game
                    createStartGame();
                    
                    /*
                    label.setVisible(false);

                    //Start the game
                    this.setVisible(false);
                    MainFrame mf = new MainFrame();
                    mf.setTitle("War!");
                    mf.setVisible(true);
                    */

                } else {
                    textField.setText(null);
                    JOptionPane.showMessageDialog(null, "Digite um ip válido!");
                }
            }
            catch (NumberFormatException ne) {
                System.out.println("NaN");   
            } catch (IOException ex) {
            	ex.printStackTrace();
            }
        });
        
        box.add(textField);
    }
    
    private void createStartGame() throws IOException {
    	JCheckBox startGame = new JCheckBox();
    	startGame.setText("Começar!");
    	
    	startGame.addActionListener((ActionEvent e) -> {
            try {
				Client.getInstance().sendMessage("StartGame");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
    	});
    	
    	
    	box.add(startGame, BorderLayout.NORTH);
    	
    	startGame.setVisible(true);
    	welcomePanel.revalidate();
    	welcomePanel.repaint();
    }
}
