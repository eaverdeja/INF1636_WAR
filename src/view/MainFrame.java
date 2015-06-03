package view;

import controller.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.image.BufferedImage;
import javax.swing.*;

public class MainFrame extends JFrame{
    public final int DEF_WIDTH = 960;
    public final int DEF_HEIGHT = 760;
    
    private MapPanel mapPanel;
    private BufferedImage image;
    private JButton rollButton;
    private JLabel diceOne;
    private JLabel diceTwo;
    private JLabel diceThree;
    private JButton nextTurn;
    private Turn turnController;
    private final int players;
    private JPanel box;
    
    public MainFrame(int players){
        
        setSize(DEF_WIDTH,DEF_HEIGHT);
        setResizable(false);
        setLocation(0,0);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //Define number of players and turn scheme
        this.players = players;
        turnController = new Turn(players);
        
        //Create map
        createMap();
        createBox();
        createRollButton();
        createDices();
        createNextTurn();
        
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
        mapPanel = new MapPanel(turnController.getPlayers());
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
    
    private void createNextTurn(){
        nextTurn = new JButton("Next turn");
        nextTurn.setAlignmentY(TOP_ALIGNMENT);
        nextTurn.setPreferredSize(new Dimension(20,20));
        mapPanel.setLayout(null);
        mapPanel.add(nextTurn);
        nextTurn.setBounds(DEF_WIDTH - 120, 30, 100, 30);
        
        
    }
    
}
