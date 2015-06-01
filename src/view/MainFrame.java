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
    public final int DEF_WIDTH = 960;
    public final int DEF_HEIGHT = 760;
    
    private Mapa mapPanel;
    private BufferedImage image;
    private JButton rollButton;
    private JPanel dicesPanel;
    private JLabel diceOne;
    private JLabel diceTwo;
    private JLabel diceThree;
    private Turn turnController;
    private final int players;
    private JPanel box;
    
    public MainFrame(int players){
        setSize(DEF_WIDTH,DEF_HEIGHT);
        setLocation(0,0);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.players = players;
        createMap();
        
        //createRollButton();
        //createDices();
        //turnController = new Turn(players);
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
    
    private void createMap(){
        
        //Load map
        mapPanel = new Mapa();
        mapPanel.setLayout(new BorderLayout());
        //Add map to pane
        getContentPane().add(mapPanel);
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
