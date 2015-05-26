package view;


import controller.*;

import java.awt.image.BufferedImage;
import javax.swing.*;

public class MainFrame extends JFrame{
    public final int DEF_WIDTH = 1000;
    public final int DEF_HEIGHT = 1000;
    
    private BufferedImage image;
    
    public MainFrame(){
        setSize(DEF_WIDTH,DEF_HEIGHT);
        setLocation(100,100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        createMap();
    }
    
    private void createMap(){
        
        //Load map
        Mapa MapPanel = new Mapa();
       
        //Add map to pane
        getContentPane().add(MapPanel);
        
    }
}
