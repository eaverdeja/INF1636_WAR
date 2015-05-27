package view;


import controller.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Label;

import java.awt.image.BufferedImage;
import javax.swing.*;

public class MainFrame extends JFrame{
    public final int DEF_WIDTH = 1000;
    public final int DEF_HEIGHT = 1000;
    
    private Mapa mapPanel;
    private JLabel label;
    private JTextField textField;
    private BufferedImage image;
    
    public MainFrame(){
        setSize(DEF_WIDTH,DEF_HEIGHT);
        setLocation(100,100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createMap();
        createLabel();
     }
    
    private void createMap(){
        
        //Load map
        mapPanel = new Mapa();
       
        //Add map to pane
        getContentPane().add(mapPanel);
        
    }
    
    private void createLabel(){
        label = new JLabel("Digite o numero de jogadores! (3-6)",JLabel.CENTER);
        label.setPreferredSize(new Dimension(400, 100));
        label.setLocation(0, 0);
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        label.setLabelFor(textField);
        mapPanel.add(label, BorderLayout.CENTER);
    }
    
    private void createTextField(){
        // No momento um text field é criado fora de lugar se adicionar essa função ao init
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(100,80));
        mapPanel.add(textField, BorderLayout.CENTER);
    }
}
