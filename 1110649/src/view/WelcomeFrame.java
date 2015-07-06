package view;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.BOTTOM_ALIGNMENT;
import java.awt.Dimension;
import java.awt.event.ActionEvent;


public class WelcomeFrame extends JFrame{
    public final int DEF_WIDTH = 455;
    public final int DEF_HEIGHT = 720;
    
    private Welcome welcomePanel;
    private JLabel label;
    private JTextField textField;
    private JPanel box;
    
    public WelcomeFrame(){
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
        createLabel();
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
    
    private void createLabel(){
        
        label = new JLabel("Digite o numero de jogadores! (3-6)",JLabel.CENTER);
        label.setPreferredSize(new Dimension(200, 100));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        box.add(label, BorderLayout.CENTER);
    }
    
    private void createTextField(){
        
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(100,80));
        textField.addActionListener((ActionEvent e) -> {
        
            //How many people are playing?
            try {
                int input = Integer.parseInt(textField.getText());
                if (input >= 3 && input <= 6) {
                    System.out.println("Ok");
                    textField.setVisible(false);
                    label.setVisible(false);

                    //Start the game
                    this.setVisible(false);
                    MainFrame mf = new MainFrame(input);
                    mf.setTitle("War!");
                    mf.setVisible(true);

                } else {
                    textField.setText(null);
                    JOptionPane.showMessageDialog(null,"Escolha de 3 a 6 jogadores!");
                }
            }
            catch (NumberFormatException ne) {
                System.out.println("NaN");   
            }
        });
        
        box.add(textField);
    }
}
