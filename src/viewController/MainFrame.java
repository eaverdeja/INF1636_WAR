package viewController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

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
        turnController = Turn.getInstance();
        turnController.createAndRadomizePlayers(players);
        
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
        
        //Define territories
        mapPanel.defineTerritories();
        
        //Add map to pane
        getContentPane().add(mapPanel);
    }
    
    private void createNextTurn(){
        nextTurn = new JButton("Next turn!");
        nextTurn.setAlignmentY(TOP_ALIGNMENT);
        nextTurn.setPreferredSize(new Dimension(20,20));
        mapPanel.setLayout(null);
        mapPanel.add(nextTurn);
        nextTurn.setBounds(DEF_WIDTH - 120, 30, 100, 30);
        
        nextTurn.addActionListener((ActionEvent e) -> {
            try {
                Turn.getInstance().nextTurn();
                repaint();
            }
            catch (Exception ex){
                System.out.println("Erro ao passar de turno" + ex.getMessage());   
            }
        });
    }

    private void createRollButton(){
        rollButton = new JButton("Roll Dices!");
        
        rollButton.setAlignmentY(TOP_ALIGNMENT);
        rollButton.setPreferredSize(new Dimension(20,20));
        mapPanel.setLayout(null);
        mapPanel.add(rollButton);
        rollButton.setBounds(DEF_WIDTH - 250, 30, 100, 30);
        
        rollButton.addActionListener((ActionEvent e) -> {
            try {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(new DicePanel());
                frame.pack();
                frame.setVisible(true);
            }
            catch (Exception ex){
                System.out.println("Erro ao rolar os dados" + ex.getMessage());   
            }
        });
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
