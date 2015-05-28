package view;


import controller.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;
import javax.swing.*;

public class MainFrame extends JFrame{
    public final int DEF_WIDTH = 1000;
    public final int DEF_HEIGHT = 1000;
    
    private Mapa mapPanel;
    private JLabel label;
    private JTextField textField;
    private JPanel box;
    private BufferedImage image;
    private JButton rollButton;
    private JPanel dicesPanel;
    private JLabel diceOne;
    private JLabel diceTwo;
    private JLabel diceThree;
    private Turn turnController;
    
    public MainFrame(){
        setSize(DEF_WIDTH,DEF_HEIGHT);
        setLocation(100,100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createMap();
        createBox();
        createLabel();
        createTextField();
     }
    
    private void createMap(){
        
        //Load map
        mapPanel = new Mapa();
        mapPanel.setLayout(new BorderLayout());
        //Add map to pane
        getContentPane().add(mapPanel);
        
    }
    
    private void createBox(){
        
        box = new JPanel();
        box.setAlignmentY(BOTTOM_ALIGNMENT);
        box.setPreferredSize(new Dimension(400, 80));
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBackground(Color.WHITE);
        box.setOpaque(true);
        mapPanel.add(box, BorderLayout.PAGE_END);
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
        try {
            int input = Integer.parseInt(textField.getText());
            if (input >= 3 && input <= 6) {
                System.out.println("Ok");
                textField.setVisible(false);
                label.setVisible(false);
                createRollButton();
                createDices();
                turnController = new Turn(input);
            } else {
                System.out.println("Entre 3 e 6");
             
            }
            }
            catch (NumberFormatException ne) {
            System.out.println("NaN");   
            }
            
        });
        box.add(textField);
    }
    
    private void createRollButton(){
           
        rollButton = new JButton("ROLL DICES!");
        box.add(rollButton,BorderLayout.CENTER);
    }
    
    private void createDices(){
           
        box.setLayout(new FlowLayout());

        diceOne = new JLabel("");
        diceOne.setBackground(Color.red);
        diceOne.setOpaque(true);
        diceOne.setPreferredSize(new Dimension(50,50));
        box.add(diceOne);
        
        diceTwo = new JLabel("");
        diceTwo.setBackground(Color.red);
        diceTwo.setOpaque(true);
        diceTwo.setPreferredSize(new Dimension(50,50));
        box.add(diceTwo);
        
        diceThree = new JLabel("");
        diceThree.setBackground(Color.red);
        diceThree.setPreferredSize(new Dimension(50,50));
        diceThree.setOpaque(true);
        box.add(diceThree);
        
        box.repaint();
    }
}
