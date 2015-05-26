package view;

import com.sun.glass.ui.Size;
import controller.MapPanel;
import model.Territory;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import java.util.List;
import java.util.Map;

public class MainFrame extends JFrame{
    public final int DEF_WIDTH = 1000;
    public final int DEF_HEIGHT = 1000;
    
    private BufferedImage image;
    private List<Territory> countries;
    
    public MainFrame(){
        setSize(DEF_WIDTH,DEF_HEIGHT);
        setLocation(100,100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        createMap();
    }
    
    private void createMap(){
        
        //Load map
        MapPanel Map = new MapPanel();
        
        defineTerritories();
        mapTerritories(Map);
        
        //Add map to pane
        getContentPane().add(Map);
        
    }
    
    private void mapTerritories(MapPanel Map){
        for(Territory t : countries){

            //Listen for click
            Map.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e){
                    float Xcoord = e.getPoint().x;
                    float Ycoord = e.getPoint().y;

                    //DEBUG
                    System.out.println(e.getPoint());

                    for(Rectangle r : t.getBorders()){
                        if(r.contains(Xcoord,Ycoord)){
                            System.out.println(t.getName());
                        }
                    }
                }
            });
        }
    }
    
    public void defineTerritories(){
        countries = new ArrayList<>();
        
        
        //START Coreia do Sul
        Territory CoreiaSul = new Territory("Coreia do sul");

        //Defining borders
        List<Rectangle> borders = new ArrayList<>();
        Rectangle r1 = new Rectangle(80,20);
        r1.setLocation(775,270);
        borders.add(r1);
        
        CoreiaSul.setBorders(borders);
        
        countries.add(CoreiaSul);
        //END Coreia do Sul
    }
}
