package view;

import controller.GameManager;

import java.util.List;
import java.util.Map;
import javax.swing.*;
import model.Territory;

public class MainFrame extends JFrame{
   
    private final int DEF_WIDTH = 960;
    private final int DEF_HEIGHT = 760;
    private GameManager gameManager;
    private Console consoleController;
    private final int players;
    
    private Map<Territory,List<Territory>> neighbourMap;
    
    public MainFrame(int players){
        
        setSize(DEF_WIDTH,DEF_HEIGHT);
        setResizable(false);
        setLocation(0,0);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //Define number of players and turn scheme
        this.players = players;
        gameManager = GameManager.getInstance();
        gameManager.setMainFrame(this);
        gameManager.gameSetUp(players);
        createConsole();
        consoleController = Console.getInstance();
    }
    

    
    
    private void createConsole(){
        //Add the console to the turnController obsList
        gameManager.addObserver(Console.getInstance());
        
        Console.getInstance().setBounds(124, 614, 266, 82);
        gameManager.getMapPanel().setLayout(null);
        gameManager.getMapPanel().add(Console.getInstance());
        repaint();
    }
    
}
